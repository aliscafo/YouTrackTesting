package ru.spbhse.erokhina.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField {
    private WebElement element;

    public TextField(WebDriver driver, String elementId) {
        this.element = driver.findElement(By.id(elementId));
    }

    public void insertText(String text) {
        element.sendKeys(text);
    }
}
