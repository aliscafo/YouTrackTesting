package ru.spbhse.erokhina;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbhse.erokhina.pages.LoginPage;
import ru.spbhse.erokhina.pages.UsersPage;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginFieldTest {
    private static WebDriver driver;
    private static HashSet<String> initUsers;
    private static UsersPage usersPage;

    private static final String HOST = "http://localhost:8080";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static void goToUsersPage() {
        usersPage = new UsersPage(driver);
        List<String> users = usersPage.getUsers();
        initUsers = new HashSet<>();
        initUsers.addAll(users);
    }

    @BeforeClass
    public static void beforeMethod() {
        driver = new ChromeDriver();
        driver.get(HOST);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.getLoginField().insertText(LOGIN);
        loginPage.getPasswordField().insertText(PASSWORD);
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        goToUsersPage();
    }

    @AfterClass
    public static void afterMethod() {
        List<String> users = usersPage.getUsers();

        for (String user : users) {
            if (!initUsers.contains(user)) {
                usersPage.removeUser(user);
            }
        }

        driver.quit();
    }

    @Test
    public void userCreationTest() {
        assertFalse(initUsers.contains("user1"));

        boolean verdict = usersPage.createNewUser("user1", "password", "password", null);

        assertTrue(verdict);
        assertFalse(initUsers.contains("user1"));
        assertTrue(usersPage.getUsers().contains("user1"));
    }

    @Test
    public void sameUsersCreatingTest() {
        boolean verdict1 = usersPage.createNewUser("user2", "password", "password",
                null);
        boolean verdict2 = usersPage.createNewUser("user2", "password", "password",
                ".message.error");

        assertTrue(verdict1);
        assertFalse(verdict2);
    }

    @Test
    public void emptyLoginTest() {
        boolean verdict = usersPage.createNewUser("", "password", "password",
                ".error-bulb2");
        assertFalse(verdict);
        assertFalse(usersPage.getUsers().contains(""));
    }

    @Test
    public void numbersLoginTest() {
        boolean verdict = usersPage.createNewUser("7777", "password", "password",
                null);
        assertTrue(verdict);
        assertTrue(usersPage.getUsers().contains("7777"));
    }

    @Test
    public void wrongConfirmPasswordTest() {
        boolean verdict = usersPage.createNewUser("user3", "password", "password2",
                ".error-bulb2");
        assertFalse(verdict);
        assertFalse(usersPage.getUsers().contains("user3"));
    }

    @Test
    public void loginWithSpaceTest() {
        boolean verdict = usersPage.createNewUser("nice user4", "password", "password",
                ".message.error");
        assertFalse(verdict);
        assertFalse(usersPage.getUsers().contains(""));
    }
}
