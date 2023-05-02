import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;

public class SovcombankOfficeTests {
    private static WebDriver driver;
    private static SovcombankOfficePage page;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://sovcombank.ru/office");
        page = new SovcombankOfficePage(driver);
    }

    @Test
    public void testOfficeFilter() {
        page.selectCity("Москва");
        int numberOfOfficesInMoscow = page.getNumberOfOffices();

        assertThat(numberOfOfficesInMoscow, Matchers.greaterThan(0));

        for (int i = 0; i < numberOfOfficesInMoscow; i++) {
            String officeCity = driver.findElements(By.xpath("//div[contains(@class,'office-info-container')]//div[contains(@class,'flex justify-between')]//*[contains(@class, 'text-lg font-medium')]"))
                    .get(i)
                    .getText();
            assertThat(officeCity, Matchers.containsString("Москва"));

        }
    }

    @Test
    public void testATMFilter() {
        page.selectCity("Саратов");
        int numberOfATMsInSaratov = page.getNumberOfATMs();

        assertThat(numberOfATMsInSaratov, Matchers.greaterThan(0));

        for (int i = 0; i < numberOfATMsInSaratov; i++) {
            String atmCity = driver.findElements(By.xpath("//div[contains(@class,'office-info-container')]//div[contains(@class,'flex justify-between')]//*[contains(@class, 'text-lg font-medium')]"))
                    .get(i)
                    .getText();
            assertThat(atmCity, Matchers.containsString("Саратов"));
        }
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}