import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AutomationPracticeFormTest {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("https://demoqa.com/automation-practice-form");
    }

    @Test
    public void submitFormWithValidData() {
        // Заполнение полей формы валидными данными
        WebElement firstNameInput = driver.findElement(By.id("firstName"));
        firstNameInput.sendKeys("John");

        WebElement lastNameInput = driver.findElement(By.id("lastName"));
        lastNameInput.sendKeys("Doe");

        WebElement userEmailInput = driver.findElement(By.id("userEmail"));
        userEmailInput.sendKeys("johndoe@mail.com");

        WebElement genderRadioMale = driver.findElement(By.xpath("//label[@for='gender-radio-1']"));
        genderRadioMale.click();

        WebElement userNumberInput = driver.findElement(By.id("userNumber"));
        userNumberInput.sendKeys("1234567890");

        WebElement dateOfBirthInput = driver.findElement(By.id("dateOfBirthInput"));
        dateOfBirthInput.click();

        WebElement monthSelect = driver.findElement(By.className("react-datepicker__month-select"));
        monthSelect.click();
        WebElement selectedMonth = driver.findElement(By.xpath("//option[@value='0']"));
        selectedMonth.click();

        WebElement yearSelect = driver.findElement(By.className("react-datepicker__year-select"));
        yearSelect.click();
        WebElement selectedYear = driver.findElement(By.xpath("//option[@value='1990']"));
        selectedYear.click();

        WebElement dateOfMonth = driver.findElement(By.xpath("//*[contains(@class, 'react-datepicker__day react-datepicker__day--004')]"));
        dateOfMonth.click();

        WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
        subjectsInput.sendKeys("English");
        subjectsInput.sendKeys(Keys.ENTER);

        WebElement hobbiesCheckbox = driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", hobbiesCheckbox);
        //hobbiesCheckbox.click();

        WebElement addressInput = driver.findElement(By.xpath("//*[contains(@placeholder, 'Current Address')]"));
        addressInput.sendKeys("123 Main street, NY");

        WebElement stateInput = driver.findElement(By.xpath("//*[contains(text(), 'Select State')]/following::*[contains(@id, 'react-select-3-input')]"));
        stateInput.sendKeys("NCR");
        stateInput.sendKeys(Keys.ENTER);

        WebElement cityInput = driver.findElement(By.xpath("//*[contains(text(), 'Select City')]/following::*[contains(@id, 'react-select-4-input')]"));
        cityInput.sendKeys("Delhi");
        cityInput.sendKeys(Keys.ENTER);

        // Нажатие на кнопку Submit
        WebElement submitButton = driver.findElement(By.id("submit"));
        //submitButton.click();
        js.executeScript("arguments[0].click();", submitButton);

        // Проверка корректности заполнения формы
        WebElement thanksPopup = driver.findElement(By.className("modal-content"));
        String thanksMessage = thanksPopup.findElement(By.className("modal-header")).getText();
        Assertions.assertEquals("Thanks for submitting the form", thanksMessage);
    }

    @Test
    public void validateRequiredFields() {
        // Нажатие на кнопку Submit без заполнения полей
        WebElement submitButton = driver.findElement(By.id("submit"));
        //submitButton.click();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submitButton);
        if(driver.findElements(By.className("modal-content")).size() == 0) {
            System.out.println("Required fields must be filled");
        }
    }

    @Test
    public void test() {
        WebElement submitButton = driver.findElement(By.id("submit"));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
