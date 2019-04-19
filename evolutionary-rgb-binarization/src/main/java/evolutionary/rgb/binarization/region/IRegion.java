package evolutionary.rgb.binarization.region;

import java.awt.image.BufferedImage;

public interface IRegion
{
	public void draw(BufferedImage image);
	
	public int getID();
	
	public double getIntensity(int x, int y);
	
	public String getSerializationString();
	
	public String getString();
	
	public IRegion mutate();
	
	public IRegion recombine(IRegion region);
}
