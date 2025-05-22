package pages;

import helpers.MenuNavigation;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Optional;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertsPage extends WebDriverManager {

    private final WebDriverWait wait;
    private Alert alert;

    public AlertsPage(){
        checkDriverInitialization();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ResultOutput.log("Загруска страницы /alerts");
        loadPage("https://kiwiduck.github.io/alerts");
    }

    public void handleAlertAndInputPassword() {
        logAndExecute("Получение пароля из всплывающего окна", () -> {
            String password = getPasswordFromAlert();
            logAndExecute("Ввод пароля", () -> inputPassword(password));
            logAndExecute("Проверка наличия сообщения об успешном выполнении операции", this::verifySuccessMessage);

            logAndExecute("Переход в меню и проверка наличия элемента", () ->
                    MenuNavigation.clickReturnToMenuAndVerifyElement(driver, By.className("return"), Optional.of(alert))
            );
        });
    }

    public void negativePasswordEntry() {
        logAndExecute("Получение пароля из всплывающего окна", () -> {
            String password = getPasswordFromAlert();
            logAndExecute("Ввод неверного пароля, добавляя некорректные символы", () -> inputPassword(password + "ххх"));

            assertTrue(driver.findElements(By.xpath("//label[text()='Great!']")).isEmpty());
            assertTrue(driver.findElements(By.xpath("//button[text()='Return to menu']")).isEmpty());
        });
    }

    private void logAndExecute(String message, Runnable action) {
        ResultOutput.log(message);
        action.run();
    }

    /**
     * Получает пароль из всплывающего окна.
     *
     * Ожидает, пока элемент с классом 'get' станет видимым,
     * затем кликает на него, после чего ожидает появления
     * всплывающего окна (alert). Из текста всплывающего окна извлекается
     * пароль, который возвращается в виде строки.
     * Всплывающее окно подтверждается после извлечения пароля.
     *
     * @return пароль, извлеченный из текста всплывающего окна
     */
    private String getPasswordFromAlert() {
        waitForElementVisible(By.className("get"));
        driver.findElement(By.className("get")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText.split(": ")[1];
    }

    /**
     * Вводит пароль во всплывающее окно.
     *
     *  Ожидает, пока элемент с классом 'set' станет видимым,
     * затем кликает на него, после чего ожидает появления
     * всплывающего окна (alert) и вводит указанный пароль.
     * После ввода пароль подтверждается.
     *
     * @param password пароль, который необходимо ввести во всплывающее окно
     */
    private void inputPassword(String password) {
        waitForElementVisible(By.className("set"));
        driver.findElement(By.className("set")).click();
        alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(password);
        alert.accept();
    }

    /**
     * Проверяет наличие сообщения об успешном выполнении операции.
     *
     *  Ожидает, пока элемент с текстом 'Great!' станет видимым,
     * и затем проверяет, отображаются ли элементы сообщения об успехе
     * и кнопка 'Return to menu'. Если элементы не отображаются,
     * будет выброшено исключение.
     */
    private void verifySuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Great!']")));
        assertTrue(driver.findElement(By.xpath("//label[text()='Great!']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//button[text()='Return to menu']")).isDisplayed());
    }

    private void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
