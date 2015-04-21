package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class Config {
	
	protected static String read(String filename)
	{
		try
		{
			File configfile = new File(filename);
			
			BufferedReader br = new BufferedReader(new FileReader(configfile));
			String line;
			StringBuilder xml = new StringBuilder();
			while((line=br.readLine())!= null)
			{
			    xml.append(line.trim());
			}
			br.close();
			return xml.toString();
		}
		catch (Exception ex)
		{
			// log exception here
			return null;
		}
	}

}
