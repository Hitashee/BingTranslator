package Assignment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
class GetDriver {

		WebDriver getDriver() {
		
		WebDriver driver= new FirefoxDriver();
		driver.get(Data.url);
		return driver;

	}

}