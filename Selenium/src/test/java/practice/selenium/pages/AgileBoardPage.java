package practice.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AgileBoardPage extends BasePage {

  @FindBy(css = "[data-test='agile-board-selector'] .entityName__cdba9")
  private WebElement boardNameElement;

  @FindBy(css = "[data-test='createActionsHeaderDropdown']")
  private WebElement createIssueButton;

  @FindBy(css = "[data-test='backlogToggler']")
  private WebElement backlogToggleButton;

  @FindBy(css = "[data-test='boardSettingsToggler']")
  private WebElement settingsButton;

  @FindBy(css = "[data-test='chartToggler']")
  private WebElement chartToggleButton;

  @FindBy(css = "[data-test='ring-query-assist-input']")
  private WebElement searchInput;

  @FindBy(css = "[data-test='ring-query-assist']")
  private WebElement searchContainer;

  @FindBy(css = "[data-test='sprintTable']")
  private WebElement sprintTable;

  @FindBy(css = "[data-test='sprintTable'] .yt-agile-table__row__cell_head")
  private List<WebElement> columnHeaders;

  @FindBy(css = "[data-test='yt-agile-board-card']")
  private List<WebElement> allCards;

  public AgileBoardPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
    waitForPageLoaded();
    waitForElementVisible(sprintTable);
  }

  public String getBoardName() {
    waitForElementVisible(boardNameElement);
    return boardNameElement.getText();
  }

  public boolean isCreateIssueButtonDisplayed() {
    return createIssueButton.isDisplayed();
  }

  public boolean isBacklogToggleDisplayed() {
    return backlogToggleButton.isDisplayed();
  }

  public boolean isSettingsButtonDisplayed() {
    return settingsButton.isDisplayed();
  }

  public boolean isChartToggleDisplayed() {
    return chartToggleButton.isDisplayed();
  }

  public boolean isSearchFieldDisplayed() {
    return searchContainer.isDisplayed();
  }

  public int getColumnCount() {
    return columnHeaders.size();
  }

  public List<String> getColumnNames() {
    return columnHeaders.stream()
        .map(header -> header.findElement(By.cssSelector(".yt-agile-table__column-name")).getText())
        .collect(Collectors.toList());
  }

  public int getCardCountInColumn(String columnName) {
    WebElement columnHeader = columnHeaders.stream()
        .filter(header -> header.findElement(By.cssSelector(".yt-agile-table__column-name"))
            .getText().equals(columnName))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Колонка с именем '" + columnName + "' не найдена"));

    WebElement counterElement = columnHeader.findElement(By.cssSelector(".yt-agile-table__row__estimation"));
    String counterText = counterElement.getText().trim();
    return Integer.parseInt(counterText);
  }

  public int getTotalCardCount() {
    return allCards.size();
  }

  public void enterSearchQuery(String query) {
    waitForElementVisible(searchInput);
    searchInput.click();
    searchInput.clear();
    searchInput.sendKeys(query);
  }

  public void clearSearch() {
    searchInput.click();
    searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    searchInput.sendKeys(Keys.DELETE);
  }

  public void clickCreateIssue() {
    createIssueButton.click();
  }

  public boolean isBoardLoaded() {
    try {
      waitForElementVisible(sprintTable);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}