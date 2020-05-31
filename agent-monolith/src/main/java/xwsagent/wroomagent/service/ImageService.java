package xwsagent.wroomagent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.repository.ImageRepository;

@Service
@AllArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	
	public Image save(Image image) {
		return imageRepository.save(image);
	}
	
	public List<Image> findAll() {
		return this.imageRepository.findAll();
	}
	
	public List<Image> findByVehicle(Vehicle v) {
		return this.imageRepository.findByVehicle(v);
	}
}
