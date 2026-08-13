package practice.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import practice.restassured.dto.*;
import practice.restassured.pojo.*;
import java.util.UUID;
import static org.hamcrest.Matchers.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class SmokeTest extends TestSpecification {
  private static volatile String projectId;
  private static volatile String issueId;
  private static volatile String commentId;
  private static volatile String leaderId;
  private static volatile String uniqueShortName;

  @BeforeAll
  static void setup() {
    leaderId = getLeaderId();
    uniqueShortName = "SMK_" + UUID.randomUUID().toString().substring(0, 4) + "_" + System.currentTimeMillis();
  }

  @Nested
  @Order(1)
  @Execution(ExecutionMode.SAME_THREAD)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  public class SetupTests {
    @Test
    @Order(1)
    public void createProjectTest() {
      ProjectReq projectReq = new ProjectReq("TestProject1", uniqueShortName, "DescTestProject1", leaderId);
      projectId = RestAssured
          .given()
          .body(projectReq)
          .when()
          .post("/admin/projects?fields=id")
          .then()
          .log().body()
          .statusCode(200)
          .extract().jsonPath().getString("id");
    }

    @Test
    @Order(2)
    public void createTaskTest() {
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
    }

    @Test
    @Order(3)
    void createCommentInTaskTest() {
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
    }
  }

  @Nested
  @Order(2)
  @Execution(ExecutionMode.CONCURRENT)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  public class ParallelTests {
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
  }

  @Nested
  @Order(3)
  @Execution(ExecutionMode.SAME_THREAD)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  public class CleanupTests {
    @Test
    @Order(1)
    public void cleanupCommentTest() {
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
    }

    @Test
    @Order(2)
    public void cleanupTaskTest() {
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
    }

    @Test
    @Order(3)
    public void cleanupProjectTest() {
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
    }
  }
}