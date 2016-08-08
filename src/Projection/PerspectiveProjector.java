package Projection;

import maths3D.Point2D;
import maths3D.Point3D;
import maths3D.Ray;
import maths3D.Vector3D;
import RayTracer.RayTracer;


public class PerspectiveProjector extends Projector{

	private Point3D camera;
	private Point3D sceneLocation;
	private double distance;
	private Vector3D u, v, w;
	
	public PerspectiveProjector(Point3D camera, Point3D sceneLocation, double FOV){
		this.camera = new Point3D(camera);
		this.sceneLocation = new Point3D(sceneLocation);

		this.distance = (RayTracer.getWorld().getViewPlane().getHeight()/2)/Math.tan(Math.toRadians(FOV));	
		
		uvw();
	}
	
	private void uvw() {
		
		
		//calculate othanormal basis eg a coordinate system from the cameras perspective
		w = camera.subtractVector(sceneLocation);
		w.normalise();
		
	

		u = new Vector3D(0.000,1.0,0.000).cross(w);
		u.normalise();
		
		v = w.cross(u);
		v.normalise();	
	}


	public Ray createRay(Point2D point) {
		
		//shoot ray from camera perspective
		
		Ray ray = new Ray(new Point3D(camera), u.multiply(point.getX()).add(v.multiply(point.getY()).subtract(w.multiply(distance))));
		
		ray.getDirection().normalise();
		
		return ray;
	}
	

}
