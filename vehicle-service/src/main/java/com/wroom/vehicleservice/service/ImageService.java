package com.wroom.vehicleservice.service;

import com.wroom.vehicleservice.domain.Image;
import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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