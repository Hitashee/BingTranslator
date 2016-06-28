package Assignment;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
public class TransTest {

	@BeforeTest
	void index() throws ParseException
	{
		Bing bing= new Bing();
		bing.bingMain();
		;
		try {
			ApiTrans api = new ApiTrans(Data.clientId,Data.clientSecret);
			ApiTrans.translate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBing() {
		
	     Assert.assertEquals(false, VerificationOfOP.verifyOutput());
	   }
	
}
