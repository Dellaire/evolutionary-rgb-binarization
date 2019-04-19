package evolutionary.rgb.binarization.tests.commonTests;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class JAXBTest
{	
	public static void main(String[] args) throws Exception
	{
		XMLFilterSeries filterSeries = new XMLFilterSeries();
		JAXBContext context = JAXBContext.newInstance(XMLFilterSeries.class, XMLAddRGB.class, XMLThreshold.class);
		
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    m.marshal(filterSeries, new File("FilterSeries.xml"));
	    
//	    Unmarshaller um = context.createUnmarshaller();
//	    filterSeries = (XMLFilterSeries) um.unmarshal(new FileReader("FilterSeries.xml"));
	}
}
