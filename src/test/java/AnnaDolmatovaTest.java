import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;

public class AnnaDolmatovaTest extends BaseTest {
    final static String BASE_URL = "https://openweathermap.org/";
    final static By H_2_CITY_COUNTRY_HEADER = By.xpath("//div[@id='weather-widget']//h2");
    final static By SEARCH_CITY_FIELD = By.xpath("//input[@placeholder='Search city']");
    final static By SEARCH_BUTTON= By.xpath("//button[@type='submit']");
    final static By SEARCH_DROPDOWN_MENU = By.className("search-dropdown-menu");
    final static By PARIS_FRANCE_CHOICE_IN_DROPDOWN_MENU = By.xpath(
            "//ul[@class = 'search-dropdown-menu']/li/span[text()='Paris, FR ']");
    final static By GUIDE_ELEMENT_IN_MENU = By.xpath("//a[@href='/guide']");
    final static By GUIDE_TITLE_ON_GUIDE_PAGE = By.xpath("//h1[@class='breadcrumb-title']");
    final static By MENU_IMPERIAL = By.xpath("//div[@class='option'][text()='Imperial: °F, mph']");
    final static By TEMPERATURE_INFORMATION = By.xpath("//span[@class='heading']");
    final static By COOKIE_PANEL = By.xpath("//div[@class='stick-footer-panel']");
    final static  By COOKIES_PANEL_DESCRIPTION = By.xpath("//p[@class='stick-footer-panel__description']");
    final static By SUPPORT_BUTTON = new By.ById("support-dropdown");
    final static By SUPPORT_DROPDOWN_ASK_A_QUESTION = By.xpath("//ul[@id='support-dropdown-menu']/li[3]");
    final static By QUESTION_FORM_EMAIL = new By.ById("question_form_email");
    final static By QUESTION_FORM_SUBJECT = new By.ById("question_form_subject");
    final static By QUESTION_FORM_MESSAGE = new By.ById("question_form_message");
    final static By QUESTION_FORM_SUBMIT_BUTTON = By.xpath("//input[@class='btn btn-default']");
    final static By CHECKBOX_CAPTCHA= By.xpath("//div[@class='recaptcha-checkbox-border']");
    final static By NAVIGATION_FIELD = By.xpath("//div[@id='desktop-menu']/form/input[1]");

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
    public void testGuideUrlAndHeader() {

        String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";
        String expectedResultUrl = "https://openweathermap.org/guide";

        openBaseURL();
        waitForLoadingFrameDisappear();

        click(GUIDE_ELEMENT_IN_MENU, getWait10());
        waitElementToBeVisible(GUIDE_TITLE_ON_GUIDE_PAGE, getWait10());

        String actualResultUrl = getDriver().getCurrentUrl();
        String actualResultTitle = getDriver().getTitle();

        Assert.assertEquals(actualResultTitle, expectedResultTitle);
        Assert.assertEquals(actualResultUrl, expectedResultUrl);

    }

    @Test
    public void testChangeMetricOfTheTemperature() {

        openBaseURL();
        waitForLoadingFrameDisappear();

        String oldMetric = getText(TEMPERATURE_INFORMATION, getDriver());

        click(MENU_IMPERIAL, getWait10());

        waitTextToBeChanged(TEMPERATURE_INFORMATION, oldMetric, getDriver(), getWait10());

        String actualMetric = getText(TEMPERATURE_INFORMATION, getDriver());

        Assert.assertNotEquals(oldMetric, actualMetric);
        Assert.assertTrue(actualMetric.contains("°F"));
    }
    @Test
    public void testCookiesPanel() {

        openBaseURL();
        waitForLoadingFrameDisappear();

        String expectedDescription = "We use cookies which are essential for " +
                "the site to work. We also use non-essential cookies to help us " +
                "improve our services. Any data collected is anonymised. " +
                "You can allow all cookies or manage them individually.";
        String button1Text = "Allow all";
        String button2Text = "Manage cookies";

        waitElementToBeVisible(COOKIE_PANEL, getWait10());

        String actualCookiesPanelDescription = getText(COOKIES_PANEL_DESCRIPTION, getDriver());

        Assert.assertEquals(actualCookiesPanelDescription, expectedDescription);
        Assert.assertEquals(getDriver().findElements(
                By.cssSelector(".stick-footer-panel__link")).size(), 2);
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//button[@class='stick-footer-panel__link']")).getText().contains(button1Text));
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//a[@class='stick-footer-panel__link']")).getText().contains(button2Text));
    }

