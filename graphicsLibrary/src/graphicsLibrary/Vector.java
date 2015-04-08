package graphicsLibrary;

/**
 * A class to represent geometric vectors in 3D space.
 * 
 * Each {@code Vector} has an x-, y-, and z-component, along with a magnitude.
 * The direction of the {@code Vector} can be returned by included methods.
 * 
 * @author caudeljn
 *
 */
public class Vector {
	private double x;
	private double y;
	private double z;
	private double magnitude;

	/**
	 * Constructs a new {@Code Vector} with the given x, y, and z
	 * components.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.magnitude = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Constructs a new {@code Vector} with the given magnitude and direction.
	 * 
	 * @param magnitude
	 *            -double value, Magnitude of vector
	 * @param direction
	 *            -Vector direction of new vector
	 */
	public Vector(double magnitude, Vector direction) {
		this.magnitude = magnitude;
		direction = direction.toUnitVector();
		this.x = magnitude * direction.x;
		this.y = magnitude * direction.y;
		this.z = magnitude * direction.z;

	}

	/**
	 * Constructs a vector from {@link Point} a to {@code Point} b.
	 * 
	 * @param a
	 *            -
	 * @param b
	 */
	public Vector(Point a, Point b) {
		this(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
	}

	/**
	 * Returns the x-value of the {@code Vector}.
	 * 
	 * @return x-value, as a double
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the y-value of the {@code Vector}.
	 * 
	 * @return y-value, as a double
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Returns the z-value of the {@code Vector}.
	 * 
	 * @return z-value, as a double
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * Returns the magnitude of the {@code Vector}.
	 * 
	 * @return magnitude, as a double
	 */
	public double getMagnitude() {
		return this.magnitude;
	}

	/**
	 * Returns a new {@code Vector} in the same direction as this {@code Vector}
	 * , with a magnitude of one.
	 * 
	 * @return new {@code Vector} with magnitude 1.
	 */
	public Vector toUnitVector() {
		return new Vector(x / this.magnitude, y / this.magnitude, z
				/ this.magnitude);
	}

	/**
	 * Returns the {@code Vector}, multiplied by -1;
	 * 
	 * @return negative {@code Vector}
	 */
	public Vector negative() {
		return new Vector(-this.x, -this.y, -this.z);
	}

	/**
	 * Multiplies the {@code Vector} by c.
	 * 
	 * @param c
	 *            -double by which to multiply {@code Vector}
	 * @return new {@code Vector}
	 */
	public void multiplyBy(double c) {
		this.x *= c;
		this.y *= c;
		this.z *= c;
		this.magnitude *= c;
	}
	
	public Vector multiply(double c){
		return new Vector(this.x * c, this.y * c, this.z * c);
	}

	/**
	 * adds the given {@code Vector} to the {@code Vector}.
	 * 
	 * @param v
	 *            -{@code Vector} to add
	 */
	public void add(Vector v) {
		this.x += v.getX();
		this.y += v.getY();
		this.z += v.getZ();
		this.magnitude = this.calculateMagnitude();
	}

	/**
	 * Returns a new {@code Vector} that is the sum of the two given
	 * {@code Vectors}.
	 * 
	 * @param a
	 *            -first {@code Vector} to add
	 * @param b
	 *            -second {@code Vector} to add
	 * @return sum of a and b
	 */
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	/**
	 * Returns the dot product of this {@code Vector} and the given
	 * {@code Vector}.
	 * 
	 * @param v
	 *            -{@code Vector} to dot
	 * @return dot product, as a double
	 */
	public double dot(Vector v) {
		return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ();
	}

	/**
	 * Returns the dot product of the two {@code Vectors}.
	 * 
	 * @param a
	 *            -first {@code Vector}
	 * @param b
	 *            -second {@code Vector}
	 * @return dot product of a and b.
	 */
	public static double dot(Vector a, Vector b) {
		// TODO: Implement this method with Coordinates.
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	/**
	 * Returns the cross product of this {@code Vector} and another
	 * {@code Vector}.
	 * 
	 * @param v
	 *            -{@code Vector} by which to cross this
	 * @return
	 */
	public Vector cross(Vector v) {
		double outX = this.y * v.z - this.z * v.y;
		double outY = this.z * v.x - this.x * v.z;
		double outZ = this.x * v.y - this.y * v.x;
		return new Vector(outX, outY, outZ);
	}

	/**
	 * Returns the cross product of the two {@code Vectors}.
	 * 
	 * @param a
	 *            -first {@code Vector}
	 * @param b
	 *            -second {@code Vector}
	 * @return cross product of a and b.
	 */
	public static Vector cross(Vector a, Vector b) {
		double outX = a.y * b.z - a.z * b.y;
		double outY = a.z * b.x - a.x * b.z;
		double outZ = a.x * b.y - a.y * b.x;
		return new Vector(outX, outY, outZ);
	}

	/**
	 * Returns the angle in radians between this {@code Vector} and the given
	 * {@code Vector}.
	 * 
	 * @param v
	 *            -{@code Vector} to find the angle of
	 * @return angle between 0 and Pi, as a double
	 */
	public double getAngle(Vector v) {
		double dot = this.dot(v);
		double cosTheta = dot / (this.magnitude * v.magnitude);
		double angle = Math.acos(cosTheta);
		return angle;
	}

	/**
	 * Returns the angle in radians of the two {@code Vectors}.
	 * 
	 * @param a
	 *            -first {@code Vector}
	 * @param b
	 *            -second {@code Vector}
	 * @return angle between 0 and Pi
	 */
	public static double getAngle(Vector a, Vector b) {
		double dot = Vector.dot(a, b);
		double cosTheta = dot / (a.magnitude * b.magnitude);
		double angle = Math.acos(cosTheta);
		return angle;
	}

	/**
	 * Returns whether or not the two {@code Vectors} are equal. {@code Vectors}
	 * are equal if they have the same x-, y-, and z-coordinates.
	 * 
	 * @param v
	 *            -{@code Vector} to check
	 * @return true if {@code Vectors} are equal.
	 */
	public boolean equals(Vector v) {
		return this.x == v.getX() && this.y == v.getY() && this.z == v.getZ();
	}

	/**
	 * Projects this {@code Vector} onto the given {@code Vector}.
	 * 
	 * @param v
	 *            -{@code Vector} on which to project
	 * @return new {@code Vector}
	 */
	public Vector project(Vector v) {
		double magnitude = this.dot(v);
		Vector direction = v.toUnitVector();
		return new Vector(magnitude, direction);
	}

	/**
	 * Projects {@code Vector} a ONTO {@code Vector} b
	 * 
	 * @param a
	 *            -{@code Vector} to project
	 * @param b
	 *            -{@code Vector} on which to project
	 * @return new {@code Vector}
	 */
	public static Vector project(Vector a, Vector b) {
		double magnitude = Vector.dot(a, b.toUnitVector());
		Vector direction = b.toUnitVector();
		return new Vector(magnitude, direction);
	}
	
	/**
	 * Returns a new {@link Point} with the same arguments as this {@code Vector}
	 * @return new {@code Point}
	 */
	public Point toPoint(){
		return new Point(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		String out = "<" + Double.toString(this.x) + Double.toString(this.y)
				+ Double.toString(this.z) + ">";
		return out;

	}

	@Override
	public Vector clone() {
		return new Vector(this.x, this.y, this.z);
	}

	/**
	 * Class method to calculate the magnitude of the {@code Vector}.
	 * @return magnitude, as a double
	 */
	private double calculateMagnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
}
