package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.restassured.model.Project;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskReq {
  private Project project;
  private String summary;
  private String description;

  public TaskReq(String summary, String description) {
    this.summary = summary;
    this.description = description;
  }
}
