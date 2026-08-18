package practice.restassured.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import practice.restassured.pojo.ProjectCreateReq;
import practice.restassured.pojo.ProjectUpdateReq;

public class ProjectApi {

  public Response createProject(String name, String shortName, String userId) {
    ProjectCreateReq projectReq = new ProjectCreateReq(name, shortName, userId);
    return RestAssured
        .given().body(projectReq)
        .when().post("/admin/projects?fields=id,name,shortName");
  }
  public Response createProject(String name, String userId) {
    ProjectCreateReq projectReq = new ProjectCreateReq(name, userId);
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
    ProjectUpdateReq projectUpdateReq = new ProjectUpdateReq(name, shortName);
    return RestAssured
        .given().body(projectUpdateReq)
        .when().post("/admin/projects/{projectId}?fields=id,name,shortName,leader", projectId);
  }

  public Response deleteProject(String projectId) {
    return RestAssured
        .given()
        .when().delete("/admin/projects/{projectId}", projectId);
  }
}
