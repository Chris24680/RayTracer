package RayTracer;

import Materials.Shader;
import antialising.Sampler;
import maths3D.Intersection;
import maths3D.Point2D;
import maths3D.Ray;
import utility.Colour;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Tracer implements Callable<ArrayList<ArrayList<Colour>>> {
    private int startY, endY;

    public Tracer(int startY, int endY){
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public ArrayList<ArrayList<Colour>> call() throws Exception {
        return tracePixels();
    }

    public ArrayList<ArrayList<Colour>> tracePixels(){
        ArrayList<ArrayList<Colour>> colours = new ArrayList<ArrayList<Colour>>();
        for(int y=0; y<endY-startY; y++){
            Boolean result = colours.add(new ArrayList<Colour>());
            for(int x = 0; x < RayTracer.getWorld().getViewPlane().getWidth(); x++){
                colours.get(y).add(trace(x,y+startY));
            }
        }

        return colours;
    }

    private Colour trace(int x, int y) {
        Colour colour = new Colour();
        //Split each pixel into a number of samples to be tested and test ray intersection for each sample
        for (int col = 0; col < RayTracer.getSampler().samples; col++) {
            for (int row = 0; row < RayTracer.getSampler().samples; row++) {

                //return a 2D point on the view plane to test
                Point2D point = RayTracer.getSampler().sample(row, col, x, y);

                Ray ray = RayTracer.getProjector().createRay(point);


                double min = Double.MAX_VALUE;
                Colour tempColour = new Colour();

                //for each object in the world if the object intersects with the ray and the intersection is the closest to the view plane, add the colour of that object to the overall pixel colour
                for (int i = 0; i < RayTracer.getWorld().getWorldObjects().size(); i++) {
                    Intersection temp = RayTracer.getWorld().getWorldObjects().get(i).intersect(ray);


                    if (temp != null && temp.getDistance() < min) {
                        Shader shader = RayTracer.getWorld().getWorldObjects().get(i).getMaterial();


                        min = temp.getDistance();

                        tempColour = shader.shade(temp, RayTracer.getWorld().getWorldLights(), ray);
                    }
                }

                colour.add(tempColour);
            }
        }


        //Divide the overall pixel colour by the number of samples
        colour.divide(RayTracer.getSampler().samples * RayTracer.getSampler().samples);

        return colour;
    }
}
