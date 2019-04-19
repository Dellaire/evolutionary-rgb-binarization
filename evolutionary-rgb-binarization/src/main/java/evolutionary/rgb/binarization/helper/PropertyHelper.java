package evolutionary.rgb.binarization.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Helps to read property information.
 * 
 * @author Henry Borasch
 */
public class PropertyHelper
{
	private static Properties properties;
	
	private PropertyHelper()
	{
		
	}
	
	public static String getGeneralProperty(String property)
	{
		if(properties == null)
		{
			try
			{
				properties = new Properties();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("src/properties/general.properties"));
				properties.load(stream);
				stream.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
		return properties.getProperty(property);
	}
	
	public static String getFilterProperty(String property)
	{
		if(properties == null)
		{
			try
			{
				properties = new Properties();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("src/properties/filter.properties"));
				properties.load(stream);
				stream.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
		return properties.getProperty(property);
	}
}
