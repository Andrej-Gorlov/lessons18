package helpers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;
import java.util.Optional;

public class MenuNavigation {

    /**
     * Нажимает на ссылку "Great! Return to menu" и проверяет, что элемент с идентификатором "kiwiduckgithubio" видим на странице.
     *
     * @param driver экземпляр WebDriver, используемый для взаимодействия с веб-страницей.
     * @throws IllegalArgumentException если переданный WebDriver равен null.
     */
    public static void clickReturnToMenuAndVerifyElement(WebDriver driver,By locator, Optional<Alert> alert) {
        Assertions.assertNotNull(driver, "WebDriver не должен быть null");

        try {
            driver.findElement(locator).click();
            alert.ifPresent(Alert::accept);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Кнопка 'Return to Menu' не найдена.", e);
        }

        ResultOutput.log("Поиск элемента с идентификатором \"kiwiduckgithubio\"");
        WebElement element = driver.findElement(By.id("kiwiduckgithubio"));
        Assertions.assertTrue(element.isDisplayed(), "Элемент <h1> не видим!");
    }
}