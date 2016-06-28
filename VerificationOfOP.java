package Assignment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class VerificationOfOP {
	static boolean verifyOutput()
	{
		try{
			
			File f1=new File(Data.loc+Data.Bfile);
		    File f2=new File(Data.loc+Data.Mfile);
		    FileInputStream fi1=new FileInputStream(f1);
		    FileInputStream fi2=new FileInputStream(f2); 
		    DataInputStream di1=new DataInputStream(fi1);
		    BufferedReader br1=new BufferedReader(new InputStreamReader(di1));
		    DataInputStream di2=new DataInputStream(fi2);
		    BufferedReader br2=new BufferedReader(new InputStreamReader(di2));
		    String s1, s2;  
		    while ((s1=br1.readLine())!=null && (s2=br2.readLine())!=null) 
		     {
		    	if(!s1.equals(s2))
		    	{
		    		return true;
		    	}
		    	
		     }
		    br1.close();
		    br2.close();
		    return false;
		    
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

}
