package helpers.drivers;

import helpers.ResultOutput;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class WebDriverManager {
    protected WebDriver driver;

    public WebDriverManager(){
        driver = initializeDriver();
    }

    private WebDriver initializeDriver() {
        //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/linux/chromedriver");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/windows/chromedriver.exe");
        driver = new ChromeDriver();
        return driver;
    }

    /**
     * Загрузка страницы.
     *
     * @param url URL страницы для загрузки.
     */
    protected void loadPage(String url) {
        driver.get(url);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete"));

    }

    /**
     * Ожидание, пока указанный элемент не станет видимым.
     *
     * @param locator локатор элемента, который нужно ожидать.
     * @param timeout время ожидания в секундах.
     */
    protected void waitForElementVisible(By locator, int timeout) {
        ResultOutput.log("Проверка видимости элемента: " + locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void checkDriverInitialization() {
        if (driver == null) {
            throw new IllegalStateException("Отсутствует подключение драйвера ChromeDriver. Пожалуйста, убедитесь, что драйвер инициализирован.");
        }
    }

    public void closeDriver(){
        if (driver != null) {
            driver.quit();
        }
    }
}
