package ru.spbhse.erokhina.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.spbhse.erokhina.elements.Button;
import ru.spbhse.erokhina.elements.TextField;

class CreatingUserForm {
    private Button okButton;
    private TextField loginField;
    private TextField passwordField;
    private TextField passwordConfirmationField;

    CreatingUserForm(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_l.U.cr.login")));

        okButton = new Button(driver, "id_l.U.cr.createUserOk");
        loginField = new TextField(driver, "id_l.U.cr.login");
        passwordField = new TextField(driver, "id_l.U.cr.password");
        passwordConfirmationField = new TextField(driver, "id_l.U.cr.confirmPassword");
    }

    void insertInfoAndClick(String login, String password, String confirmPassword) {
        loginField.insertText(login);
        passwordField.insertText(password);
        passwordConfirmationField.insertText(confirmPassword);
        okButton.click();
    }
}
