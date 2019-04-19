package evolutionary.rgb.binarization.factorys;

public class Interval
{
	private int min;
	private int max;
	
	public Interval(int min, int max)
	{
		if(min <= max)
		{
			this.min = min;
			this.max = max;
		}
		else
			throw new RuntimeException("### ERROR ### : Illegal period!");
	}
	
	public int getMin()
	{
		return this.min;
	}
	
	public int getMax()
	{
		return this.max;
	}
	
	public void setMin(int min)
	{
		if(min <= this.max)
			this.min = min;
		else
			throw new RuntimeException("### ERROR ### : Illegal period!");
	}
	
	public void setMax(int max)
	{
		if(this.min <= max)
			this.max = max;
		else
			throw new RuntimeException("### ERROR ### : Illegal period!");
	}
	
	public void setMinMax(int min, int max)
	{
		if(min <= max)
		{
			this.min = min;
			this.max = max;
		}
		else
			throw new RuntimeException("### ERROR ### : Illegal period!");
	}
}
