package graphicsLibrary;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {

	@Test
	public void test() {
		Vector a = new Vector(1,2,5);
		Vector b = new Vector(3,6,15);
		
		assertTrue(a.multiply(3).equals(b));
		assertTrue(a.multiply(0).equals(new Vector(0,0,0)));

	}

}
