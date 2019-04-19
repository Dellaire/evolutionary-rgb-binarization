package evolutionary.rgb.binarization.helper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import evolutionary.rgb.binarization.region.Spot;

@XmlRootElement(namespace = "evo.bin.rgb")
public class JAXBSpot
{
	@XmlAttribute
	private int x;
	@XmlAttribute
	private int y;
	@XmlAttribute
	private int r;
	@XmlAttribute
	private int blur;
	
	public JAXBSpot()
	{
		
	}
	
	public JAXBSpot(Spot spot)
	{
		this.x = spot.getX();
		this.y = spot.getY();
		this.r = spot.getR();
		this.blur = spot.getBlur();
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
}