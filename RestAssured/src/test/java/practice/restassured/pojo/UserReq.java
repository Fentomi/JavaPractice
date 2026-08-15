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

  public UserReq(String email, String fullName, String login) {
    this.email = email;
    this.fullName = fullName;
    this.login = login;
  }
}
