package page_object_test;

import base.BrowserDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_object.GuruLoginPO;
import readers.ExcelReader;
import sun.security.smartcardio.SunPCSC;
import utils.BrowserUtil;

public class GuruLoginPOTest extends BrowserDriver {
    private GuruLoginPO guruLoginPO;

    @BeforeMethod
    public void init() {
        guruLoginPO = PageFactory.initElements(driver, GuruLoginPO.class);
    }

    @DataProvider()
    @BeforeClass
    public Object[][] provide() throws Exception {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("test.xlsx");
        return excelReader.getExcelSheetData(0);
    }

    @Test(dataProvider = "provide")
    public void LoginTest(String username, String password) {
        String loginUrl = driver.getCurrentUrl();
        guruLoginPO.login(username, password);
        try {

            Alert alert = driver.switchTo().alert();


            String alertText = "User or Password is not valid";
            String text = alert.getText();
            alert.accept();
            Assert.assertEquals(text, alertText);
            System.out.println("text = " + text);


        } catch (NoAlertPresentException e) {
            System.out.println("loginUrl = " + loginUrl);
            String mainPageUrl = "http://www.demo.guru99.com/V4/manager/Managerhomepage.php";
            System.out.println("mainPageUrl = " + mainPageUrl);
            Assert.assertEquals(driver.getCurrentUrl(), mainPageUrl);
        }


    }


}
