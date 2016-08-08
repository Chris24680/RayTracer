package renderables;

import maths3D.Intersection;
import maths3D.Normal;
import maths3D.Point3D;
import maths3D.Ray;
import Materials.Shader;

public class Sphere extends Renderable {

	private Point3D center;
	private double radius;
	
	public Sphere(Point3D center, double radius, Shader material){
		this.center = center;
		this.radius = radius;
		this.setMaterial(material);
		
		this.setColour(this.getMaterial().getDiffuseColour());
		
	}
	
	public Intersection intersect(Ray ray) {
		
		double a = ray.getDirection().dot(ray.getDirection());
		double b = 2*ray.getOrigin().subtract(center).dot(ray.getDirection());
		double c = ray.getOrigin().subtract(center).dot(ray.getOrigin().subtract(center))-radius*radius;
		double discriminant = b*b-4*a*c;
		
		
		
		//if discriminant < 0 then the ray does not intersect
		if(discriminant < 0.0){
			return null;
		}else{
			double t = (-b - Math.sqrt(discriminant))/2*a;
			
			if (t < 10e-9){
				return null;
			}else{
				
				//create intersection object
				Intersection i = new Intersection();
				i.setPoint(new Point3D(ray.getDirection()));
				i.setPoint(i.getPoint().multiply(t));
				i.setPoint(i.getPoint().add(ray.getOrigin()));
				i.setNormal(getNormalAt(i.getPoint()));
				i.setDistance(t);
				i.setInside(true);
				return i;
				
			}
		}
	}

	public Normal getNormalAt(Point3D point) {
		
		Normal normal = new Normal(point);
		
		normal = center.subtract(normal);
		
		normal.normalise();
		return normal;
		
	}
	
	

}
