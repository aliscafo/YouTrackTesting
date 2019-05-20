package ru.spbhse.erokhina.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button {
    private WebElement element;

    public Button(WebDriver driver, String elementId) {
        this.element = driver.findElement(By.id(elementId));
    }

    public void click() {
        element.click();
    }
}