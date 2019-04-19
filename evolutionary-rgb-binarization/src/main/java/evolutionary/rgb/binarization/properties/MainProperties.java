package evolutionary.rgb.binarization.properties;

import evolutionary.rgb.binarization.evolution.Mode.*;

public class MainProperties
{
	private static MainProperties instance;
	
	private int mutationFilterValue;
	private int mutationRegionValue;
	private int corridorForeground;
	private int corridorBackground;
	private double arithmeticDistance;
	private double fitnessAlpha;
	private double fitnessBeta;
	
	private FitnessMode fitnessMode;
	private MutationMode mutationMode;
	private RecombinationMode recombinationMode;
	
	private MainProperties()
	{
		this.mutationFilterValue = 100;
		this.mutationRegionValue = 100;
		this.corridorForeground = 10;
		this.corridorBackground = 30;
		this.arithmeticDistance = 0.5;
		this.fitnessAlpha = 400.0;
		this.fitnessBeta = 1.0;
		
		this.fitnessMode = FitnessMode.rgbDistance;
		this.mutationMode = MutationMode.equal;
		this.recombinationMode = RecombinationMode.arithmetic;
	}
	
	public static MainProperties getInstance()
	{
		if(instance == null)
			instance = new MainProperties();
		return instance;
	}
	
	public double getArithmeticDistance()
	{
		return this.arithmeticDistance;
	}
	
	public int getCorridorForeground()
	{
		return this.corridorForeground;
	}
	
	public int getCorridorBackground()
	{
		return this.corridorBackground;
	}
	
	public double getFitnessAlpha()
	{
		return this.fitnessAlpha;
	}
	public double getFitnessBeta()
	{
		return this.fitnessBeta;
	}
	
	public FitnessMode getFitnessMode()
	{
		return this.fitnessMode;
	}
	
	public int getMutationFilterValue()
	{
		return this.mutationFilterValue;
	}
	
	public MutationMode getMutationMode()
	{
		return this.mutationMode;
	}
	
	public int getMutationRegionValue()
	{
		return this.mutationRegionValue;
	}
	
	public RecombinationMode getRecombinationMode()
	{
		return this.recombinationMode;
	}
	
	public void setFitnessAlpha(double alpha)
	{
		this.fitnessAlpha = alpha;
	}
	
	public void setFitnessBeta(double beta)
	{
		this.fitnessAlpha = beta;
	}
	
	public void setFitnessMode(FitnessMode fitnessMode)
	{
		this.fitnessMode = fitnessMode;
	}
	
	public void setMutationMode(MutationMode mutationMode)
	{
		this.mutationMode = mutationMode;
	}
	
	public void setRecombinationMode(RecombinationMode recombinationMode)
	{
		this.recombinationMode = recombinationMode;
	}
}
