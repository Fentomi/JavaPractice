package practice.restassured.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import practice.restassured.dto.Project;
import practice.restassured.dto.User;
import practice.restassured.specifications.Specifications;

import java.util.UUID;

public class ProjectTest extends BaseTest {
  private final String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  private final String name = "TestProject_" + randomUUID;
  private final String shortName = "TP_" + randomUUID;
  private User createdUser;
  private Project createdProject;

  @BeforeEach
  void setUp() {
    String userLogin = "userProject_" + randomUUID;
    String userPassword = "userPassword" + randomUUID;
    String userFullName = "User Project " + randomUUID;
    String userEmail = userLogin + "@mail.ru";

    createdUser = userApi.createUser(userLogin, userFullName, userEmail, userPassword)
        .then().extract().as(User.class);
    System.out.println(createdUser);

    String createdProjectId = projectApi.createProject(
        name, shortName, createdUser.getId())
        .then().extract().jsonPath().getString("id");
    createdProject = projectApi.getProjectById(createdProjectId).then().extract().as(Project.class);
    System.out.println(createdProjectId);
    System.out.println(createdProject);
  }

  @AfterEach
  void tearDown() {
    projectApi.deleteProject(createdProject.getId());
    userApi.deleteUser(createdUser.getId(), "2-1");
  }

  @Test
  void createProjectTest() {
    assertEquals(name, createdProject.getName());
    assertEquals(shortName, createdProject.getShortName());
  }

  @Test
  void getAllProjectsTest() {
    projectApi.getAllProjects()
        .then().spec(Specifications.response200())
        .body("size()", Matchers.greaterThan(0));
  }
}
