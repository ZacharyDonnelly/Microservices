package com.zachdonnelly.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
					 "name": "Vue Web Starter v3",
				     "description": "Starter for a Vue web application with all the goodies.",
				     "category": "Starters",
				     "quantity": 10000,
				     "price": 99.99
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/products")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Vue Web Starter v3"))
				.body("description", Matchers.equalTo("Starter for a Vue web application with all the goodies."))
				.body("category", Matchers.equalTo("Starters"))
				.body("quantity", Matchers.equalTo(10000))
				.body("price", Matchers.equalTo(99.99f));
	}

}
