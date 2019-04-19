package evolutionary.rgb.binarization.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.helper.Support;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.region.IRegion;
import evolutionary.rgb.binarization.region.Pixel;

public class LocalBinarization extends Binarization
{
	public LocalBinarization(int x, int y)
	{
		super();
		this.region = new Pixel(x, y);
	}
	
	@Override
	public void apply(BufferedImage image)
	{
		Color color;
		
		int pX, pY;
		pX = ((Pixel)this.region).getX();
		pY = ((Pixel)this.region).getY();
		
		color = new Color(image.getRGB(pX, pY));
		
		if((color.getRed() >= this.minRed) && (color.getGreen() >= this.minGreen) && (color.getBlue() >= this.minBlue) && (color.getRed() <= this.maxRed) && (color.getGreen() <= this.maxGreen) && (color.getBlue() <= this.maxBlue))
		{
			if(this.mapInRed >= 0)
				image.setRGB(pX, pY, new Color(this.mapInRed, this.mapInGreen, this.mapInBlue).getRGB());
		}
		else
		{
			if(this.mapOutRed >= 0)
				image.setRGB(pX, pY, new Color(this.mapOutRed, this.mapOutGreen, this.mapOutBlue).getRGB());
		}
		
//		System.out.println(pX + " | " + pY);
	}
	
	@Override
	public AbstractFilter mutate()
	{
		if(!this.isModifiable())
			return this;
		
		int tmpMinRed, tmpMinGreen, tmpMinBlue, tmpMaxRed, tmpMaxGreen, tmpMaxBlue, mutationValue;
		LocalBinarization tmpThreshold;
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
		tmpThreshold = new LocalBinarization(((Pixel)this.region).getX(), ((Pixel)this.region).getY());
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
