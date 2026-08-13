package practice.restassured.dto;

public class Leader {
  private String id;

  public Leader(String id) {
    this.id = id;
  }

  public Leader() {}

  public String getId() {
    return id;
  }
}
