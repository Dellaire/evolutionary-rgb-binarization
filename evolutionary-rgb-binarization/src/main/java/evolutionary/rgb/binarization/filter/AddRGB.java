package evolutionary.rgb.binarization.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.helper.Support;
import evolutionary.rgb.binarization.properties.MainProperties;
import evolutionary.rgb.binarization.region.IRegion;

public class AddRGB extends AbstractFilter
{
	private int red;
	private int green;
	private int blue;
	
	public AddRGB(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	private void addRGB(BufferedImage image, int x, int y, double intensity)
	{
		int[] oldColor, newColor;
		Color tmpColor;
		
		tmpColor = new Color(image.getRGB(x, y));
		oldColor = new int[3];
		oldColor[0] = tmpColor.getRed();
		oldColor[1] = tmpColor.getGreen();
		oldColor[2] = tmpColor.getBlue();
		newColor = new int[3];
		newColor[0] = Support.cap255(oldColor[0] + (int)(this.red * intensity));
		newColor[1] = Support.cap255(oldColor[1] + (int)(this.green * intensity));
		newColor[2] = Support.cap255(oldColor[2] + (int)(this.blue * intensity));
		tmpColor = new Color(newColor[0], newColor[1], newColor[2]);
		image.setRGB(x, y, tmpColor.getRGB());
	}
	
	@Override
	public void apply(BufferedImage image)
	{
		double intensity;
		
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				intensity = this.region.getIntensity(x, y);
				if(intensity > 0)
					this.addRGB(image, x, y, intensity);
			}
		}
	}
	
	@Override
	public void apply(BufferedImage image, int x, int y)
	{
		double intensity;
		
		intensity = this.region.getIntensity(x, y);
		if(intensity > 0)
			this.addRGB(image, x, y, intensity);
	}
	
//	private int cap(int value)
//	{
//		if(value < 256)
//		{
//			if(value < -255)
//				return -255;
//			else
//				return value;
//		}
//		else
//			return 255;
//	}
	
	public int getBlue()
	{
		return this.blue;
	}
	
	public int getGreen()
	{
		return this.green;
	}
	
	@Override
	public int getID()
	{
		return 0;
	}
	
	public int getRed()
	{
		return this.red;
	}
	
	@Override
	public String getSerializationString()
	{
		String serializationString;
		
		serializationString = "<AddRGB red=" + this.red + " green=" + this.green + " blue=" + this.blue + ">\n";
		serializationString += this.region.getSerializationString();
		serializationString += "</AddRGB>\n";
		
		return serializationString;
	}
	
	@Override
	public String getString()
	{
		return "addRGB [" + this.red + ", " + this.green + ", " + this.blue + "] @ " + this.region.getString();
	}
	
	@Override
	public AddRGB mutate()
	{
		if(!this.isModifiable())
			return this;
		
		int tmpRed, tmpGreen, tmpBlue, mutationValue;
		IRegion tmpRegion;
		AddRGB tmpAddRGB;
		
		mutationValue = MainProperties.getInstance().getMutationFilterValue();
		tmpRegion = this.region.mutate();
		tmpRed = this.red;
		tmpGreen = this.green;
		tmpBlue = this.blue;
		tmpRed += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpGreen += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpBlue += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpAddRGB = new AddRGB(tmpRed, tmpGreen, tmpBlue);
		tmpAddRGB.setRegion(tmpRegion);
		
		return tmpAddRGB;
	}

	@Override
	public AbstractFilter recombine(AbstractFilter filter)
	{
		if(!this.isModifiable() || !filter.isModifiable())
			return this;
		
		int newRed, newGreen, newBlue;
		IRegion newRegion;
		AddRGB newAddRGB;
		
		newRed = ((AddRGB)filter).getRed();
		newGreen = ((AddRGB)filter).getGreen();
		newBlue = ((AddRGB)filter).getBlue();
		newRegion = filter.getRegion();
		
		switch(MainProperties.getInstance().getRecombinationMode())
		{
			case arithmetic:
			{
				double distance;
				
				distance = MainProperties.getInstance().getArithmeticDistance();
				newRed += this.red;
				newGreen += this.green;
				newBlue += this.blue;
				newRed *= distance;
				newGreen *= distance;
				newBlue *= distance;
				break;
			}
			case permutation:
			{
				if(RandomHelper.getRandom().nextBoolean())
					newRed = this.red;
				if(RandomHelper.getRandom().nextBoolean())
					newGreen = this.green;
				if(RandomHelper.getRandom().nextBoolean())
					newBlue = this.blue;
			}
			default:
				break;
		}
		
		newRegion = newRegion.recombine(this.region);
		newAddRGB = new AddRGB(newRed, newGreen, newBlue);
		newAddRGB.setRegion(newRegion);
		
		return newAddRGB;
	}
}
