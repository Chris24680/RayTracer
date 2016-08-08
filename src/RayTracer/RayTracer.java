package RayTracer;
import maths3D.Intersection;
import maths3D.Point2D;
import maths3D.Point3D;
import maths3D.Ray;
import utility.Colour;
import utility.Image;
import world.World;
import Materials.Shader;
import Projection.PerspectiveProjector;
import Projection.Projector;
import antialising.Sampler;
import antialising.SimpleSampler;

public class RayTracer {
	
	//Set Up the rendering environment
	private static World world;
	private static Image image;
	private static Sampler sampler;
	private static Projector projector;
	private static Parser parser;

	
    public static void main(String[] args){
    	//set up timer
        System.out.println("Rendering...");
        long start = System.nanoTime();
        
        parser = new Parser("input.xml");
        parser.parse();
        image = new Image("image.png");
        
      
        
        double perc = 0;
        //Main loop of program, goes through each pixel in image and assigns a colour value
        for(int y = 0; y<world.getViewPlane().getHeight(); y++){
        	
        	
        	double tempperc = (y/(float) world.getViewPlane().getHeight())*100.0;
        	
        	if (tempperc-perc>1){
        		//print percentage computed
        		System.out.printf("%.2f", tempperc);
        		System.out.print("%\n");
        		perc = tempperc;
        	}
        	
        	
            for(int x = 0; x<world.getViewPlane().getWidth(); x++) {

            	//Render pixel colour
            	trace(x, y);
            	
                
            }
        }
        
        //Save final image
        image.saveImage("PNG");

        long end = System.nanoTime();
        
        System.out.println("100.00%");

        //Print rendering time
        System.out.print("Loop Time = " + ((end - start)/1000000000.0f));
    }
    
    public static void trace(int x, int y){	
    	Colour colour = new Colour();

    	//Split each pixel into a number of samples to be tested and test ray intersection for each sample
    	for(int col = 0; col < sampler.samples; col++){
    		for(int row = 0; row < sampler.samples; row++){
    			
    			//return a 2D point on the view plane to test
    			Point2D point = sampler.sample(row, col, x, y);
        		
    			Ray ray = projector.createRay(point);
        		
    			
    			
        		double min = Double.MAX_VALUE;
        		Colour tempColour = new Colour();
        		
        		//for each object in the world if the object intersects with the ray and the intersection is the closest to the view plane, add the colour of that object to the overall pixel colour
        		for(int i=0; i<world.getWorldObjects().size(); i++){
            		Intersection temp = world.getWorldObjects().get(i).intersect(ray);
            	

            		if(temp!=null && temp.getDistance()<min){
            			Shader shader = world.getWorldObjects().get(i).getMaterial();
            			
            			
            			min = temp.getDistance();
            			
            			tempColour = shader.shade(temp, world.getWorldLights(), ray);
            		}
        		}
        		
        		colour.add(tempColour);
    		}
    	}
    	
    	
    	//Divide the overall pixel colour by the number of samples
    	colour.divide(sampler.samples*sampler.samples); 
    	
    	
    	//Set pixel colour.

    	image.getBuffer().setRGB(x, world.getViewPlane().getHeight()-y-1, colour.toInteger());

    }
    
    
    
    public static void setWorld(int width, int height){
    	
    	world = new World(width, height, 1.0);

    }

	public static void setSampler(int samples) {
		sampler = new SimpleSampler(samples);
		
	}

	public static void setCamera(float originX, float originY,
			float originZ, float dirX, float dirY,
			float dirZ, int FOV) {
		projector = new PerspectiveProjector(new Point3D(originX, originY, originZ),new Point3D(dirX, dirY, dirZ),FOV);	
	}

	public static void setCamera(Point3D origin, Point3D dir, int FOV) {
		projector = new PerspectiveProjector(origin,dir,FOV);
		
	}

	public static World getWorld() {
		return world;
	}

	public static void setWorld(World world) {
		RayTracer.world = world;
	}

	public static Image getImage() {
		return image;
	}

	public static void setImage(Image image) {
		RayTracer.image = image;
	}

	public static Sampler getSampler() {
		return sampler;
	}

	public static void setSampler(Sampler sampler) {
		RayTracer.sampler = sampler;
	}

	public static Projector getProjector() {
		return projector;
	}

	public static void setProjector(Projector projector) {
		RayTracer.projector = projector;
	}

	public static Parser getParser() {
		return parser;
	}

	public static void setParser(Parser parser) {
		RayTracer.parser = parser;
	}
	
	
	
	

}
