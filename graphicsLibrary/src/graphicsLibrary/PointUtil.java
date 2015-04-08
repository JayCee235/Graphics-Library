package graphicsLibrary;

/**
 * Utility package for the Point class, along with generic functions for randomly generated height maps.
 * 
 * @author Jeremiah Caudell
 *
 */
public class PointUtil {
	public static Point[][] seeded(int size, double a, double b, double c,
			double d, int startX, int endX, int startZ, int endZ)
			throws Exception {
		Point[][] p = new Point[size][size];
		int max = size - 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				p[i][j] = new Point(startX + endX
						* ((double) i / ((double) max)), 0, startZ
						+ endZ * ((double) i / ((double) max)));
			}
		}
		p[0][0].setY(a);
		p[0][max].setY(b);
		p[max][0].setY(c);
		p[max][max].setY(d);
		return p;
	}

	/**
	 * Private function for perlinNoise. The public function calls this.
	 * 
	 * @param p
	 * @param period
	 * @param amplitude
	 * @return
	 */
	private static Point[][] perlinNoise(Point[][] p, int period,
			double amplitude) {
		double r = 2 * Math.random() - 1;
		double[][] addThis = new double[p.length][p[0].length];
		// Add values.
		for (int i = 0; i < p.length; i += period) {
			for (int j = 0; j < p[i].length; j += period) {
				addThis[i][j] = amplitude * r;
			}
		}
		// Smooth.
		for (int i = 0; i < p.length - period; i += period) {
			for (int j = 0; j < p[i].length - period; j += period) {
				for (int ii = i; i < i + period; ii++) {
					for (int jj = j; j < j + period; jj++) {
						double di = (double) (ii - i) / (period);
						double dj = (double) (jj - j) / (period);
						double x = Math.cos(Math.PI * di);
						double y = Math.cos(Math.PI * dj);
						double xx = 1 - x;
						double yy = 1 - y;

						addThis[ii][jj] = addThis[i][j] * x * y
								+ addThis[i][j + period] * x * yy
								+ addThis[i + period][j] * xx * y
								+ addThis[i + period][j + period] * xx * yy;
					}
				}
			}
		}
		// Add addThis to points
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[i].length; j++) {
				p[i][j].setY(p[i][j].getY()+addThis[i][j]);
			}
		}
		// Repeat.
		if (period > 1) {
			p = perlinNoise(p, period / 2, amplitude / 2);
		}
		// Return.
		return p;
	}

	/**
	 * Not fully implimented. No guarentees that it works.
	 * @param p
	 * @param amplitude
	 * @return
	 */
	public static Point[][] perlinNoise(Point[][] p, double amplitude) {
		return perlinNoise(p, p.length - 1, amplitude);
	}

	/**
	 * Fills a Point array with the specified height.
	 * 
	 * @param p
	 *            -point array to fill
	 * @param fill
	 *            -value to fill
	 * @return -filled point array
	 */
	public static Point[][] fill(Point[][] p, double fill) {
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[i].length; j++) {
				p[i][j].setY(fill);
			}
		}
		return p;
	}
	
	public static double[][] generate(int width, int height, double start,
			double rand, double hardness){
		double[][] out = new double[width+1][height+1];
		out[0][0] = start;
		out[0][height] = start;
		out[width][0] = start;
		out[width][height] = start;
		
		boolean[][] set = new boolean[width+1][height+1];
		set[0][0] = true;
		set[0][height] = true;
		set[width][0] = true;
		set[width][height] = true;
		
		generate(out, 0, 0, width, rand, hardness, set);
		return out;
	}
	
	public static double[][] generateIsland(int width, int height, double low, double high, 
			double rand, double hardness) {
		double[][] out = new double[width+1][height+1];
		out[0][0] = low;
		out[0][height] = low;
		out[width][0] = low;
		out[width][height] = low;
		out[width/2][height/2] = high;
		
		boolean[][] set = new boolean[width+1][height+1];
		set[0][0] = true;
		set[0][height] = true;
		set[width][0] = true;
		set[width][height] = true;
		set[width/2][height/2] = true;
		
		return generate(out, 0, 0, width, rand, hardness, set);
	}
	
	private static double[][] generate(double[][] out, int sx, int sy, int dd,
			double r, double h, boolean[][] set){
		boolean go = false;
		int ch = dd/2;
		double a = out[sx][sy];
		double b = out[sx][sy+dd];
		double c = out[sx+dd][sy];
		double d = out[sx+dd][sy+dd];
		
		if (!set[sx + ch][sy + ch]) {
			out[sx + ch][sy + ch] = ((a + b + c + d) / 4.0)
					+ (Math.random() * 2 - 1) * r;
			set[sx + ch][sy + ch] = true;
			go = true;
		}
		double e = out[sx+ch][sy+ch];
		
		if (!set[sx][sy+ch]) {
			out[sx][sy + ch] = ((a + b + e) / 3.0) + (Math.random() * 2 - 1) * r;
			set[sx][sy+ch] = true;
			go = true;
		}
		if (!set[sx + ch][sy]) {
			out[sx + ch][sy] = ((a + c + e) / 3.0) + (Math.random() * 2 - 1) * r;
			set[sx + ch][sy] = true;
			go = true;
		}
		if (!set[sx+ch][sy+dd]) {
			out[sx + ch][sy + dd] = ((b + d + e) / 3.0)
					+ (Math.random() * 2 - 1) * r;
			set[sx+ch][sy+dd] = true;
			go = true;
		}
		if (!set[sx+dd][sy+ch]) {
			out[sx + dd][sy + ch] = ((c + d + e) / 3.0)
					+ (Math.random() * 2 - 1) * r;
			set[sx+dd][sy+ch] = true;
			go = true;
		}
		if (go) {
			out = generate(out, sx, sy, ch, r / h, h, set);
			out = generate(out, sx, sy + ch, ch, r / h, h, set);
			out = generate(out, sx + ch, sy, ch, r / h, h, set);
			out = generate(out, sx + ch, sy + ch, ch, r / h, h, set);
		}
		return out;
	}
	
	public static double[][] scale(double[][] in) {
		double min = in[0][0];
		double max = in[0][0];
		for(int i = 0; i < in.length; i++) {
			for(int j = 0; j < in[i].length; j++) {
				min = in[i][j]<min?in[i][j]:min;
				max = in[i][j]>max?in[i][j]:max;
			}
		}
		double[][] out = new double[in.length][in[0].length];
		for(int i = 0; i < in.length; i++) {
			for(int j = 0; j < in[i].length; j++) {
				out[i][j] = (in[i][j] - min) / (max - min);
			}
		}
		return out;
	}
	
	public static int[] findMax(double[][] in) {
		int[] out = new int[]{0, 0};
		double max = in[0][0];
		for(int i = 0; i < in.length; i++) {
			for(int j = 0; j < in[i].length; j++) {
				if(in[i][j] > max)
					out[0] = i;
				out[1] = j;
			}
		}
		return out;
	}
}
