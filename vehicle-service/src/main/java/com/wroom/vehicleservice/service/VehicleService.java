package com.wroom.vehicleservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wroom.vehicleservice.converter.AMQPVehicleConverter;
import com.wroom.vehicleservice.converter.VehicleConverter;
import com.wroom.vehicleservice.domain.Image;
import com.wroom.vehicleservice.domain.Vehicle;
import com.wroom.vehicleservice.domain.dto.VehicleDTO;
import com.wroom.vehicleservice.domain.dto.VehicleImageDTO;
import com.wroom.vehicleservice.exception.GeneralException;
import com.wroom.vehicleservice.jwt.UserPrincipal;
import com.wroom.vehicleservice.producer.VehicleProducer;
import com.wroom.vehicleservice.producer.message.OperationEnum;
import com.wroom.vehicleservice.repository.BodyTypeRepository;
import com.wroom.vehicleservice.repository.FuelTypeRepository;
import com.wroom.vehicleservice.repository.GearboxTypeRepository;
import com.wroom.vehicleservice.repository.ModelTypeRepository;
import com.wroom.vehicleservice.repository.VehicleRepository;

@Service
public class VehicleService {

	private final VehicleRepository vehicleRepository;
	private final ImageService imageService;
	private final ModelTypeRepository modelTypeRepository;
	private final BodyTypeRepository bodyTypeRepository;
	private final FuelTypeRepository fuelTypeRepository;
	private final GearboxTypeRepository gearboxTypeRepository;
	private final VehicleProducer vehicleProducer;
	// private final UserRepository userRepository;

	public VehicleService(VehicleRepository vehicleRepository, ImageService imageService,
			ModelTypeRepository modelTypeRepository, BodyTypeRepository bodyTypeRepository,
			FuelTypeRepository fuelTypeRepository, GearboxTypeRepository gearboxTypeRepository,
			VehicleProducer vehicleProducer) {
		this.vehicleRepository = vehicleRepository;
		this.imageService = imageService;
		this.modelTypeRepository = modelTypeRepository;
		this.bodyTypeRepository = bodyTypeRepository;
		this.fuelTypeRepository = fuelTypeRepository;
		this.gearboxTypeRepository = gearboxTypeRepository;
		this.vehicleProducer = vehicleProducer;
	}

	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}

	public List<Vehicle> getAllActive(Authentication auth) {
		return vehicleRepository.findAllActiveForUser(((UserPrincipal) auth.getPrincipal()).getId());
	}

	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new GeneralException("Unable to find reference to " + id.toString() + " vehicle"));
	}

	public List<Vehicle> findAllActiveForUser(Long userId) {
		return vehicleRepository.findAllActiveForUser(userId);
	}

	public void delete(Long id) {
		Vehicle entity = findById(id);
		entity.setDeleted(true);
		Vehicle vehicle = vehicleRepository.save(entity);

		// Notify search service
		VehicleDTO dto = VehicleConverter.fromEntity(vehicle);
		this.vehicleProducer.send(AMQPVehicleConverter.toVehicleMessage(dto, OperationEnum.DELETE));
	}

	public Vehicle update(VehicleDTO vehicleDTO) {
		if (vehicleDTO == null) {
			throw new GeneralException("Forwarded DTO is null");
		}
		Vehicle entity = findById(vehicleDTO.getId());
		
		entity.setChildSeats(vehicleDTO.getChildSeats());
		entity.setCdw(vehicleDTO.getCdw());
		entity.setMileage(vehicleDTO.getMileage());
		entity.setModelType(this.modelTypeRepository.findByName(vehicleDTO.getModelType().getName()));
		entity.setBodyType(this.bodyTypeRepository.findOneByName(vehicleDTO.getBodyType().getName()));
		entity.setFuelType(this.fuelTypeRepository.findByName(vehicleDTO.getFuelType().getName()));
		entity.setGearboxType(this.gearboxTypeRepository.findByName(vehicleDTO.getGearboxType().getName()));

		Vehicle vehicle = this.vehicleRepository.save(entity);

		// Notify search service
		VehicleDTO dto = VehicleConverter.fromEntity(vehicle);
		this.vehicleProducer.send(AMQPVehicleConverter.toVehicleMessage(dto, OperationEnum.UPDATE));

		return vehicle;
	}

	/**
	 *
	 * @param vehicledto
	 * @param auth       - currently logged in user, the one who creates the vehicle
	 * @return created vehicle
	 */
	public Vehicle save(VehicleDTO vehicledto, Authentication auth) {
		Vehicle entity = VehicleConverter.toEntity(vehicledto);
		entity.setModelType(this.modelTypeRepository.findByName(vehicledto.getModelType().getName()));
//		entity.setBrandType(this.brandTypeRepository.findByName(vehicledto.getBrandType().getName()));
		entity.setBodyType(this.bodyTypeRepository.findOneByName(vehicledto.getBodyType().getName()));
		entity.setFuelType(this.fuelTypeRepository.findByName(vehicledto.getFuelType().getName()));
		entity.setGearboxType(this.gearboxTypeRepository.findByName(vehicledto.getGearboxType().getName()));
		entity.setOwnerId(((UserPrincipal) auth.getPrincipal()).getId());

		Vehicle vehicle = this.vehicleRepository.save(entity);

		// Notify search service
		VehicleDTO dto = VehicleConverter.fromEntity(vehicle);
		this.vehicleProducer.send(AMQPVehicleConverter.toVehicleMessage(dto, OperationEnum.CREATE));

		return vehicle;
	}

	public List<byte[]> getFile(Long id) throws IOException {
		List<Image> listImage = vehicleRepository.findByVehicle(findById(id));
		List<byte[]> arrays = new ArrayList<byte[]>();
		for (Image i : listImage) {
			Path path = Paths.get(i.getUrlPath());
			arrays.add(Files.readAllBytes(path));
		}
		return arrays;
	}

	public void postImages(List<MultipartFile> files, Vehicle vehicle) {
		List<Image> images = new ArrayList<Image>();
		for (MultipartFile f : files) {
			InputStream in = null;
			OutputStream out = null;
			String fileName = f.getOriginalFilename();
			System.out.println("File name " + fileName);
			String path = "./src/main/resources/static/images/";
			File newFile = new File(path + fileName);
			System.out.println(newFile.getAbsolutePath());
			// System.out.println(newFile.getP);
			try {
				in = f.getInputStream();

				if (!newFile.exists()) {
					newFile.createNewFile();
				}

				out = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image image = new Image();
			image.setUrlPath(newFile.getAbsolutePath());
			image.setVehicle(vehicle);
			Image im = imageService.save(image);
			images.add(im);
		}
	}

	public List<VehicleImageDTO> getVehiclesImage() throws IOException {
		List<Vehicle> vehicles = this.vehicleRepository.findAll();
		List<VehicleImageDTO> ret = new ArrayList<VehicleImageDTO>();

		List<Image> vehicleImages;
		for (Vehicle v : vehicles) {
			vehicleImages = this.imageService.findByVehicle(v);
			if (vehicleImages.size() > 0) {
				Path path = Paths.get(vehicleImages.get(0).getUrlPath());
				ret.add(new VehicleImageDTO(v.getId(), Files.readAllBytes(path)));
			} else {
				ret.add(new VehicleImageDTO(v.getId(), null));
			}
		}

		return ret;
	}
}