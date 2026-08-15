package practice.restassured.pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
  private String login;
  private String fullName;
  private String email;
  private String password;

  public UserReq(String login, String fullName, String email) {
    this.login = login;
    this.fullName = fullName;
    this.email = email;
  }
}
