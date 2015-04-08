package graphicsLibrary;

import org.lwjgl.opengl.GL11;

public class Triangle {
	private Point a;
	private Point b;
	private Point c;
	private Point center;
	private Vector normal;
	private Color color;

	public static final int FILL_MODE = 0;
	public static final int LINE_MODE = 1;

	/**
	 * Constructs a new Triangle with the given points, and colours it white
	 * (#FFFFFF). The order of the points should go counterclockwise from the
	 * front of the face
	 * 
	 * @param a
	 *            -first point
	 * @param b
	 *            -second point
	 * @param c
	 *            -third point
	 */
	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = Color.newColorRGB(1, 1, 1);
		this.normal = Vector.cross(new Vector(a, c), new Vector(a, b))
				.toUnitVector();

		this.center = new Point((a.getX() + b.getX() + c.getX()) / 3, (a.getY()
				+ b.getY() + c.getY()) / 3,
				(a.getZ() + b.getZ() + c.getZ()) / 3);
	}

	/**
	 * Draws the triangle. Called within {@link GL11#glBegin(int)} block, after
	 * appropriate type has been set.
	 * 
	 * @param mode
	 *            -Triangle.FILL_MODE for whole triangle, Triangle.LINE_MODE for
	 *            lines. Defaults to full triangles.
	 */
	public void draw(int mode) {
		switch (mode) {
		case Triangle.FILL_MODE:
			GL11.glColor3d(this.color.getRed(), this.color.getGreen(),
					this.color.getBlue());
			this.a.drawPointGL();
			this.b.drawPointGL();
			this.c.drawPointGL();
			break;
		case Triangle.LINE_MODE:
			GL11.glColor3d(this.color.getRed(), this.color.getGreen(),
					this.color.getBlue());
			this.a.drawPointGL();
			this.b.drawPointGL();
			this.b.drawPointGL();
			this.c.drawPointGL();
			this.c.drawPointGL();
			this.a.drawPointGL();
			break;
		default:
			GL11.glColor3d(this.color.getRed(), this.color.getGreen(),
					this.color.getBlue());
			this.a.drawPointGL();
			this.b.drawPointGL();
			this.c.drawPointGL();
			break;
		}
	}

	public Color getColor() {
		return this.color;
	}

	public Point[] getPoints() {
		Point[] p = new Point[3];
		p[0] = this.a;
		p[1] = this.b;
		p[2] = this.c;
		return p;
	}

	public Vector getNormal() {
		this.calculateNormal();
		return this.normal;
	}

	public void shade(double frac, int mode) {
		this.color.shade(frac, mode);
	}

	public Point getCenter() {
		this.calculateCenter();
		return this.center;
	}

	public void setColor(double red, double green, double blue) {
		this.color.setColor(red, green, blue);
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public boolean isColliding(Triangle c) {
		double planeConst1 = -this.a.toVector().dot(this.normal);
		double distA1 = c.a.toVector().dot(this.normal) + planeConst1;
		double distB1 = c.b.toVector().dot(this.normal) + planeConst1;
		double distC1 = c.c.toVector().dot(this.normal) + planeConst1;
		if((distA1>0&&distB1>0&&distC1>0)||(distA1<0&&distB1<0&&distC1<0)){
			//Triangle didn't intersect plane
			return false;
		}
		double planeConst2 = -c.a.toVector().dot(c.normal);
		double distA2 = this.a.toVector().dot(c.normal) + planeConst2;
		double distB2 = this.b.toVector().dot(c.normal) + planeConst2;
		double distC2 = this.c.toVector().dot(c.normal) + planeConst2;
		if((distA2>0&&distB2>0&&distC2>0)||(distA2<0&&distB2<0&&distC2<0)){
			//Triangle didn't intersect plane
			return false;
		}
		if(distA2==0&&distB2==0&&distC2==0){
			//coplanar code
		} else {
			double[] range1 = {0, 0};
			double[] range2 = {0, 0};
			Vector lineL = this.getNormal().cross(c.getNormal());
			//Triangle 1
			if(distA1==0||distB1==0||distC1==0){
				//at least one point is coplanar (maybe 2)
				
				//TODO: if 1, t goes from the point to the one intersection
				//TODO: if 2, t goes from point to point
				
			} else {
				//One point is one on side, two more are on the other.
				double[] list1 = {distA2, distB2, distC2};
				Point Odd1 = findOdd(this.getPoints(), list1);	
				
				range1 = calculateRange(distA2, distB2, distC2, Odd1, lineL);
				
			}
			
			//Triangle 2
			if(distA1==0||distB1==0||distC1==0){
				//at least one point is coplanar (maybe 2)
				
				//TODO: if 1, t goes from the point to the one intersection
				//TODO: if 2, t goes from point to point
				
			} else {
				//One point is one on side, two more are on the other.
				double[] list1 = {distA1, distB1, distC1};
				Point Odd2 = findOdd(this.getPoints(), list1);	
				
				range2 = calculateRange(distA1, distB1, distC1, Odd2, lineL);
				
			}
			
			boolean a1IsInB = (range1[0]>=range2[0]&&range1[0]<=range2[1])||
					(range1[0]>=range2[1]&&range1[0]<=range2[0]);
			boolean a2IsInB = (range1[1]>=range2[0]&&range1[1]<=range2[1])||
					(range1[1]>=range2[1]&&range1[1]<=range2[0]);
			
			boolean b1IsInA = (range2[0]>=range1[0]&&range2[0]<=range1[1])||
					(range2[0]>=range1[1]&&range2[0]<=range1[0]);
			boolean b2IsInA = (range2[1]>=range1[0]&&range2[1]<=range1[1])||
					(range2[1]>=range1[1]&&range2[1]<=range1[0]);
			
			return a1IsInB||a2IsInB||b1IsInA||b2IsInA;
			
		}
		
		return false;

	}
	
	private Point findOdd(Point[] points, double[] dis){
		double check = (dis[0]>0?1:0 + dis[1]>0?1:0 + dis[2]>0?1:0)==1?1:-1;
		for(int i = 0; i<dis.length; i++){
			if(dis[i]*check > 0){
				return points[i];
			}
		}
		return null;
	}
	
	private double[] calculateRange(double distA, double distB, double distC, Point odd, Vector lineL){
		double d0 = 0;
		double d1 = 0;
		double d2 = 0;
		
		double p0 = 0;
		double p1 = 0;
		double p2 = 0;
		
		if(odd==null){
			System.exit(-1);
		}
		
		if(odd==this.a){
			d1 = distA;
			
			d0 = distB;
			d2 = distC;
			
			p1 = a.toVector().dot(lineL);
			
			p0 = b.toVector().dot(lineL);
			p2 = this.c.toVector().dot(lineL);
		}
		
		if(odd==this.b){
			d1 = distB;
			
			d0 = distA;
			d2 = distC;
			
			p1 = b.toVector().dot(lineL);
			
			p0 = a.toVector().dot(lineL);
			p2 = this.c.toVector().dot(lineL);
		}
		
		if(odd==this.c){
			d1 = distC;
			
			d0 = distB;
			d2 = distA;
			
			p1 = this.c.toVector().dot(lineL);
			
			p0 = b.toVector().dot(lineL);
			p2 = a.toVector().dot(lineL);
		}
		
		double t1 = p0 + (p1 - p0)*(d0/(d0-d1));
		double t2 = p2 + (p1 - p2)*(d2/(d2-d1));
		
		double[] out = new double[2];
		out[0] = t1;
		out[1] = t2;
		
		return out;
	}

	private void calculateNormal() {
		this.normal = Vector.cross(new Vector(a, c), new Vector(a, b))
				.toUnitVector();
	}

	private void calculateCenter() {
		this.center = new Point((a.getX() + b.getX() + c.getX()) / 3, (a.getY()
				+ b.getY() + c.getY()) / 3,
				(a.getZ() + b.getZ() + c.getZ()) / 3);
	}
}
