package evolutionary.rgb.binarization.visualization;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Histogram
{
	public static void main(String[] args)
	{
		int[] countRed = new int[256];
		int[] countGreen = new int[256];
		int[] countBlue = new int[256];
		
		Color color;
		BufferedImage inputImage = null;
		
		try
		{
			inputImage = ImageIO.read(new File("examples/abcSrc.bmp"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		for(int x = 0; x < inputImage.getWidth(); x++)
		{
			for(int y = 0; y < inputImage.getHeight(); y++)
			{
				color = new Color(inputImage.getRGB(x, y));
				countRed[color.getRed()]++;
				countGreen[color.getGreen()]++;
				countBlue[color.getBlue()]++;
			}
		}
		
		for(int i = 0; i < 256; i++)
		{
			//System.out.print("[" + countRed[i] + "," + countGreen[i] + "," + countBlue[i] + "] ");
			System.out.println(countRed[i]);
		}
	}
}
