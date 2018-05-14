package utility;

import RayTracer.RayTracer;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PixelSetter implements Callable<Boolean> {
    ArrayList<ArrayList<Colour>> colours;
    int start;
    public PixelSetter(ArrayList<ArrayList<Colour>> colours, int start){
        this.colours = colours;
        this.start = start;
    }

    @Override
    public Boolean call() throws Exception {
        for(int y=0; y<colours.size(); y++){
            for(int x=0; x<colours.get(0).size(); x++){
                RayTracer.getImage().getBuffer().setRGB(x, ((RayTracer.getWorld().getViewPlane().getHeight()-start)-y)-1, colours.get(y).get(x).toInteger());
            }
        }
        return true;
    }
}
