import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AnnaDolmatovaTest extends BaseTest {

    @Test
    public void testH2TagTest_WhenSearchingCityCountry() throws InterruptedException {

        String url = "https://openweathermap.org/";
        String cityName = "Paris";
        String expectedResult = "Paris, FR";

        getDriver().get(url);

        Thread.sleep(5000);

        WebElement searchCityField = getDriver().findElement(
                By.xpath("//input[@placeholder='Search city']"));
        searchCityField.click();
        searchCityField.sendKeys(cityName);
        WebElement searchButton = getDriver().findElement(
                By.xpath("//button[@type='submit']"));
        searchButton.click();

        Thread.sleep(3000);

        WebElement parisFranceChoiceInDropDownMenu = getDriver().findElement(
                By.xpath("//ul[@class = 'search-dropdown-menu']/li/span[text()='Paris, FR ']"));
        parisFranceChoiceInDropDownMenu.click();

        Thread.sleep(3000);

        WebElement h2CityCountryHeader = getDriver().findElement(
                By.xpath("//div[@class='current-container mobile-padding']/div/h2")
        );

        String actualResult = h2CityCountryHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
