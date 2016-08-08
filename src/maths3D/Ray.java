package maths3D;

public class Ray {
	
	private Point3D origin;
	private Vector3D direction;
	
	public Ray(){
		origin = new Point3D();
		direction = new Vector3D();
	}
	
	public Ray(Point3D origin, Vector3D direction){
		this.origin = new Point3D(origin);
		this.direction = new Vector3D(direction);
	}

	public Point3D getOrigin() {
		return origin;
	}

	public void setOrigin(Point3D origin) {
		this.origin = origin;
	}

	public Vector3D getDirection() {
		return direction;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}
	
	

}
