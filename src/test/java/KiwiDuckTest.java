import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.AlertsPage;
import pages.TablePage;

public class KiwiDuckTest {
    private String nameMethod;

    @BeforeEach
    public void setUp() {
        ResultOutput.log("выполнение тестов класса KiwiDuckTest");
    }

    @Test
    public void testAlerts() {
        runTest("testAlerts", () -> {
            AlertsPage page = new AlertsPage();
            page.handleAlertAndInputPassword();
        });
    }

    @Test
    public void testInvalidPasswordAlerts() {
        runTest("testAlerts", () -> {
            AlertsPage page = new AlertsPage();
            page.negativePasswordEntry();
        });
    }

    @Test
    public void testTable() {
        runTest("testTable", () -> {
            TablePage page = new TablePage();
            page.manageCustomerRecords();
        });
    }

    private void runTest(String methodName, Runnable test) {
        nameMethod = methodName;
        ResultOutput.printTestStart(nameMethod);
        test.run();
    }

    @AfterEach
    public void tearDown() {
        ResultOutput.printTestEnd(nameMethod);
        WebDriverManager.closeDriver();
    }
}
