package utility;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import RayTracer.RayTracer;

public class Image {

	private File image;
	
	private BufferedImage buffer;

	
	public Image(String fileName){
		
		image = new File(fileName);
		
		buffer = new BufferedImage(RayTracer.getWorld().getViewPlane().getWidth(), RayTracer.getWorld().getViewPlane().getHeight(), BufferedImage.TYPE_INT_RGB);
	
	}
	
	public void saveImage(String fileType){
        try {
			ImageIO.write(buffer, fileType, image);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}
	
	
	
}
