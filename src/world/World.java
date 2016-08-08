package world;

import java.util.ArrayList;

import maths3D.Normal;
import maths3D.Point3D;
import renderables.Plane;
import renderables.Renderable;
import renderables.Sphere;
import utility.Colour;
import utility.Light;
import Materials.Shader;

public class World {
	private ViewPlane viewPlane;
	private ArrayList<Renderable> worldObjects;
	private ArrayList<Light> worldLights;

	
	public World(int width, int height, double size){
		
		viewPlane = new ViewPlane(width, height, size);	
		worldObjects = new ArrayList<Renderable>();
		worldLights = new ArrayList<Light>();

	}
	
	public void lightRatio(){
		
		//find ratio of light intensities and then sets light intensity so that it never goes above 1
		float totalR = 0;
		float totalG = 0;
		float totalB = 0;
		for (int i = 0; i < worldLights.size(); i++){
		
			totalR += worldLights.get(i).getIntensity().getR();
			totalG += worldLights.get(i).getIntensity().getG();
			totalB += worldLights.get(i).getIntensity().getB();
		}
		
		for (int i = 0; i < worldLights.size(); i++){
			
			
			worldLights.get(i).getIntensity().setR(worldLights.get(i).getIntensity().getR()/totalR);
			worldLights.get(i).getIntensity().setG(worldLights.get(i).getIntensity().getG()/totalG);
			worldLights.get(i).getIntensity().setB(worldLights.get(i).getIntensity().getB()/totalB);
		}
		
	}

	public void addSphere(Point3D sphOrigin, int radius, Shader sphShader) {
		worldObjects.add(new Sphere(sphOrigin,radius,sphShader));
		
	}

	public void addPlane(Point3D plnOrigin, Normal plnNormal, Shader plnShader) {
		worldObjects.add(new Plane(plnOrigin,plnNormal, plnShader));
		
	}

	public void addLight(Point3D lightOrigin, Colour colour) {
		
		worldLights.add(new Light(lightOrigin, colour));
		
	}

	public ViewPlane getViewPlane() {
		return viewPlane;
	}

	public void setViewPlane(ViewPlane viewPlane) {
		this.viewPlane = viewPlane;
	}

	public ArrayList<Renderable> getWorldObjects() {
		return worldObjects;
	}

	public void setWorldObjects(ArrayList<Renderable> worldObjects) {
		this.worldObjects = worldObjects;
	}

	public ArrayList<Light> getWorldLights() {
		return worldLights;
	}

	public void setWorldLights(ArrayList<Light> worldLights) {
		this.worldLights = worldLights;
	}

	
	
}
