package practice.restassured.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
  private String id;
  private String name;
  private String shortName;
  private @JsonProperty("$type") String type;

  public Project(String type, String name, String id) {
    this.type = type;
    this.name = name;
    this.id = id;
  }
}
