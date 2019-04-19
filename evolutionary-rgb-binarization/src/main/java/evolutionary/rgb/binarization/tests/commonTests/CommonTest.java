package evolutionary.rgb.binarization.tests.commonTests;

import java.awt.Color;

import evolutionary.rgb.binarization.helper.Support;

public class CommonTest
{
	public static void main(String[] args)
	{
		System.out.println(Support.RGBToInt(0, 0, 0));
		System.out.println(Color.BLACK.getRGB());
	}
}
