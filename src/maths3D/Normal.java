package maths3D;

public class Normal {
	
	private double x;
	private double y;
	private double z;
	
	public Normal(){	
		x=0.0;
		y=0.0;
		z=0.0;
	}
	
	public Normal(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Normal(Normal normal){
		this.x=normal.x;
		this.y=normal.y;
		this.z=normal.z;
	}
	
	public Normal(Vector3D point) {
		this.x=point.getX();
		this.y=point.getY();
		this.z=point.getZ();
	}
	
	public Normal(Point3D point) {
		this.x=point.getX();
		this.y=point.getY();
		this.z=point.getZ();	}
	
	public void normalise(){
		double magnitude = Math.sqrt(x*x+y*y+z*z);
		
		x = x/magnitude;
		y = y/magnitude;
		z = z/magnitude;
	}

	public Normal subtract(Point3D point){
		return new Normal(x-point.getX(), y-point.getY(), z-point.getZ());
	}

	public Normal multiply(double i) {
		Normal multipliedNormal =  new Normal();
		
		multipliedNormal.x = x*i;
		multipliedNormal.y = y*i;
		multipliedNormal.z = z*i;
		
		return multipliedNormal;
	}


	public double dot(Point3D point){
		return x*point.getX() + y*point.getY() +z*point.getZ();
	}
	
	public double dot(Vector3D vector){
		return x*vector.getX() + y*vector.getY() + z*vector.getZ();
	}
	
	public double dot(Normal normal){
		return x*normal.x + y*normal.y +z*normal.z;
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
