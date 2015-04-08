package graphicsLibrary;


//TODO: implement this interface, and make Point and Vector implement it.
public interface Coordinates {
	
	public double getX();
	public double getY();
	public double getZ();
	
	public void setX();
	public void setY();
	public void setZ();
	
	public void equals(Coordinates c);
}
