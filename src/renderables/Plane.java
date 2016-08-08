package renderables;

import maths3D.Intersection;
import maths3D.Normal;
import maths3D.Point3D;
import maths3D.Ray;
import Materials.Shader;

public class Plane extends Renderable {
	
	private Point3D point;
	
	private Normal normal;
	
	public Plane(Point3D point, Normal normal,  Shader material){
		this.point = new Point3D(point);
		this.normal = new Normal(normal);
		this.setMaterial(material);
		this.setColour(this.getMaterial().getDiffuseColour());
		
	}


	public Intersection intersect(Ray ray) {
		
		
		// test for intersection using ray equation and plane equation
		double t = point.subtract(ray.getOrigin()).dot(normal)/ray.getDirection().dot(normal);
		
		if(t < 10e-9){
			return null;
		} else{
			
			//create an intersection object containing values of the intersection
			Intersection i = new Intersection();
			
			i.setPoint(new Point3D(ray.getDirection()));
			i.setPoint(i.getPoint().multiply(t));
			i.setPoint(i.getPoint().add(ray.getOrigin()));
			i.setNormal(normal);
			i.setDistance(t);
			
			
			return i;
		}
		
		
		
	}

}
