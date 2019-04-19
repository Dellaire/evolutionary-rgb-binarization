package evolutionary.rgb.binarization.visualization;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.helper.SerializationHelper;

public class ShowFilter
{
	public static void main(String[] args) throws Exception
	{
		FilterSeries series;
		BufferedImage sourceImage = null;
		
		sourceImage = ImageIO.read(new File("set3/book4Src150.bmp"));
		
		Monitor monitor = new Monitor(100, 0, sourceImage);
		
//		mainProperties.setFitnessMode(FitnessMode.rgbDistance);
		series = new FilterSeries();
		series = SerializationHelper.JAXBDeserialize("serialization/FilterSeriesBook.xml");
//		series.setFitnessFunction(new Fitness(sourceImage, targetImage));
//		System.out.println(series.getFitness());
		series.apply(sourceImage);
		
		monitor.setImage(sourceImage);
		monitor.paint();
	}
}
