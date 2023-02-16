import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

    @Test
    public void testGuideUrlAndHeader() throws InterruptedException {

        String url = "https://openweathermap.org/";
        String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";
        String expectedResultUrl = "https://openweathermap.org/guide";

        getDriver().get(url);

        Thread.sleep(5000);

        WebElement guideElementInMenu =  getDriver().findElement(
                By.xpath("//a[@href='/guide']"));
        guideElementInMenu.click();

        Thread.sleep(2000);

        String actualResultUrl = getDriver().getCurrentUrl();
        String actualResultTitle = getDriver().getTitle();

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertEquals(actualResultUrl, expectedResultUrl);

    }

    @Test
    public void testChangeMetricOfTheTemperature() throws InterruptedException {

        String url = "https://openweathermap.org/";
        String expectedResult = "°F";

        getDriver().get(url);

        Thread.sleep(5000);

        WebElement menuImperial = getDriver().findElement(
                By.xpath("//div[@class='option'][text()='Imperial: °F, mph']"));

        menuImperial.click();

        Thread.sleep(2000);

        WebElement temperatureF = getDriver().findElement(
                By.xpath("//span[@class='heading']"));

        Assert.assertTrue(temperatureF.getText().contains("°F"));
    }
}
