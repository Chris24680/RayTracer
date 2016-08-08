package antialising;

import maths3D.Point2D;


// each sampler must be able to provide a set of 2d points on the view plane
public abstract class Sampler {
	public int samples;
	
	public abstract Point2D sample(int row, int col, int x, int y);
	
}
