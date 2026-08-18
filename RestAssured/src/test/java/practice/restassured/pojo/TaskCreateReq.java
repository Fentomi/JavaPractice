package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TaskCreateReq {
  private String summary;
  private String description;
  private Project project;

  @Data
  @AllArgsConstructor
  private static class Project {
    private String id;
  }

  public TaskCreateReq(String summary, String description) {
    this.summary = summary;
    this.description = description;
  }

  public TaskCreateReq(String summary, String description, String projectId) {
    this.summary = summary;
    this.description = description;
    this.project = new Project(projectId);
  }
}
