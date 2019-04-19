package evolutionary.rgb.binarization.visualization;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import evolutionary.rgb.binarization.evolution.FilterSeries;
import evolutionary.rgb.binarization.helper.ImageHelper;

public class Monitor extends Frame
{	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	
	public Monitor(int x, int y, BufferedImage image)
	{
		this.setBounds(x, y, 1800, 1000);
		this.image = ImageHelper.copyImage(image);
		
		this.addWindowListener
		(
			new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent event)
				{
					System.exit(0);
				}
				
				@Override
				public void windowActivated(WindowEvent event)
				{
					repaint();
				}
			}
		);
		
		this.setVisible(true);
	}
	
	public void paint()
	{
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
	}
	
	public void paint(FilterSeries filterSeries, int n)
	{
		filterSeries.applyFirstNFilters(this.image, n);
		this.repaint();
	}
	
	public void repaint()
	{
		this.getGraphics().drawRect(24, 49, this.image.getWidth() + 1, this.image.getHeight() + 1);
		this.getGraphics().drawImage(this.image, 25, 50, this);
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
}
