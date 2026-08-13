package practice.restassured.pojo;

public class CommentReq {
  private String text;

  public CommentReq(String text) {
    this.text = text;
  }

  public CommentReq() {}

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}