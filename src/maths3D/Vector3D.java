package maths3D;

public class Vector3D {

	private double x;
	private double y;
	private double z;
	
	public Vector3D(){	
		x=0.0;
		y=0.0;
		z=0.0;
	}
	
	public Vector3D(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vector3D(Vector3D vector){
		this.x=vector.x;
		this.y=vector.y;
		this.z=vector.z;
	}
	
	public Vector3D(Normal normal){
		this.x=normal.getX();
		this.y=normal.getY();
		this.z=normal.getZ();
	}
	
	public Vector3D add(Vector3D vector){
		return new Vector3D(x+vector.x, y+vector.y, z+vector.z);
	}
	
	public Vector3D subtract(Vector3D vector){
		return new Vector3D(x-vector.x, y-vector.y, z-vector.z);
	}
	
	public Vector3D subtractNormal(Normal normal){
		return new Vector3D(x-normal.getX(), y-normal.getY(), z-normal.getZ());
	}
	
	public double dot(Vector3D vector){
		return x*vector.x + y*vector.y +z*vector.z;
	}
	
	public double dot(Point3D point){
		return x*point.getX() + y*point.getY() +z*point.getZ();
	}
	
	public double dot(Normal normal){
		return x*normal.getX() + y*normal.getY() +z*normal.getZ();
	}
	
	public Vector3D cross(Vector3D vector) {
		
		Vector3D crossedVector =  new Vector3D();
		
		crossedVector.x = y*vector.z - z*vector.y;
		crossedVector.y = z*vector.x - x*vector.z;
		crossedVector.z = x*vector.y - y*vector.x;
		
		return crossedVector;
		
	}
	
	public Vector3D multiply(double i){
		Vector3D multipliedVector =  new Vector3D();
		
		multipliedVector.x = x*i;
		multipliedVector.y = y*i;
		multipliedVector.z = z*i;
		
		return multipliedVector;		
	}
	
	public void normalise(){
		double magnitude = Math.sqrt(x*x+y*y+z*z);
		
		x = x/magnitude;
		y = y/magnitude;
		z = z/magnitude;
	}

	public Vector3D add(Point3D point) {
		return new Vector3D(x+point.getX(), y+point.getY(), z+point.getZ());	
	}
	
	public double length(){
		return Math.sqrt(x*x+y*y+z*z);
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