package practice.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
  @FindBy(id = "username") private WebElement loginInput;
  @FindBy(id = "password") private WebElement passwordInput;
  @FindBy(xpath = "//button[contains(@class, 'auth-button')]") private WebElement loginButton;
  @FindBy(xpath = "//div[contains(@class, 'login-page__bottom-panel')]//a[1]") private WebElement registerButton;
  @FindBy(xpath = "//div[contains(@class, 'header__text__error')][1]") private WebElement errorMessage;

  public LoginPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    switchToFrameByTitle("Login dialog");
    waitForElementVisible(loginInput);
  }

  public AuthorizedMainPage auth(String login, String password) {
    loginInput.sendKeys(login);
    passwordInput.sendKeys(password);
    loginButton.click();
    switchToDefaultContent();
    return new AuthorizedMainPage(driver);
  }

  public String invalidAuth(String login, String password) {
    loginInput.sendKeys(login);
    passwordInput.sendKeys(password);
    loginButton.click();

    waitForElementVisible(errorMessage);
    return errorMessage.getText();
  }

  public RegisterPage openRegisterPage() {
    registerButton.click();
    switchToDefaultContent();
    return new RegisterPage(driver);
  }
}
