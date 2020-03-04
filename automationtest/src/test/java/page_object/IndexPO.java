package page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BrowserUtil;

public class IndexPO {
    @FindBy(css = "a.login")
    private WebElement loginLink;
    @FindBy(partialLinkText = "Contact us")
    private WebElement contactLink;

    public void goToLoginPage() {
        loginLink.click();
        BrowserUtil.waitPageLoad();
    }
    public void goContactPage() {
        loginLink.click();
        BrowserUtil.waitPageLoad();
    }
}
