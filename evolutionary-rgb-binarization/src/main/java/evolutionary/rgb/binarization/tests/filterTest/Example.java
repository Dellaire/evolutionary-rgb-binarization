package evolutionary.rgb.binarization.tests.filterTest;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.evolution.Fitness;
import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.filter.Threshold;
import evolutionary.rgb.binarization.region.Spot;
import evolutionary.rgb.binarization.visualization.Monitor;

public class Example
{
	public static void main(String[] args)
	{
		BufferedImage actualImage = null;
		BufferedImage optimalImage = null;
		
		try
		{
			actualImage = ImageIO.read(new File("examples/rectSrc.bmp"));
			optimalImage = ImageIO.read(new File("examples/rectTar.bmp"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		AddRGB addRGB;
		
		/*addRGB = new AddRGB(0, -255, -255);
		addRGB.setRegion(new Spot(150, 150, 50, 0));
		addRGB.apply(actualImage);*/
		
		addRGB = new AddRGB(-255, 0, -255);
		addRGB.setRegion(new Spot(350, 150, 80, 100));
		addRGB.apply(actualImage);
		
		Threshold thresh = new Threshold();
		thresh.setMinRGB(0, 0, 0);
		thresh.setMaxRGB(255, 190, 50);
		thresh.setMapIn(0, 0, 0);
		thresh.setMapOut(255, 255, 255);
		thresh.setRegion(new Spot(150, 350, 80, 0));
		thresh.apply(actualImage);
		
		/*DeNoise deNoise = new DeNoise(10);
		deNoise.setRegion(new Spot(350, 350, 50, 0));
		deNoise.apply(actualImage);*/
		
		System.out.println(Fitness.getFitnessByBinarization_2(actualImage, optimalImage));
		
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
