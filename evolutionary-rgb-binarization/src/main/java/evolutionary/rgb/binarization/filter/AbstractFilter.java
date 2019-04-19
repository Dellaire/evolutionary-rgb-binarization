package evolutionary.rgb.binarization.filter;

import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.region.IRegion;

/**
 * General operations for filter
 * 
 * @author Henry Borasch
 */
public abstract class AbstractFilter
{
	protected boolean modifiable = true;
	protected IRegion region;
	
	/**
	 * Applies the entire filter on a BufferedImage
	 * 
	 * @param image BufferedImage on which the filter should be applied
	 */
	public abstract void apply(BufferedImage image);
	
	/**
	 * Applies the filter on a specified Pixel of a BufferedImage
	 * 
	 * @param image BufferedImage on which the filter should be applied
	 * @param x X-coordinate of the pixel
	 * @param y Y-coordinate of the pixel
	 */
	public abstract void apply(BufferedImage image, int x, int y);
	
	/**
	 * Returns an ID that describes the kind of filter
	 * 
	 * @return ID of the filter
	 */
	public abstract int getID();
	
	/**
	 * Returns the region of the filter
	 * 
	 * @return Region of the filter
	 */
	public IRegion getRegion()
	{
		return this.region;
	}
	
	/**
	 * Writes all information, that is needed to define the filter in a xml-compliant string
	 * 
	 * @return Filter information within a xml-compliant string
	 */
	public abstract String getSerializationString();
	
	/**
	 * Creates a String that contains all information about the filter
	 * 
	 * @return Information string
	 */
	public abstract String getString();
	
	/**
	 * Mutates the filter by using the previous set mode
	 * 
	 * @param valueRegion Value that determines the mutation of the region values
	 * @param valueFilter Value that determines the mutation of the filter values
	 */
	public abstract AbstractFilter mutate();
	
	/**
	 * Draws the region of the filter on an image and adds some information in the center
	 * 
	 * @param image Image to be drawn
	 */
	public void drawTrace(BufferedImage image)
	{
		this.region.draw(image);
	}
	
	public boolean isModifiable()
	{
		return this.modifiable;
	}
	
	/**
	 * Recombines the filter with another
	 * 
	 * @param filter Foreign filter to recombine with
	 * @return New filter
	 */
	public abstract AbstractFilter recombine(AbstractFilter filter);
	
	public void setModifiable(boolean modifiable)
	{
		this.modifiable = modifiable;
	}
	
	/**
	 * Sets the region on which the filter will have effect in {@link #apply(BufferedImage)} and {@link #apply(BufferedImage, int, int)}
	 * 
	 * @param region Influence region
	 */
	public AbstractFilter setRegion(IRegion region)
	{
		this.region = region;
		
		return this;
	}
}
