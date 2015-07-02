package graphicsLibrary;

import java.awt.Color;

public class ColorUtil {
	private static final int RGB = 0;
	private static final int HSV = 1;
	// TODO: Maybe fill these in. Probably just delete.
	// public static final Color RED = new Color(1, 0, 0, Color.RGB);
	
	public static final int FULL_SHADE = 11;
	public static final int HALF_SHADE = 22;
	public static final int COLOR_SHIFT = 33;
	public static final int SHADOW_MODE = 44;
	public static final int FULL = 55;

	public static Color clone(Color c) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static Color inverse(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	public static Color shade(Color c, double frac, int mode) {
		double red = c.getRed()/255.0;
		double green = c.getGreen()/255.0;
		double blue = c.getBlue()/255.0;
		
		Color out;
		
		switch (mode) {
		case ColorUtil.FULL_SHADE:
			if (frac > 0 && frac < 1) {
				red *= frac;
				green *= frac;
				blue *= frac;
			}
			break;
		case ColorUtil.HALF_SHADE:
			if (frac > 0 && frac < 1) {
				red *= red * frac / 2.0 + 0.5;
				green *= green * frac / 2.0 + 0.5;
				blue *= blue * frac / 2.0 + 0.5;
			}
			break;
		case ColorUtil.COLOR_SHIFT:
			if (frac > 0 && frac < 1) {
				red *= -Math.cos(frac * Math.PI) / 2.0 + (1 - frac / 2.0);
				green *= -Math.cos(frac * Math.PI);
				blue *= Math.cos(frac * Math.PI);
			}
			break;
		case ColorUtil.SHADOW_MODE:
			if (frac > 0 && frac < 1) {
				double multiply = frac>=0.5?2*frac-1:0;
				red *= multiply;
				green *=multiply;
				blue *= multiply;
			}
			break;
		case ColorUtil.FULL:
			break;
		default:
			out = shade(c, frac, ColorUtil.FULL_SHADE);
			break;
		}
		
		out = new Color((int) (red*255), (int) (green*255), (int) (blue*255));
		return out;
	}
}
