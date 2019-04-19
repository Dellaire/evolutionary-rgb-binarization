package evolutionary.rgb.binarization.tests.commonTests;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddRGB")
public class XMLAddRGB
{
	@XmlAttribute
	private int red = 1;
	@XmlAttribute
	private int green = 2;
	@XmlAttribute
	private int blue = 3;
	
	public void print()
	{
		System.out.println("[" + this.red + "," + this.green + "," + this.blue + "]");
	}
}
