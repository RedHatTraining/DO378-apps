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
@TestHTTPEndpoint( ExpenseResource.class )
public class ExpenseCreationTest {

    @Test
    public void testCreateExpense() {
        given()
                .body( Expense.of( "Test Expense", PaymentMethod.CASH, "1234" ) )
                .contentType( ContentType.JSON )
                .post();

        when()
                .get()
        .then()
                .statusCode( 200 )
                .assertThat()
                .body( "size()", is( 1 ) )
                .body( "[0].name", is( "Test Expense" ) )
                .body( "[0].paymentMethod", is( PaymentMethod.CASH.name() ) )
                .body( "[0].amount", is( 1234.0F ) );

    }

}
