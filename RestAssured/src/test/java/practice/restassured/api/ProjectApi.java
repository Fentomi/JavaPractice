package practice.restassured.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import practice.restassured.model.Leader;
import practice.restassured.pojo.ProjectReq;
import practice.restassured.secure.SecureData;

public class ProjectApi {

  public Response createProject(String name, String shortName, String userId) {
    ProjectReq projectReq = new ProjectReq(name, shortName, new Leader(userId));
    return RestAssured
        .given().body(projectReq)
        .when().post("/admin/projects?fields=id,name,shortName");
  }

  public Response getAllProjects() {
    return RestAssured
        .given()
        .when().get("/admin/projects?fields=id,name,shortName");
  }

  public Response getProjectById(String projectId) {
    return RestAssured
        .given()
        .when().get("/admin/projects/{projectId}?fields=id,name,shortName", projectId);
  }

  public Response updateProject(String projectId, String name, String shortName) {
    ProjectReq projectReq = new ProjectReq(name, shortName);
    return RestAssured
        .given().body(projectReq)
        .when().post("/admin/projects/{projectId}?fields=id,name,shortName,leader", projectId);
  }

  public Response deleteProject(String projectId) {
    return RestAssured
        .given()
        .when().delete("/admin/projects/{projectId}", projectId);
  }
}
