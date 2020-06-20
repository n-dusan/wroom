package xwsagent.wroomagent.service;

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
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.VehicleDTO;
import xwsagent.wroomagent.domain.dto.VehicleImageDTO;
import xwsagent.wroomagent.exception.InvalidDataException;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.AdRepository;
import xwsagent.wroomagent.repository.BodyTypeRepository;
import xwsagent.wroomagent.repository.BrandTypeRepository;
import xwsagent.wroomagent.repository.FuelTypeRepository;
import xwsagent.wroomagent.repository.GearboxTypeRepository;
import xwsagent.wroomagent.repository.ModelTypeRepository;
import xwsagent.wroomagent.repository.VehicleRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.clients.VehicleClient;
import xwsagent.wroomagent.soap.xsd.Operation;

@Service
public class VehicleService {

	private final VehicleRepository vehicleRepository;
	private final ImageService imageService;
	private final ModelTypeRepository modelTypeRepository;
	private final BrandTypeRepository brandTypeRepository;
	private final BodyTypeRepository bodyTypeRepository;
	private final FuelTypeRepository fuelTypeRepository;
	private final GearboxTypeRepository gearboxTypeRepository;
	private final UserRepository userRepository;
	private final AdRepository adRepository;
	private final VehicleClient vehicleClient;

	public VehicleService(VehicleRepository vehicleRepository, ImageService imageService,
			ModelTypeRepository modelTypeRepository, BrandTypeRepository brandTypeRepository,
			BodyTypeRepository bodyTypeRepository, FuelTypeRepository fuelTypeRepository,
			GearboxTypeRepository gearboxTypeRepository, UserRepository userRepository,
			AdRepository adRepository, VehicleClient vehicleClient) {
		this.vehicleRepository = vehicleRepository;
		this.imageService = imageService;
		this.modelTypeRepository = modelTypeRepository;
		this.brandTypeRepository = brandTypeRepository;
		this.bodyTypeRepository = bodyTypeRepository;
		this.fuelTypeRepository = fuelTypeRepository;
		this.gearboxTypeRepository = gearboxTypeRepository;
		this.userRepository = userRepository;
		this.adRepository = adRepository;
		this.vehicleClient = vehicleClient;
	}

	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}

	public List<Vehicle> getAllActive(Authentication auth) {
		return vehicleRepository.findAllActiveForUser(((UserPrincipal) auth.getPrincipal()).getId());
	}

	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id).orElseThrow(
				() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " vehicle"));
	}

	public List<Vehicle> findAllActiveForUser(Long userId) {
		return vehicleRepository.findAllActiveForUser(userId);
	}

	public void delete(Long id) {
		Vehicle vehicle = findById(id);
		vehicle.setDeleted(true);
		Vehicle saved = vehicleRepository.save(vehicle);
		
		this.vehicleClient.send(saved, Operation.DELETE);
	}

	public Vehicle update(Vehicle vehicle, VehicleDTO vehicleDTO) {
		if (vehicleDTO == null) {
			throw new InvalidDataException("Forwarded DTO is null");
		}

		vehicle.setChildSeats(vehicleDTO.getChildSeats());
		vehicle.setCdw(vehicleDTO.getCdw());
		vehicle.setMileage(vehicleDTO.getMileage());
		vehicle.setModelType(this.modelTypeRepository.findByName(vehicleDTO.getModelType().getName()));
		vehicle.setBodyType(this.bodyTypeRepository.findByName(vehicleDTO.getBodyType().getName()));
		vehicle.setFuelType(this.fuelTypeRepository.findByName(vehicleDTO.getFuelType().getName()));
		vehicle.setGearboxType(this.gearboxTypeRepository.findByName(vehicleDTO.getGearboxType().getName()));

		Vehicle saved = this.vehicleRepository.save(vehicle);
		
		try {
			this.vehicleClient.send(saved, Operation.UPDATE);
		} catch(Exception e) {
			System.err.println("Did not sync with wroom.");
		}
		
		
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
		entity.setBodyType(this.bodyTypeRepository.findByName(vehicledto.getBodyType().getName()));
		entity.setFuelType(this.fuelTypeRepository.findByName(vehicledto.getFuelType().getName()));
		entity.setGearboxType(this.gearboxTypeRepository.findByName(vehicledto.getGearboxType().getName()));
		// weird java syntax to set Optional<User>
		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		user.getUsername();
		Optional<User> loggedInUser = userRepository.findByEmail(user.getUsername());
		if (loggedInUser.isPresent()) {
			entity.setOwner(loggedInUser.get());
		}
		
		Vehicle saved = vehicleRepository.save(entity);
		
		//Send to wroom
		this.vehicleClient.send(saved, Operation.CREATE);
		
		return saved;
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
			String path = "target/classes/static/images/";
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
	
	/**
	 * Fetch one image of an ad
	 * Used in Shopping Cart.
	 * @param ad_id
	 * @return
	 */
	public byte[] getImage(Long ad_id) {
		List<Image> allImages = new ArrayList<Image>(this.adRepository.findById(ad_id).get().getVehicle().getImages());
		if(allImages.size() > 0) {
			Path path = Paths.get(allImages.get(0).getUrlPath());
			try {
				return Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
