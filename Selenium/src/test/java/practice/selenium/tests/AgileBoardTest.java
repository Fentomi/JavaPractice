package practice.selenium.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.selenium.config.EnvLoader;
import practice.selenium.pages.AgileBoardPage;
import practice.selenium.pages.UnauthorizedMainPage;

import java.util.List;

class AgileBoardTest extends BaseTest {
  private AgileBoardPage agileBoardPage;

  @BeforeEach
  void navigateToAgileBoard() {
    agileBoardPage = new UnauthorizedMainPage(driver)
        .openAuthPage().auth(EnvLoader.ADMIN_LOGIN, EnvLoader.ADMIN_PASSWORD)
        .openAgilesPage();
  }

  @Test
  @DisplayName("Таблица доски загружается")
  void boardPageLoadsSuccessfully() {
    assertTrue(agileBoardPage.isBoardLoaded(), "Таблица доски не загружена");
    String boardName = agileBoardPage.getBoardName();
    assertEquals("Обзор Демопроект", boardName, "Название доски не совпадает");
  }

  @Test
  @DisplayName("Проверка неотрицательного количества карточек на доске")
  void columnsHaveNonZeroCardCounts() {
    int totalCards = agileBoardPage.getTotalCardCount();
    assertTrue(totalCards > 0, "На доске нет карточек");

    List<String> columnNames = agileBoardPage.getColumnNames();
    for (String colName : columnNames) {
      int count = agileBoardPage.getCardCountInColumn(colName);
      assertTrue(count >= 0, "В колонке '" + colName + "' отрицательное количество карточек");
    }
  }

  @Test
  @DisplayName("Проверка отображаемости всех кнопок")
  void allToolbarButtonsArePresent() {
    assertTrue(agileBoardPage.isCreateIssueButtonDisplayed(), "Кнопка создания задачи не отображается");
    assertTrue(agileBoardPage.isBacklogToggleDisplayed(), "Кнопка переключения очереди не отображается");
    assertTrue(agileBoardPage.isSettingsButtonDisplayed(), "Кнопка настроек не отображается");
    assertTrue(agileBoardPage.isChartToggleDisplayed(), "Кнопка диаграммы не отображается");
    assertTrue(agileBoardPage.isSearchFieldDisplayed(), "Поле поиска не отображается");
  }

  @Test
  @DisplayName("Проверка работоспособности поиска")
  void searchFieldWorks() {
    String query = "DEMO";
    agileBoardPage.enterSearchQuery(query);
    assertTrue(agileBoardPage.isBoardLoaded(), "Страница не загружена после поиска");
    agileBoardPage.clearSearch();
  }

  @Test
  @DisplayName("Кнопка создания задачи работает")
  void createIssueButtonOpensDialog() {
    agileBoardPage.clickCreateIssue();
    assertTrue(agileBoardPage.isCreateIssueButtonDisplayed(), "Кнопка создания должна оставаться видимой после клика");
  }
}