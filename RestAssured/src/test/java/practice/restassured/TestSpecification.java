package practice.restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import practice.restassured.secure.SecureData;

public abstract class TestSpecification {

  @BeforeAll
  static void init() {
    RestAssured.requestSpecification = new RequestSpecBuilder()
        .setBaseUri("http://localhost:8080/api")
        .addHeader("Content-type", "application/json")
        .addHeader("Application", "application/json")
        .addHeader("Authorization", "Bearer " + SecureData.getToken())
        .build();
  }

  @BeforeEach
  void logBeforeEach(TestInfo testInfo) {
    System.out.println("⏳ [START] " + testInfo.getDisplayName());
  }

  @AfterEach
  void logAfterEach(TestInfo testInfo) {
    System.out.println("✅ [FINISH] " + testInfo.getDisplayName());
    System.out.println("--------------------------------------------------");
  }

  protected String getLeaderId() {
    return RestAssured
        .given()
        .when()
          .get("/users/me")
        .then()
          .statusCode(200)
          .extract().jsonPath().getString("id");
  }
}
