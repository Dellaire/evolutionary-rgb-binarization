package evolutionary.rgb.binarization.tests.evo;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.evolution.Fitness;
import evolutionary.rgb.binarization.evolution.Population;
import evolutionary.rgb.binarization.evolution.Mode.FitnessMode;
import evolutionary.rgb.binarization.filter.Binarization;
import evolutionary.rgb.binarization.helper.LoggingHelper;
import evolutionary.rgb.binarization.helper.SerializationHelper;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.visualization.Monitor;

public class BinarizationTest
{
	public static void main(String[] args)
	{
		LoggingHelper.reset();
		
		Fitness fitness;
		FilterSeries series = new FilterSeries();
		Population population, recombineCandidates;
		MainProperties mainProperties = MainProperties.getInstance();
		
		BufferedImage sourceImage = null;
		BufferedImage targetImage = null;
		
		try
		{
			sourceImage = ImageIO.read(new File("set3/book2Src.bmp"));
			targetImage = ImageIO.read(new File("set3/book2Tar.bmp"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		series = SerializationHelper.JAXBDeserialize("serialization/FilterSeries.xml");
		series.apply(sourceImage);
		
		Monitor monitor = new Monitor(100, 0, sourceImage);
		monitor.paint();
		
		population = new Population();
		fitness = new Fitness(sourceImage, targetImage);
		population.setFitnessFunction(fitness);
		
		for(int i = 0; i < 50; i++)
			population.addIndividual(new FilterSeries().addFilter(new Binarization()));
		
		mainProperties.setFitnessMode(FitnessMode.binary);
//		mainProperties.setFitnessAlpha(20.0);
		
		FilterSeries tmpFilterSeries, bestFilterSeries;
		for(int i = 0, j = 0; i < 10; i++)
		{
			bestFilterSeries = population.getBestIndividual();
			System.out.println("best fitness (" + j++ + "): " + bestFilterSeries.getFitness());
			recombineCandidates = population.tournamentSelection(10, 5, false);
			for(FilterSeries filterSeries : recombineCandidates.getIndividuals())
			{
				filterSeries.recombine(population.getRandomIndividual(filterSeries));
				population.addIndividual(filterSeries.mutate());
			}
			population = population.tournamentSelection(50, 5, false);
			population.printFitness();
			tmpFilterSeries = population.getBestIndividual();
			if(tmpFilterSeries.getFitness() > bestFilterSeries.getFitness())
				i = 0;
		}
		bestFilterSeries = population.getBestIndividual();
		bestFilterSeries.apply(sourceImage);
		monitor.setImage(sourceImage);
		monitor.paint();
		
		series.addFilters(population.getBestIndividual());
		SerializationHelper.JAXBSerialize(series);
		
		System.out.println("\nDONE");
	}
}