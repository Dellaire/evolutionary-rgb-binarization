package evolutionary.rgb.binarization.factorys;

import evolutionary.rgb.binarization.helper.PropertyHelper;
import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.region.Spot;

public class SpotFactory_alt
{
	private enum DistributionMode
	{
		equal, gaussian
	}
	
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int rMin;
	private int rMax;
	private int blurMin;
	private int blurMax;
	private DistributionMode distributionMode;
	
	public SpotFactory_alt()
	{
		this.xMin = 0;
		this.xMax = Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeX"));
		this.yMin = 0;
		this.yMax = Integer.parseInt(PropertyHelper.getGeneralProperty("imageSizeY"));
		this.rMin = 0;
		this.rMax = (this.xMax + this.yMax) / 2;
		this.blurMin = 0;
		this.blurMax = 100;
		this.distributionMode = DistributionMode.equal;
	}
	
	public Spot createSpot()
	{
		return new Spot
		(
			this.getRandomBetween(this.xMin, this.xMax),
			this.getRandomBetween(this.yMin, this.yMax),
			this.getRandomBetween(this.rMin, this.rMax),
			this.getRandomBetween(this.blurMin, this.blurMax)
		);
	}
	
	public SpotFactory_alt setXMin(int xMin)
	{
		this.xMin = xMin;
		
		return this;
	}
	
	public SpotFactory_alt setXMan(int xMax)
	{
		this.xMax = xMax;
		
		return this;
	}
	
	public SpotFactory_alt setYMin(int yMin)
	{
		this.yMin = yMin;
		
		return this;
	}
	
	public SpotFactory_alt setYMax(int yMax)
	{
		this.yMax = yMax;
		
		return this;
	}
	
	public SpotFactory_alt setRMin(int rMin)
	{
		this.rMin = rMin;
		
		return this;
	}
	
	public SpotFactory_alt setRMax(int rMax)
	{
		this.rMax = rMax;
		
		return this;
	}
	
	public SpotFactory_alt setBlurMin(int blurMin)
	{
		this.blurMin = blurMin;
		
		return this;
	}
	
	public SpotFactory_alt setBlurMax(int blurMax)
	{
		this.blurMax = blurMax;
		
		return this;
	}
	
	private int getRandomBetween(int min, int max)
	{
		switch(this.distributionMode)
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
