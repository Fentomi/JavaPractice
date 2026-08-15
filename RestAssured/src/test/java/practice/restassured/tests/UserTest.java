package practice.restassured.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import practice.restassured.dto.User;
import practice.restassured.specifications.Specifications;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest {
  private final String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  private final String login = "testUser_" + randomUUID;
  private final String fullName = "Test User " + randomUUID;
  private final String email = login + "@mail.ru";
  private final String password = "password" + randomUUID;
  private User createdUser;

  @BeforeEach
  void setUp() {
    createdUser = userApi.createUser(login,fullName,email,password)
        .then().spec(Specifications.response200())
        .extract().as(User.class);
    System.out.println("Создан пользователь: " + createdUser);
  }

  @AfterEach
  void tearDown() {
    userApi.deleteUser(createdUser.getId(), "2-1");
    System.out.println("Пользователь " + createdUser.getLogin() + " удален");
  }

  @Test
  @DisplayName("Создание пользователя - проверка полей")
  void createUserTest() {
    assertEquals(createdUser.getLogin(), login);
    assertEquals(createdUser.getEmail(), email);
    assertEquals(createdUser.getFullName(), fullName);
    assertNotNull(createdUser.getId());
  }
  
  @Test
  @DisplayName("Бан пользователя")
  void banUserTest() {
    userApi.banUser(createdUser.getId())
        .then().spec(Specifications.response200())
        .body("banned", equalTo(true));
  }

  @Test
  @DisplayName("Разбан пользователя")
  void unbanUserTest() {
    userApi.banUser(createdUser.getId());
    userApi.unbanUser(createdUser.getId())
        .then().spec(Specifications.response200())
        .body("banned", equalTo(false));
  }

  @Test
  @DisplayName("Получение списка пользователей - список не пуст")
  void getAllUsersTest() {
    userApi.getAllUsers()
        .then().spec(Specifications.response200())
        .body("size()", greaterThan(0));
  }

  @Test
  @DisplayName("Получение пользователя по айди - проверка полей")
  void getUserByIdTest() {
    userApi.getUserById(createdUser.getId())
        .then().spec(Specifications.response200())
        .body("login", equalTo(createdUser.getLogin()))
        .body("fullName", equalTo(createdUser.getFullName()))
        .body("email", equalTo(createdUser.getEmail()));
  }

  @ParameterizedTest
  @DisplayName("Получение пользователя по логину и имени")
  @CsvSource({"admin, admin"})
  void getUserUsedLoginAndFullNameTest(String login, String fullName){
    userApi.getUserByLoginAndFullName(login, fullName)
        .then().spec(Specifications.response200())
        .body("login", equalTo(login))
        .body("fullName", equalTo(fullName));
  }

  //TODO: сделать тест на изменение данных пользователя через userApi.updateUser();

  @Test
  @DisplayName("Удаление пользователя")
  void deleteUserTest() {
    userApi.deleteUser(createdUser.getId(), "2-1")
        .then().spec(Specifications.response200());
  }

  @ParameterizedTest
  @DisplayName("Попытка получить несуществующего пользователя - 404 ошибка")
  @CsvSource({"invalid-id-12345"})
  void getUserByInvalidId(String id) {
    userApi.getUserById(id)
        .then().spec(Specifications.response404())
        .body("error", equalTo("Not Found"));
  }

  @ParameterizedTest
  @DisplayName("Попытка создать пользователя без пароля - 400 ошибка")
  @CsvSource({"NoPasswordLogin, NoPasswordUser, nopassword@mail.ru"})
  void createUserWithoutPassword(String login, String fullName, String email) {
    userApi.createUser(login, fullName, email)
        .then().spec(Specifications.response400())
        .body("error", equalTo("Bad Request"));
  }

  @ParameterizedTest
  @DisplayName("Создание пользователя через вводные параметры - проверка полей")
  @Disabled("Включать тест только в случае уникальных CsvSource данных")
  @CsvSource({"yourLogin, yourFullName, yourEmail@mail.ru, yourPassword"})
  void createUserWithParameters(String login, String fullName, String email, String password) {
    userApi.createUser(login, fullName, email, password)
        .then().spec(Specifications.response200())
        .body("login", equalTo(login))
        .body("email", equalTo(email))
        .body("fullName", equalTo(fullName))
        .body("id", notNullValue());
  }
}
