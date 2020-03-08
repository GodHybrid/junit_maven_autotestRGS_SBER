import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class da
{
    WebDriver selectorDriver;

    @Before
    public void preparation()
    {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        selectorDriver = new ChromeDriver();
        selectorDriver.manage().window().maximize();
        selectorDriver.get("https://rgs.ru/");
        selectorDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        //execute(selectorDriver);
    }

    @Test
    public void execute()
    {
        WebDriverWait waitForLoad = new WebDriverWait(selectorDriver, 20);

        String menuClick = "//a[@class='hidden-xs' and @data-toggle='dropdown']";
        WebElement menuElement = selectorDriver.findElement(By.xpath(menuClick));
        clickOn(menuElement, waitForLoad);
        menuClick = "//a[contains(text(), 'ДМС')]";
        menuElement = selectorDriver.findElement(By.xpath(menuClick));
        clickOn(menuElement, waitForLoad);
        selectorDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        menuElement = selectorDriver.findElement(By.xpath("//h1[@class='content-document-header']"));
        //System.out.println(menuElement.getText());
        waitForLoad.until(ExpectedConditions.visibilityOf(menuElement));
        if(!menuElement.getText().contains("добровольное медицинское страхование"))
        {
            finisher();
        }

        menuClick = "//a[@data-product='VoluntaryMedicalInsurance' and contains(@class, 'desktop')]";
        menuElement = selectorDriver.findElement(By.xpath(menuClick));
        clickOn(menuElement, waitForLoad);
        selectorDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        selectorDriver.switchTo().activeElement();
        menuElement = selectorDriver.findElement(By.xpath("//div[@class='modal-header']/h4/b"));
        waitForLoad.until(ExpectedConditions.visibilityOf(menuElement));
        if(!menuElement.getText().contains("добровольное медицинское страхование"))
        {
            finisher();
        }

        menuClick = "//form[@id='applicationForm']/div[@class='row']";
        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[1]/input"));
        //waitForLoad.until(ExpectedConditions.elementToBeClickable(menuElement)).sendKeys("Агамиров");
        menuElement.sendKeys("Агамиров");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[2]/input"));
        menuElement.sendKeys("Александр");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[3]/input"));
        menuElement.sendKeys("Аркадьевич");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[4]/select"));
        Select menuRegion = new Select(menuElement);
        menuRegion.selectByVisibleText("Москва");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[5]/input"));
        menuElement.click();
        menuElement.sendKeys("9162141673");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[6]/input"));
        menuElement.sendKeys("qwertyqwerty");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[7]/input"));
        menuElement.click();
        menuElement.sendKeys("04042020");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[8]/textarea"));
        menuElement.sendKeys("According to all known laws of aviation, there is no way a bee should be able to fly. " +
                "Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible.");

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[9]/label/input"));
        menuElement.click();

        selectorDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[10]/div/button"));
        menuElement.click();

        menuElement = selectorDriver.findElement(By.xpath(menuClick + "/div[6]/div/label"));
        if(menuElement.isDisplayed())
        {
            System.out.println("All found");
        }
        else System.out.println("u gay");
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
