package page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import utils.BrowserUtil;

import javax.xml.ws.WebEndpoint;

public class GuruLoginPO {
    @FindBy(how = How.XPATH, using = "//input[@name='btnLogin']")
    @CacheLookup
    private WebElement loginBtn;
    @FindBy(name = "uid")
    @CacheLookup
    private WebElement username;
    @FindBy(name = "password")
    @CacheLookup
    private WebElement password;

    public void login(String name, String pass){
        username.sendKeys(name);
        password.sendKeys(pass);
        loginBtn.click();
        BrowserUtil.sleep(1);
    }

}
