package practice.restassured.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import practice.restassured.secure.SecureData;

public class Specifications {

  public static RequestSpecification reqSpec() {
    return new RequestSpecBuilder()
        .setBaseUri(SecureData.getURI())
        .log(LogDetail.ALL)
        .addHeader("Authorization", "Bearer " + SecureData.getToken())
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
