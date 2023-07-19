package Testcases;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utility.BaseClass ;

public class TestCasesClass {
	WebDriver driver;
	BaseClass baseClass = new BaseClass();
	SoftAssert softAssert = new SoftAssert();
	String herokuImgURL;
	String herokuUploadURL;
		
	@BeforeTest
	public void setup() throws Exception{
		baseClass.setup_webdriver();
	}

	@Test(enabled = true, priority = 1)
	public void validate_broken_img() throws IOException {
		baseClass.validate_broken_image();
	}
	
	@Test(enabled = true, priority = 2)
	private void upload_file() throws IOException, InterruptedException {
		baseClass.validate_upload_file();
	}
}