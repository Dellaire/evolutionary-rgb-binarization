package evolutionary.rgb.binarization.tests.evo;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.evolution.Fitness;
import evolutionary.rgb.binarization.evolution.Population;
import evolutionary.rgb.binarization.evolution.Mode.FitnessMode;
import evolutionary.rgb.binarization.filter.LocalBinarization;
import evolutionary.rgb.binarization.helper.LoggingHelper;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.visualization.Monitor;

public class LocalThreshold
{
	public static void main(String[] args)
	{
		LoggingHelper.reset();
		
		Fitness fitness;
		FilterSeries pixelSeries;
		Population population, recombineCandidates;
		MainProperties mainProperties = MainProperties.getInstance();
		
		BufferedImage sourceImage = null;
		BufferedImage targetImage = null;
		
		try
		{
			sourceImage = ImageIO.read(new File("examples/defSrc.bmp"));
			targetImage = ImageIO.read(new File("examples/defTar.bmp"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		Monitor monitor = new Monitor(100, 0, sourceImage);
		monitor.paint();
		
		population = new Population();
		fitness = new Fitness(sourceImage, targetImage);
		population.setFitnessFunction(fitness);
		
		pixelSeries = new FilterSeries();
		for(int i = 0; i < 10; i++)
		{
			for(int x = 0; x < sourceImage.getWidth(); x++)
			{
				for(int y = 0; y < sourceImage.getHeight(); y++)
				{
					pixelSeries.addFilter(new LocalBinarization(x, y));
				}
			}
			population.addIndividual(pixelSeries);
		}
		System.out.println("> population initiated");
		
		mainProperties.setFitnessMode(FitnessMode.binary);
		mainProperties.setFitnessAlpha(20.0);
		
		FilterSeries tmpFilterSeries, bestFilterSeries;
		for(int i = 0, j = 0; i < 20; i++)
		{
			bestFilterSeries = population.getBestIndividual();
			System.out.println("best fitness (" + j++ + "): " + bestFilterSeries.getFitness());
			recombineCandidates = population.tournamentSelection(10, 5, false);
			for(FilterSeries filterSeries : recombineCandidates.getIndividuals())
			{
				filterSeries.recombine(population.getRandomIndividual(filterSeries));
				population.addIndividual(filterSeries.mutate());
			}
			population = population.tournamentSelection(10, 5, false);
			population.printFitness();
			tmpFilterSeries = population.getBestIndividual();
			if(tmpFilterSeries.getFitness() > bestFilterSeries.getFitness())
				i = 0;
		}
		bestFilterSeries = population.getBestIndividual();
		bestFilterSeries.apply(sourceImage);
		monitor.setImage(sourceImage);
		monitor.paint();
		
//		population.getBestIndividual().apply(sourceImage);
//		monitor.setImage(sourceImage);
//		monitor.paint();
		
		System.out.println("\n> DONE");
	}
}