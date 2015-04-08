package graphicsLibrary;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

public class Shape {
	private ArrayList<Triangle> triangles;
	private ArrayList<Point> points;
	private Point center;
	private Color color;

	private Shape() {
		triangles = new ArrayList<Triangle>();
		points = new ArrayList<Point>();
		this.center = new Point(0,0,0);
	}
	
	public Color getColor(){
		return this.color.clone();
	}
	
	public void setColor(Color c){
		this.color = c.clone();
		for(Triangle t : this.triangles){
			t.setColor(c.clone());
		}
	}
	
	public Point getCenter(){
		return this.center;
	}
	
	public double getX() {
		return this.center.getX();
	}

	public void setX(double x) {
		this.center.setX(x);
		for(Point p : this.points){
			p.setX(x);
		}
	}

	public double getY() {
		return this.center.getY();

	}

	public void setY(double y) {
		this.center.setY(y);
		for(Point p : this.points){
			p.setY(y);
		}
	}

	public double getZ() {
		return this.center.getZ();
	}

	public void setZ(double z) {
		this.center.setZ(z);
		for(Point p : this.points){
			p.setZ(z);
		}
	}

	public ArrayList<Triangle> getTriangles(){
		return this.triangles;
	}
	
	public ArrayList<Point> getPoints(){
		return this.points;
	}
	
	public void addPoint(Point p){
		this.points.add(p);
	}
	
	public void addTriangle(Triangle t){
		this.triangles.add(t);
		for(Point c : t.getPoints()){
			this.points.add(c);
		}
	}
	
	public void translate(double dx, double dy, double dz){
		for(Point c : this.points){
			c.translate(dx, dy, dz);
		}
		this.center.translate(dx, dy, dz);
	}
	
	public void translate(Vector v){
		this.translate(v.getX(), v.getY(), v.getZ());
	}
	
	public void rotateX(double angle, double Cx, double Cy, double Cz){
		for(Point c : this.points){
			c.rotateX(angle, Cx, Cy, Cz);
		}
		this.center.rotateX(angle, Cx, Cy, Cz);
		
	}
	
	public void rotateY(double angle, double Cx, double Cy, double Cz){
		for(Point c : this.points){
			c.rotateY(angle, Cx, Cy, Cz);
		}
		this.center.rotateY(angle, Cx, Cy, Cz);
	}
	
	public void rotateZ(double angle, double Cx, double Cy, double Cz){
		for(Point c : this.points){
			c.rotateZ(angle, Cx, Cy, Cz);
		}
		this.center.rotateZ(angle, Cx, Cy, Cz);
	}
	
	public void rotateX(double angle, Point p){
		for(Point c : this.points){
			c.rotateX(angle, p);
		}
		this.center.rotateX(angle, p);
	}
	
	public void rotateY(double angle, Point p){
		for(Point c : this.points){
			c.rotateY(angle, p);
		}
		this.center.rotateY(angle, p);
	}
	
	public void rotateZ(double angle, Point p){
		for(Point c : this.points){
			c.rotateZ(angle, p);
		}
		this.center.rotateZ(angle, p);
	}

