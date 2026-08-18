package practice.restassured.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import practice.restassured.api.TaskCommentApi;
import practice.restassured.api.ProjectApi;
import practice.restassured.api.TaskApi;
import practice.restassured.api.UserApi;
import practice.restassured.specifications.Specifications;

public abstract class BaseTest {
  protected static UserApi userApi;
  protected static TaskApi taskApi;
  protected static ProjectApi projectApi;
  protected static TaskCommentApi taskCommentApi;

  @BeforeAll
  public static void setupTests() {
    userApi = new UserApi();
    taskApi = new TaskApi();
    projectApi = new ProjectApi();
    taskCommentApi = new TaskCommentApi();

    RestAssured.requestSpecification = Specifications.reqSpec();
    RestAssured.filters(new AllureRestAssured());
  }
}
