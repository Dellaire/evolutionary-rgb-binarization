package evolutionary.rgb.binarization.helper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.region.Spot;

@XmlRootElement(namespace = "evo.bin.rgb")
public class JAXBAddRGB
{
	@XmlAttribute
	private int red;
	@XmlAttribute
	private int green;
	@XmlAttribute
	private int blue;
	@XmlElement(name = "Spot")
	private JAXBSpot spot;
	
	public JAXBAddRGB(AddRGB addRGB)
	{
		this.red = addRGB.getRed();
		this.green = addRGB.getGreen();
		this.blue = addRGB.getBlue();
		this.spot = new JAXBSpot(((Spot)addRGB.getRegion()));
	}
	
	public JAXBAddRGB()
	{
		
	}
	
	public int getRed()
	{
		return this.red;
	}
	
	public int getGreen()
	{
		return this.green;
	}
	
	public int getBlue()
	{
		return this.blue;
	}
	
	public JAXBSpot getSpot()
	{
		return this.spot;
	}
}