package com.redhat.training.expenses;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import com.redhat.training.expenses.Expense.PaymentMethod;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class ExpenseResourceTest {

    @Test
    public void testCreateExpense() {
        given()
                .body( Expense.of( "Test Expense", PaymentMethod.CASH, "1234" ) )
                .contentType( ContentType.JSON )
                .post( "/expenses" );

        when()
                .get( "/expenses" )
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