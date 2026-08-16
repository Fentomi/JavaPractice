package practice.restassured.tests;


import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.restassured.dto.Project;
import practice.restassured.dto.Task;
import practice.restassured.dto.User;

import java.util.UUID;

public class TaskTest extends BaseTest {
  private final String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  private final String taskSummary = "Task_"+randomUUID;
  private final String taskDescription = "Description_"+randomUUID;
  private final String successorId = "2-1";
  private User createdUser;
  private Project createdProject;
  private Task createdTask;

  @BeforeEach
  void setUp() {
    String userLogin = "userTask_" + randomUUID;
    String userPassword = "passwordTask_" + randomUUID;
    String userEmail = userLogin + "@mail.ru";
    String userFullName = "User Task " + randomUUID;
    createdUser = userApi.createUser(userLogin, userFullName, userEmail, userPassword)
        .then().extract().as(User.class);

    String projectName = "projectTask_" + randomUUID;
    String projectShortName = "PT_" + randomUUID;
    createdProject = projectApi.createProject(projectName, projectShortName, createdUser.getId())
        .then().extract().as(Project.class);

    createdTask = taskApi.createTask(createdProject.getId(), taskSummary, taskDescription)
        .then().extract().as(Task.class);
  }

  @AfterEach
  void tearDown() {
    taskApi.deleteTask(createdTask.getId());
    projectApi.deleteProject(createdProject.getId());
    userApi.deleteUser(createdUser.getId(), successorId);
  }

  @Test
  @DisplayName("Создание задачи")
  void createTask() {
    assertEquals(createdTask.getSummary(), taskSummary);
    assertEquals(createdTask.getDescription(), taskDescription);
    assertNotNull(createdTask.getId());
  }
}
