package evolutionary.rgb.binarization.region;

import java.awt.image.BufferedImage;

public class Pixel implements IRegion
{
	private int x;
	private int y;

	public Pixel(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	@Override
	public void draw(BufferedImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public double getIntensity(int x, int y)
	{
		if((x == this.x) && (y == this.y))
			return 1;
		return 0;
	}

	@Override
	public String getSerializationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRegion mutate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRegion recombine(IRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

}
