package ru.spbhse.erokhina.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbhse.erokhina.elements.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersPage {
    private WebDriver driver;

    public UsersPage(WebDriver driver) {
        this.driver = driver;
        toUsersPage();
    }

    private void toUsersPage() {
        WebElement settings = driver.findElement(By.cssSelector(".ring-menu__item__i.ring-font-icon.ring-font-icon_cog"));
        settings.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ring-dropdown__item.ring-link")));
        List<WebElement> dropdownOptions = driver.findElements(By.cssSelector(".ring-dropdown__item.ring-link"));

        WebElement usersOption = null;

        for (WebElement element : dropdownOptions) {
            if (element.getAttribute("href").contains("/users")) {
                usersOption = element;
            }
        }

        Objects.requireNonNull(usersOption).click();
        wait.until(ExpectedConditions.urlContains("/users"));
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<>();

        WebElement table = driver.findElement(By.cssSelector(".table.users-table tbody"));
        List<WebElement> elements = table.findElements(By.tagName("tr"));

        for (WebElement elem : elements) {
            users.add(elem.findElement(By.cssSelector("*[cn='l.U.usersList.UserLogin.editUser']")).getText());
        }

        return users;
    }

    private void createUserBasic(String login, String password, String confirmPassword) {
        new Button(driver, "id_l.U.createNewUser").click();

        CreatingUserForm creatingUserForm = new CreatingUserForm(driver);
        creatingUserForm.insertInfoAndClick(login, password, confirmPassword);
    }

    /**
     * Returns true if user was created, false otherwise.
     * @param login login
     * @param password password
     */
    public boolean createNewUser(String login, String password, String confirmPassword, String expectedElem) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        createUserBasic(login, password, confirmPassword);

        boolean verdict;

        if (expectedElem == null) {
            wait.until(ExpectedConditions.urlContains("/editUser"));
            verdict = true;
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(expectedElem)));

            Button cancelButton = new Button(driver, "id_l.U.cr.createUserCancel");
            cancelButton.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("id_l.U.cr.login")));

            verdict = false;
        }

        toUsersPage();

        return verdict;
    }

    public void removeUser(String login) {
        WebElement table = driver.findElement(By.cssSelector(".table.users-table tbody"));
        List<WebElement> elements = table.findElements(By.tagName("tr"));

        WebElement userElement = null;

        for (WebElement element : elements) {
            if (element.findElement(By.cssSelector("*[cn='l.U.usersList.UserLogin.editUser']")).getText().equals(login)) {
                userElement = element;
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, 5);
        Objects.requireNonNull(userElement).findElement(By.cssSelector("*[cn='l.U.usersList.deleteUser']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.stalenessOf(userElement));
    }
}