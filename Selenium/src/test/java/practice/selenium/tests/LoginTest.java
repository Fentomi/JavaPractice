package practice.selenium.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import practice.selenium.config.EnvLoader;
import practice.selenium.pages.UnauthorizedMainPage;

class LoginTest extends BaseTest {

  @Test
  @DisplayName("Авторизация под учетной записью администратора")
  void authAdminTest() {
    UnauthorizedMainPage unauthorizedMainPage = new UnauthorizedMainPage(driver);
    WebElement userAvatar = unauthorizedMainPage.openAuthPage()
        .auth(EnvLoader.ADMIN_LOGIN, EnvLoader.ADMIN_PASSWORD)
        .getUserAvatar();
    assertNotNull(userAvatar);
  }

  @Test
  @DisplayName("Авторизация под учетной записью пользователя")
  void authUserTest() {
    UnauthorizedMainPage unauthorizedMainPage = new UnauthorizedMainPage(driver);
    WebElement userAvatar = unauthorizedMainPage.openAuthPage()
        .auth(EnvLoader.ADMIN_LOGIN, EnvLoader.ADMIN_PASSWORD)
        .getUserAvatar();
    assertNotNull(userAvatar);
  }

  @ParameterizedTest
  @CsvSource({"user, invalidUser", "invalidUser, user"})
  @DisplayName("Ошибка авторизации: Некорректное имя пользователя или пароль")
  void invalidLoginAndPasswordTest(String login, String password) {
    UnauthorizedMainPage unauthorizedMainPage = new UnauthorizedMainPage(driver);
    String error = unauthorizedMainPage.openAuthPage()
        .invalidAuth(login, password);
    assertEquals("Некорректное имя пользователя или пароль.", error);
  }
}
