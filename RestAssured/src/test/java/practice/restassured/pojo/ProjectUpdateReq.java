package practice.restassured.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectUpdateReq {
  private String name;
  private String shortName;
}
