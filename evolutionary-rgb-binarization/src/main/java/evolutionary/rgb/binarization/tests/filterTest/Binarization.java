package evolutionary.rgb.binarization.tests.filterTest;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.filter.Threshold;
import evolutionary.rgb.binarization.region.Global;
import evolutionary.rgb.binarization.visualization.Monitor;

public class Binarization
{
	public static void main(String[] args)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(new File("examples/abcSrc.bmp"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		Threshold thresh = new Threshold();
		thresh.setMinRGB(0, 0, 0);
		thresh.setMaxRGB(100, 100, 100);
		thresh.setMapIn(0, 0, 0);
		thresh.setMapOut(255, 255, 255);
		thresh.setRegion(new Global());
		thresh.apply(image);
		
		Monitor monitor = new Monitor(100, 0, image);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitor.paint();
	}
}
