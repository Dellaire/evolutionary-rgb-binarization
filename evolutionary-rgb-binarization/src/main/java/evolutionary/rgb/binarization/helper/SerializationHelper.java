package evolutionary.rgb.binarization.helper;

import java.io.File;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.filter.AddRGB;
import evolutionary.rgb.binarization.filter.Binarization;
import evolutionary.rgb.binarization.region.Global;
import evolutionary.rgb.binarization.region.Spot;

public class SerializationHelper
{	
	public static void JAXBSerialize(FilterSeries filterSeries)
	{
		try
		{
			JAXBFilterSeries jaxbFilterSeries = new JAXBFilterSeries(filterSeries);
			
			JAXBContext context = JAXBContext.newInstance(JAXBFilterSeries.class);
		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    m.marshal(jaxbFilterSeries, new File("serialization/FilterSeries.xml"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static FilterSeries JAXBDeserialize(String path)
	{
		FilterSeries filterSeries = new FilterSeries();
		JAXBFilterSeries jaxbFilterseries;
		AddRGB addRGB;
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(JAXBFilterSeries.class);
			Unmarshaller um = context.createUnmarshaller();
			jaxbFilterseries = (JAXBFilterSeries) um.unmarshal(new FileReader(path));

			for(JAXBAddRGB jaxbAddRGB : jaxbFilterseries.getAddRGBs())
			{
				addRGB = new AddRGB(jaxbAddRGB.getRed(), jaxbAddRGB.getGreen(), jaxbAddRGB.getBlue());
				addRGB.setRegion
				(
					new Spot(jaxbAddRGB.getSpot().getX(), jaxbAddRGB.getSpot().getY(), jaxbAddRGB.getSpot().getR(), jaxbAddRGB.getSpot().getBlur())
				);
				filterSeries.addFilter(addRGB);
			}
			if(jaxbFilterseries.getJAXBBinarizationThreshold() != null)
			{
				filterSeries.addFilter
				(
					new Binarization
					(
						jaxbFilterseries.getJAXBBinarizationThreshold().getRedMin(),
						jaxbFilterseries.getJAXBBinarizationThreshold().getGreenMin(),
						jaxbFilterseries.getJAXBBinarizationThreshold().getBlueMin(),
						jaxbFilterseries.getJAXBBinarizationThreshold().getRedMax(),
						jaxbFilterseries.getJAXBBinarizationThreshold().getGreenMax(),
						jaxbFilterseries.getJAXBBinarizationThreshold().getBlueMax()
					).setRegion(new Global())
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return filterSeries;
	}
}
