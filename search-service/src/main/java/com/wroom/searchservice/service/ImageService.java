package com.wroom.searchservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.Image;
import com.wroom.searchservice.domain.Vehicle;
import com.wroom.searchservice.repository.ImageRepository;

import lombok.AllArgsConstructor;

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
