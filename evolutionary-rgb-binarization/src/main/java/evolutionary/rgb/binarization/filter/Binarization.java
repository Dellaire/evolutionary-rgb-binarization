package evolutionary.rgb.binarization.filter;

import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.region.Global;

public class Binarization extends Threshold
{
	public Binarization()
	{
		int minRed, minGreen, minBlue;
		
		minRed = RandomHelper.getRandom().nextInt(200);
		minGreen = RandomHelper.getRandom().nextInt(200);
		minBlue = RandomHelper.getRandom().nextInt(200);
		
		new Binarization
		(
			minRed,
			minGreen,
			minBlue,
			minRed + RandomHelper.getRandom().nextInt(55),
			minGreen + RandomHelper.getRandom().nextInt(55),
			minBlue + RandomHelper.getRandom().nextInt(55)
		);
		this.region = new Global();
	}
	
	public Binarization(int minRed, int minGreen, int minBlue, int maxRed, int maxGreen, int maxBlue)
	{
		super(minRed, minGreen, minBlue, maxRed, maxGreen, maxBlue, 0, 0, 0, 255, 255, 255);
	}
	
	@Override
	public int getID()
	{
		return 10;
	}
	
	@Override
	public void setMapIn(int mapInRed, int mapInGreen, int mapInBlue)
	{
		
	}
	
	@Override
	public void setMapOut(int mapOutRed, int mapOutGreen, int mapOutBlue)
	{
		
	}
	
	@Override
	public AbstractFilter recombine(AbstractFilter filter)
	{
		if(!this.isModifiable() || !filter.isModifiable())
			return this;
		
		int[] newMinRGB = ((Threshold)filter).getMinRGB();
		int[] newMaxRGB = ((Threshold)filter).getMaxRGB();
		Binarization newBinarization;
		
		newBinarization = new Binarization
						(
							(this.minRed + (int)(0.5 * (newMinRGB[0] - this.minRed))),
							(this.minGreen + (int)(0.5 * (newMinRGB[1] - this.minGreen))),
							(this.minBlue + (int)(0.5 * (newMinRGB[2] - this.minBlue))),
							(this.maxRed + (int)(0.5 * (newMaxRGB[0] - this.maxRed))),
							(this.maxGreen + (int)(0.5 * (newMaxRGB[1] - this.maxGreen))),
							(this.maxBlue + (int)(0.5 * (newMaxRGB[2] - this.maxBlue)))
						);
		newBinarization.setRegion(this.region.recombine(filter.region));
		
		return newBinarization;
	}
}
