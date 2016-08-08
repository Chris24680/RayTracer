package renderables;

import maths3D.Intersection;
import maths3D.Ray;
import utility.Colour;
import Materials.Shader;

public abstract class Renderable {
	
	private Colour colour;
	private Shader material;
	
	public abstract Intersection intersect(Ray ray);

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public Shader getMaterial() {
		return material;
	}

	public void setMaterial(Shader material) {
		this.material = material;
	}

}
