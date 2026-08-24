package practice.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import practice.selenium.config.EnvLoader;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
  protected WebDriver driver;

  @BeforeEach
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(EnvLoader.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(EnvLoader.IMPLICIT_WAIT, TimeUnit.SECONDS);
  }

  @AfterEach
  public void tearDown() {
    driver.close();
    driver.quit();
  }
}
