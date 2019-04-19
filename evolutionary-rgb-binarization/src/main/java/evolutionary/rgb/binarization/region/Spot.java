package evolutionary.rgb.binarization.region;

import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.helper.RandomHelper;
import evolutionary.rgb.binarization.properties.MainProperties;

public class Spot implements IRegion
{
	private int x;
	private int y;
	private int r;
	private int blur;
	
	public Spot(int x, int y, int r, int blur)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.blur = blur;
		//TODO delete --v
		this.blur = 100;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getR()
	{
		return this.r;
	}
	
	public int getBlur()
	{
		return this.blur;
	}
	
	@Override
	public void draw(BufferedImage image)
	{
		image.getGraphics().drawOval(this.x - (this.r / 2), this.y - (this.r / 2), this.r, this.r);
	}
	
	@Override
	public int getID()
	{
		return 0;
	}

	@Override
	public double getIntensity(int x, int y)
	{
		if(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) > Math.pow(this.r, 2))
			return 0.0;
		else
		{
			double px = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
			double p1 = (double)this.r * (1 - ((double)this.blur / 100));
			
			if(px <= p1)
				return 1.0;
			else
			{
				return ((double)this.r - px) / ((double)this.r - (p1));
			}
		}
	}
	
	@Override
	public String getSerializationString()
	{
		return "<Spot x=" + this.x + " y=" + this.y + " r=" + this.r + " blur=" + this.blur + "/>\n";
	}
	
	@Override
	public String getString()
	{
		return "spot [x: " + this.x + ", y: " + this.y + ", r: " + this.r + ", blur: " + this.blur + "]";
	}

	@Override
	public Spot mutate()
	{
		/*int tmpX, tmpY, tmpR, tmpBlur; TODO origin code
		
		tmpX = this.x;
		tmpY = this.y;
		tmpR = this.r;
		tmpBlur = this.blur;
		tmpX += RandomHelper.getRandom().nextInt(value * 2) - value;
		tmpY += RandomHelper.getRandom().nextInt(value * 2) - value;
		tmpR += RandomHelper.getRandom().nextInt(value * 2) - value;
		tmpBlur += RandomHelper.getRandom().nextInt(value * 2) - value;
		
		return new Spot(tmpX, tmpY, tmpR, tmpBlur);*/
		
		int tmpX, tmpY, tmpR, mutationValue;
		
		mutationValue = MainProperties.getInstance().getMutationRegionValue();
		tmpX = this.x;
		tmpY = this.y;
		tmpR = this.r;
		tmpX += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpY += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpR += RandomHelper.getRandom().nextInt(mutationValue * 2) - mutationValue;
		tmpR = Math.abs(tmpR);
		
		return new Spot(tmpX, tmpY, tmpR, this.blur);
	}
	
	@Override
	public IRegion recombine(IRegion region)
	{
		double distance;
		
		distance = MainProperties.getInstance().getArithmeticDistance();
		
		return new Spot
				(
					this.x + (int)(distance * (((Spot)region).getX() - this.x)),
					this.y + (int)(distance * (((Spot)region).getY() - this.y)),
					this.r + (int)(distance * (((Spot)region).getR() - this.r)),
					this.blur + (int)(distance * (((Spot)region).getBlur() - this.blur))
				);
	}
}
