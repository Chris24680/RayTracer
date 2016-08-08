package Materials;

import java.util.ArrayList;

import maths3D.Intersection;
import maths3D.Ray;
import utility.Colour;
import utility.Light;

public abstract class Shader {
	
	private Colour diffuseColour;
	
	public abstract Colour shade(Intersection intersection, ArrayList<Light> lights, Ray incidenceRay);
	
	public Colour getDiffuseColour(){
		return diffuseColour;
	}
	
}
