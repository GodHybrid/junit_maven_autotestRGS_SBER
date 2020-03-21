import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Sber
{
    WebDriver selectorDriver;
    WebDriverWait waitForLoad;

    @BeforeEach
    public void preparation()
    {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        selectorDriver = new ChromeDriver();
        selectorDriver.manage().window().maximize();
        selectorDriver.get("https://sberbank.ru/ru/person");
        selectorDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        waitForLoad = new WebDriverWait(selectorDriver, 20);
    }

    @Test
    public void execute()
    {
        String menuXPath = "//div[contains(@class,'header__region')]/div/a";
        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXPath)));
        WebElement menuElement = selectorDriver.findElement(By.xpath(menuXPath));
        clickOn(menuElement);
        selectorDriver.switchTo().activeElement();

        String inputXPath = "//div[@class='kit-grid-modal__window']/div/div/div/div/input";
        WebElement inputElement = selectorDriver.findElement(By.xpath(inputXPath));
        inputElement.sendKeys("Нижегородская");
        inputElement.sendKeys(Keys.ENTER);

        waitForLoad.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXPath)));
        menuElement = selectorDriver.findElement(By.xpath(menuXPath));

        String cityXPath = "//div[@class='hd-ft-region__title']/span";
        WebElement cityElement = selectorDriver.findElement(By.xpath(cityXPath));

        Assertions.assertTrue((cityElement.getText().contains("Нижегородская")));

        String footerXPath = "//footer";
        WebElement footerElement = selectorDriver.findElement((By.xpath(footerXPath)));

        JavascriptExecutor je = (JavascriptExecutor) selectorDriver;
        je.executeScript("arguments[0].scrollIntoView();", footerElement);
        waitForLoad.until(ExpectedConditions.visibilityOf(footerElement));

        //String footerSocialXPath = "//ul[@class='footer__social']";
        WebElement facebookSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'facebook')]"));
        WebElement twitterSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'twitter')]"));
        WebElement youtubeSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'youtube')]"));
        WebElement instagramSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'instagram')]"));
        WebElement vkSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'vk.com')]"));
        WebElement okSocialElement = selectorDriver.findElement(By.xpath("//a[contains(@href,'ok.ru')]"));

        Assertions.assertTrue(facebookSocialElement.isDisplayed() && twitterSocialElement.isDisplayed() && youtubeSocialElement.isDisplayed() && instagramSocialElement.isDisplayed() && vkSocialElement.isDisplayed() && okSocialElement.isDisplayed());
        System.out.println("Congrats!");
    }

    private void clickOn(WebElement me)
    {
        waitForLoad.until(ExpectedConditions.elementToBeClickable(me));
        me.click();
    }

    @AfterEach
    public void finisher()
    {
        selectorDriver.quit();
    }
}
