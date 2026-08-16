package practice.restassured.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//{
//    "idReadable": "TP_1786877887-1",
//    "summary": "Task_1786877892",
//    "project": {
//        "name": "TestProject_1786877887",
//        "id": "0-45",
//        "$type": "Project"
//    },
//    "id": "3-20",
//    "$type": "Issue"
//}
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
  private String summary;
  private String description;
  private String idReadable;
  private Project project;
  private String id;
  private @JsonProperty("$type") String type;
}
