package RayTracer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import maths3D.Normal;
import maths3D.Point3D;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import utility.Colour;
import Materials.LambertShader;
import Materials.ReflectiveSurfaceShader;
import Materials.Shader;
import Materials.TransparentShader;



public class Parser {
	
	private String fileName;
	
	public Parser(String fileName){
		this.fileName = fileName;
	}
	
	public void parse(){
		try{	
			//build document parser
			File inputFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(inputFile);
			
			doc.getDocumentElement().normalize();
			
			Element rootEle = doc.getDocumentElement();
			
			removeWhitespaceNodes(rootEle);
			
			
			//get image size
			String resX = rootEle.getAttribute("x");
			String resY = rootEle.getAttribute("y");
			
			int width = Integer.parseInt(resX);
			int height = Integer.parseInt(resY);
			//set world
			RayTracer.setWorld(width, height);
				
			NodeList nodes = rootEle.getChildNodes();
			//first item is samples
			Node sampler = nodes.item(0);
			
			String samplesTemp = sampler.getTextContent();
			
			int samples = Integer.parseInt(samplesTemp);
			
			RayTracer.setSampler(samples);
			//second item is camera
			Node camera = nodes.item(1);
			//get camera properties and se them
			NodeList CameraElements = camera.getChildNodes();
			
			Element cameraOrigin = (Element) CameraElements.item(0);
			
			Element cameraDir = (Element) CameraElements.item(1);
			
			float cameraOriginX = Float.parseFloat(cameraOrigin.getAttribute("x"));
			float cameraOriginY = Float.parseFloat(cameraOrigin.getAttribute("y"));
			float cameraOriginZ = Float.parseFloat(cameraOrigin.getAttribute("z"));
			
			Point3D origin = new Point3D(cameraOriginX, cameraOriginY, cameraOriginZ);
			
			float cameraDirX = Float.parseFloat(cameraDir.getAttribute("x"));
			float cameraDirY = Float.parseFloat(cameraDir.getAttribute("y"));
			float cameraDirZ = Float.parseFloat(cameraDir.getAttribute("z"));
			
			Point3D dir = new Point3D(cameraDirX, cameraDirY, cameraDirZ);
			
			Node CameraFOV = CameraElements.item(2);
			
			int FOV = Integer.parseInt(CameraFOV.getTextContent());
			
			RayTracer.setCamera(origin, dir, FOV);
			
			
			// third and fourth items are objects and lights
			Element objects = (Element) nodes.item(2);
			
			Element lights = (Element) nodes.item(3);
			
			
			//get all planes, spheres and lights in a list
			NodeList lightList = lights.getElementsByTagName("light");
			
			NodeList spheresList = objects.getElementsByTagName("sphere");
			
			NodeList planesList = objects.getElementsByTagName("plane");
			
			
			//for each sphere depending on the spheres shader, create that sphere
			for (int i = 0; i<spheresList.getLength();i++){
				Element sphere = (Element) spheresList.item(i);
				
				NodeList sphereElements = sphere.getChildNodes();
				
				Element shader = (Element) sphereElements.item(0);
				String shaderName = shader.getTextContent();
				
				if(shaderName.equals("Lambertian")){
					Element colourEle = (Element) sphereElements.item(1);
					
					float r = Float.parseFloat(colourEle.getAttribute("r"));
					float g = Float.parseFloat(colourEle.getAttribute("g"));
					float b = Float.parseFloat(colourEle.getAttribute("b"));
					
					Colour colour = new Colour(r,g,b);
					
					Shader sphShader = new LambertShader(colour);
					
					Element originEle = (Element) sphereElements.item(2);
					
					float x = Float.parseFloat(originEle.getAttribute("x"));
					float y = Float.parseFloat(originEle.getAttribute("y"));
					float z = Float.parseFloat(originEle.getAttribute("z"));
					
					Point3D sphOrigin = new Point3D(x,y,z);
					
					Element radEle = (Element) sphereElements.item(3);
					
					int radius = Integer.parseInt(radEle.getTextContent());
					
					RayTracer.getWorld().addSphere(sphOrigin, radius, sphShader);	
					
					
				}else if(shaderName.equals("Reflective")){
					
					Shader sphShader = new ReflectiveSurfaceShader();
					
					Element originEle = (Element) sphereElements.item(1);
					
					float x = Float.parseFloat(originEle.getAttribute("x"));
					float y = Float.parseFloat(originEle.getAttribute("y"));
					float z = Float.parseFloat(originEle.getAttribute("z"));
					
					Point3D sphOrigin = new Point3D(x,y,z);
					
					Element radEle = (Element) sphereElements.item(2);
					
					int radius = Integer.parseInt(radEle.getTextContent());
					
					RayTracer.getWorld().addSphere(sphOrigin, radius, sphShader);
					
					
				}else if(shaderName.equals("Transparent")){
					
					Element originEle = (Element) sphereElements.item(1);
					
					float x = Float.parseFloat(originEle.getAttribute("x"));
					float y = Float.parseFloat(originEle.getAttribute("y"));
					float z = Float.parseFloat(originEle.getAttribute("z"));
					
					Point3D sphOrigin = new Point3D(x,y,z);
					
					Element radEle = (Element) sphereElements.item(2);
					
					int radius = Integer.parseInt(radEle.getTextContent());
					
					Element riEle = (Element) sphereElements.item(3);
					
					double ri = Double.parseDouble(riEle.getTextContent());
					
					Shader sphShader = new TransparentShader(ri);
					
					RayTracer.getWorld().addSphere(sphOrigin, radius, sphShader);	
				}
				
			}
			
			//for each plane depending on the planes shader, create that plane
			for (int i = 0; i<planesList.getLength();i++){
				
				
				Element plane = (Element) planesList.item(i);
				
				NodeList planeElements = plane.getChildNodes();
				
				Element shader = (Element) planeElements.item(0);
				String shaderName = shader.getTextContent();
				
				if(shaderName.equals("Lambertian")){
					Element colourEle = (Element) planeElements.item(1);
					
					float r = Float.parseFloat(colourEle.getAttribute("r"));
					float g = Float.parseFloat(colourEle.getAttribute("g"));
					float b = Float.parseFloat(colourEle.getAttribute("b"));
					
					Colour colour = new Colour(r,g,b);
					
					Shader plnShader = new LambertShader(colour);
					
					Element originEle = (Element) planeElements.item(2);
					
					
					
					float x = Float.parseFloat(originEle.getAttribute("x"));
					float y = Float.parseFloat(originEle.getAttribute("y"));
					float z = Float.parseFloat(originEle.getAttribute("z"));
					
					Point3D plnOrigin = new Point3D(x,y,z);
					
					Element normalEle = (Element) planeElements.item(3);
					
					float nx = Float.parseFloat(normalEle.getAttribute("x"));
					float ny = Float.parseFloat(normalEle.getAttribute("y"));
					float nz = Float.parseFloat(normalEle.getAttribute("z"));
					
					Normal plnNormal = new Normal(nx,ny,nz);
					
					RayTracer.getWorld().addPlane(plnOrigin, plnNormal, plnShader);
						
				}else if(shaderName.equals("Reflective")){
					
					Shader plnShader = new ReflectiveSurfaceShader();
					
					Element originEle = (Element) planeElements.item(1);
					
					float x = Float.parseFloat(originEle.getAttribute("x"));
					float y = Float.parseFloat(originEle.getAttribute("y"));
					float z = Float.parseFloat(originEle.getAttribute("z"));
					
					Point3D plnOrigin = new Point3D(x,y,z);
					
					Element normalEle = (Element) planeElements.item(2);
					
					float nx = Float.parseFloat(normalEle.getAttribute("x"));
					float ny = Float.parseFloat(normalEle.getAttribute("y"));
					float nz = Float.parseFloat(normalEle.getAttribute("z"));
					
					Normal plnNormal = new Normal(nx,ny,nz);
					
					RayTracer.getWorld().addPlane(plnOrigin, plnNormal, plnShader);
					
				}
			}
			
			//for each light create that light
			
			for (int i = 0; i<lightList.getLength();i++){
				
				Element light = (Element) lightList.item(i);
				
				NodeList lightElements = light.getChildNodes();
				
				
				
				
				Element originEle = (Element) lightElements.item(0);
				
				float x = Float.parseFloat(originEle.getAttribute("x"));
				float y = Float.parseFloat(originEle.getAttribute("y"));
				float z = Float.parseFloat(originEle.getAttribute("z"));
				
				Point3D lightOrigin = new Point3D(x,y,z);
				
				Element colourEle = (Element) lightElements.item(1);
				
				float r = Float.parseFloat(colourEle.getAttribute("r"));
				float g = Float.parseFloat(colourEle.getAttribute("g"));
				float b = Float.parseFloat(colourEle.getAttribute("b"));
				
				Colour colour = new Colour(r,g,b);
				
				RayTracer.getWorld().addLight(lightOrigin, colour);
				
				
			}
			
			RayTracer.getWorld().lightRatio();
			
			
		}catch (Exception e) {
	         e.printStackTrace();
		}
		
	}
	
	private static void removeWhitespaceNodes(Element e) {
		NodeList children = e.getChildNodes();
		for (int i = children.getLength() - 1; i >= 0; i--) {
			Node child = children.item(i);
			if (child instanceof Text && ((Text) child).getData().trim().length() == 0) {
				e.removeChild(child);
			}
			else if (child instanceof Element) {
				removeWhitespaceNodes((Element) child);
			}
		}
	}
}
