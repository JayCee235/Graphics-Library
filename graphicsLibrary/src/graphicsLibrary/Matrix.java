package graphicsLibrary;

/**
 * A class to handle Matrices. 
 * @author caudeljn
 *
 */
public class Matrix {
	private int width;
	private int height;
	private double[][] entries;

	public Matrix(double[][] in) {
		this.height = in.length;
		this.width = in[0].length;
		entries = in.clone();
	}

	public double getEntry(int i, int j) {
		return this.entries[i][j];
	}

	/**
	 * Returns the product of two matrices.
	 * 
	 * @param a
	 *            -first matrix to multiply
	 * @param b
	 *            -second matrix to multiply
	 * @return product ab
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the width of a does not equal the height of b
	 */
	public static Matrix multiply(Matrix a, Matrix b)
			throws ArrayIndexOutOfBoundsException {
		if (a.width != b.height) {
			throw new ArrayIndexOutOfBoundsException();
		}
		double[][] out = new double[b.width][a.height];
		for (int i = 0; i < b.width; i++) {
			for (int j = 0; j < a.height; j++) {
				double set = 0;
				for (int ii = 0; ii < b.height; ii++) {
					set += a.getEntry(j, ii) * b.getEntry(ii, i);
				}
				out[i][j] = set;
			}
		}
		return new Matrix(out);
	}

	public String toString() {
		String out = "[";
		for (int i = 0; i < this.height; i++) {
			out += "[";
			for (int j = 0; j < this.width; j++) {
				out += String.format(j != this.width - 1 ? "%.1f, " : "%.1f",
						this.getEntry(i, j));
			}
			out += i != this.height - 1 ? "]\n " : "]";
		}
		out += "]";
		return out;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	/**
	 * Turns a given point array of length n into a n*3 matrix
	 * 
	 * @param p
	 *            -point array to change
	 * @return n*3 matrix of points
	 */
	public static Matrix pointArrayToMatrix(Point[] p) {
		double[][] out = new double[p.length][3];
		for (int i = 0; i < out.length; i++) {
			out[i][0] = p[i].getX();
			out[i][1] = p[i].getY();
			out[i][2] = p[i].getZ();
		}
		return new Matrix(out);
	}

	/**
	 * Turns the given matrix into an array of points. Assumes the matrix has a
	 * width of 3.
	 * 
	 * @param m
	 *            -matrix to turn into points
	 * @return point array from matrix
	 */
	public static Point[] matrixToPointArray(Matrix m) throws Exception {
		if (m.getWidth() != 3) {
			throw new Exception();
		}
		Point[] out = new Point[m.getHeight()];
		for (int i = 0; i < out.length; i++) {
			out[i] = new Point(m.getEntry(i, 0), m.getEntry(i, 1), m.getEntry(
					i, 2));
		}
		return out;
	}

	/**
	 * Returns the determinant of the matrix.
	 * 
	 * @return determinant
	 * @throws Exception
	 *             if the matrix is not square.
	 */
	public double determinant() throws Exception {
		if (this.width != this.height) {
			throw new Exception();
		}
		if (this.width == 1) {
			return this.getEntry(0, 0);
		} else {
			// TODO: implement.
			double total = 0;
			for (int i = 0; i < this.width; i++) {
				Matrix cofactor = this.removeColumn(i).removeRow(0);
				total += cofactor.determinant() * this.getEntry(0, i)
						* (i % 2 == 0 ? 1 : -1);
			}
			return total;
		}
	}

	/**
	 * Return a submatrix of the matrix, starting at the given index and with
	 * the given height and width
	 * 
	 * @param startHeight
	 *            -i index to start
	 * @param startWidth
	 *            -j index to start
	 * @param height
	 *            -height of matrix to return
	 * @param width
	 *            -width of array to return
	 * @return submatrix of matrix
	 */
	public Matrix submatrix(int startHeight, int startWidth, int height,
			int width) {
		double[][] out = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				out[i][j] = this.getEntry(startHeight + i, startWidth + j);
			}
		}
		return new Matrix(out);
	}

	/**
	 * Returns the matrix with the given row removed.
	 * 
	 * @param index
	 *            -index of row to remove
	 * @return new Matrix
	 */
	public Matrix removeRow(int index) {
		double[][] out = new double[this.getHeight() - 1][this.getWidth()];
		int fix = 0;
		for (int i = 0; i < this.getHeight(); i++) {
			if (i == index) {
				fix++;
			} else {
				for (int j = 0; j < this.getWidth(); j++) {
					out[i - fix][j] = this.getEntry(i, j);
				}
			}
		}
		return new Matrix(out);
	}

	/**
	 * Returns the matrix with the given column removed.
	 * 
	 * @param index
	 *            -index of column to remove
	 * @return new Matrix
	 */
	public Matrix removeColumn(int index) {
		double[][] out = new double[this.getHeight()][this.getWidth() - 1];
		int fix = 0;
		for (int j = 0; j < this.getWidth(); j++) {
			if (j == index) {
				fix++;
			} else {
				for (int i = 0; i < this.getHeight(); i++) {
					out[i][j - fix] = this.getEntry(i, j);
				}
			}
		}
		return new Matrix(out);
	}

	/**
	 * Returns an n*n identity matrix.
	 * 
	 * @param n
	 *            -size of matrix
	 * @return
	 */
	public static Matrix identity(int n) {
		double[][] out = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					out[i][j] = 1;
				}
			}
		}
		return new Matrix(out);
	}
	
	/**
	 * Returns a 2D rotation matrix of the given angle.
	 * @param angle
	 * 				-angle by which to rotate
	 * @return
	 */
	public static Matrix rotationMatrix2D(double angle) {
		double[][] in = new double[][]{{Math.cos(angle),-Math.sin(angle)},{Math.sin(angle),Math.cos(angle)}};
		Matrix out = new Matrix(in);
		return out;
	}
	
	/**
	 * Returns a 3D rotation matrix of the given angle, around the x-axis.
	 * @param angle
	 * 				-angle by which to rotate
	 * @return
	 */
	public static Matrix rotationMatrix3DX(double angle) {
		double[][] in = new double[][]{{1, 0, 			   0},
									   {0, Math.cos(angle),-Math.sin(angle)},
									   {0, Math.sin(angle),Math.cos(angle)}};
		Matrix out = new Matrix(in);
		return out;
	}
	
	/**
	 * Returns a 3D rotation matrix of the given angle, around the y-axis.
	 * @param angle
	 * 				-angle by which to rotate
	 * @return
	 */
	public static Matrix rotationMatrix3DY(double angle) {
		double[][] in = new double[][]{{Math.cos(angle), 0, -Math.sin(angle)},
									   {0,				 1, 0},
									   {Math.sin(angle), 0, Math.cos(angle)}};
		Matrix out = new Matrix(in);
		return out;
	}
	
	/**
	 * Returns a 3D rotation matrix of the given angle, around the z-axis.
	 * @param angle
	 * 				-angle by which to rotate
	 * @return
	 */
	public static Matrix rotationMatrix3DZ(double angle) {
		double[][] in = new double[][]{{Math.cos(angle),-Math.sin(angle), 0},
									   {Math.sin(angle),Math.cos(angle),  0},
									   {0,				0,   			  1}};
		Matrix out = new Matrix(in);
		return out;
	}
}
