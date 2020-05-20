package xwsagent.wroomagent.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.service.ImageService;
import xwsagent.wroomagent.service.VehicleService;

@RestController
@RequestMapping(value="api/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<VehicleDTO> create(@RequestBody VehicleDTO vehicleDTO){
		System.out.println("Tu je dosao");
		
		Vehicle v = vehicleService.save(vehicleDTO);
		System.out.println(v.getImages() + "ovo su slike");
		return new ResponseEntity<>(
				HttpStatus.CREATED);
	}
	
	
	@PostMapping(value="/upload")
	public List<Image> postImage(@RequestParam("file") List<MultipartFile> files) throws IOException {
		List<Image> images = new ArrayList<Image>();
		System.out.println(files + "Fajlovi");
		for(MultipartFile f : files) {
		System.out.println(f.getOriginalFilename() + "Ovo je ime fajla");
		InputStream in = null;
		OutputStream out = null;
		String fileName = f.getOriginalFilename();
		String path = "./src/main/resources/static/";
		File newFile = new File(path + fileName);
		System.out.println(newFile.getAbsolutePath());
		try {
			in = f.getInputStream();
			
			if (!newFile.exists()) {
                newFile.createNewFile();
            }
			
			out = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		Image image = new Image();
		image.setUrlPath(newFile.getAbsolutePath());
		//imageService.save(image);
		//return image;
		Image im = imageService.save(image);
		images.add(im);
		}
		System.out.println(images);
		return images;
	}
}
