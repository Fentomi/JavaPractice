package practice.restassured.tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import practice.restassured.dto.Project;
import practice.restassured.dto.User;
import practice.restassured.specifications.Specifications;

import java.util.UUID;

public class ProjectTest extends BaseTest {
  private final String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  private final String name = "TestProject_" + randomUUID;
  private final String shortName = "TP_" + randomUUID;
  private static User createdUser;
  private Project createdProject;

  @BeforeAll
  static void beforeAll() {
    String userLogin = "userProject";
    String userPassword = "userPassword";
    String userFullName = "User Project";
    String userEmail = userLogin + "@mail.ru";

    createdUser = userApi.createUser(userLogin, userFullName, userEmail, userPassword)
        .then().extract().as(User.class);
    System.out.println("Создан пользователь: " + createdUser);
  }

  @BeforeEach
  void setUp() {
    String createdProjectId = projectApi.createProject(
        name, shortName, createdUser.getId())
        .then().extract().jsonPath().getString("id");
    System.out.println(createdProjectId);
    createdProject = projectApi.getProjectById(createdProjectId).then().extract().as(Project.class);
    System.out.println("Создан проект: " + createdProject);
  }

  @AfterAll
  static void afterAll() {
    if (createdUser != null) {
      userApi.deleteUser(createdUser.getId(), "2-1");
      System.out.println("Удален пользователь: " + createdUser);
    }
  }

  @AfterEach
  void tearDown() {
    if (createdProject != null) {
      projectApi.deleteProject(createdProject.getId());
      System.out.println("Удален проект: " + createdProject);
    }
  }

  @Test
  @DisplayName("Создание проекта - проверка полей")
  void createProjectTest() {
    assertEquals(name, createdProject.getName());
    assertEquals(shortName, createdProject.getShortName());
  }

  @Test
  @DisplayName("Получение всего списка проектов - проверка ответа в формате списка")
  void getAllProjectsTest() {
    projectApi.getAllProjects()
        .then().spec(Specifications.response200())
        .body("size()", greaterThan(0));
  }

  @Test
  @DisplayName("Обновление проекта - проверка обновляемых полей")
  void updateProjectTest() {
    String updatedName = "updated"+createdProject.getName();
    String updatedShortName = "U"+createdProject.getShortName();

    projectApi.updateProject(createdProject.getId(), updatedName, updatedShortName)
        .then().spec(Specifications.response200())
        .body("id", equalTo(createdProject.getId()))
        .body("name", equalTo("updated"+createdProject.getName()))
        .body("shortName", equalTo("U"+createdProject.getShortName()));
  }

  @Test
  @DisplayName("Удаление проекта")
  void deleteProjectTest() {
    projectApi.deleteProject(createdProject.getId())
        .then().spec(Specifications.response200());
  }

  @ParameterizedTest
  @DisplayName("Попытка получить проект по неправильному айди - ошибка 404")
  @CsvSource({"invalid-id-project"})
  void getProjectByInvalidId(String projectId) {
    projectApi.getProjectById(projectId)
        .then().spec(Specifications.response404());
  }

  @ParameterizedTest
  @DisplayName("Попытка создать проект без shortName - ошибка 400")
  @CsvSource("InvalidProjectName")
  void createProjectWithoutShortName(String name) {
    projectApi.createProject(name, createdUser.getId())
        .then().spec(Specifications.response400());
  }
}
