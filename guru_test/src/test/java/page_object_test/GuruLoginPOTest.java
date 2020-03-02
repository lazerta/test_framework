package page_object_test;

import base.BrowserDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_object.GuruLoginPO;
import sun.security.smartcardio.SunPCSC;

public class GuruLoginPOTest  extends BrowserDriver {
    private GuruLoginPO guruLoginPO;
   @BeforeMethod
    public void init(){
       guruLoginPO = PageFactory.initElements(driver, GuruLoginPO.class);
    }
    @Test
    public void LoginTest(){
        String loginUrl = driver.getCurrentUrl();
        guruLoginPO.login("mngr1336", "dAnavUq");
        String mainPageUrl = driver.getCurrentUrl();
        Assert.assertEquals(loginUrl, mainPageUrl);

    }


}
