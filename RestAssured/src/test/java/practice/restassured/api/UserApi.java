package practice.restassured.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import practice.restassured.pojo.UserReq;

public class UserApi {

  public Response createUser(String login, String fullName, String email, String password) {
    UserReq req;
    if (password == null) {
      req = new UserReq(login, fullName, email);
    } else {
      req = new UserReq(login, fullName, email, password);
    }

    return RestAssured
        .given().body(req)
        .when().post("/users?fields=id,login,fullName,email");
  }

  public Response createUser(String login, String fullName, String email) {
    return createUser(login, fullName, email, null);
  }

  public Response getAllUsers() {
    return RestAssured
        .given()
        .when().get("/users?fields=id,login,fullName,email");
  }

  public Response getUserById(String userId) {
    return RestAssured
        .given()
        .when().get("/users/{userId}/?fields=id,login,fullName,email", userId);
  }

  public Response updateUser(String userId, String login, String fullName, String email) {
    UserReq req = new UserReq(login, fullName, email);
    return RestAssured
        .given().body(req)
        .when().post("/users/{userId}/?fields=id,login,fullName,email", userId);
  }

  public Response banUser(String userId) {
    String jsonBody = "{\"banned\": true}";
    return RestAssured
        .given().body(jsonBody)
        .when().post("/users/{userId}?fields=id,login,banned", userId);
  }

  public Response unbanUser(String userId) {
    String jsonBody = "{\"banned\": false}";
    return RestAssured
        .given().body(jsonBody)
        .when().post("/users/{userId}?fields=id,login,banned", userId);
  }

  public Response getUserByLoginAndFullName(String login, String fullName) {
    return RestAssured.given()
        .queryParam("fields", "login,fullName")
        .when().get("/users/me");
  }

  public Response deleteUser(String deleteUserId, String successorUserId) {
    return RestAssured
        .given().queryParam("successor", successorUserId)
        .when().delete("/users/{deleteUserId}", deleteUserId);
  }
}
