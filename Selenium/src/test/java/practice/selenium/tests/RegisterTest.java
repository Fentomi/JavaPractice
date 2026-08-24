package practice.selenium.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import practice.selenium.config.EnvLoader;
import practice.selenium.pages.UnauthorizedMainPage;

class RegisterTest extends BaseTest {

  @ParameterizedTest
  @CsvSource({"Artyom, ArtyHtmlHacker, thebestperson@mail.ru, qwerty123", "Nikitosik, Fentomi,fentomi1337@mail.ru, paSsWorD!23"})
  @DisplayName("Регистрация пользователя")
  void registerTest(String name, String login, String email, String password) {
    UnauthorizedMainPage unauthorizedMainPage = new UnauthorizedMainPage(driver);
    WebElement userAvatar = unauthorizedMainPage.openAuthPage()
        .openRegisterPage()
        .register(name, login, email, password, password)
        .getUserAvatar();
    assertNotNull(userAvatar);
  }

  @Test
  @DisplayName("Ошибка регистрации: Это имя пользователя уже используется.")
  void invalidRegisterTest() {
    UnauthorizedMainPage unauthorizedMainPage = new UnauthorizedMainPage(driver);
    String login = EnvLoader.ADMIN_LOGIN;
    String password = EnvLoader.ADMIN_PASSWORD;
    String email = login + "@mail.ru";

    String errorMessage = unauthorizedMainPage.openAuthPage()
        .openRegisterPage()
        .invalidRegister(login, login, email, password, password);
    assertEquals("Это имя пользователя уже используется", errorMessage);
  }
}
