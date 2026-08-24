package practice.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class IssuesPage extends BasePage {

  @FindBy(xpath = "//a[contains(@href, 'issues') and contains(text(), 'Задачи')]")
  private WebElement breadcrumbIssuesLink;

  @FindBy(xpath = "//table[@data-test='ring-table']")
  private WebElement issuesTable;

  @FindBy(xpath = "//a[@data-test='createIssueButton']")
  private WebElement createIssueButton;

  @FindBy(xpath = "//div[@data-test='query-constructor']")
  private WebElement queryConstructor;

  @FindBy(xpath = "//button[@data-test='unresolved-only-button']")
  private WebElement unresolvedOnlyButton;

  @FindBy(xpath = "//button[@data-test='list-settings-button']")
  private WebElement listSettingsButton;

  public IssuesPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    waitForPageLoaded();
    waitForElementVisible(breadcrumbIssuesLink);
  }

  public boolean isIssuesTableDisplayed() {
    return issuesTable.isDisplayed();
  }

  public boolean isCreateIssueButtonDisplayed() {
    return createIssueButton.isDisplayed();
  }

  public void clickCreateIssue() {
    createIssueButton.click();
  }

  public boolean isQueryConstructorDisplayed() {
    return queryConstructor.isDisplayed();
  }

  public boolean isUnresolvedOnlyButtonDisplayed() {
    return unresolvedOnlyButton.isDisplayed();
  }

  public void toggleUnresolvedOnly() {
    unresolvedOnlyButton.click();
  }

  public boolean isListSettingsButtonDisplayed() {
    return listSettingsButton.isDisplayed();
  }
}