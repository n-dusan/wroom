package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;
	
	public Image save(Image image) {
		return imageRepository.save(image);
	}
}
