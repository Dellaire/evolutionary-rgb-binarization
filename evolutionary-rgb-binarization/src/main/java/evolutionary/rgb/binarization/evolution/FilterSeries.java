package evolutionary.rgb.binarization.evolution;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import evolutionary.rgb.binarization.filter.AbstractFilter;
import evolutionary.rgb.binarization.properties.MainProperties;

public class FilterSeries
{
	private double memFitness;
	private List<AbstractFilter> filters;
	private Fitness fitnessFunction;
	private MainProperties mainProperties;
	
	public FilterSeries()
	{
		this.filters = new ArrayList<AbstractFilter>();
		this.memFitness = 1;
		this.mainProperties = MainProperties.getInstance();
	}
	
	public FilterSeries addFilter(AbstractFilter filter)
	{
		this.filters.add(filter);
		
		return this;
	}
	
	public FilterSeries addFilter(int n, AbstractFilter filter)
	{
		this.filters.add(n, filter);
		
		return this;
	}
	
	public FilterSeries addFilters(FilterSeries filterSeries)
	{
		for(AbstractFilter filter : filterSeries.getFilters())
			this.filters.add(filter);
		
		return this;
	}
	
	public void apply(BufferedImage image)
	{
		for(AbstractFilter filter : filters)
			filter.apply(image);
	}
	
	public void applyByPixel(BufferedImage image)
	{
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				for(AbstractFilter filter : filters)
					filter.apply(image, x, y);
			}
		}
	}
	
	public void applyFirstNFilters(BufferedImage image, int n)
	{
		for(int i = 0; (i < n) && (i < this.filters.size()); i++)
			this.filters.get(i).apply(image);
	}
	
	public FilterSeries copy()
	{
		FilterSeries copy;
		
		copy = new FilterSeries();
		for(AbstractFilter filter : this.filters)
			copy.addFilter(filter);
		copy.setFitnessFunction(this.fitnessFunction);
		
		return copy;
	}
	
	public void draw(BufferedImage image)
	{
		for(AbstractFilter filter : this.filters)
			filter.drawTrace(image);
	}
	
	public List<AbstractFilter> getFilters()
	{
		return this.filters;
	}
	
	public double getFitness()
	{
		if(this.memFitness > 0)
			this.memFitness = this.fitnessFunction.getFitness(this, mainProperties.getFitnessMode());
		return this.memFitness;
	}
	
	public String getNthFilterString(int n)
	{
		return this.filters.get(n).getString();
	}
	
	public String getSerializationString()
	{
		String serializationString;
		
		serializationString = "<FilterSeries>\n";
		for(AbstractFilter filter : this.filters)
			serializationString += filter.getSerializationString();
		serializationString += "</FilterSeries>\n";
		
		return serializationString;
	}
	
	public int getSize()
	{
		return this.filters.size();
	}
	
	public FilterSeries mutate()
	{
		FilterSeries tmpFilterSeries;
		
		tmpFilterSeries = new FilterSeries();
		tmpFilterSeries.setFitnessFunction(this.fitnessFunction);
		for(AbstractFilter filter : this.filters)
			tmpFilterSeries.addFilter(filter.mutate());
		tmpFilterSeries.resetFitness();
		
		return tmpFilterSeries;
	}
	
	public FilterSeries recombine(FilterSeries filterSeries)
	{
		boolean equal;
		int i;
		FilterSeries newFilterSeries;
		
		equal = true;
		if(this.filters.size() == filterSeries.getFilters().size())
		{
			for(i = 0; i < this.filters.size(); i++)
			{
				if((this.filters.get(i).getID() != filterSeries.getFilters().get(i).getID()) || (this.filters.get(i).getRegion().getID() != filterSeries.getFilters().get(i).getRegion().getID()))
					equal = false;
			}
		}
		else
			equal = false;
		if(equal)
		{
			newFilterSeries = new FilterSeries();
			for(i = 0; i < this.filters.size(); i++)
				newFilterSeries.addFilter(this.filters.get(i).recombine(filterSeries.getFilters().get(i)));
			
			return newFilterSeries;
		}
		else
			return this;
	}
	
	public void resetFitness()
	{
		this.memFitness = 1;
	}
	
	public void setFitnessFunction(Fitness fitnessFunction)
	{
		this.fitnessFunction = fitnessFunction;
	}
	
	public void setAllModifiable(boolean modifiable)
	{
		for(AbstractFilter filter : this.filters)
			filter.setModifiable(modifiable);
	}
	
	public void setModifiable(int n, boolean modifiable)
	{
		this.filters.get(n).setModifiable(modifiable);
	}
}
