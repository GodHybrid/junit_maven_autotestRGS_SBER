import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class sber
{
    WebDriver selectorDriver;

    @Before
    public void preparation()
    {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        selectorDriver = new ChromeDriver();
        selectorDriver.manage().window().maximize();
        selectorDriver.get("https://sberbank.ru/ru/person");
        selectorDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @Test
    public void execute()
    {
        WebDriverWait waitForLoad = new WebDriverWait(selectorDriver, 20);

        String menuClick = "//div[contains(@class,'header__region')]/div/a/div";
        WebElement menuElement = selectorDriver.findElement(By.xpath(menuClick));
        clickOn(menuElement, waitForLoad);
        selectorDriver.switchTo().activeElement();
        menuClick = "//div[@class='kit-grid-modal__window']/div/div/div/div/input";
        menuElement = selectorDriver.findElement(By.xpath(menuClick));
        menuElement.sendKeys("Нижегородская");
        menuClick = "//a[@class='kit-link kit-link_m hd-ft-region__city']";
        menuElement = selectorDriver.findElement(By.xpath(menuClick));
        clickOn(menuElement, waitForLoad);
        selectorDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        menuClick = "//div[@class='hd-ft-region__title']/span";
        menuElement = selectorDriver.findElement(By.xpath(menuClick));
        if(!(menuElement.getText().contains("Нижегородская")))
        {
            finisher();
        }
        JavascriptExecutor je = (JavascriptExecutor) selectorDriver;
        menuClick = "//footer";
        menuElement = selectorDriver.findElement((By.xpath(menuClick)));
        je.executeScript("arguments[0].scrollIntoView();", menuElement);
        waitForLoad.until(ExpectedConditions.visibilityOf(menuElement));
        menuClick = "//ul[@class='footer__social']";
        menuElement = selectorDriver.findElement(By.xpath((menuClick)));
        if(menuElement.isDisplayed())
        {
            System.out.println("All done.");
        }
        else finisher();
    }

    private void clickOn(WebElement me, WebDriverWait w)
    {
        w.until(ExpectedConditions.visibilityOf(me));
        me.click();
    }

    @After
    public void finisher()
    {
        selectorDriver.quit();
    }
}
