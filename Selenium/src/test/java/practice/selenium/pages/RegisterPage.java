package practice.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {
  @FindBy(id = "name") private WebElement nameInput;
  @FindBy(id = "login") private WebElement loginInput;
  @FindBy(id = "email") private WebElement emailInput;
  @FindBy(id = "password") private WebElement passwordInput;
  @FindBy(id = "passwordRepeat") private WebElement passwordRepeatInput;
  @FindBy(xpath = "//button[contains(@class, 'auth-button')]") private WebElement registerButton;
  @FindBy(xpath = "//div[contains(@class, 'header__text__error')][1]") private WebElement errorMessage;

  public RegisterPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    waitForPageLoaded();
  }

  public AuthorizedMainPage register(String name, String login, String email, String password, String repeatedPassword) {
    fillFields(name, login, email, password, repeatedPassword);
    registerButton.click();
    return new AuthorizedMainPage(driver);
  }

  public String invalidRegister(String name, String login, String email, String password, String repeatedPassword) {
    fillFields(name, login, email, password, password);
    registerButton.click();
    waitForElementVisible(errorMessage);
    return errorMessage.getText();
  }

  public void fillFields(String name, String login, String email, String password, String repeatedPassword) {
    nameInput.sendKeys(name);
    loginInput.sendKeys(login);
    emailInput.sendKeys(email);
    passwordInput.sendKeys(password);
    passwordRepeatInput.sendKeys(repeatedPassword);
  }
}
