package Assignment;

import java.io.BufferedReader;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Bing {

	 void bingMain() {
		// TODO Auto-generated method stub
		 GetDriver gd=new GetDriver();
		WebDriver driver= gd.getDriver();
		try{
			BufferedReader br=BingFile.fileRead();
			bingTrans(driver,br);
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		driver.quit();
	}
	static void bingTrans(WebDriver driver,BufferedReader br)throws Exception
	{
		
		String line = "";
		List<WebElement> list=driver.findElements(By.className("LS_HeaderTitle"));
		while( (line = br.readLine())!= null ){
  		  	String[] tokens= line.split(",");
			String from=tokens[0];
			String to=tokens[1];
			String word=tokens[2];
			String s1 = "(//td[text()='"+from+"'])[1]";
	        String s2 = "(//td[text()='"+to+"'])[2]";
	        list.get(0).click();
	        Thread.sleep(2000);
	        driver.findElement(By.xpath(s1)).click();
	        driver.findElement(By.className("srcTextarea")).sendKeys(word);
	        list.get(1).click();
	        driver.findElement(By.xpath(s2)).click();
	        Thread.sleep(2000);
	        String trans=driver.findElement(By.className("textArea")).getText();
	        BingFile.fileWrite(trans,Data.Bfile);
	        driver.findElement(By.className("srcTextarea")).clear();    
		}
		br.close();
	}

}
