package practice.restassured.tests;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import practice.restassured.dto.Project;
import practice.restassured.dto.Task;
import practice.restassured.dto.User;
import practice.restassured.specifications.Specifications;

import java.util.UUID;

public class TaskTest extends BaseTest {
  private final String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  private final String taskSummary = "Task_"+randomUUID;
  private final String taskDescription = "Description_"+randomUUID;
  private static User createdUser;
  private static Project createdProject;
  private Task createdTask;

  @BeforeAll
  static void beforeAll() {
    String userLogin = "userTask";
    String userPassword = "passwordTask";
    String userEmail = userLogin + "@mail.ru";
    String userFullName = "User Task";
    createdUser = userApi.createUser(userLogin, userFullName, userEmail, userPassword)
        .then().extract().as(User.class);

    String projectName = "projectTask";
    String projectShortName = "PT";
    createdProject = projectApi.createProject(projectName, projectShortName, createdUser.getId())
        .then().extract().as(Project.class);
  }

  @BeforeEach
  void setUp() {
    createdTask = taskApi.createTask(createdProject.getId(), taskSummary, taskDescription)
        .then().extract().as(Task.class);
  }

  @AfterAll
  static void afterAll() {
    if (createdProject != null) {
      projectApi.deleteProject(createdProject.getId());
      System.out.println("Удален проект: " + createdProject);
    }
    if (createdUser != null) {
      userApi.deleteUser(createdUser.getId(), "2-1");
      System.out.println("Удален пользователь: " + createdUser);
    }
  }

  @AfterEach
  void tearDown() {
    if (createdTask != null) {
      taskApi.deleteTask(createdTask.getId());
      System.out.println("Удалена задача: " + createdTask);
    }
  }

  @Test
  @DisplayName("Создание задачи - проверка полей")
  void createTaskTest() {
    assertEquals(createdTask.getSummary(), taskSummary);
    assertEquals(createdTask.getDescription(), taskDescription);
    assertNotNull(createdTask.getId());
  }

  @Test
  @DisplayName("Получение всего списка измеющихся задач - количество полученных объектов больше нуля")
  void getAllTasksTest() {
    taskApi.getAllTasks()
        .then().spec(Specifications.response200())
        .body("size()", greaterThan(0));
  }

  @Test
  @DisplayName("Получение задачи по айди - проверка полей")
  void getTaskByIdTest() {
    taskApi.getTaskById(createdTask.getId())
        .then().spec(Specifications.response200())
        .body("id", equalTo(createdTask.getId()))
        .body("summary", equalTo(createdTask.getSummary()))
        .body("description", equalTo(createdTask.getDescription()));
  }

  @Test
  @DisplayName("Изменение задачи - проверка полей")
  void updateTaskTest() {
    String updatedSummary = "updated" + taskSummary;
    String updatedDescription = "updated" + taskDescription;

    taskApi.updateTask(createdTask.getId(), updatedSummary, updatedDescription)
        .then().spec(Specifications.response200())
        .body("id", equalTo(createdTask.getId()))
        .body("summary", equalTo(updatedSummary))
        .body("description", equalTo(updatedDescription));
  }

  @Test
  @DisplayName("Удаление задачи - проверка кода")
  void deleteTaskTest() {
    taskApi.deleteTask(createdTask.getId())
        .then().spec(Specifications.response200());
    taskApi.getTaskById(createdTask.getId())
        .then().spec(Specifications.response404());
  }

  @ParameterizedTest
  @DisplayName("Попытка получения задачи по неправильному айди - 404 ошибка")
  @CsvSource({"invalid-id"})
  void getTaskByInvalidId(String invalidId) {
    taskApi.getTaskById(invalidId)
        .then().spec(Specifications.response404());
  }

  @ParameterizedTest
  @DisplayName("Попытка создания задачи без projectId - 400 ошибка")
  @CsvSource({"Задача без родителя, Описание для задачи"})
  void createTaskWithoutProject(String taskSummary, String taskDescription) {
    taskApi.createTask(taskSummary, taskDescription)
        .then().spec(Specifications.response400());
  }
}
