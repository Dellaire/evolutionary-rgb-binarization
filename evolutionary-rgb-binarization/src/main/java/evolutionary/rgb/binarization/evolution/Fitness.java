package evolutionary.rgb.binarization.evolution;

import java.awt.Color;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.evolution.Mode.FitnessMode;
import evolutionary.rgb.binarization.helper.ImageHelper;
import evolutionary.rgb.binarization.helper.PropertyHelper;
import evolutionary.rgb.binarization.properties.MainProperties;

/**
 * This class provides functions to compute the fitness of an individual.
 * 
 * @author Henry Borasch
 */
public class Fitness
{
	private BufferedImage sourceImage;
	private BufferedImage targetImage;
	
	public Fitness(BufferedImage sourceImage, BufferedImage targetImage)
	{
		this.sourceImage = sourceImage;
		this.targetImage = targetImage;
	}
	
	public double getFitness(FilterSeries filterSeries, FitnessMode fitnessMode)
	{
		switch(fitnessMode)
		{
			case binary:
				return this.getFitnessByBinaization(filterSeries);
			case rgbDistance:
				return this.getFitnessByRGBDistance(filterSeries);
			case tmp:
				return this.getTmpFitness(filterSeries);
			default:
				throw new RuntimeException("### ERROR ### : Illegal fitness mode!");
		}
	}
	
	/**
	 * Applies a FilterSeries on sourceImage and compares the result with targetImage.
	 * 
	 * @param filterSeries FilterSeries to be applied on sourceImage
	 * @return Fitness of the FilterSeries
	 */
	public double getFitnessByBinaization(FilterSeries filterSeries)
	{
		int red, green, blue;
		double fitness = 0.0;
		Color color;
		BufferedImage appliedFilter;
		
		appliedFilter = ImageHelper.copyImage(this.sourceImage);
		filterSeries.apply(appliedFilter);		
		for(int x = 0; x < this.sourceImage.getWidth(); x++)
		{
			for(int y = 0; y < this.sourceImage.getHeight(); y++)
			{
				color = new Color(appliedFilter.getRGB(x, y));
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				if(new Color(this.targetImage.getRGB(x, y)).getRed() < 255)
				{
					// foreground
					
					fitness -= ((double)(red + green + blue) / 3) * MainProperties.getInstance().getFitnessAlpha();
				}
				else
				{
					// background
					
					fitness += (((double)(red + green + blue) / 3) - 255) * MainProperties.getInstance().getFitnessBeta();
				}
			}
		}
		
		return fitness;
	}
	
