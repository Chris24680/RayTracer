package maths3D;

public class Intersection {
	
	private Point3D point;
	
	private Normal normal;
	
	private double distance;
	
	private boolean isInside;
	
	public Intersection(){
		point = new Point3D();
		normal = new Normal();
	}
	
	public Intersection(Point3D point, Normal normal, double distance){
		this.point = point;
		this.normal = normal;
		this.distance = distance;
	}
	
	public Point3D getPoint(){
		return point;
	}
	
	public void setPoint(Point3D newPoint){
		point = newPoint;
	}

	public Normal getNormal() {
		return normal;
	}

	public void setNormal(Normal normal) {
		this.normal = normal;
	}

	public double getDistance(){
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isInside() {
		return isInside;
	}

	public void setInside(boolean isInside) {
		this.isInside = isInside;
	}

}
