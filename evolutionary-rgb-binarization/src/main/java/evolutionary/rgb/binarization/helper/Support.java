package evolutionary.rgb.binarization.helper;

public class Support
{
	public static int cap255(int color)
	{
		if(color < 0)
			return 0;
		if(color > 255)
			return 255;
		
		return color;
	}
	
	public static long RGBToInt(int red, int green, int blue)
	{
		long intVal = 0;
		intVal = 255 ^ 128;
		
		return intVal;
	}
}
