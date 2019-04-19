package evolutionary.rgb.binarization.helper;

import java.util.Random;

/**
 * Helps to apply random operations.
 * 
 * @author Henry Borasch
 *
 */
public class RandomHelper
{
	private static Random random = new Random();
	
	private RandomHelper()
	{
		
	}
	
	public static Random getRandom()
	{
		return random;
	}
}
