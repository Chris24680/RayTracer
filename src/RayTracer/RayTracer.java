package RayTracer;
import maths3D.Intersection;
import maths3D.Point2D;
import maths3D.Point3D;
import maths3D.Ray;
import utility.Colour;
import utility.Image;
import utility.PixelSetter;
import world.World;
import Materials.Shader;
import Projection.PerspectiveProjector;
import Projection.Projector;
import antialising.Sampler;
import antialising.SimpleSampler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

		int numberOfCores = Runtime.getRuntime().availableProcessors();
		ArrayList<Tracer> tracers = new ArrayList<Tracer>();

		for(int i=1; i<=numberOfCores; i++){
			int startLine = (world.getViewPlane().getHeight()/numberOfCores)*i;
			int endLine = startLine+(world.getViewPlane().getHeight()/numberOfCores);
			tracers.add(new Tracer(startLine, endLine));
		}

		//ExecutorService executorService = Executors.newFixedThreadPool(numberOfCores);
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		ArrayList<ArrayList<ArrayList<Colour>>> coloursArray = new ArrayList<ArrayList<ArrayList<Colour>>>();

		try {
			ArrayList<Future<ArrayList<ArrayList<Colour>>>> futuresArray = new ArrayList<Future<ArrayList<ArrayList<Colour>>>>();
			for(int i = 0; i<tracers.size(); i++){
				futuresArray.add(executorService.submit(tracers.get(i)));
			}
			for(int i=0; i<futuresArray.size(); i++){
				coloursArray.add(futuresArray.get(i).get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<PixelSetter> pixelSetters = new ArrayList<PixelSetter>();
		for(int i=0; i<coloursArray.size(); i++){
			pixelSetters.add(new PixelSetter(coloursArray.get(i), (world.getViewPlane().getHeight()/numberOfCores)*i));
		}

		ArrayList<Future<Boolean>> futureResults = new ArrayList<Future<Boolean>>();

		try{
			for(int i = 0; i<pixelSetters.size(); i++){
				futureResults.add(executorService.submit(pixelSetters.get(i)));
			}
			for(int i = 0; i<futureResults.size(); i++){
				Boolean result = futureResults.get(i).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Save final image
        image.saveImage("PNG");

        long end = System.nanoTime();
        
        System.out.println("100.00%");

        //Print rendering time
        System.out.print("Loop Time = " + ((end - start)/1000000000.0f));
    }
    
    public static void setImagePixels(ArrayList<ArrayList<Colour>> colours, int start){
    	//Set pixel colour.
		for(int y=0; y<colours.size(); y++){
			for(int x=0; x<colours.get(0).size(); x++){
				image.getBuffer().setRGB(x, ((world.getViewPlane().getHeight()-start)-y)-1, colours.get(y).get(x).toInteger());
			}
		}
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
