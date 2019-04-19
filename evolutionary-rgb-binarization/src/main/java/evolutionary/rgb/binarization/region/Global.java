package evolutionary.rgb.binarization.region;

import java.awt.image.BufferedImage;

public class Global implements IRegion
{
	public Global()
	{
		
	}
	
	@Override
	public void draw(BufferedImage image) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getID()
	{
		return 1;
	}
	
	@Override
	public double getIntensity(int x, int y)
	{
		return 1.0;
	}
	
	@Override
	public String getSerializationString()
	{
		return "<Global/>\n";
	}
	
	@Override
	public String getString()
	{
		return "global";
	}
	
	@Override
	public IRegion mutate() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IRegion recombine(IRegion region)
	{
		return this;
	}
}
