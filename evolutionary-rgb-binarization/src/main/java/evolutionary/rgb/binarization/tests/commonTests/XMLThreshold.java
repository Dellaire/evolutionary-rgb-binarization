package evolutionary.rgb.binarization.tests.commonTests;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Threshold")
public class XMLThreshold
{
	@XmlAttribute
	private int minRed = 10;
	@XmlAttribute
	private int minGreen = 10;
	@XmlAttribute
	private int minBlue = 10;
	@XmlAttribute
	private int maxRed = 30;
	@XmlAttribute
	private int maxGreen = 30;
	@XmlAttribute
	private int maxBlue = 30;
}
