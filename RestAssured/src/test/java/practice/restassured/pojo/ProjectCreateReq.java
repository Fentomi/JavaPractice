package practice.restassured.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practice.restassured.model.Leader;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectCreateReq {
  private String name;
  private String shortName;
  private Leader leader;

  public ProjectCreateReq(String name, Leader leader) {
    this.name = name;
    this.leader = leader;
  }
}
