package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.restassured.model.Project;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateReq {
  private Project project;
  private String summary;
  private String description;
}
