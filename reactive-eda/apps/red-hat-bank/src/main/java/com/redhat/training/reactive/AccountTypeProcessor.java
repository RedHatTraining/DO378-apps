package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.model.BankAccount;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

@ApplicationScoped
public class AccountTypeProcessor {
    private static final Logger LOGGER = Logger.getLogger(AccountTypeProcessor.class);

    @Inject
    Mutiny.Session session;

    public String calculateAccountType(Long balance) {
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
