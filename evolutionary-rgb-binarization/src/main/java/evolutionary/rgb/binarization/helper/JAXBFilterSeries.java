package evolutionary.rgb.binarization.helper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.filter.AbstractFilter;
import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.filter.Threshold;

@XmlRootElement(namespace = "evo.bin.rgb")
public class JAXBFilterSeries
{
//	@XmlElementWrapper(name = "FilterSeries")
	@XmlElement(name = "AddRGB")
	private List<JAXBAddRGB> addRGBs;
	@XmlElement(name = "BinarizationThreshold")
	private JAXBBinarizationThreshold binarizationThreshold;
	
	public JAXBFilterSeries()
	{
		
	}
	
	public JAXBFilterSeries(FilterSeries filterSeries)
	{
		this.addRGBs = new ArrayList<JAXBAddRGB>();
		for(AbstractFilter filter : filterSeries.getFilters())
		{
			if(filter.getClass().equals(AddRGB.class))
				this.addRGBs.add(new JAXBAddRGB((AddRGB)filter));
			else
				this.binarizationThreshold = new JAXBBinarizationThreshold((Threshold)filter);
		}
	}
	
	public List<JAXBAddRGB> getAddRGBs()
	{
		return this.addRGBs;
	}
	
	public JAXBBinarizationThreshold getJAXBBinarizationThreshold()
	{
		return this.binarizationThreshold;
	}
}