package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import practice.restassured.model.Leader;

@Data
@AllArgsConstructor
public class ProjectCreateReq {
  private String name;
  private String shortName;
  private Leader leader;

  public ProjectCreateReq(String name, Leader leader) {
    this.name = name;
    this.leader = leader;
  }
}
