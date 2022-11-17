package com.redhat.training.expense;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PanacheMockTest {

    @BeforeAll
    public static void setup() {
        PanacheMock.mock(Expense.class);
    }

    @Test
    public void listOfExpensesReturnsAnEmptyList() {
        Mockito.when(Expense.listAll()).thenReturn(Collections.emptyList());

        given()
        .when()
            .get("/expenses")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }
}
