package com.redhat.training.expenses;

import org.junit.jupiter.api.Test;
import com.redhat.training.expenses.Expense.PaymentMethod;
import io.quarkus.test.junit.QuarkusTest;

import java.net.URI;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import io.restassured.http.ContentType;

@QuarkusTest
public class ExpenseCreationTest {

    @TestHTTPResource
    @TestHTTPEndpoint( ExpenseResource.class )
    URI endpoint;

    @Test
    public void testCreateExpense() {
        given()
                .body( Expense.of( "Test Expense", PaymentMethod.CASH, "1234" ) )
                .contentType( ContentType.JSON )
                .post( endpoint );

        when()
                .get( endpoint )
                .then()
                .statusCode( 200 )
                .assertThat()
                .body( "size()", is( 1 ) )
                .body(
                        containsString( "\"name\":\"Test Expense\"" ),
                        containsString( "\"paymentMethod\":\"" + PaymentMethod.CASH + "\"" ),
                        containsString( "\"amount\":1234.0" ) );

    }

}