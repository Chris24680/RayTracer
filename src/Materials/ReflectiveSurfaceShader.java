package Materials;

import java.util.ArrayList;

import maths3D.Intersection;
import maths3D.Ray;
import maths3D.Vector3D;
import utility.Colour;
import utility.Light;
import RayTracer.RayTracer;

public class ReflectiveSurfaceShader extends Shader{
	


	public ReflectiveSurfaceShader(){
		
	}


	public Colour shade(Intersection intersection, ArrayList<Light> lights, Ray incidenceRay) {
		
			//create a ray reflected from the intersection
			Vector3D reflectedDirection = incidenceRay.getDirection().subtractNormal(intersection.getNormal().multiply(2).multiply(incidenceRay.getDirection().dot(intersection.getNormal())));
			
			Ray reflectiveRay = new Ray(intersection.getPoint(), reflectedDirection);
			
			double min = Double.MAX_VALUE;
			Colour tempColour = new Colour();
			
			//check for intersection from the ray and each object
			for(int i = 0; i<RayTracer.getWorld().getWorldObjects().size(); i++){
				Intersection reflectiveRayIntersection = RayTracer.getWorld().getWorldObjects().get(i).intersect(reflectiveRay);
				if (reflectiveRayIntersection != null && reflectiveRayIntersection.getDistance()<min){
					
					min = reflectiveRayIntersection.getDistance();
					//if there is an intersection find the colour of that intersection
					tempColour = RayTracer.getWorld().getWorldObjects().get(i).getMaterial().shade(reflectiveRayIntersection, lights, reflectiveRay);
				}
	
			}
			
			return tempColour;

		
	}
	
}
