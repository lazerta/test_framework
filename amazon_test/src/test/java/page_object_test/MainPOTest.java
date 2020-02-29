package page_object_test;

import base.BrowserDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_object.MainPo;

public class MainPOTest extends BrowserDriver {
    MainPo mainPo;

    @BeforeMethod
    public void init() {
        mainPo = PageFactory.initElements(driver, MainPo.class);
    }

    @Test
    public void loginTest() {

        // mainPo.login("linxiang08@gmail.com", "linxiang119");


    }
}
