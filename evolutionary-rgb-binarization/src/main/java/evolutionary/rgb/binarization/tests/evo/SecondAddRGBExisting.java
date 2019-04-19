package evolutionary.rgb.binarization.tests.evo;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.evolution.Fitness;
import evolutionary.rgb.binarization.evolution.Population;
import evolutionary.rgb.binarization.evolution.Mode.FitnessMode;
import evolutionary.rgb.binarization.evolution.Mode.RecombinationMode;
import evolutionary.rgb.binarization.helper.SerializationHelper;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.visualization.Monitor;

public class SecondAddRGBExisting
{
	public static void main(String[] args)
	{
		Fitness fitness;
		FilterSeries series = new FilterSeries(), tmpSeries;
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
		
		Monitor monitor = new Monitor(100, 0, sourceImage);
		monitor.paint();
		
		population = new Population();
		fitness = new Fitness(sourceImage, targetImage);
		population.setFitnessFunction(fitness);
		
		mainProperties.setFitnessMode(FitnessMode.binary);
		mainProperties.setRecombinationMode(RecombinationMode.arithmetic);
//		mainProperties.setFitnessAlpha(1.0);
		
		for(int i = 0; i < 50; i++)
		{
			tmpSeries = series.copy();
			tmpSeries.setAllModifiable(false);
			tmpSeries.setModifiable(1, true);
			tmpSeries.mutate();
			population.addIndividual(tmpSeries);
		}
		
		double tmpFitness, bestFitness;
		FilterSeries bestFilterSeries;
		for(int i = 0; i < 10; i++)
		{
			bestFitness = population.getBestFitness();
			System.out.println("best fitness: " + bestFitness);
			recombineCandidates = population.tournamentSelection(10, 2, false);
			for(FilterSeries filterSeries : recombineCandidates.getIndividuals())
			{
				filterSeries.recombine(population.getRandomIndividual(filterSeries));
				population.addIndividual(filterSeries.mutate());
			}
			population = population.tournamentSelection(50, 2, false);
			population.printFitness();
			tmpFitness = population.getBestFitness();
			if(tmpFitness > bestFitness)
				i = 0;
		}
		
		bestFilterSeries = population.getBestIndividual();
		System.out.println("==> " + bestFilterSeries.getFitness());
		bestFilterSeries.apply(sourceImage);
		monitor.setImage(sourceImage);
		monitor.paint();
		
//		series.addFilters(population.getBestIndividual());
		SerializationHelper.JAXBSerialize(bestFilterSeries);
		
		System.out.println("\nDONE");
	}
}