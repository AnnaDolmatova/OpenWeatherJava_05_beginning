import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AnnaDolmatovaTest extends BaseTest {
    final static String BASE_URL = "https://openweathermap.org/";
    final static By H_2_CITY_COUNTRY_HEADER = By.xpath("//div[@id='weather-widget']//h2");
    final static By SEARCH_CITY_FIELD = By.xpath("//input[@placeholder='Search city']");
    final static By SEARCH_BUTTON= By.xpath("//button[@type='submit']");
    final static By SEARCH_DROPDOWN_MENU = By.className("search-dropdown-menu");
    final static By PARIS_FRANCE_CHOICE_IN_DROPDOWN_MENU = By.xpath("//ul[@class = 'search-dropdown-menu']/li/span[text()='Paris, FR ']");
    private void openBaseURL() {
        getDriver().get(BASE_URL);
    }

    private void waitForLoadingFrameDisappear() {
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("own-loader-container")));
    }

    private String getText(By by, WebDriver driver) {
        return driver.findElement(by).getText();
    }

    private void click(By by, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    private void input(String text, By by, WebDriver driver) {
        driver.findElement(by).sendKeys(text);
    }

    private void waitElementToBeVisible(By by, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void waitTextToBeChanged(By by, String text, WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions
                .not(ExpectedConditions.textToBePresentInElement(driver.findElement(by), text)));

    }

    //explicitly wait -> BaseTest
    //WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

    @Test
    public void testH2TagTest_WhenSearchingCityCountry() {

        String cityName = "Paris";
        String expectedResult = "Paris, FR";

        openBaseURL();
        waitForLoadingFrameDisappear();

        String oldH2 = getText(H_2_CITY_COUNTRY_HEADER, getDriver());

        click(SEARCH_CITY_FIELD, getWait10());
        input(cityName, SEARCH_CITY_FIELD, getDriver());
        click(SEARCH_BUTTON, getWait10());
        waitElementToBeVisible(SEARCH_DROPDOWN_MENU, getWait10());
        click(PARIS_FRANCE_CHOICE_IN_DROPDOWN_MENU, getWait10());
        waitTextToBeChanged(H_2_CITY_COUNTRY_HEADER, oldH2, getDriver(), getWait10());

        String actualResult = getText(H_2_CITY_COUNTRY_HEADER, getDriver());

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
