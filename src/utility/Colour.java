package utility;

public class Colour {

	private float r;
	private float g;
	private float b;
	
	public Colour(){
		r = 0.0f;
		g = 0.0f;
		b = 0.0f;	
	}
	
	public Colour(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;	
	}
	
	public Colour(Colour colour){
		r = colour.r;
		g = colour.g;
		b = colour.b;
	}
	
	public void add(Colour colour){
		r += colour.r;
		g += colour.g;
		b += colour.b;
	}
	
	public void divide(int scalar){
		r /= scalar;
		g /= scalar;
		b /= scalar;
	}
	
	public int toInteger(){
		
		// converts individual red green blue values into a single int for use with the image buffer
		return (int) (r*255)<<16|(int) (g*255)<<8|(int) (b*255);
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
	
	
	
	
}