	/**
	 * Compares the source individual with the optimal individual.
	 * 
	 * @param source Individual to evaluate
	 * @param target Optimal individual
	 * @return Fitness of actual individual
	 */
	public static double getFitnessByBinarization_2(BufferedImage source, BufferedImage target)
	{
		int red, green, blue;
		double fitness = 0.0;
		Color color;
		
		for(int x = 0; x < Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeX")); x++)
		{
			for(int y = 0; y < Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeX")); y++)
			{
				color = new Color(source.getRGB(x, y));
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				
				if(new Color(target.getRGB(x, y)).getRed() < 255)
					fitness -= Math.max(Math.max(red, green), blue) / 10;
				else
					fitness += (Math.min(Math.min(red, green), blue) - 255) / 100;
			}
		}
		
		return fitness;
	}
	
	public double getTmpFitness(FilterSeries filterSeries)
	{
		int red, green, blue;
		double fitness = 0.0;
		Color color;
		BufferedImage appliedFilter;
		
		appliedFilter = ImageHelper.copyImage(this.sourceImage);
		filterSeries.apply(appliedFilter);		
		for(int x = 0; x < this.sourceImage.getWidth(); x++)
		{
			for(int y = 0; y < this.sourceImage.getHeight(); y++)
			{
				color = new Color(appliedFilter.getRGB(x, y));
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				
				if(new Color(this.targetImage.getRGB(x, y)).getRed() < 255)
					fitness -= ((double)(red + green + blue) / 3) / 1;
				else
					fitness += (((double)(red + green + blue) / 3) - 255) / 100;
			}
		}
		
		return fitness;
	}
	
	public double getFitnessByRGBDistance(FilterSeries filterSeries)
	{
		int[] rgb;
		double distanceFore, distanceBack;
		double[] avg;
		Color sourceColor, targetColor;
		BufferedImage appliedFilter;
		
		appliedFilter = ImageHelper.copyImage(this.sourceImage);
		filterSeries.apply(appliedFilter);
		distanceFore = 0.0;
		distanceBack = 0.0;
		avg = this.averageForegroundColor(appliedFilter);
		
		for(int x = 0; x < this.sourceImage.getWidth(); x++)
		{
			for(int y = 0; y < this.sourceImage.getHeight(); y++)
			{
				sourceColor = new Color(appliedFilter.getRGB(x, y));
				targetColor = new Color(this.targetImage.getRGB(x, y));
				rgb = new int[3];
				rgb[0] = sourceColor.getRed();
				rgb[1] = sourceColor.getGreen();
				rgb[2] = sourceColor.getBlue();
				
				if(targetColor.getRed() < 255)
				{
					//Foreground					
					
					distanceFore += this.foregroundCorridor(avg, rgb, MainProperties.getInstance().getCorridorForeground());
				}
				else
				{
					//Background
					
					distanceBack += this.backgroundCorridor(avg, rgb, MainProperties.getInstance().getCorridorBackground());
				}
			}
		}
		
		return ((distanceFore * MainProperties.getInstance().getFitnessAlpha()) + (distanceBack * MainProperties.getInstance().getFitnessBeta())) / 1000000;
	}
	
	private double[] averageForegroundColor(BufferedImage appliedFilter)
	{
		int pixels;
		double[] avg = new double[3];
		Color sourceColor, targetColor;
		
		pixels = 0;
		avg[0] = 0.0;
		avg[1] = 0.0;
		avg[2] = 0.0;
		for(int x = 0; x < appliedFilter.getWidth(); x++)
		{
			for(int y = 0; y < appliedFilter.getHeight(); y++)
			{
				sourceColor = new Color(appliedFilter.getRGB(x, y));
				targetColor = new Color(this.targetImage.getRGB(x, y));
				if(targetColor.getRed() < 255)
				{
					avg[0] += sourceColor.getRed();
					avg[1] += sourceColor.getGreen();
					avg[2] += sourceColor.getBlue();
					pixels++;
				}
			}
		}
		avg[0] /= pixels;
		avg[1] /= pixels;
		avg[2] /= pixels;
		
		return avg;
	}
	
	private double backgroundCorridor(double[] avg, int[] rgb, int corridor)
	{
		double punRed = Math.abs(rgb[0] - avg[0]);
		double punGreen = Math.abs(rgb[1] - avg[1]);
		double punBlue = Math.abs(rgb[2] - avg[2]);
		
		if(punRed > corridor)
			punRed = 0;
		else
			punRed = Math.pow(corridor - punRed, 2);
		if(punGreen > corridor)
			punGreen = 0;
		else
			punGreen = Math.pow(corridor - punGreen, 2);
		if(punBlue > corridor)
			punBlue = 0;
		else
			punBlue = Math.pow(corridor - punBlue, 2);

		if((punRed == 0) || (punGreen == 0) || (punBlue == 0))
			return 0.0;
		return -punRed - punGreen - punBlue;
	}
	
	private double foregroundCorridor(double[] avg, int[] rgb, int corridor)
	{
		double punRed = Math.abs(rgb[0] - avg[0]);
		double punGreen = Math.abs(rgb[1] - avg[1]);
		double punBlue = Math.abs(rgb[2] - avg[2]);
		
		if(punRed < corridor)
			punRed = 0;
		else
			punRed = Math.pow(punRed - corridor, 2);
		if(punGreen < corridor)
			punGreen = 0;
		else
			punGreen = Math.pow(punGreen - corridor, 2);
		if(punBlue < corridor)
			punBlue = 0;
		else
			punBlue = Math.pow(punBlue - corridor, 2);

		return -punRed - punGreen - punBlue;
	}
	
//	private double intensity(FilterSeries filterSeries)
//	{
//		
//	}
}
