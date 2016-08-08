package maths3D;

public class Point3D {

	private double x;
	private double y;
	private double z;
	
	public Point3D(){	
		x=0.0;
		y=0.0;
		z=0.0;
	}
	
	public Point3D(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Point3D(Point3D point){
		this.x=point.x;
		this.y=point.y;
		this.z=point.z;
	}
	
	public Point3D(Vector3D point){
		this.x=point.getX();
		this.y=point.getY();
		this.z=point.getZ();
	}
	
	
	//addition and subtraction of 3d points
	
	
	public Point3D add(Point3D point){
		return new Point3D(x+point.x, y+point.y, z+point.z);
	}
	
	public Point3D subtract(Point3D point){
		return new Point3D(x-point.x, y-point.y, z-point.z);
	}
	
	public double dot(Point3D point){
		return x*point.x + y*point.y +z*point.z;
	}
	
	public double dot(Vector3D vector){
		return x*vector.getX() + y*vector.getY() +z*vector.getZ();
	}
	
	public double dot(Normal normal){
		return x*normal.getX() + y*normal.getY() +z*normal.getZ();
	}

	public Vector3D subtractVector(Point3D point) {
		return new Vector3D(x-point.x, y-point.y, z-point.z);
	}
	
	public Point3D multiply(double i){
		Point3D multipliedPoint =  new Point3D();
		
		multipliedPoint.x = x*i;
		multipliedPoint.y = y*i;
		multipliedPoint.z = z*i;
		
		return multipliedPoint;		
	}

	public Normal subtract(Normal normal) {

		return new Normal(x-normal.getX(), y-normal.getY(), z-normal.getZ());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	

	
}
