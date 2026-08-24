package practice.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
  protected WebDriver driver;

  public BasePage(WebDriver driver) {
    this.driver = driver;
  }

  protected void waitForPageLoaded() {
    waitForPageLoaded(10);
  }

  protected void waitForPageLoaded(int timeoutSeconds) {
    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
  }

  protected void waitForElementVisible(WebElement element) {
    waitForElementVisible(element, 10);
  }

  protected void waitForElementVisible(WebElement element, int timeoutSeconds) {
    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(ExpectedConditions.visibilityOf(element));
  }

  protected void waitForElementClickable(WebElement element) {
    waitForElementClickable(element, 10);
  }

  protected void waitForElementClickable(WebElement element, int timeoutSeconds) {
    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(ExpectedConditions.elementToBeClickable(element));
  }

  protected void switchToFrameByTitle(String title) {
    waitForFrameAndSwitchTo(By.cssSelector("iframe[title='" + title + "']"));
  }

  protected void waitForFrameAndSwitchTo(By frameLocator) {
    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
  }

  protected void switchToDefaultContent() {
    driver.switchTo().defaultContent();
  }
}