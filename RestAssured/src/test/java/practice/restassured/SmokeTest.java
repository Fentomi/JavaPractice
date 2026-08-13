package practice.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import practice.restassured.dto.*;
import practice.restassured.pojo.*;
import java.util.UUID;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmokeTest extends TestSpecification {
  private String projectId;
  private String issueId;
  private String commentId;
  private String leaderId;
  private String uniqueShortName;

  @BeforeAll
  void setup() {
    System.out.println("=== НАЧАЛО ПОДГОТОВКИ ДАННЫХ ===");
    leaderId = getLeaderId();
    uniqueShortName = "SMK_" + UUID.randomUUID().toString().substring(0, 4);

    // 1. Создание проекта
    System.out.println("[INFO] Создание проекта..");
    ProjectReq projectReq = new ProjectReq("Smoke Project", uniqueShortName, "Project for smoke tests", leaderId);
    projectId = RestAssured
        .given()
          .body(projectReq)
        .when()
          .post("/admin/projects?fields=id")
        .then()
          .log().body()
          .statusCode(200)
          .extract().jsonPath().getString("id");
    System.out.println("[SUCCESS] Проект создан: id=" + projectId + ", shortName=" + uniqueShortName + "\n");

    // 2. Создание задачи
    System.out.println("[INFO] Создание задачи..");
    IssueReq issueReq = new IssueReq(projectId, "Smoke Issue");
    issueId = RestAssured
        .given()
          .body(issueReq)
        .when()
          .post("/issues?fields=id")
        .then()
          .log().body()
          .statusCode(200)
          .extract().path("id");
    System.out.println("[SUCCESS] Задача создана: id=" + issueId + "\n");

    // 3. Создание комментария
    System.out.println("[INFO] Создание комментария..");
    CommentReq commentReq = new CommentReq("Initial comment");
    commentId = RestAssured
        .given()
          .body(commentReq)
        .when()
          .post("/issues/{issueId}/comments?fields=id", issueId)
        .then()
          .log().body()
          .statusCode(200)
         .extract().path("id");
    System.out.println("[SUCCESS] Комментарий создан: id=" + commentId + "\n");
    System.out.println("=== ПОДГОТОВКА ЗАВЕРШЕНА ===");
  }

  @Test
  void editProjectTest() {
    ProjectReq updatedProject = new ProjectReq("Updated Project", uniqueShortName, "Updated description", leaderId);
    RestAssured
        .given()
          .body(updatedProject)
        .when()
          .post("/admin/projects/{projectId}?fields=id,name,description", projectId)
        .then()
          .log().body()
          .statusCode(200)
          .body("name", equalTo("Updated Project"))
          .body("description", equalTo("Updated description"));
  }

  @Test
  void editIssueTest() {
    IssueReq updatedIssue = new IssueReq(projectId, "Updated Issue Summary");
    RestAssured
        .given()
          .body(updatedIssue)
        .when()
          .post("/issues/{issueId}?fields=id,summary", issueId)
        .then()
          .log().body()
          .statusCode(200)
          .body("summary", equalTo("Updated Issue Summary"));
  }

  @Test
  void editCommentTest() {
    CommentReq updatedComment = new CommentReq("Updated comment text");

    RestAssured
        .given()
          .body(updatedComment)
        .when()
          .post("/issues/{issueId}/comments/{commentId}?fields=id,text", issueId, commentId)
        .then()
          .log().body()
          .statusCode(200)
          .body("text", equalTo("Updated comment text"));
  }

  @Test
  void negativeCreateProjectWithoutShortName() {
    ProjectReq invalid = new ProjectReq();
    invalid.setName("Invalid Project");
    invalid.setDescription("No shortName");
    invalid.setLeader(new Leader(leaderId));

    RestAssured
        .given()
          .body(invalid)
        .when()
          .post("/admin/projects")
        .then()
          .log().body()
          .statusCode(400)
          .body("error", equalTo("Bad Request"))
          .body("error_description", equalTo("Field shortName cannot be null"));
  }

  @Test
  void negativeCreateProjectWithCyrillicShortName() {
    ProjectReq invalid = new ProjectReq("Project", "Тест", "Cyrillic shortName", leaderId);

    RestAssured
        .given()
          .body(invalid)
        .when()
          .post("/admin/projects")
        .then()
          .log().body()
          .statusCode(400)
          .body("error_description", containsString("Запрещенные символы"));
  }

  @Test
  void negativeCreateProjectWithDuplicateShortName() {
    ProjectReq duplicate = new ProjectReq("Duplicate Project", uniqueShortName, "Duplicate", leaderId);

    RestAssured
        .given()
          .body(duplicate)
        .when()
          .post("/admin/projects")
        .then()
          .log().body()
          .statusCode(400)
          .body("error", equalTo("invalid_properties"))
          .body("error_description", containsStringIgnoringCase("уникальным"));
  }

  @Test
  void negativeCreateIssueWithoutSummary() {
    IssueReq invalid = new IssueReq();
    invalid.setProject(new Project(projectId));

    RestAssured
        .given()
          .body(invalid)
        .when()
         .post("/issues")
        .then()
          .log().body()
          .statusCode(400)
          .body("error_description", containsStringIgnoringCase("summary"));
  }

  @Test
  void negativeCreateIssueWithNonExistentProject() {
    IssueReq invalid = new IssueReq("non-existent-id", "Summary");

    RestAssured
        .given()
          .body(invalid)
        .when()
         .post("/issues")
        .then()
          .log().body()
          .statusCode(400)
          .body("error", equalTo("bad_request"))
          .body("error_description", containsStringIgnoringCase("Invalid structure of entity id"));
  }

  @AfterAll
  void cleanup() {
    System.out.println("=== НАЧАЛО ОЧИСТКИ ===");
    if (commentId != null) {
      try {
        RestAssured
            .given()
              .delete("/issues/{issueId}/comments/{commentId}", issueId, commentId)
            .then()
              .log().body()
              .statusCode(200);
        System.out.println("Комментарий удалён: id=" + commentId);
      } catch (AssertionError e) {
        System.out.println("Не удалось удалить комментарий: " + e.getMessage());
      }
    }

    if (issueId != null) {
      try {
        RestAssured
            .given()
              .delete("/issues/{issueId}", issueId)
            .then()
              .log().body()
              .statusCode(200);
        System.out.println("Задача удалена: id=" + issueId);
      } catch (AssertionError e) {
        System.out.println("Не удалось удалить задачу: " + e.getMessage());
      }
    }

    if (projectId != null) {
      try {
        RestAssured
            .given()
              .delete("/admin/projects/{projectId}", projectId)
            .then()
              .log().body()
              .statusCode(200);
        System.out.println("Проект удалён: id=" + projectId);
      } catch (AssertionError e) {
        System.out.println("Не удалось удалить проект: " + e.getMessage());
      }
    }

    System.out.println("=== ОЧИСТКА ЗАВЕРШЕНА ===");
  }
}