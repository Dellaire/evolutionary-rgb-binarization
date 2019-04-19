package evolutionary.rgb.binarization.factorys;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.filter.AbstractFilter;
import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.filter.Threshold;
import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.region.Global;
import evolutionary.rgb.binarization.region.IRegion;
import evolutionary.rgb.binarization.region.Spot;

public class FilterSeriesFactory
{
	private int[] addRGBProperties;
	private int[] thresholdProperties;
	
	private int[] spotProperties;
	
	public FilterSeriesFactory()
	{
		this.addRGBProperties = new int[6];
		this.thresholdProperties = new int[24];
		this.spotProperties = new int[8];
		
		int[] addRGB = {0, 256, 0, 256, 0, 256};
		this.setAddRGBProperties(addRGB);
		int[] spot = {0, 500, 0, 500, 10, 50, 0, 101};
		this.setSpotProperties(spot);
		int[] threshold = {0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256, 0, 256};
		this.setThresholdProperties(threshold);
	}
	
	public FilterSeries createFilterSeries(String series, DistributionMode distributionMode)
	{
		//AddRGB,Spot|Threshold,Global|...
		String[] filters, parts;
		IRegion tmpRegion;
		AbstractFilter tmpFilter;
		FilterSeries tmpFilterSeries;
		
		tmpFilterSeries = new FilterSeries();
		filters = series.split(";");
		for(int i = 0; i < filters.length; i++)
		{
			if(filters[i].length() > 0)
			{
				parts = filters[i].split(",");
				switch(parts[0])
				{
					case "AddRGB":
						tmpFilter = this.createAddRGB(distributionMode);
						break;
					case "Threshold":
						tmpFilter = this.createThreshold(distributionMode);
						break;
					default:
						throw new RuntimeException("### ERROR ### : Illegal filter name!");
				}
				switch(parts[1])
				{
					case "Spot":
						tmpRegion = this.createSpot(distributionMode);
						break;
					case "Global":
						tmpRegion = this.createGlobal();
						break;
					default:
						throw new RuntimeException("### ERROR ### : Illegal region name!");
				}
				tmpFilter.setRegion(tmpRegion);
				tmpFilterSeries.addFilter(tmpFilter);
			}
		}
		
		return tmpFilterSeries;
	}
	
	private AddRGB createAddRGB(DistributionMode distributionMode)
	{
		return new AddRGB
				(
					this.getRandomBetween(this.addRGBProperties[0], this.addRGBProperties[1], distributionMode),
					this.getRandomBetween(this.addRGBProperties[2], this.addRGBProperties[3], distributionMode),
					this.getRandomBetween(this.addRGBProperties[4], this.addRGBProperties[5], distributionMode)
				);
	}
	
	private Global createGlobal()
	{
		return new Global();
	}
	
	private Spot createSpot(DistributionMode distributionMode)
	{
		return new Spot
				(
					this.getRandomBetween(this.spotProperties[0], this.spotProperties[1], distributionMode),
					this.getRandomBetween(this.spotProperties[2], this.spotProperties[3], distributionMode),
					this.getRandomBetween(this.spotProperties[4], this.spotProperties[5], distributionMode),
					this.getRandomBetween(this.spotProperties[6], this.spotProperties[7], distributionMode)
				);
	}
	
	private Threshold createThreshold(DistributionMode distributionMode)
	{
		return new Threshold
				(
					this.getRandomBetween(this.thresholdProperties[0], this.thresholdProperties[1], distributionMode),
					this.getRandomBetween(this.thresholdProperties[2], this.thresholdProperties[3], distributionMode),
					this.getRandomBetween(this.thresholdProperties[4], this.thresholdProperties[5], distributionMode),
					this.getRandomBetween(this.thresholdProperties[6], this.thresholdProperties[7], distributionMode),
					this.getRandomBetween(this.thresholdProperties[8], this.thresholdProperties[9], distributionMode),
					this.getRandomBetween(this.thresholdProperties[10], this.thresholdProperties[11], distributionMode),
					this.getRandomBetween(this.thresholdProperties[12], this.thresholdProperties[13], distributionMode),
					this.getRandomBetween(this.thresholdProperties[14], this.thresholdProperties[15], distributionMode),
					this.getRandomBetween(this.thresholdProperties[16], this.thresholdProperties[17], distributionMode),
					this.getRandomBetween(this.thresholdProperties[18], this.thresholdProperties[19], distributionMode),
					this.getRandomBetween(this.thresholdProperties[20], this.thresholdProperties[21], distributionMode),
					this.getRandomBetween(this.thresholdProperties[22], this.thresholdProperties[23], distributionMode)
				);
	}
	
	public void setAddRGBProperties(int[] addRGBProperties)
	{
		this.addRGBProperties = addRGBProperties;
	}
	
	public void setSpotProperties(int[] spotProperties)
	{
		this.spotProperties = spotProperties;
	}
	
	public void setThresholdProperties(int[] thresholdProperties)
	{
		this.thresholdProperties = thresholdProperties;
	}
	
	private int getRandomBetween(int min, int max, DistributionMode distributionMode)
	{
		if(min == max)
			return min;
		switch(distributionMode)
		{
			case equal:
				return RandomHelper.getRandom().nextInt(max - min);
			case gaussian:
				return (int)(RandomHelper.getRandom().nextGaussian() + 1) * ((max - min) / 2);
			default:
				throw new RuntimeException("### ERROR ### : Illegal distribution mode!");
		}
	}
}
