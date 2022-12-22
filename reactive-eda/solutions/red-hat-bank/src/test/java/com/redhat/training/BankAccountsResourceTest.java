package com.redhat.training;

import com.redhat.training.model.BankAccount;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankAccountsResourceTest {
    @Test
    public void listOfAccountsReturnsRecords() {
        PanacheMock.mock(BankAccount.class);
        BankAccount bankAccount = new BankAccount(123L, "regular");
        Mockito.when(BankAccount.listAll(Mockito.any())).thenReturn(
                Uni.createFrom().item(List.of(bankAccount))
        );

        given()
        .when()
            .get("/accounts")
        .then()
            .statusCode(200)
            .body("$.size()", is(1));
    }
}
