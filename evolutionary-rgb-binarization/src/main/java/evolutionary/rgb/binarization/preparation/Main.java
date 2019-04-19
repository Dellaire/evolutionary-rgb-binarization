package evolutionary.rgb.binarization.preparation;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.visualization.Monitor;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		int red, green, blue;
		Monitor monitor;
		Color tmpColor;
		BufferedImage image = null;
		
		red = 0;
		green = 255;
		blue = 0;
		image = ImageIO.read(new File("set/book2Tar.bmp"));
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				tmpColor = new Color(image.getRGB(x, y));
				if((tmpColor.getRed() == red) && (tmpColor.getGreen() == green) && (tmpColor.getBlue() == blue))
					image.setRGB(x, y, new Color(0, 0, 0).getRGB());
				else
					image.setRGB(x, y, new Color(255, 255, 255).getRGB());
			}
		}
		monitor = new Monitor(10, 10, image);
		monitor.paint();
	}
}