	/**
	 * Returns a new Sphere.
	 * 
	 * @param Cx
	 *            -The x-coordinate of the center of the sphere.
	 * @param Cy
	 *            -The y-coordinate of the center of the sphere.
	 * @param Cz
	 *            -The z-coordinate of the center of the sphere.
	 * @param radius
	 *            -The radius of the sphere.
	 * @param detail
	 *            -The number of 'rings' that the sphere uses to generate. One
	 *            ring makes an octahedron. Generally, the higher this number,
	 *            the more detailed the sphere.
	 * @return Shape
	 */
	public static Shape newSphere(double Cx, double Cy, double Cz,
			double radius, int detail) {
		// Adds a sphere of points.
		// A top and bottol half of points, add both then combine them.
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> bottomHalf = new ArrayList<Point>();
		points.add(new Point(Cx, Cy + radius, Cz));
		bottomHalf.add(new Point(Cx, Cy - radius, Cz));
		// Adds the top half of the sphere.
		// Loops through the number of rings minus one, each ring having 4*i
		// points.
		for (int i = 1; i < detail; i++) {
			double riseAngle = ((double) i / (double) detail) * (Math.PI / 2.0);
			int ringSize = 4 * i;
			for (int j = 0; j < ringSize; j++) {
				double angle = ((double) j / (double) ringSize) * Math.PI * 2.0;
				points.add(new Point(radius * Math.cos(angle)
						* Math.sin(riseAngle) + Cx, Cy + radius
						* Math.cos(riseAngle), radius * Math.sin(angle)
						* Math.sin(riseAngle) + Cz));
				bottomHalf.add(new Point(radius * Math.cos(angle)
						* Math.sin(riseAngle) + Cx, Cy - radius
						* Math.cos(riseAngle), radius * Math.sin(angle)
						* Math.sin(riseAngle) + Cz));
			}
		}
		// Adds the center ring.
		double riseAngle = Math.PI / 2.0;
		int ringSize = 4 * detail;
		for (int j = 0; j < ringSize; j++) {
			double angle = ((double) j / (double) ringSize) * Math.PI * 2.0;
			// Note that the same point is being added to both lists.
			// The duplicates are removed later; this is important though.
			Point c = new Point(radius * Math.cos(angle) * Math.sin(riseAngle)
					+ Cx, Cy + radius * Math.cos(riseAngle), radius
					* Math.sin(angle) * Math.sin(riseAngle) + Cz);
			points.add(c);
			bottomHalf.add(c);

		}
		// This is to know where the bottom half starts.
		int firstBottomIndex = points.size();
		// Adds the bottom of the sphere.
		for (Point c : bottomHalf) {
			points.add(c);
		}

		// A map that says what 2 points a point connects to to make a triangle.
		HashMap<Integer, Integer[][]> trianglesDef = new HashMap<Integer, Integer[][]>();
		HashMap<Integer, ArrayList<Integer>> sphere = new HashMap<Integer, ArrayList<Integer>>();
		sphere.put(0, new ArrayList<Integer>());
		// The top connects to the first ring in this way.
		sphere.get(0).add(1);
		sphere.get(0).add(2);
		sphere.get(0).add(3);
		sphere.get(0).add(4);
		sphere.get(0).add(1);
		sphere.put(firstBottomIndex + 0, new ArrayList<Integer>());
		sphere.get(firstBottomIndex + 0).add(firstBottomIndex + 1);
		sphere.get(firstBottomIndex + 0).add(firstBottomIndex + 2);
		sphere.get(firstBottomIndex + 0).add(firstBottomIndex + 3);
		sphere.get(firstBottomIndex + 0).add(firstBottomIndex + 4);
		sphere.get(firstBottomIndex + 0).add(firstBottomIndex + 1);

		// various information about the current point. Makes it easier to
		// generate the sphere.
		int ringNumber = 0;
		int sizeOfRing = 0;
		int sizeOfNextRing = 4;
		int startOfRing = 0;
		int lastOfRing = 0;
		int startOfNextRing = 1;
		int lastOfNextRing = 4;
		int currentIndex = 4;

		// See notes in notebook for this one.
		/*
		 * Basically, make triangles. If the point is the first in the ring, the
		 * "previous" in the ring is the last, and the first outside is the last
		 * of the next ring.
		 * 
		 * Each point should connect to it's previous, then the current index,
		 * and the next index. If the point is a corner, connect to the next as
		 * well.
		 * 
		 * Top and bottom are done at once for convenience.
		 */
		for (int i = 1; i < points.size(); i++) {
			if (i == startOfNextRing) {
				ringNumber++;
				sizeOfRing += 4;
				sizeOfNextRing += 4;
				startOfRing = i;
				lastOfRing = lastOfNextRing;
				startOfNextRing = lastOfRing + 1;
				lastOfNextRing = lastOfRing + sizeOfNextRing;
				if (ringNumber < detail) {
					sphere.put(i, new ArrayList<Integer>());
					sphere.put(firstBottomIndex + i, new ArrayList<Integer>());

					ArrayList<Integer> addToThis = sphere.get(i);
					ArrayList<Integer> addToThis2 = sphere.get(firstBottomIndex
							+ i);

					addToThis.add(lastOfRing);
					addToThis2.add(firstBottomIndex + lastOfRing);

					addToThis.add(lastOfNextRing);
					addToThis2.add(firstBottomIndex + lastOfNextRing);
					currentIndex++;

					addToThis.add(currentIndex);
					addToThis2.add(firstBottomIndex + currentIndex);
					currentIndex++;

					addToThis.add(currentIndex);
					addToThis2.add(firstBottomIndex + currentIndex);

				}
			} else if (ringNumber < detail) {
				sphere.put(i, new ArrayList<Integer>());
				sphere.put(firstBottomIndex + i, new ArrayList<Integer>());

				ArrayList<Integer> addToThis = sphere.get(i);
				ArrayList<Integer> addToThis2 = sphere
						.get(firstBottomIndex + i);

				addToThis.add(i - 1);
				addToThis2.add(firstBottomIndex + i - 1);

				addToThis.add(currentIndex);
				addToThis2.add(firstBottomIndex + currentIndex);
				currentIndex++;

				addToThis.add(currentIndex);
				addToThis2.add(firstBottomIndex + currentIndex);

				if ((i - 1) % ringNumber == 0) {
					currentIndex++;
					addToThis.add(currentIndex);
					addToThis2.add(firstBottomIndex + currentIndex);
				}
			}
			if (ringNumber >= detail) {
				// Center has no connected; each connects to it instead.
				// The same triangles are made either way, so it's okay.
				break;
			}
		}

		/*
		 * Loop through the points and convert the ArrayList to the 2D array,
		 * because that was already written when I started the ArrayList method.
		 */
		for (Integer i : sphere.keySet()) {
			if (sphere.get(i) != null) {
				ArrayList<Integer> list = sphere.get(i);
				Integer[][] addThis = new Integer[list.size() - 1][2];
				for (int j = 0; j < addThis.length; j++) {
					if (i < firstBottomIndex) {
						addThis[j][1] = list.get(j);
						addThis[j][0] = list.get(j + 1);
					} else {
						addThis[j][0] = list.get(j);
						addThis[j][1] = list.get(j + 1);
					}
				}
				trianglesDef.put(i, addThis);
			}
		}

		// Define the actual triangles. This is what the shape will keep.
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for (Integer e : trianglesDef.keySet()) {
			Point a = points.get(e);
			Integer[][] tris = trianglesDef.get(e);
			for (Integer[] d : tris) {
				Point b = points.get(d[0]);
				Point c = points.get(d[1]);
				triangles.add(new Triangle(a, b, c));
			}
		}

		// Add the center of the shape to the end of the point array.
		Point shapeCenter = new Point(Cx, Cy, Cz);
		points.add(shapeCenter);

		// Removes duplicate points from the ArrayList so transformations don't
		// affect them multiple times.
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size(); j++) {
				if (i != j && points.get(i) == points.get(j)) {
					points.remove(j--);
				}
			}
		}
		Shape out = new Shape();
		out.points = points;
		out.triangles = triangles;
		out.center = new Point(Cx, Cy, Cz);

		return out;
	}
	
	public static Shape newShape(Point center){
		Shape out = new Shape();
		out.setX(center.getX());
		out.setY(center.getY());
		out.setZ(center.getZ());
		
		return out;
	}
	
	public void fillShape(Color color, Point light, int mode){
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Triangle c : this.triangles) {
			//Shader
			double lighting = (Vector.getAngle(c.getNormal(),
					new Vector(c.getCenter(), light)))
					/ (Math.PI);
			Color holder = c.getColor().clone();
			c.setColor(color.clone());
			c.shade(lighting, mode);
			c.draw(Triangle.FILL_MODE);
			c.setColor(holder);
		}
		GL11.glEnd();
	}
	
	public void drawShape(Point light, int mode){
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Triangle c : this.triangles) {
			//Shader
			double lighting = (Vector.getAngle(c.getNormal(),
					new Vector(c.getCenter(), light)))
					/ (Math.PI);
			Color holder = c.getColor().clone();
			c.shade(lighting, mode);
			c.draw(Triangle.FILL_MODE);
			c.setColor(holder);
		}
		GL11.glEnd();
	}
	
	public void drawShape(Point[] lights, int mode){
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Triangle c : this.triangles) {
			//Shader
			double darkness = 1;
			double lighting = 0;
			for(int i = 0; i<lights.length; i++){
				double check = (Vector.getAngle(c.getNormal(),
				new Vector(c.getCenter(), lights[i]))) / (Math.PI);
				darkness = 1-lighting;
				lighting += check*darkness;
			}
			Color holder = c.getColor().clone();
			c.shade(lighting, mode);
			c.draw(Triangle.FILL_MODE);
			c.setColor(holder);
		}
		GL11.glEnd();
	}
	
	public void rotateAroundVector(double angle, Point p, Vector v){
		for(Point c : this.points){
			c.rotateAroundVector(angle, p, v);
		}
		this.center.rotateAroundVector(angle, p, v);
	}
}
