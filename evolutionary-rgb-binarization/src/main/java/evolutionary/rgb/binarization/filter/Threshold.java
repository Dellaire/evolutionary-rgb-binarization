package evolutionary.rgb.binarization.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.helper.Support;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.region.IRegion;

public class Threshold extends AbstractFilter
{
	protected int minRed = 0;
	protected int minGreen = 0;
	protected int minBlue = 0;
	protected int maxRed = 255;
	protected int maxGreen = 255;
	protected int maxBlue = 255;
	protected int mapInRed = 0;
	protected int mapInGreen = 0;
	protected int mapInBlue = 0;
	protected int mapOutRed = 255;
	protected int mapOutGreen = 255;
	protected int mapOutBlue = 255;
	
	public Threshold()
	{
		
	}
	
	public Threshold(int minRed, int minGreen, int minBlue, int maxRed, int maxGreen, int maxBlue, int mapInRed, int mapInGreen, int mapInBlue, int mapOutRed, int mapOutGreen, int mapOutBlue)
	{
		this.minRed = minRed;
		this.minGreen = minGreen;
		this.minBlue = minBlue;
		this.maxRed = maxRed;
		this.maxGreen = maxGreen;
		this.maxBlue = maxBlue;
		this.mapInRed = mapInRed;
		this.mapInGreen = mapInGreen;
		this.mapInBlue = mapInBlue;
		this.mapOutRed = mapOutRed;
		this.mapOutGreen = mapOutGreen;
		this.mapOutBlue = mapOutBlue;
	}
	
	public int[] getMinRGB()
	{
		int[] minRGB = {this.minRed, this.minGreen, this.minBlue};
		
		return minRGB;
	}
	
	public int[] getMaxRGB()
	{
		int[] maxRGB = {this.maxRed, this.maxGreen, this.maxBlue};
		
		return maxRGB;
	}
	
	public int[] getMapInRGB()
	{
		int[] mapInRGB = {this.mapInRed, this.mapInGreen, this.mapInBlue};
		
		return mapInRGB;
	}
	
	public int[] getMapOutRGB()
	{
		int[] mapOutRGB = {this.mapOutRed, this.mapOutGreen, this.mapOutBlue};
		
		return mapOutRGB;
	}
	
	public void setMapIn(int mapInRed, int mapInGreen, int mapInBlue)
	{
		this.mapInRed = mapInRed;
		this.mapInGreen = mapInGreen;
		this.mapInBlue = mapInBlue;
	}
	public void setMapOut(int mapOutRed, int mapOutGreen, int mapOutBlue)
	{
		this.mapOutRed = mapOutRed;
		this.mapOutGreen = mapOutGreen;
		this.mapOutBlue = mapOutBlue;
	}
	
	public void setMaxRGB(int maxRed, int maxGreen, int maxBlue)
	{
		this.maxRed = maxRed;
		this.maxGreen = maxGreen;
		this.maxBlue = maxBlue;
	}
	
	public void setMinRGB(int minRed, int minGreen, int minBlue)
	{
		this.minRed = minRed;
		this.minGreen = minGreen;
		this.minBlue = minBlue;
	}
	
