package practice.restassured.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import practice.restassured.model.Project;
import practice.restassured.pojo.TaskCreateReq;
import practice.restassured.pojo.TaskUpdateReq;

public class TaskApi {

  public Response createTask(String projectId, String summary, String description) {
    TaskCreateReq taskCreateReq = new TaskCreateReq(new Project(projectId), summary, description);
    return RestAssured
        .given().body(taskCreateReq)
        .when().post("/issues?fields=id,idReadable,summary,project(id,name),description");
  }

  public Response getAllTasks() {
    return RestAssured
        .given()
        .when().get("/issues?fields=id,idReadable,summary,project(id,name)");
  }

  public Response getTaskById(String taskId) {
    return RestAssured
        .given()
        .when().get("/issues/{taskId}?fields=id,idReadable,summary,project(id,name),description", taskId);
  }

  public Response updateTask(String taskId, String summary, String description) {
    TaskUpdateReq taskUpdateReq = new TaskUpdateReq(summary, description);
    return RestAssured
        .given().body(taskUpdateReq)
        .when().post("/issues/{taskId}?fields=id,idReadable,summary,project(id,name),description", taskId);
  }

  public Response deleteTask(String taskId) {
    return RestAssured
        .given()
        .when().delete("/issues/{taskId}", taskId);
  }
}
