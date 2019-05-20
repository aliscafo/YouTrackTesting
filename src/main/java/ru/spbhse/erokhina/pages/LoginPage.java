package ru.spbhse.erokhina.pages;

import org.openqa.selenium.WebDriver;
import ru.spbhse.erokhina.elements.Button;
import ru.spbhse.erokhina.elements.TextField;

public class LoginPage {
    private TextField loginField;
    private TextField passwordField;
    private Button loginButton;

    public LoginPage(WebDriver driver) {
        loginButton = new Button(driver, "id_l.L.loginButton");
        loginField = new TextField(driver, "id_l.L.login");
        passwordField = new TextField(driver, "id_l.L.password");
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }
}
