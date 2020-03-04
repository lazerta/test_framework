package page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import utils.BrowserUtil;

public class LoginPo {
    @FindBy(id = "SubmitLogin")
    @CacheLookup
    private WebElement loginBtn;
    @FindBy(name = "email")
    @CacheLookup
    private WebElement username;
    @FindBy(css = "input[name='passwd']")
    @CacheLookup
    private WebElement password;
    public void login(String name, String pass){
        BrowserUtil.clear(username,password);
        username.sendKeys(name);
        password.sendKeys(pass);
        loginBtn.click();
        BrowserUtil.waitPageLoad();
    }
}
