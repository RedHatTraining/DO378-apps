package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.model.BankAccount;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountTypeProcessor {
    private static final Logger LOGGER = Logger.getLogger(AccountTypeProcessor.class);

    @Inject
    Mutiny.Session session;

    @Incoming("new-bank-accounts-in")
    @ActivateRequestContext
    public Uni<Void> processNewBankAccountEvents(BankAccountWasCreated event) {
        String assignedAccountType = calculateAccountType(event.balance);

        logEvent(event, assignedAccountType);

        return session.withTransaction(
            t -> BankAccount.<BankAccount>findById(event.id)
                .onItem()
                .ifNotNull()
                .invoke(
                    entity -> entity.type = assignedAccountType
                ).replaceWithVoid()
        ).onTermination().call(() -> session.close());
    }

    public String calculateAccountType(Long balance) {
        return balance >= 100000 ? "premium" : "regular";
    }

    private void logEvent(BankAccountWasCreated event, String assignedType) {
        LOGGER.infov(
                "Processing BankAccountWasCreated - ID: {0} Balance: {1} Type: {2}",
                event.id,
                event.balance,
                assignedType
        );
    }
}
