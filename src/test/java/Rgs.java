import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class Rgs
{
    WebDriver selectorDriver;
    WebDriverWait waitForLoad;

    @Before
    public void preparation()
    {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        selectorDriver = new ChromeDriver();
        selectorDriver.manage().window().maximize();
        selectorDriver.get("https://rgs.ru/");
        selectorDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        waitForLoad = new WebDriverWait(selectorDriver, 20);
    }

    @Test
    public void execute()
    {
        ///Main menu
        String menuXPath = "//a[@class='hidden-xs' and @data-toggle='dropdown']";
        WebElement menuElement = selectorDriver.findElement(By.xpath(menuXPath));
        clickOn(menuElement);
        //Sub menu
        String dmsXPath = "//a[contains(text(), 'ДМС')]";
        WebElement subMenuElement = selectorDriver.findElement(By.xpath(dmsXPath));
        clickOn(subMenuElement);
        //New page, header
        String headerXPath = "//h1[@class='content-document-header']";
        waitForLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(headerXPath)));
        WebElement headerPostPageElement = selectorDriver.findElement(By.xpath(headerXPath));
        //Header check
        Assert.assertTrue(headerPostPageElement.getText().contains("добровольное медицинское страхование"));
        //Query button
        String sendQueryXPath = "//a[@data-product='VoluntaryMedicalInsurance' and contains(@class, 'desktop')]";
        WebElement beginButtonElement = selectorDriver.findElement(By.xpath(sendQueryXPath));
        clickOn(beginButtonElement);
        //New modal window, switching
        String modalWindowXPath = "//div[@class='modal-header']";
        WebElement modalWindowElement = selectorDriver.findElement(By.xpath(modalWindowXPath));
        waitForLoad.until(ExpectedConditions.visibilityOf(modalWindowElement));
        selectorDriver.switchTo().activeElement();
        //Header
        String modalWindowHeaderXPath = "//div[@class='modal-header']/h4/b";
        WebElement modalWindowHeaderElement = selectorDriver.findElement(By.xpath(modalWindowHeaderXPath));
        waitForLoad.until(ExpectedConditions.visibilityOf(modalWindowHeaderElement));
        //Header check
        Assert.assertTrue(modalWindowHeaderElement.getText().contains("добровольное медицинское страхование"));
        //Fill field: surname
        String lastName = "//*[text()='Фамилия']/parent::div/input";
        WebElement lastNameElement = selectorDriver.findElement(By.xpath(lastName));
        lastNameElement.sendKeys("Агамиров");
        //Fill field: name
        String firstName = "//*[text()='Имя']/parent::div/input";
        WebElement firstNameElement = selectorDriver.findElement(By.xpath(firstName));
        firstNameElement.sendKeys("Александр");
        //Fill field: paternity
        String paternity = "//*[text()='Отчество']/parent::div/input";
        WebElement paternityElement = selectorDriver.findElement(By.xpath(paternity));
        paternityElement.sendKeys("Аркадьевич");
        //Select field: region
        String region = "//select[@name='Region']";
        WebElement regionElement = selectorDriver.findElement(By.xpath(region));
        Select menuRegion = new Select(regionElement);
        menuRegion.selectByVisibleText("Москва");
        //Fill field: phone number (with a mask)
        String phoneNum = "//*[text()='Телефон']/parent::div/input";
        WebElement phoneNumElement = selectorDriver.findElement(By.xpath(phoneNum));
        phoneNumElement.click();
        phoneNumElement.sendKeys("9162141673");
        //Fill field: e-mail
        String emailAddress = "//*[text()='Эл. почта']/parent::div/input";
        WebElement addressElement = selectorDriver.findElement(By.xpath(emailAddress));
        addressElement.sendKeys("qwertyqwerty");
        //Fill field: contact date (with a mask)
        String contactDate = "//*[contains(text(), 'дата')]/parent::div/input";
        WebElement contactDateElement = selectorDriver.findElement(By.xpath(contactDate));
        contactDateElement.click();
        contactDateElement.sendKeys("04042020");
        //Fill field: additional commentaries
        String additionalComments = "//*[text()='Комментарии']/parent::div/textarea";
        WebElement commentsElement = selectorDriver.findElement(By.xpath(additionalComments));
        commentsElement.sendKeys("According to all known laws of aviation, there is no way a bee should be able to fly. " +
                "Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible.");
        //Checkbox
        String complyWithTerms = "//*[contains(text(),'Я согласен на обработку')]/parent::label/input";
        WebElement termsElement = selectorDriver.findElement(By.xpath(complyWithTerms));
        termsElement.click();
        //Finish & send
        String confirmButton = "//button[@id]";
        WebElement confirmButtonElement = selectorDriver.findElement(By.xpath(confirmButton));
        confirmButtonElement.click();
        //Check the e-mail is correct
        addressElement = selectorDriver.findElement(By.xpath(emailAddress));
        String tmp = addressElement.getText();
        Assert.assertNotEquals("flames@mail.ru", tmp);

        System.out.println("Congratulations x18");
    }

    private void clickOn(WebElement me)
    {
        waitForLoad.until(ExpectedConditions.visibilityOf(me));
        me.click();
    }

    @After
    public void finisher()
    {
        selectorDriver.quit();
    }
}
