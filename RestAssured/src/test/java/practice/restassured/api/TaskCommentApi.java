package practice.restassured.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import practice.restassured.pojo.TaskCommentReq;

public class TaskCommentApi {

  public Response createTaskComment(String taskId, String text) {
    TaskCommentReq taskCommentReq = new TaskCommentReq(text);
    return RestAssured
        .given().body(taskCommentReq)
        .when().post("/issues/{{taskId}}/comments?fields=id,text,created", taskId);
  }

  public Response getAllCommentsForTask(String taskId) {
    return RestAssured
        .given()
        .when().get("/issues/{{taskId}}/comments?fields=id,text,created", taskId);
  }

  public Response getCommentForTask(String taskId, String commentId) {
    return RestAssured
        .given()
        .when().get("/issues/{{taskId}}/comments/{commentId}?fields=id,text,created", taskId, commentId);
  }

  public Response updateTaskComment(String taskId, String commentId, String text) {
    TaskCommentReq taskCommentReq = new TaskCommentReq(text);
    return RestAssured
        .given().body(taskCommentReq)
        .when().post("/issues/{{taskId}}/comments/{commentId}?fields=id,text,created", taskId, commentId);
  }

  public Response deleteTaskComment(String taskId, String commentId) {
    return RestAssured
        .given()
        .when().delete("/issues/{{taskId}}/comments/{{commentId}}?fields=id,text,created", taskId, commentId);
  }
}
