package evolutionary.rgb.binarization.helper;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageHelper
{
	public static BufferedImage copyImage(BufferedImage image)
	{
		ColorModel cm = image.getColorModel();
		WritableRaster raster = image.copyData(null);
		
		return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
	}
}
