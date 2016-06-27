package Assignment;
import java.io.BufferedReader;
import java.io.IOException;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class MSD extends BingFile {
	
	void MSMain() {
		try{
			BufferedReader br=fileRead();
			msTrans(br);
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static void msTrans(BufferedReader br)throws IOException{

		Translate.setClientId("e8061be2-57bf-47ab-b78a-f4763a8a6285");
	    Translate.setClientSecret("O+kxgHLN72c/TqRxuSXMhodYqbvCr2mtdlGasu5+q/M");
	    String line="";
	    try{
	    	while( (line = br.readLine())!= null ){
	  		  	String[] tokens= line.split(",");
				String from=tokens[0];
				String to=tokens[1];
				String word=tokens[2];
		    Language x = Language.valueOf(from.toUpperCase());
		    Language y = Language.valueOf(to.toUpperCase());
		    //Thread.sleep(2000);
		    String trans = Translate.execute(word,x,y );
		    fileWrite(trans,Data.Mfile);
		    }
	    }
		catch(Exception e)
	    {
	    	e.printStackTrace();
	    }

	}

}
