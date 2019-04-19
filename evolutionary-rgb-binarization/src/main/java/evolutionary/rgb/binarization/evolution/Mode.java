package evolutionary.rgb.binarization.evolution;

public class Mode
{
	public enum FitnessMode
	{
		binary, rgbDistance, tmp
	}
	
	public enum MutationMode
	{
		equal, gaussian
	}
	
	public enum RecombinationMode
	{
		permutation, arithmetic
	}
}
