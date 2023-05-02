import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SovcombankOfficePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cityDropdown = By.xpath("//button[contains(@class, 'relative flex items-end')]");
    private final By cityInput = By.xpath("//input[@placeholder = 'Где вы находитесь']");
    private final By offices = By.xpath("//div[contains(@class,'office-info-container')]//div[contains(@class,'flex justify-between')]");
    private final By atms = By.xpath("//div[contains(@class,'office-info-container')]//div[contains(@class,'flex justify-between')]");

    public SovcombankOfficePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectCity(String cityName) {
        WebElement cityDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(cityDropdown));
        cityDropdownElement.click();

        WebElement cityInputElement = wait.until(ExpectedConditions.elementToBeClickable(cityInput));
        cityInputElement.sendKeys(cityName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, absolute')]//*[contains(text(), '" + cityName + "')]"))).click();
    }

    public int getNumberOfOffices() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(offices, 0));
        return driver.findElements(offices).size();
    }

    public int getNumberOfATMs() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(atms, 0));
        return driver.findElements(atms).size();
    }
}
