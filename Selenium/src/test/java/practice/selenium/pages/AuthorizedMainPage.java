package practice.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthorizedMainPage extends BasePage {
  @FindBy(xpath = "//a[@href='issues']") private WebElement issuesOpenButton;
  @FindBy(xpath = "//a[@href='agiles']") private WebElement agileOpenButton;

  @FindBy(xpath = "//img[@data-test='avatar']") private WebElement userAvatar;

  public AuthorizedMainPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    waitForPageLoaded();
    waitForElementVisible(userAvatar);
  }

  public IssuesPage openIssuesPage() {
    issuesOpenButton.click();
    return new IssuesPage(driver);
  }

  public AgileBoardPage openAgilesPage() {
    agileOpenButton.click();
    return new AgileBoardPage(driver);
  }

  public WebElement getUserAvatar() {
    waitForElementVisible(userAvatar);
    return userAvatar;
  }
}
