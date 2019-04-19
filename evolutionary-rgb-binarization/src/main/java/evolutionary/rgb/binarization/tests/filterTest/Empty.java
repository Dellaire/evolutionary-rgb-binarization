package evolutionary.rgb.binarization.tests.filterTest;

import java.awt.Color;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.region.Spot;
import evolutionary.rgb.binarization.visualization.Monitor;

public class Empty
{
	public static void main(String[] args)
	{
		BufferedImage actualImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_BGR);
		
		for(int x = 0; x < 500; x++)
		{
			for(int y = 0; y < 500; y++)
			{
				actualImage.setRGB(x, y, new Color(255, 255, 255).getRGB());
			}
		}
		
		AddRGB addRGB;
		addRGB = new AddRGB(0, -255, -255);
		addRGB.setRegion(new Spot(200, 200, 100, 100));
		addRGB.apply(actualImage);
		
		addRGB = new AddRGB(-255, 0, -255);
		addRGB.setRegion(new Spot(300, 200, 100, 100));
		addRGB.apply(actualImage);
		
		addRGB = new AddRGB(-255, -255, 0);
		addRGB.setRegion(new Spot(250, 300, 100, 100));
		addRGB.apply(actualImage);
		
		Monitor monitor = new Monitor(100, 0, actualImage);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitor.paint();
	}
}
