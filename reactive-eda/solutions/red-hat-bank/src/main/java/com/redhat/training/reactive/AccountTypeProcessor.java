package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.model.BankAccount;
import io.quarkus.hibernate.reactive.panache.Panache;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountTypeProcessor {
    private static final Logger LOGGER = Logger.getLogger(AccountTypeProcessor.class);

    @Incoming("new-bank-account")
    public void processNewBankAccountEvents(Message<BankAccountWasCreated> message) {
        BankAccountWasCreated event = message.getPayload();
        String assignedAccountType = event.balance >= 100000 ? "premium" : "regular";

        logEvent(event, assignedAccountType);

        Panache.withTransaction(
                () -> BankAccount
                        .<BankAccount> findById(event.id)
                        .onItem()
                        .ifNotNull()
                        .invoke(entity -> entity.type = assignedAccountType)
        );
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
