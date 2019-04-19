package evolutionary.rgb.binarization.helper;

import java.io.FileWriter;
import java.io.IOException;

public class LoggingHelper
{
	private static FileWriter writer;
	
	private LoggingHelper()
	{
		
	}
	
	public static void reset()
	{
		try {
			writer = new FileWriter("logs/output.txt", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void write(String entry)
	{
		try
		{
			writer = new FileWriter("logs/output.txt", true);
			writer.write(entry);
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
