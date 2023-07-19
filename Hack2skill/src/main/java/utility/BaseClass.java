package utility;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	WebDriver driver;
	String herokuImgURL;
	String herokuUploadURL;
	SoftAssert softAssert = new SoftAssert();
	
	public void setup_webdriver() throws Exception{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public void validate_broken_image() throws IOException {
		
//		Importing URL from .Properties file
		FileReader fis = new FileReader(System.getProperty("user.dir")+ "\\src\\main\\resources\\Globle.properties" );
		Properties ps = new Properties();
		ps.load(fis);
		herokuImgURL = ps.getProperty("HerokuURL") ;
		
//		Open URL in browser
		driver.get(herokuImgURL);
		
//		Creating list of all image elements
		List<WebElement> images = driver.findElements(By.tagName("img")) ;
		
//		Iterate over the list
		for( int i=1; i<images.size(); i++ ){
			
//			getting src attribute of image
			String imgURL = images.get(i).getAttribute("src") ;
			
//			Checking the image src and validate the image
			Boolean BrokenImg =  checkImageBroken(imgURL) ;
			
//			Applying assertion
//			Assert.assertTrue(BrokenImg);
			softAssert.assertTrue(BrokenImg);
		}

	}
	
	public void validate_upload_file() throws IOException, InterruptedException {
		
		FileReader fis = new FileReader(System.getProperty("user.dir")+ "\\src\\main\\resources\\Globle.properties" );
		Properties ps = new Properties();
		ps.load(fis);
		
		herokuUploadURL = ps.getProperty("HerokuUploadURL") ;
		String filepath = ps.getProperty("FilePath") ;

		driver.get(herokuUploadURL);
		
//		Uploading a file of given path
		driver.findElement(By.id("file-upload")).sendKeys(filepath);
		
		driver.findElement(By.id("file-submit")).click();
		
		String text = driver.findElement(By.tagName("h3")).getText();
		
		softAssert.assertEquals(text, "File Uploaded!");
		
		driver.quit();
	}
	
    public boolean checkImageBroken(String imageURL) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(imageURL));
            System.out.println("Image is not broken | ImageURL: " + imageURL);
            return bufferedImage == null;
            
        } catch (IOException exception) {
            System.out.println("src of this image is not working | ImageURL: " + imageURL);
            return true;
            
        }
    }
    
}