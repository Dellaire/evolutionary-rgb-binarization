package evolutionary.rgb.binarization.evolution;

import java.util.ArrayList;
import java.util.List;

import evolutionary.rgb.binarization.evolution.Mode.FitnessMode;
import evolutionary.rgb.binarization.filter.AbstractFilter;
import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.filter.Threshold;
import evolutionary.rgb.binarization.helper.LoggingHelper;
import evolutionary.rgb.binarization.helper.PropertyHelper;
import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.region.Global;
import evolutionary.rgb.binarization.region.Spot;

public class Population
{
	private double sumFitness;
	private Fitness fitness;
	private List<FilterSeries> individuals;
	
	public Population()
	{
		this.individuals = new ArrayList<FilterSeries>();
	}
	
	public Population(int nIndividuals, int size)
	{
		this.individuals = new ArrayList<FilterSeries>();
		
		for(int i = 0; i < nIndividuals; i++)
		{
			AbstractFilter filter;
			FilterSeries filterSeries;
			
			filterSeries = new FilterSeries();
			for(int j = 0; j < size - 1; j++)
			{
				filter = new AddRGB
						(
							RandomHelper.getRandom().nextInt(511) - 255, 
							RandomHelper.getRandom().nextInt(511) - 255,
							RandomHelper.getRandom().nextInt(511) - 255
						);
				filterSeries.addFilter(filter);
				filter.setRegion
				(
					new Spot
					(
						RandomHelper.getRandom().nextInt(Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeX"))),
						RandomHelper.getRandom().nextInt(Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeY"))),
						RandomHelper.getRandom().nextInt(250) + 10,
						RandomHelper.getRandom().nextInt(90) + 10
					)
				);
			}
			
			Threshold threshold = new Threshold();
			threshold.setRegion(new Global());
			int minRed = RandomHelper.getRandom().nextInt(230) + 20;
			int minGreen = RandomHelper.getRandom().nextInt(230) + 20;
			int minBlue = RandomHelper.getRandom().nextInt(230) + 20;
			threshold.setMinRGB
			(
				minRed,
				minGreen,
				minBlue
			);
			threshold.setMaxRGB
			(
				RandomHelper.getRandom().nextInt(256 - minRed) + minRed,
				RandomHelper.getRandom().nextInt(256 - minGreen) + minGreen,
				RandomHelper.getRandom().nextInt(256 - minBlue) + minBlue
			);
			threshold.setMapIn(0, 0, 0);
			threshold.setMapOut(255, 255, 255);
			filterSeries.addFilter(threshold);
			
			filterSeries.setFitnessFunction(this.fitness);
			this.individuals.add(filterSeries);
		}
	}
	
	public double getSumFitness() {
		return sumFitness;
	}

	public void addIndividual(FilterSeries filterSeries)
	{
		filterSeries.setFitnessFunction(this.fitness);
		this.individuals.add(filterSeries);
	}
	
	public double getAverageFitness()
	{
		double fitness;
		
		fitness = 0.0;
		for(FilterSeries filterSeries : this.individuals)
			fitness += filterSeries.getFitness();
		
		return fitness / this.individuals.size();
	}
	
	public double getBestFitness()
	{
		return this.getBestIndividual().getFitness();
	}
	
	public FilterSeries getBestIndividual()
	{
		double bestFitness, tmpFitness;
		FilterSeries bestIndividual;
		
		bestIndividual = this.individuals.get(0);
		bestFitness = bestIndividual.getFitness();
		
		for(FilterSeries filterSeries : this.individuals)
		{
			tmpFitness = filterSeries.getFitness();
			if(tmpFitness > bestFitness)
			{
				bestIndividual = filterSeries;
				bestFitness = tmpFitness;
			}
		}
		
		return bestIndividual;
	}
	
	public List<FilterSeries> getIndividuals()
	{
		return this.individuals;
	}
	
	public FilterSeries getNthIndividual(int n)
	{
		return this.individuals.get(n);
	}
	
	public FilterSeries getRandomIndividual(FilterSeries exceptIndividual)
	{
		int randomIndividual;
		
		randomIndividual = RandomHelper.getRandom().nextInt(this.individuals.size());
		if(this.individuals.get(randomIndividual).equals(exceptIndividual))
		{
			if(randomIndividual < this.individuals.size() - 1)
				return this.individuals.get(randomIndividual + 1);
			else
				return this.individuals.get(randomIndividual - 1);
		}
		else
			return this.individuals.get(randomIndividual);
	}
	
	public int getSize()
	{
		return this.individuals.size();
	}
	
	public FilterSeries getWorstIndividual()
	{
		double worstFitness, tmpFitness;
		FilterSeries worstIndividual;
		
		worstIndividual = this.individuals.get(0);
		worstFitness = worstIndividual.getFitness();
		
		for(FilterSeries filterSeries : this.individuals)
		{
			tmpFitness = filterSeries.getFitness();
			if(tmpFitness < worstFitness)
			{
				worstIndividual = filterSeries;
				worstFitness = tmpFitness;
			}
		}
		
		return worstIndividual;
	}
	
	public Population mutate(boolean enableLogging)
	{
		FilterSeries tmpFilterSeries;
		Population newPopulation;
		
		newPopulation = new Population();
		newPopulation.setFitnessFunction(this.fitness);
		if(enableLogging)
			LoggingHelper.write("mutation:\n");
		for(FilterSeries filterSeries : this.individuals)
		{
			tmpFilterSeries = filterSeries.mutate();
			newPopulation.addIndividual(tmpFilterSeries);
			if(enableLogging)
				LoggingHelper.write(filterSeries.getFitness() + " --> " + tmpFilterSeries.getFitness() + "\n");
		}
		if(enableLogging)
			LoggingHelper.write("\n");
		
		return newPopulation;
	}
	
	public void print(FitnessMode fitnessMode)
	{
		LoggingHelper.write("\n\n");
		for(FilterSeries filterSeries : this.individuals)
		{
			for(int i = 0; i < filterSeries.getSize(); i++)
				LoggingHelper.write(filterSeries.getNthFilterString(i) + "\n");
			LoggingHelper.write("---- : " + this.fitness.getFitness(filterSeries, fitnessMode) + "\n");
		}
	}
	
	public void printFitness()
	{
		LoggingHelper.write(this.individuals.size() + " individuals with fitness:\n");
		for(FilterSeries filterSeries : this.individuals)
			LoggingHelper.write(filterSeries.getFitness() + "\n");
		LoggingHelper.write("\n");
	}
	
	public void resetFitness()
	{
		for(FilterSeries filterSeries : this.individuals)
			filterSeries.resetFitness();
	}
	
	public void setFitnessFunction(Fitness fitness)
	{
		this.fitness = fitness;
		for(FilterSeries filterSeries : this.individuals)
			filterSeries.setFitnessFunction(fitness);
	}
	
	public void setModifiable(int n, boolean modifiable)
	{
		for(FilterSeries filterSeries : this.individuals)
			filterSeries.setModifiable(n, modifiable);
	}
	
	public Population tournamentSelection(int newSize, int nContest, boolean deleteSelectedIndividuals)
	{
		int best;
		int[] wins;
		Population newPopulation;
		
		wins = new int[this.individuals.size()];
		newPopulation = new Population();
		for(int i = 0; i < wins.length; i++)
		{
			wins[i] = 0;
			for(int j = 0; j < nContest; j++)
			{
				if(this.individuals.get(i).getFitness() > this.individuals.get(RandomHelper.getRandom().nextInt(this.individuals.size())).getFitness())
					wins[i]++;
			}
		}
		for(int i = 0; i < newSize; i++)
		{
			best = 0;
			for(int j = 1; j < wins.length; j++)
			{
				if(wins[j] > wins[best])
					best = j;
			}
			newPopulation.addIndividual(this.individuals.get(best));
			wins[best] = -1;
		}
		if(deleteSelectedIndividuals)
		{
			for(FilterSeries filterSeries : newPopulation.getIndividuals())
				this.individuals.remove(filterSeries);
		}
		newPopulation.setFitnessFunction(this.fitness);
		
		return newPopulation;
	}
	
	public void union(Population population)
	{
		this.individuals.addAll(population.getIndividuals());
	}
	
	public void updateFitness(FitnessMode fitnessMode)
	{
		int i;
		
		i = 0;
		this.sumFitness = 0;
		for(FilterSeries filterSeries : this.individuals)
		{
			System.out.println(++i + " of " + this.individuals.size() + " : " + filterSeries.getFitness());
			this.sumFitness += filterSeries.getFitness();
		}
	}
}
