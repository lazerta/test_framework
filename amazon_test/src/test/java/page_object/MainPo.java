package page_object;

import base.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


public class MainPo {
    @FindBy(how = How.NAME, using = "email")
    @CacheLookup
    public WebElement username;
    @FindBy(name = "pass")
    @CacheLookup
    public WebElement password;
    @FindBy(how = How.CSS, using = "input[data-testid=royal_login_button]")
    @CacheLookup
    public WebElement loginButton;

    public boolean login(String name, String pass) {
        username.sendKeys(name);
        password.sendKeys(pass);
        loginButton.click();
        return true;
    }


}
