package Materials;

import java.util.ArrayList;

import maths3D.Intersection;
import maths3D.Normal;
import maths3D.Point3D;
import maths3D.Ray;
import maths3D.Vector3D;
import utility.Colour;
import utility.Light;
import RayTracer.RayTracer;

public class LambertShader extends Shader {
	private Colour diffuseColour;


	public LambertShader(Colour diffuseColour){
		this.diffuseColour = diffuseColour;
	}

	public Colour shade(Intersection intersection, ArrayList<Light> lights, Ray incidenceRay){
		float ambientLight = 0.05f;
		Colour finalColour = new Colour();	

		for (int j = 0; j<lights.size(); j++){

			//for each light source find the direction vector between the intersection and that light
			Vector3D lightDirection = intersection.getPoint().subtractVector(lights.get(j).getLocation());
			double lightDirectionLength = lightDirection.length();

			lightDirection.normalise();
			Normal normal = intersection.getNormal();

			//find the dot product between the intersection normal and the light direction to get the lambertian cooefficient 
			double x = Math.max(0, normal.dot(lightDirection));

			// create a shadow ray starting at the intersection and going towards the light
			Point3D shadowRayOrigin = new Point3D(lights.get(j).getLocation());
			
			//starts shadow ray just above surface to remove floating point errors
			shadowRayOrigin = shadowRayOrigin.subtract(new Point3D(lightDirection.getX(), lightDirection.getY(), lightDirection.getZ()));

			Ray shadowRay = new Ray(shadowRayOrigin, lightDirection);

			double min = Double.MAX_VALUE;
			Intersection temp = new Intersection();

			//test for intersection between the ray and the scene objects
			for(int i=0; i<RayTracer.getWorld().getWorldObjects().size(); i++){


				temp = RayTracer.getWorld().getWorldObjects().get(i).intersect(shadowRay);

				if(temp!=null && temp.getDistance()<min){
					min = temp.getDistance();

				}

			}
			
			//if there is no intersection between the light and the intersection, add that lights intensity to the pixel
			if (lightDirectionLength<min){
				finalColour.setR((float)(finalColour.getR()+(diffuseColour.getR() * lights.get(j).getIntensity().getR() * x)));
				finalColour.setG((float)(finalColour.getG()+(diffuseColour.getG() * lights.get(j).getIntensity().getG() * x)));
				finalColour.setB((float)(finalColour.getB()+(diffuseColour.getB() * lights.get(j).getIntensity().getB() * x)));


			}

		}

		// if the pixel is too dark, add ambient light
		if (finalColour.getR()<ambientLight&&finalColour.getG()<ambientLight&&finalColour.getB()<ambientLight){
			finalColour.setR(diffuseColour.getR() * ambientLight);
			finalColour.setG(diffuseColour.getG() * ambientLight);
			finalColour.setB(diffuseColour.getB() * ambientLight);
			
		}

		return finalColour;

	}
}
