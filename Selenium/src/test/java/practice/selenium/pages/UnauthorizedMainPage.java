package practice.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import practice.selenium.config.EnvLoader;

public class UnauthorizedMainPage extends BasePage {
  @FindBy(xpath = "//a[@href='issues']") private WebElement issuesOpenButton;

  @FindBy(xpath = "//button[contains(@data-test, 'login-button')]") private WebElement authDialogButton;

  public UnauthorizedMainPage(WebDriver driver) {
    super(driver);
    driver.get(EnvLoader.BASE_URL);
    PageFactory.initElements(driver, this);
    waitForPageLoaded();
    waitForElementVisible(issuesOpenButton);
  }

  public LoginPage openAuthPage() {
    authDialogButton.click();
    return new LoginPage(driver);
  }

}
