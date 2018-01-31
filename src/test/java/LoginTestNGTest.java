/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author 1991r
 */
public class LoginTestNGTest {

    public LoginTestNGTest() {
    }
    public WebDriver driver;


    @Test
    public void test() throws InterruptedException {

        driver.findElement(By.xpath("//a[@class='btn btn-default text-center text-uppercase taxi-button center-block hidden-xs']")).click();
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//a[@class='text-center center-block btn-default btn send-btn']"));
        
//scroll down for find login button
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.perform();
        
        element.click();
        
        login("", "");
        checkText("  Correo electronico es requerido.     ");
        checkText("   Se requiere contraseña.     ");
        
        login("aaa@aaa", "");
        checkText("   Se requiere contraseña.     ");
        
        login("aaa@mail.ru", "a");
        checkText("  Correo o contraseña incorrectos.     ");
        
        login("test.l1bre@mailinator.com", "testtest123");
        Thread.sleep(5000);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        String exePath = "chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("disable-infobars");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://l1bre.com/");

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        driver.quit();
    }

    //function for login
    public void login(String email, String password) throws InterruptedException {
        if (email.length() > 0) {
            driver.findElement(By.xpath("//input[@type='email']")).clear();
            driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email);
            Thread.sleep(5000);
        }
        if (password.length() > 0) {
            driver.findElement(By.xpath("//input[@type='password']")).clear();
            driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
            Thread.sleep(5000);
        }
        driver.findElement(By.xpath("//input[@type='submit']")).click();

    }

//function for check text
    public void checkText(String text) {
        boolean k = false;
        try {
            driver.findElement(By.xpath("//*[text()='" + text + "']")).isDisplayed();
        } catch (RuntimeException e) {
            k = true;
        }
        Assert.assertFalse(text + " not found", k == true);
    }
}
