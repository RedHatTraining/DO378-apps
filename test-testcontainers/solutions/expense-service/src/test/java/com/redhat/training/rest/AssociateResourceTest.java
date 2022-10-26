package com.redhat.training.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.redhat.training.model.Associate;

@QuarkusTest
@TestHTTPEndpoint( AssociateResource.class )
@WithPostgresDB( name = "tc-test", username = "tc-user", password = "tc-pass")
public class AssociateResourceTest {

	@Test
	public void testListAllEndpoint() {
		Associate[] associates = given()
				.when().get()
				.then()
					.statusCode( 200 )
					.extract()
					.as( Associate[].class );
		assertThat( associates ).hasSize(2);
	}
}
