package xwsagent.wroomagent.controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.service.ImageService;

@RestController
@RequestMapping(value = "api/image")
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestParam("imageFile") MultipartFile file){
		System.out.println("Tu sam");
		InputStream in = null;
		OutputStream out = null;
	    int height = 640;   //height of the image
	    BufferedImage image1 = null;
	    FileOutputStream os = null;
	    System.out.println(file.getOriginalFilename() + "Ovo je ime fajla");
	    return new ResponseEntity<>(HttpStatus.OK);
		//for(Image image: images) {
			//try {
			//File root = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
			
//			File fileToBeReaded = new File("./" + image.getUrlPath());
//			System.out.println(fileToBeReaded.getPath() + "A OVOOOO?");
//			System.out.println(fileToBeReaded.getName() + "OVO JE MOLIM TE");
//			 URL loc = this.getClass().getResource("./" + image.getUrlPath()); 
//		      String path = loc.getPath(); 
//		          System.out.println(path + "OVOOOOOOOOOOOOOo");
//			} catch (IOException e) {
//		        e.printStackTrace();
//		    } 
//			System.out.println(image.getUrlPath() + "Ovo je URL path");
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			String path = classLoader.getResource(image.getUrlPath()).getPath();
//			image.setUrlPath(path);
//			  FileOutputStream out = null;
//		        FileInputStream in = null;
//		        int cursor;
//		    
			//image.setUrlPath(path);
//			try {
//				f = new File(".\\95767783_252458292784820_1837400216253235200_n.jpg");
//				image1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//				image1 = ImageIO.read(f);
//			}catch(IOException e) {
//				
//			}
//			try {
//				f = new File("./target/" + "Slika" +".jpg");
//				ImageIO.write(image1, "jpg", f);
//			}catch(IOException e) {
//				
//			}
//			imageService.save(image);
//		}
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	}
}
