package graphicsLibrary;

public class Entity {
	private Shape shape;
	private Point center;
	private Vector velocity;
	private double radius, volume,  mass, density;
	
	public Entity(double Cx, double Cy, double Cz, 
			Vector velocity, double radius, double mass){
		this.center = new Point(Cx, Cy, Cz);
		this.velocity = velocity.clone();
		this.radius = radius;
		this.mass = mass;
		this.volume = radius * radius * radius * Math.PI * (4.0/3.0);
		this.density = this.mass/this.volume;
		this.shape = Shape.newSphere(Cx, Cy, Cz, radius, 20);
	}
	
	public Entity(Point center, Vector velocity, double radius, double mass){
		this(center.getX(), center.getY(), center.getZ(), velocity, radius, mass);
	}
	
	public Entity(Point center, double radius, double mass){
		this(center.getX(), center.getY(), center.getZ(), new Vector(0,0,0), radius, mass);
	}

	public Shape getShape() {
		return shape;
	}

	public Point getCenter() {
		return center;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public double getRadius() {
		return radius;
	}

	public double getVolume() {
		return volume;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
		this.density = mass/this.volume;
	}

	public double getDensity() {
		return density;
	}
	
	public void accelerate(Vector a){
		this.velocity = Vector.add(this.velocity, a);
	}
	
	public boolean isColliding(Entity o){
		double dis = (new Vector(this.getCenter(), o.getCenter()).getMagnitude());
		return dis<=this.radius+o.radius;
	}
	
	public void checkCollision(Entity o){
		if (this.isColliding(o)) {
			Vector normal = new Vector(o.getCenter(), this.getCenter());
			Vector correct = new Vector(this.getCenter(), o.getCenter());
			correct = correct.toUnitVector().multiply(this.radius+o.radius*0.5-
					normal.getMagnitude()*0.5);
			this.center.translate(correct.negative());
			o.center.translate(correct);
			this.shape.translate(correct.negative());
			o.shape.translate(correct);

		}
	}
	
	
}
