package practice.selenium.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.selenium.config.EnvLoader;
import practice.selenium.pages.IssuesPage;
import practice.selenium.pages.UnauthorizedMainPage;

import static org.junit.jupiter.api.Assertions.*;

public class IssuesTest extends BaseTest {
  private IssuesPage issuesPage;

  @BeforeEach
  public void navigateToIssuesBoard() {
    issuesPage = new UnauthorizedMainPage(driver)
        .openAuthPage().auth(EnvLoader.ADMIN_LOGIN, EnvLoader.ADMIN_PASSWORD)
        .openIssuesPage();
  }

  @Test
  @DisplayName("Отображаются все необходимые таблицы, кнопки и поля")
  public void issuesPageShouldLoadSuccessfully() {
    assertTrue(issuesPage.isIssuesTableDisplayed(), "Таблица задач должна отображаться");
    assertTrue(issuesPage.isCreateIssueButtonDisplayed(), "Кнопка 'Новая задача' должна быть видна");
    assertTrue(issuesPage.isQueryConstructorDisplayed(), "Поле поиска должно быть видно");
    assertTrue(issuesPage.isUnresolvedOnlyButtonDisplayed(), "Кнопка 'Скрыть завершённые' должна быть видна");
    assertTrue(issuesPage.isListSettingsButtonDisplayed(), "Кнопка 'Параметры' должна быть видна");
  }

  @Test
  @DisplayName("Кнопка 'Новая задача' работает")
  public void createIssueButtonIsClickable() {
    assertDoesNotThrow(() -> issuesPage.clickCreateIssue(),
        "Клик по кнопке 'Новая задача' не должен вызывать исключений");
  }

  @Test
  @DisplayName("Кнопка 'Скрыть завершенные' работает")
  public void unresolvedOnlyButtonCanBeToggled() {
    assertDoesNotThrow(() -> issuesPage.toggleUnresolvedOnly(),
        "Клик по кнопке 'Скрыть завершённые' не должен вызывать исключений");
  }
}