	@Override
	public void apply(BufferedImage image)
	{
		Color color;
		
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				if(this.region.getIntensity(x, y) > 0.0)
				{
					color = new Color(image.getRGB(x, y));
					
					if((color.getRed() >= this.minRed) && (color.getGreen() >= this.minGreen) && (color.getBlue() >= this.minBlue) && (color.getRed() <= this.maxRed) && (color.getGreen() <= this.maxGreen) && (color.getBlue() <= this.maxBlue))
					{
						if(this.mapInRed >= 0)
							image.setRGB(x, y, new Color(this.mapInRed, this.mapInGreen, this.mapInBlue).getRGB());
					}
					else
					{
						if(this.mapOutRed >= 0)
							image.setRGB(x, y, new Color(this.mapOutRed, this.mapOutGreen, this.mapOutBlue).getRGB());
					}
				}
			}
		}
	}
	
	@Override
	public void apply(BufferedImage image, int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getID()
	{
		return 1;
	}
	
	@Override
	public String getSerializationString()
	{
		String serializationString;
		
		serializationString = "<Threshold";
		serializationString += " minRed=" + this.minRed;
		serializationString += " minGreen=" + this.minGreen;
		serializationString += " minBlue=" + this.minBlue;
		serializationString += " maxRed=" + this.maxRed;
		serializationString += " maxGreen=" + this.maxGreen;
		serializationString += " maxBlue=" + this.maxBlue;
		serializationString += " mapInRed=" + this.mapInRed;
		serializationString += " mapInGreen=" + this.mapInGreen;
		serializationString += " mapInBlue=" + this.mapInBlue;
		serializationString += " mapOutRed=" + this.mapOutRed;
		serializationString += " mapOutGreen=" + this.mapOutGreen;
		serializationString += " mapOutBlue=" + this.mapOutBlue;
		serializationString += ">\n";
		serializationString += this.region.getSerializationString();
		serializationString += "</Threshold>\n";
		
		return serializationString;
	}
	
	@Override
	public String getString()
	{
		return "threshold [" +
				"minR: " + this.minRed + ", " +
				"minG: " + this.minGreen + ", " +
				"minB: " + this.minBlue + ", " +
				"maxR: " + this.maxRed + ", " +
				"maxG: " + this.maxGreen + ", " +
				"maxB: " + this.maxBlue + "]" +
				" @ " + this.region.getString();
	}
	
	@Override
	public AbstractFilter mutate()
	{
		if(!this.isModifiable())
			return this;
		
		int tmpMinRed, tmpMinGreen, tmpMinBlue, tmpMaxRed, tmpMaxGreen, tmpMaxBlue, mutationValue;
		Threshold tmpThreshold;
		IRegion tmpRegion;
		
		mutationValue = MainProperties.getInstance().getMutationFilterValue();
		tmpMinRed = this.minRed;
		tmpMinGreen = this.minGreen;
		tmpMinBlue = this.minBlue;
		tmpMaxRed = this.maxRed;
		tmpMaxGreen = this.maxGreen;
		tmpMaxBlue = this.maxBlue;
		tmpMinRed += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpMinGreen += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpMinBlue += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpMaxRed += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpMaxGreen += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpMaxBlue += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpRegion = this.region.mutate();
		tmpThreshold = new Threshold();
		tmpThreshold.setMinRGB(Support.cap255(tmpMinRed), Support.cap255(tmpMinGreen), Support.cap255(tmpMinBlue));
		tmpThreshold.setMaxRGB(Support.cap255(tmpMaxRed), Support.cap255(tmpMaxGreen), Support.cap255(tmpMaxBlue));
		tmpThreshold.setRegion(tmpRegion);
		
		return tmpThreshold;
	}
	
	@Override
	public AbstractFilter recombine(AbstractFilter filter)
	{
		if(!this.isModifiable() || !filter.isModifiable())
			return this;
		
		int[] newMinRGB = ((Threshold)filter).getMinRGB();
		int[] newMaxRGB = ((Threshold)filter).getMaxRGB();
		int[] newMapInRGB = ((Threshold)filter).getMapInRGB();
		int[] newMapOutRGB = ((Threshold)filter).getMapOutRGB();
		Threshold newThreshold;
		
		newThreshold = new Threshold
						(
							(this.minRed + (int)(0.5 * (newMinRGB[0] - this.minRed))),
							(this.minGreen + (int)(0.5 * (newMinRGB[1] - this.minGreen))),
							(this.minBlue + (int)(0.5 * (newMinRGB[2] - this.minBlue))),
							(this.maxRed + (int)(0.5 * (newMaxRGB[0] - this.maxRed))),
							(this.maxGreen + (int)(0.5 * (newMaxRGB[1] - this.maxGreen))),
							(this.maxBlue + (int)(0.5 * (newMaxRGB[2] - this.maxBlue))),
							(this.mapInRed + (int)(0.5 * (newMapInRGB[0] - this.mapInRed))),
							(this.mapInGreen + (int)(0.5 * (newMapInRGB[1] - this.mapInGreen))),
							(this.mapInBlue + (int)(0.5 * (newMapInRGB[2] - this.mapInBlue))),
							(this.mapOutRed + (int)(0.5 * (newMapOutRGB[0] - this.mapOutRed))),
							(this.mapOutGreen + (int)(0.5 * (newMapOutRGB[1] - this.mapOutGreen))),
							(this.mapOutBlue + (int)(0.5 * (newMapOutRGB[2] - this.mapOutBlue)))
						);
		newThreshold.setRegion(this.region.recombine(filter.region));
		
		return newThreshold;
	}
}
