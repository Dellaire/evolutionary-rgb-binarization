package evolutionary.rgb.binarization.tests.commonTests;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "evo.bin.rgb")
public class XMLFilterSeries
{
	@XmlElementWrapper(name = "FilterSeries")
	@XmlElement(name = "Filter")
	private List filter;
	
	public XMLFilterSeries()
	{
		this.filter = new ArrayList<XMLFilter>();
		this.filter.add(new XMLAddRGB());
		this.filter.add(new XMLThreshold());
		this.filter.add(new XMLAddRGB());
	}
	
//	public void print()
//	{
//		for(XMLAddRGB addRGB : filter)
//			addRGB.print();
//	}
}
