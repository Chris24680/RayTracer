package antialising;

import maths3D.Point2D;
import RayTracer.RayTracer;

public class SimpleSampler extends Sampler {

	public SimpleSampler(int samples){
		this.samples = samples;
	}

	
	public Point2D sample(int row, int col, int x, int y) {
		
		
		//Simple sampler gives uniformly spaced points in a grid for each pixel
		Point2D point = new Point2D(x-RayTracer.getWorld().getViewPlane().getWidth()/2+(col+0.5)/samples,y-RayTracer.getWorld().getViewPlane().getHeight()/2+(row+0.5)/samples);
		
		return point;
		
	}
	
}
