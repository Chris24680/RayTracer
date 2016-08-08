package Projection;

import maths3D.Point2D;
import maths3D.Ray;


public abstract class Projector {
	
	public abstract Ray createRay(Point2D point);

}
