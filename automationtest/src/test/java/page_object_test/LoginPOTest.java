package page_object_test;

import base.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_object.LoginPo;
import readers.ExcelReader;
import reporter.ReportTestManager;
import utils.BrowserUtil;


public class LoginPOTest extends BrowserDriver {
    private LoginPo loginPo;

    @BeforeMethod
    public void init() {
        loginPo = PageFactory.initElements(driver, LoginPo.class);
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
    }

    @DataProvider(name = "invalid")
    public Object[][] invalidProvide() throws Exception {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("test.xlsx");
        return excelReader.getExcelSheetData(0);
    }

    @DataProvider(name = "valid")
    public Object[][] validProvide() throws Exception {
        ExcelReader excelReader = new ExcelReader();
        excelReader.setExcelFile("test.xlsx");
        return excelReader.getExcelSheetData(2);
    }

    @Test(dataProvider = "valid")
    public void LoginWithValidCredentialTest(String username, String password) {
        loginPo.login(username, password);
        boolean equals = driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=my-account");
        Assert.assertTrue(equals);
    }

    @Test(dataProvider = "invalid")
    public void LoginWithInValidCredentialTest(String username, String password) {
        loginPo.login(username, password);

        boolean equals = driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=authentication");
        BrowserUtil.waitFor(10, ExpectedConditions.presenceOfElementLocated(By.className("alert")));
        Assert.assertFalse(equals);
    }


}
