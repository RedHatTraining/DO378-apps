package com.redhat.training.expense;

import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@Mock
@ApplicationScoped
public class ExpenseServiceMock extends ExpenseService {
    @Override
    public boolean exists(UUID uuid) {
        return !uuid.equals(UUID.fromString(CrudTest.NON_EXISTING_UUID));
    }
}
