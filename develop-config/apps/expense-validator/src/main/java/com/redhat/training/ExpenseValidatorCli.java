package com.redhat.training;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.inject.Inject;

@QuarkusMain
public class ExpenseValidatorCli implements QuarkusApplication {
    @Inject
    ExpenseValidator validator;

    @Override
    public int run(String... args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("The command requires 1 argument");
        }

        try {
            int amountValue = Integer.parseInt(args[0]);

            if (validator.isValidAmount(amountValue)) {
                System.out.println("Valid amount: " + amountValue);

                return 0;
            }

            System.out.println("Invalid amount: " + amountValue);

            return 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The argument must be an integer");
        }
    }
}
