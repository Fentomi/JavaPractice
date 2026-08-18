package practice.restassured.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
  private String idReadable;
  private String summary;
  private String description;
  private Project project;
  private String id;
  private @JsonProperty("$type") String type;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class Project {
    private String name;
    private String id;
    private @JsonProperty("$type") String type;
  }
}
