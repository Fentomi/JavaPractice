package practice.selenium.config;
import io.github.cdimascio.dotenv.Dotenv;


public class EnvLoader {
  public static final String BASE_URL = Dotenv.load().get("BASE_URL");
  public static final String BROWSER = Dotenv.load().get("BROWSER");
  public static final long IMPLICIT_WAIT = Long.parseLong(Dotenv.load().get("IMPLICIT_WAIT"));
  public static final long PAGE_LOAD_TIMEOUT = Long.parseLong(Dotenv.load().get("PAGE_LOAD_TIMEOUT"));

  public static final String ADMIN_LOGIN = Dotenv.load().get("ADMIN_LOGIN");
  public static final String ADMIN_PASSWORD = Dotenv.load().get("ADMIN_PASSWORD");

  public static final String USER_LOGIN = Dotenv.load().get("USER_LOGIN");
  public static final String USER_PASSWORD = Dotenv.load().get("USER_PASSWORD");
}
