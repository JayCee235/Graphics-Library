package graphicsLibrary;

public class Color {
	private static final int RGB = 0;
	private static final int HSV = 1;
	// TODO: Maybe fill these in. Probably just delete.
	// public static final Color RED = new Color(1, 0, 0, Color.RGB);

	private double red;
	private double green;
	private double blue;
	
	public static final int FULL_SHADE = 11;
	public static final int HALF_SHADE = 22;
	public static final int COLOR_SHIFT = 33;
	public static final int SHADOW_MODE = 44;
	public static final int FULL = 55;

	private Color(double arg1, double arg2, double arg3, int type) {
		switch (type) {
		case Color.RGB:
			this.red = arg1;
			this.green = arg2;
			this.blue = arg3;
			break;
		case Color.HSV:
			while (arg1 < 0) {
				arg1 += 360;
			}
			arg1 = arg1 % 360;
			if (arg1 >= 0 && arg1 <= 60) {
				double frac = arg1 / 60.0;
				this.red = 1.0;
				this.green = frac;
				this.blue = 0;
			} else if (arg1 >= 60 && arg1 <= 120) {
				double frac = (arg1 - 60) / 60.0;
				this.red = 1.0 - frac;
				this.green = 1.0;
				this.blue = 0;
			} else if (arg1 >= 120 && arg1 <= 180) {
				double frac = (arg1 - 120) / 60.0;
				this.red = 0;
				this.green = 1.0;
				this.blue = frac;
			} else if (arg1 >= 180 && arg1 <= 240) {
				double frac = (arg1 - 180) / 60.0;
				this.red = 0;
				this.green = 1.0 - frac;
				this.blue = 1.0;
			} else if (arg1 >= 240 && arg1 <= 300) {
				double frac = (arg1 - 240) / 60.0;
				this.red = frac;
				this.green = 0;
				this.blue = 1.0;
			} else if (arg1 >= 300 && arg1 < 360) {
				double frac = (arg1 - 300) / 60.0;
				this.red = 1.0;
				this.green = 0;
				this.blue = 1 - frac;
			}

			this.red += (1 - this.red) * (1 - arg2);
			this.green += (1 - this.green) * (1 - arg2);
			this.blue += (1 - this.blue) * (1 - arg2);

			this.red *= arg3;
			this.green *= arg3;
			this.blue *= arg3;
			break;
		default:
			this.red = arg1;
			this.green = arg2;
			this.blue = arg3;
			break;
		}
	}

	public Color clone() {
		return new Color(this.red, this.green, this.blue, Color.RGB);
	}

	public double getRed() {
		return this.red;
	}

	public double getGreen() {
		return this.green;
	}

	public double getBlue() {
		return this.blue;
	}

	public Color inverse() {
		return new Color(1 - this.red, 1 - this.green, 1 - this.blue, Color.RGB);
	}

	public static Color newColorRGB(double red, double green, double blue) {
		return new Color(red, green, blue, Color.RGB);
	}

	public static Color newColorHSV(double hue, double saturation, double value) {
		return new Color(hue, saturation, value, Color.HSV);
	}

	public void shade(double frac, int mode) {
		switch (mode) {
		case Color.FULL_SHADE:
			if (frac > 0 && frac < 1) {
				this.red *= frac;
				this.green *= frac;
				this.blue *= frac;
			}
			break;
		case Color.HALF_SHADE:
			if (frac > 0 && frac < 1) {
				this.red *= this.red * frac / 2.0 + 0.5;
				this.green *= this.green * frac / 2.0 + 0.5;
				this.blue *= this.blue * frac / 2.0 + 0.5;
			}
			break;
		case Color.COLOR_SHIFT:
			if (frac > 0 && frac < 1) {
				this.red *= -Math.cos(frac * Math.PI) / 2.0 + (1 - frac / 2.0);
				this.green *= -Math.cos(frac * Math.PI);
				this.blue *= Math.cos(frac * Math.PI);
			}
			break;
		case Color.SHADOW_MODE:
			if (frac > 0 && frac < 1) {
				double multiply = frac>=0.5?2*frac-1:0;
				this.red *= multiply;
				this.green *=multiply;
				this.blue *= multiply;
			}
			break;
		case Color.FULL:
			break;
		default:
			this.shade(frac, Color.FULL_SHADE);
			break;
		}
	}

	public void setColor(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}