    @Test
    public void testSupportDropDownMenu() {

        String button1Text = "FAQ";
        String button2Text = "How to start";
        String button3Text = "Ask a question";

        openBaseURL();
        waitForLoadingFrameDisappear();

        getDriver().manage().window().maximize();
        click(SUPPORT_BUTTON, getWait10());
        waitElementToBeVisible(By.xpath("//ul[@id='support-dropdown-menu']/li"), getWait10());

        Assert.assertEquals(getDriver().findElements(
                By.xpath("//ul[@id='support-dropdown-menu']/li")).size(), 3);
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li[1]")).getText().contains(button1Text));
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li[2]")).getText().contains(button2Text));
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//ul[@id='support-dropdown-menu']/li[3]")).getText().contains(button3Text));
    }

    @Test
    public void testAskAQuestionTabFail() {

        String expectedResult = "reCAPTCHA verification failed, please try again.";
        String email = "google1@gmail.com";
        String message = "Hello";

        openBaseURL();
        waitForLoadingFrameDisappear();

        getDriver().manage().window().maximize();

        click(SUPPORT_BUTTON, getWait10());
        click(SUPPORT_DROPDOWN_ASK_A_QUESTION, getWait10());

        ArrayList<String> tabs2 = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs2.get(1));

        getDriver().manage().window().maximize();

        input(email, QUESTION_FORM_EMAIL, getDriver());
        click(QUESTION_FORM_SUBJECT, getWait10());
        click(By.xpath("//option[@value='Sales']"), getWait10());
        input(message, QUESTION_FORM_MESSAGE, getDriver());
        click(QUESTION_FORM_SUBMIT_BUTTON, getWait10());

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@class='help-block']")).getText().contains(expectedResult));
        getDriver().close();
    }
    @Ignore
    @Test
    public void testConfirmErrorInEmailField() {

        String expectedResult = "can't be blank";
        String message = "Hello";

        openBaseURL();
        waitForLoadingFrameDisappear();

        getDriver().manage().window().maximize();

        click(SUPPORT_BUTTON, getWait10());
        click(SUPPORT_DROPDOWN_ASK_A_QUESTION, getWait10());

        ArrayList<String> tabs2 = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs2.get(1));

        getDriver().manage().window().maximize();

        click(QUESTION_FORM_SUBJECT, getWait10());
        click(By.xpath("//option[@value='Sales']"), getWait10());
        input(message, QUESTION_FORM_MESSAGE, getDriver());

        WebElement iframeCaptchaWindow = getDriver().findElement(By.xpath("//iframe[@title='reCAPTCHA']"));
        getDriver().switchTo().frame(iframeCaptchaWindow);

        click(CHECKBOX_CAPTCHA, getWait10());

        getDriver().switchTo().window(tabs2.get(1));
        click(QUESTION_FORM_SUBMIT_BUTTON, getWait10());

        waitElementToBeVisible(By.xpath("//span[@class='help-block']"), getWait10());
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//span[@class='help-block']")).getText().contains(expectedResult));

        getDriver().close();
    }

    @Test
    public void testReloadPageSuccessfully() {

        openBaseURL();
        waitForLoadingFrameDisappear();

        click(By.xpath("//ul[@id='first-level-nav']/li/a/img"), getWait10());

        waitForLoadingFrameDisappear();

        String actualUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(actualUrl, BASE_URL);
    }

    @Test
    public void testSearchRome() {

        String cityName = "Rome";

        openBaseURL();
        waitForLoadingFrameDisappear();
        input(cityName, NAVIGATION_FIELD, getDriver());
        getDriver().findElement(NAVIGATION_FIELD).sendKeys(Keys.ENTER);
        waitElementToBeVisible(By.xpath("//h2[@class='headline first-child text-color']"), getWait10());

        Assert.assertTrue(getDriver().getCurrentUrl().contains("find"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("Rome"));
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//input[@id='search_str']")).getAttribute("value").contains("Rome"));
    }

    @Test
    public void testApiPageOrangeButtons() {

        openBaseURL();
        waitForLoadingFrameDisappear();

        click(By.xpath("//div[@id='desktop-menu']/ul/li[2]/a"), getWait10());
        waitElementToBeVisible(By.xpath("//h1[@class='breadcrumb-title']"), getWait10());

        Assert.assertEquals(getDriver().findElements(
                By.xpath("//a[contains(@class,'orange')]")).size(), 30);
    }
}
