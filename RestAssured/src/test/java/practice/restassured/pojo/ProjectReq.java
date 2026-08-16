package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import practice.restassured.model.Leader;

@Data
@AllArgsConstructor
public class ProjectReq {
  private String name;
  private String shortName;
  private Leader leader;

  public ProjectReq(String name, String shortName) {
    this.name = name;
    this.shortName = shortName;
  }
}
