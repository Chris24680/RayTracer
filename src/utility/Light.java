package utility;

import maths3D.Point3D;

public class Light {

	private Point3D location;
	private Colour intensity;
	
	public Light(Point3D location, Colour intensity){
		
		this.location = location;
		this.intensity = intensity;
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public Colour getIntensity() {
		return intensity;
	}

	public void setIntensity(Colour intensity) {
		this.intensity = intensity;
	}
	
	
}
