package Materials;

import java.util.ArrayList;

import maths3D.Intersection;
import maths3D.Normal;
import maths3D.Ray;
import maths3D.Vector3D;
import utility.Colour;
import utility.Light;
import RayTracer.RayTracer;

public class TransparentShader extends Shader {

	private double RI;

	public boolean isInside;

	public TransparentShader(double RI){

		this.RI = RI;

	}

	@Override
	public Colour shade(Intersection intersection, ArrayList<Light> lights,
			Ray incidenceRay) {

		//create a ray in the refracted direction throught the material
		Vector3D refractedDirection = getRefractedVector(RI, incidenceRay.getDirection(), intersection.getNormal(), intersection.isInside());
		
		Ray refractedRay = new Ray(intersection.getPoint(), refractedDirection);
		
		double min = Double.MAX_VALUE;
		Colour tempColour = new Colour();
		
		//check for intersection
		for(int i = 0; i<RayTracer.getWorld().getWorldObjects().size(); i++){
			Intersection refractedRayIntersection = RayTracer.getWorld().getWorldObjects().get(i).intersect(refractedRay);
			if (refractedRayIntersection != null && refractedRayIntersection.getDistance()<min){
				min = refractedRayIntersection.getDistance();
				//find the colour of this intersection
				refractedRayIntersection.setInside(!intersection.isInside());

				tempColour = RayTracer.getWorld().getWorldObjects().get(i).getMaterial().shade(refractedRayIntersection, lights, refractedRay);
				
			}

		}
		
		return tempColour;
	}


	private Vector3D getRefractedVector(double objectRI, Vector3D incomingRayDirection, Normal surfaceNormal, boolean isInside){

		Normal normal = new Normal();
		double origRI;
		double newRI;
		
		//difference between entering and leaving material accounted for

		if (!isInside){
			normal = surfaceNormal;
			origRI = objectRI;
			newRI = 1;
			
		}else{
			normal = surfaceNormal.multiply(-1);
			origRI = 1;
			newRI = objectRI;
		}
		
		normal.normalise();
		
		// calculate the refraction angle 
		Vector3D inRayDirection = incomingRayDirection;
		
		inRayDirection.normalise();

		Vector3D transmittedRayDirection = new Vector3D();

		double r = origRI/newRI;

		double c = (normal.dot(inRayDirection))*-1;

		Vector3D tempVec1 = inRayDirection.multiply(r);

		Vector3D tempVec2 = new Vector3D( normal.multiply(r*c - Math.sqrt(1-((r*r)*(1-(c*c))))) );

		transmittedRayDirection = tempVec1.add(tempVec2);

		transmittedRayDirection.normalise();
		
		
		//if the angle is too large the light is reflected inside the object
		if(Math.sqrt(1-((r*r)*(1-(c*c))))<0.0){
			
			double a = inRayDirection.dot(normal);
			
			Normal b = normal.multiply(a);
			
			Vector3D TORRayDirection = inRayDirection.subtractNormal(b.multiply(2));  //Total internal Reflection
			
			return TORRayDirection;
			
		}

		return transmittedRayDirection;
	}


}
