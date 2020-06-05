package com.wroom.searchservice.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wroom.searchservice.config.EndpointConfig;
import com.wroom.searchservice.converter.AdConverter;
import com.wroom.searchservice.converter.BodyTypeConverter;
import com.wroom.searchservice.converter.BrandTypeConverter;
import com.wroom.searchservice.converter.FuelTypeConverter;
import com.wroom.searchservice.converter.GearboxTypeConverter;
import com.wroom.searchservice.converter.LocationConverter;
import com.wroom.searchservice.converter.ModelTypeConverter;
import com.wroom.searchservice.converter.PriceListConverter;
import com.wroom.searchservice.converter.VehicleConverter;
import com.wroom.searchservice.domain.dto.AdDTO;
import com.wroom.searchservice.domain.dto.FeatureDTO;
import com.wroom.searchservice.domain.dto.LocationDTO;
import com.wroom.searchservice.domain.dto.PriceListDTO;
import com.wroom.searchservice.domain.dto.SearchCriteriaDTO;
import com.wroom.searchservice.domain.dto.VehicleDTO;
import com.wroom.searchservice.domain.dto.VehicleImageDTO;
import com.wroom.searchservice.service.AdService;
import com.wroom.searchservice.service.BodyTypeService;
import com.wroom.searchservice.service.BrandTypeService;
import com.wroom.searchservice.service.FuelTypeService;
import com.wroom.searchservice.service.GearboxTypeService;
import com.wroom.searchservice.service.ModelTypeService;
import com.wroom.searchservice.service.PriceListService;
import com.wroom.searchservice.service.SearchService;
import com.wroom.searchservice.service.VehicleService;

@RestController
@RequestMapping(value = EndpointConfig.SEARCH_BASE_URL)
public class SearchController {

	private final SearchService searchService;
	private final AdService adService;
	private final VehicleService vehicleService;
	private final PriceListService pricelistService;
	private final BrandTypeService brandsService;
	private final ModelTypeService modelsService;
	private final FuelTypeService fuelService;
	private final GearboxTypeService gearboxService;
	private final BodyTypeService bodyService;
	
	
    

	public SearchController(SearchService searchService, AdService adService, VehicleService vehicleService,
			PriceListService pricelistService, BrandTypeService brandsService, ModelTypeService modelsService,
			FuelTypeService fuelService, GearboxTypeService gearboxService, BodyTypeService bodyService) {
		this.searchService = searchService;
		this.adService = adService;
		this.vehicleService = vehicleService;
		this.pricelistService = pricelistService;
		this.brandsService = brandsService;
		this.modelsService = modelsService;
		this.fuelService = fuelService;
		this.gearboxService = gearboxService;
		this.bodyService = bodyService;
	}

	@GetMapping("/hello")
    public ResponseEntity<?> get() throws UnknownHostException {
        System.out.println("I am reached.");
        String ip = InetAddress.getLocalHost().getHostAddress();
        return new ResponseEntity<>(String.format("Hello from search service with ip address %s, my child.", ip), HttpStatus.OK);
    }
    
    /**
	 * POST /api/ads/search
	 * 
	 * @param search
	 * @return
	 */
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchCriteriaDTO search) {
		System.out.println(">>> I'm searching...");
		return new ResponseEntity<>(
				AdConverter.fromEntityList(this.searchService.search(search), AdConverter::fromEntity), HttpStatus.OK);
	}
	
	@GetMapping(value = "/ads")
	public ResponseEntity<List<AdDTO>> getAllAds() {
		return new ResponseEntity<>(AdConverter.fromEntityList(adService.findAll(), AdConverter::fromEntity),
				HttpStatus.OK);
	}
	
	@GetMapping("/ads/{ad}")
	public ResponseEntity<AdDTO> getAd(@PathVariable("ad") Long adId) {
		return new ResponseEntity<>(AdConverter.fromEntity(adService.findById(adId)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/vehicles")
	public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
		return new ResponseEntity<>(VehicleConverter.fromEntityList(vehicleService.findAll(), VehicleConverter::fromEntity),
				HttpStatus.OK);
	}
	
	@GetMapping("/vehicles/{id}")
	public ResponseEntity<?> getVehicle(@PathVariable("id") Long id) {
		return new ResponseEntity<>(VehicleConverter.fromEntity(vehicleService.findById(id)), HttpStatus.OK);
	}
	
	/**
	 * Used for search, attaches only one image for each vehicle, that is going to
	 * be shown on Search page.
	 * 
	 * @return
	 */
	@GetMapping(value = "/vehicles/with-image")
	public ResponseEntity<List<VehicleImageDTO>> getVehicleImage() throws IOException {
		try {
			return new ResponseEntity<>(vehicleService.getVehiclesImage(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception is thrown");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/vehicles/images/{id}")
	public List<byte[]> getFile(@PathVariable("id") Long id) throws IOException {
		return vehicleService.getFile(id);
	}
	
	@GetMapping("/locations")
	public ResponseEntity<List<LocationDTO>> getAllLocations() {
		return new ResponseEntity<>(
				LocationConverter.fromEntityList(adService.getAllLocations(), LocationConverter::fromEntity),
				HttpStatus.OK);
	}
	
	
    @GetMapping("/pricelists")
    public ResponseEntity<List<PriceListDTO>> getAllActive() {
        return new ResponseEntity<>(
                PriceListConverter.fromEntityList(pricelistService.getAllActive(), PriceListConverter::fromEntity),
                HttpStatus.OK
        );
    }
    
    @GetMapping(value="/pricelists/{id}")
    public ResponseEntity<PriceListDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                PriceListConverter.fromEntity(pricelistService.findById(id)),
                HttpStatus.OK
        );
    }
    
    @GetMapping(produces = "application/json", value = "/brands")
	public ResponseEntity<List<FeatureDTO>> getBrandTypes(){
		
		return new ResponseEntity<>(
				BrandTypeConverter.fromEntityList(brandsService.getAll(), BrandTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
    
    @GetMapping(produces = "application/json", value = "/models")
	public ResponseEntity<List<FeatureDTO>> getModelTypes(){
		
		return new ResponseEntity<>(
				ModelTypeConverter.fromEntityList(modelsService.getAll(), ModelTypeConverter::fromEntity),
				HttpStatus.OK
		);
	}
    
    @GetMapping(produces = "application/json", value = "/fuels")
   	public ResponseEntity<List<FeatureDTO>> getFuelTypes(){
   		
   		return new ResponseEntity<>(
   				FuelTypeConverter.fromEntityList(fuelService.getAll(), FuelTypeConverter::fromEntity),
   				HttpStatus.OK
   		);
   	}
    
    @GetMapping(produces = "application/json", value = "/gearboxes")
   	public ResponseEntity<List<FeatureDTO>> getGearboxTypes(){
   		
   		return new ResponseEntity<>(
   				GearboxTypeConverter.fromEntityList(gearboxService.getAll(), GearboxTypeConverter::fromEntity),
   				HttpStatus.OK
   		);
   	}
    
    @GetMapping(produces = "application/json", value = "/bodies")
   	public ResponseEntity<List<FeatureDTO>> getBodyTypes(){
   		
   		return new ResponseEntity<>(
   				BodyTypeConverter.fromEntityList(bodyService.getAll(), BodyTypeConverter::fromEntity),
   				HttpStatus.OK
   		);
   	}

}
