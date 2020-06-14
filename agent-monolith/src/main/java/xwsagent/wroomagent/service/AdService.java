package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.CommentConverter;
import xwsagent.wroomagent.converter.UserConverter;
import xwsagent.wroomagent.converter.VehicleConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.domain.Location;
import xwsagent.wroomagent.domain.Vehicle;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.CommentDTO;
import xwsagent.wroomagent.domain.dto.UserDTO;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.AdRepository;
import xwsagent.wroomagent.repository.CommentRepository;
import xwsagent.wroomagent.repository.LocationRepository;
import xwsagent.wroomagent.repository.PriceListRepository;
import xwsagent.wroomagent.repository.VehicleRepository;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final LocationRepository locationRepository;
    private final PriceListRepository priceListRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public AdService(LocationRepository locationRepository,
                     AdRepository adRepository,
                     PriceListRepository priceListRepository,
                     VehicleRepository vehicleRepository,
                     VehicleService vehicleService,
                     UserService userService,
                     CommentRepository commentRepository) {
        this.locationRepository = locationRepository;
        this.adRepository = adRepository;
        this.priceListRepository = priceListRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public List<Ad> findAll() {
    	return this.adRepository.findAll();
    }
    
    public Location findLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " location"));
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Ad findById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " ad"));
    }

    public Ad save(AdDTO adDTO) {
        Ad ad = AdConverter.toEntity(adDTO);
        ad.setVehicle(vehicleService.findById(adDTO.getVehicleId()));
        ad.setPriceList(priceListRepository.findOneById(adDTO.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(adDTO.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public Ad update(Long adId, AdDTO dto) {
        Ad ad = AdConverter.toEntity(dto);
        ad.setId(adId);
        ad.setVehicle(vehicleService.findById(dto.getVehicleId()));
        ad.setPriceList(priceListRepository.findOneById(dto.getPriceListId()));
        ad.setLocation(locationRepository.findOneById(dto.getLocationId()));
        ad.setPublishDate(Calendar.getInstance().getTime());
        return adRepository.save(ad);
    }

    public List<Ad> findAllActiveForUser(Long userId) {
        return adRepository.findAllActiveUser(userId);
    }

    public void delete(Long adId) {
        Ad ad = findById(adId);
        ad.setDeleted(true);
        adRepository.save(ad);
    }

    public Integer countAds(Long user_id) {
        User user = userService.findById(user_id);
        return adRepository.checkAdCountForUser(user.getId());
    }

    public UserDTO getOwner(Long ad_id) {
    	return UserConverter.fromEntity(this.adRepository.findById(ad_id).get().getVehicle().getOwner());
    }
    
    public Comment addComment(CommentDTO dto, Long id, Authentication auth) {
    	Comment comment = CommentConverter.toEntity(dto);
    	comment.setTitle(dto.getTitle());
    	comment.setContent(dto.getContent());
    	comment.setApproved(false);
    	comment.setDeleted(false);
    	User user = userService.findByEmail(((UserPrincipal) auth.getPrincipal()).getUsername());
    	comment.setClientUsername(user.getName() + " " + user.getSurname());
    	comment.setAd(findById(id));
    	comment.setRating(dto.getRating());
    	Calendar cal = Calendar.getInstance();
    	Date date = cal.getTime();
    	comment.setCommentDate(date);
    	commentRepository.save(comment);
    	return comment;
    }
    
    public List<Comment> getComments(){
		List<Comment> list = new ArrayList<Comment>();
		for(Comment c : commentRepository.findAll()) {
			if(c.isApproved() == false && c.isDeleted() == false) {
				list.add(c);
			}
		}
		return list;
	}
	
	public Comment findByCommentId(Long id) {
		return commentRepository.findById(id)
				.orElseThrow(() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " ad"));
	}
	
	public void confirm(Long id) {
		Comment comment = findByCommentId(id);
		comment.setApproved(true);
		commentRepository.save(comment);
	}
	
	public void refuse(Long id) {
		Comment comment = findByCommentId(id);
		comment.setApproved(false);
		comment.setDeleted(true);
		commentRepository.save(comment);
	}
}
