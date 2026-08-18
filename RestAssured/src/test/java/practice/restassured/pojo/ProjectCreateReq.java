package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectCreateReq {
  private String name;
  private String shortName;
  private Leader leader;

  @Data
  @AllArgsConstructor
  private static class Leader {
    private String id;
  }

  public ProjectCreateReq(String name, String leaderId) {
    this.name = name;
    this.leader = new Leader(leaderId);
  }

  public ProjectCreateReq(String name, String shortName, String leaderId) {
    this.name = name;
    this.shortName = shortName;
    this.leader = new Leader(leaderId);
  }
}
