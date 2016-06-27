package Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class BingFile
{
	static BufferedReader fileRead() throws Exception
	{
	    File file = new File(Data.fileloc);
	   	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));	  
	    return br;
	}
	static void fileWrite(String word,String fileOP)throws IOException
	{
		File file = new File(fileOP);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(word);
		bw.newLine();
		bw.close();

	}
}