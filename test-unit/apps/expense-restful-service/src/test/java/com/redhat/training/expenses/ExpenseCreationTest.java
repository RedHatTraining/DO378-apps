package com.redhat.training.expenses;

import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import com.redhat.training.expenses.Expense.PaymentMethod;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExpenseCreationTest {

    @Test
    public void testCreateExpense() {
        org.junit.jupiter.api.Assertions.fail();
    }

}