package pages;

import helpers.MenuNavigation;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import helpers.enums.InputValues;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TablePage extends WebDriverManager {

    public TablePage(){
        checkDriverInitialization();
        ResultOutput.log("Загруска страницы /table");
        loadPage("https://kiwiduck.github.io/table");
    }

    public void manageCustomerRecords(){
        ResultOutput.log("Удаление отмеченных записей клиентов");
        deleteCheckedRows();
        ResultOutput.log("Заполнение полей ввода значениями");
        fillInputFields();

        waitForElementAndFind(By.xpath("//input[@value='Add']")).click();
        WebElement returnToMenu = waitForElementAndFind(By.id("back"));
        assertTrue(returnToMenu.isDisplayed());

        ResultOutput.log("Переход в меню и проверка наличия элемента");
        MenuNavigation.clickReturnToMenuAndVerifyElement(driver, By.id("back"), Optional.empty());
    }


    /**
     * Удаляет отмеченные строки из таблицы клиентов.
     *
     * Находит все строки в таблице с идентификатором 'customers',
     * отмечает чекбоксы в каждой строке и инициирует процесс удаления.
     * После каждого удаления обновляется список строк, чтобы гарантировать,
     * что все отмеченные строки будут удалены, даже если таблица изменяется.
     */
    private void deleteCheckedRows() {
        List<WebElement> rows = waitForElementsAndFind(By.xpath("//table[@id='customers']//tr"));

        for (int i = 1; i < rows.size(); i++) { // начинаем с 1, чтобы пропустить заголовок
            WebElement checkbox = rows.get(i).findElement(By.xpath(".//input[@type='checkbox']"));
            checkbox.click();

            waitForElementAndFind(By.xpath("//input[@value='Delete']")).click();

            // Обновление строк после удаления
            rows = waitForElementsAndFind(By.xpath("//table[@id='customers']//tr"));
            i--;
        }
    }

    /**
     * Заполняет текстовые поля значениями из перечисления InputValues.
     *
     * Находит все текстовые поля на странице и заполняет их значениями,
     * соответствующими перечислению InputValues. Если количество текстовых полей
     * меньше, чем количество значений в перечислении, будут заполнены только
     * доступные текстовые поля.
     */
    public void fillInputFields() {
        List<WebElement> inputs = waitForElementsAndFind(By.cssSelector("input[type='text']"));
        InputValues[] values = InputValues.values();

        IntStream.range(0, Math.min(values.length, inputs.size()))
                .forEach(i -> inputs.get(i).sendKeys(values[i].getValue()));
    }

    private List<WebElement> waitForElementsAndFind(By locator) {
        waitForElementVisible(locator, 10);
        return driver.findElements(locator);
    }

    private WebElement waitForElementAndFind(By locator) {
        waitForElementVisible(locator, 10);
        return driver.findElement(locator);
    }
}
