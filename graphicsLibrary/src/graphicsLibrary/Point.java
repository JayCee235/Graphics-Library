package graphicsLibrary;

import org.lwjgl.opengl.GL11;

/**
 * A class to represent geometric points in 3D space.
 * 
 * Each {@code Point} has an x-, y-, and z- coordinate, stored as a double.
 * 
 * @author caudeljn
 *
 */
public class Point {
	private double x;
	private double y;
	private double z;

	/**
	 * Creates a new {@code Point} with the given parameters.
	 * 
	 * @param x
	 *            -x-coordinate of point.
	 * @param y
	 *            -y-coordinate of point.
	 * @param z
	 *            -z-coordinate of point.
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a copy of the {@code Point} given.
	 * 
	 * @param p
	 *            -point to copy.
	 */
	public Point(Point p) {
		this(p.x, p.y, p.z);
	}

	/**
	 * Returns the x-value of the {@code Point}.
	 * 
	 * @return x-value, as a double
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the y-value of the {@code Point}.
	 * 
	 * @return y-value, as a double
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Returns the z-value of the {@code Point}.
	 * 
	 * @return z-value, as a double
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the x-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param xr
	 *            -x-coordinate of center of rotation.
	 * @param yr
	 *            -y-coordinate of center of rotation.
	 * @param zr
	 *            -z-coordinate of center of rotation.
	 */
	public void rotateX(double angle, double xr, double yr, double zr) {
		double x1 = this.x;
		double y1 = this.y;
		double z1 = this.z;
		double z2 = (z1 - zr) * Math.cos(angle) - (y1 - yr) * Math.sin(angle)
				+ zr;
		double y2 = (z1 - zr) * Math.sin(angle) + (y1 - yr) * Math.cos(angle)
				+ yr;
		double x2 = x1;
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the x-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param p
	 *            -Center of rotation.
	 */
	public void rotateX(double angle, Point p) {
		this.rotateX(angle, p.getX(), p.getY(), p.getZ());
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the y-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param xr
	 *            -x-coordinate of center of rotation.
	 * @param yr
	 *            -y-coordinate of center of rotation.
	 * @param zr
	 *            -z-coordinate of center of rotation.
	 */
	public void rotateY(double angle, double xr, double yr, double zr) {
		double x1 = this.x;
		double y1 = this.y;
		double z1 = this.z;
		double z2 = (x1 - xr) * Math.sin(angle) + (z1 - zr) * Math.cos(angle)
				+ zr;
		double y2 = y1;
		double x2 = (x1 - xr) * Math.cos(angle) - (z1 - zr) * Math.sin(angle)
				+ xr;
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the y-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param p
	 *            -Center of rotation.
	 */
	public void rotateY(double angle, Point p) {
		this.rotateY(angle, p.getX(), p.getY(), p.getZ());
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the z-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param xr
	 *            -x-coordinate of center of rotation.
	 * @param yr
	 *            -y-coordinate of center of rotation.
	 * @param zr
	 *            -z-coordinate of center of rotation.
	 */
	public void rotateZ(double angle, double xr, double yr, double zr) {
		double x1 = this.x;
		double y1 = this.y;
		double z1 = this.z;
		double z2 = z1;
		double y2 = (x1 - xr) * Math.sin(angle) + (y1 - yr) * Math.cos(angle)
				+ yr;
		double x2 = (x1 - xr) * Math.cos(angle) - (y1 - yr) * Math.sin(angle)
				+ xr;
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}

	/**
	 * Rotates the {@code Point} around the given center by the given angle,
	 * around the z-axis.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians.
	 * @param p
	 *            -Center of rotation.
	 */
	public void rotateZ(double angle, Point p) {
		this.rotateZ(angle, p.getX(), p.getY(), p.getZ());
	}

	/**
	 * Translates the {@code Point} by the given amount.
	 * 
	 * @param dx
	 *            -Change in x.
	 * @param dy
	 *            -Change in y.
	 * @param dz
	 *            -Change in z.
	 */
	public void translate(double dx, double dy, double dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
	}

	/**
	 * Translates the {@code Point} by the given {@link Vector}.
	 * 
	 * @param v
	 *            -Vector by which to translate.
	 */
	public void translate(Vector v) {
		this.translate(v.getX(), v.getY(), v.getZ());
	}

	/**
	 * Returns true if the given {@code Point} is a copy of the {@code Point}
	 * that called the function
	 * 
	 * @param p
	 *            -{@code Point} to check
	 * 
	 * @return true if {@code Point} objects are equivalent (have the same x, y,
	 *         and z values).
	 */
	public boolean equals(Point p) {
		return this.x == p.x && this.y == p.y && this.z == p.z;
	}

	/**
	 * Sets the x-value of a {@code Point}
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Sets the y-value of a {@code Point}
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Sets the z-value of a {@code Point}
	 * 
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Draws the {@code Point} using LWJGL. Do not call outside of a
	 * {@link GL11#glBegin(int)} block.
	 */
	public void drawPointGL() {
		GL11.glVertex3d(this.x, this.y, this.z);
	}

	/**
	 * Rotates the {@code Point} by the given angle around the given
	 * {@link Vector}.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians
	 * @param center
	 *            -center about which to rotate
	 * @param v
	 *            -{@link Vector} around which to rotate
	 */
	public void rotateAroundVector(double angle, Point center, Vector v) {
		Vector pointToPoint = new Vector(center, this);

		if (!this.equals(center)) {
			double m = Vector.dot(v, pointToPoint) / Vector.dot(v, v);
			double ix = pointToPoint.getX() - m * v.getX();
			double iy = pointToPoint.getY() - m * v.getY();
			double iz = pointToPoint.getZ() - m * v.getZ();

			Vector i = (new Vector(ix, iy, iz));
			double radius = i.getMagnitude();
			if (i.getMagnitude() > 0.00001) {
				i = i.toUnitVector();
				Vector j = Vector.cross(i, v).toUnitVector();
				// double Cx = center.getX();
				// double Cy = center.getY();
				// double Cz = center.getZ();
				Vector halffin = Vector.add(
						i.multiply(radius * Math.cos(angle)),
						j.multiply(radius * Math.sin(angle)));
				Vector fin = Vector.add(halffin, v.multiply(m));
				this.x = center.getX() + fin.getX();
				this.y = center.getY() + fin.getY();
				this.z = center.getZ() + fin.getZ();
			}
		}
	}

	/**
	 * Rotates the {@code Point} by the given angle around the given
	 * {@link Vector}.
	 * 
	 * @param angle
	 *            -Angle to rotate, in radians
	 * @param Cx
	 *            -x-coordinate of center of rotation.
	 * @param Cy
	 *            -y-coordinate of center of rotation.
	 * @param Cz
	 *            -z-coordinate of center of rotation.
	 * @param v
	 *            -{@link Vector} around which to rotate
	 */
	public void rotateAroundVector(double angle, double Cx, double Cy,
			double Cz, Vector v) {
		Point p = new Point(Cx, Cy, Cz);
		this.rotateAroundVector(angle, p, v);
	}

	/**
	 * Returns a {@link Vector} pointing from the origin (0,0,0) to this
	 * {@code Point}.
	 * 
	 * @return -A new {@code Vector} with the same x-, y-, and z-coordinates.
	 */
	public Vector toVector() {
		return new Vector(this.x, this.y, this.z);
	}

	public String toString() {
		String out = "(" + Double.toString(this.x) + ", "
				+ Double.toString(this.y) + ", " + Double.toString(this.z)
				+ ")";
		return out;
	}

	@Override
	public Point clone() {
		return new Point(this);
	}
}
