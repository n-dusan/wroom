package xwsagent.wroomagent.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired 
	ImageService imageService;
	
	public List<Vehicle> getAll(){
		
		return vehicleRepository.findAll();
	}
	
	public Vehicle findOne(Long id) {
		return vehicleRepository.findById(id).orElseGet(null);
	}
	
	public Vehicle save(VehicleDTO vehicledto) {
		Vehicle entity = VehicleConverter.toEntity(vehicledto);
		return vehicleRepository.save(entity);
	}
	
	public void postImages(List<MultipartFile> files, Vehicle vehicle) {
		List<Image> images = new ArrayList<Image>();
		for(MultipartFile f : files) {
		InputStream in = null;
		OutputStream out = null;
		String fileName = f.getOriginalFilename();
		String path = "./src/main/resources/static/images/";
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
		image.setVehicle(vehicle);
		Image im = imageService.save(image);
		images.add(im);
		}
	}
}
