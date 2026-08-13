package practice.restassured.dto;

public class Project {
  private String id;

  public Project(String id) {
    this.id = id;
  }

  public Project() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
