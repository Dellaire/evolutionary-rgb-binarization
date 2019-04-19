package evolutionary.rgb.binarization.helper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import evolutionary.rgb.binarization.filter.Threshold;

@XmlRootElement(namespace = "evo.bin.rgb")
public class JAXBBinarizationThreshold
{
	@XmlAttribute
	private int redMin;
	@XmlAttribute
	private int greenMin;
	@XmlAttribute
	private int blueMin;
	@XmlAttribute
	private int redMax;
	@XmlAttribute
	private int greenMax;
	@XmlAttribute
	private int blueMax;
	@XmlElement(name = "Global")
	private JAXBGlobal global;
	
	public JAXBBinarizationThreshold(Threshold threshold)
	{
		int[] min = threshold.getMinRGB();
		int[] max = threshold.getMaxRGB();
		this.redMin = min[0];
		this.greenMin = min[1];
		this.blueMin = min[2];
		this.redMax = max[0];
		this.greenMax = max[1];
		this.blueMax = max[2];
		this.global = new JAXBGlobal();
	}
	
	public JAXBBinarizationThreshold()
	{
		
	}
	
	public int getRedMin()
	{
		return this.redMin;
	}
	
	public int getGreenMin()
	{
		return this.greenMin;
	}
	
	public int getBlueMin()
	{
		return this.blueMin;
	}
	
	public int getRedMax()
	{
		return this.redMax;
	}
	
	public int getGreenMax()
	{
		return this.greenMax;
	}
	
	public int getBlueMax()
	{
		return this.blueMax;
	}
}