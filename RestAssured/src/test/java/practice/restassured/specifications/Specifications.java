package practice.restassured.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.github.cdimascio.dotenv.Dotenv;

public class Specifications {
  private static final Dotenv dotenv = Dotenv.load();

  public static RequestSpecification reqSpec() {
    return new RequestSpecBuilder()
        .setBaseUri(dotenv.get("API_URI"))
        .log(LogDetail.ALL)
        .addHeader("Authorization", "Bearer " + dotenv.get("API_TOKEN"))
        .addHeader("Content-Type", "application/json")
        .build();
  }

  public static ResponseSpecification response200() {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .build();
  }

  public static ResponseSpecification response400() {
    return new ResponseSpecBuilder()
        .expectStatusCode(400)
        .build();
  }

  public static ResponseSpecification response404() {
    return new ResponseSpecBuilder()
        .expectStatusCode(404)
        .build();
  }
}
