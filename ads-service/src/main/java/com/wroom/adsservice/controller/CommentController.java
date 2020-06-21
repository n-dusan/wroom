package com.wroom.adsservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wroom.adsservice.config.EndpointConfig;
import com.wroom.adsservice.converter.CommentConverter;
import com.wroom.adsservice.domain.dto.CommentDTO;
import com.wroom.adsservice.service.CommentService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = EndpointConfig.COMMENTS_BASE_URL)
@Log4j2
public class CommentController {

	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@GetMapping(value = "/{ad}")
	public ResponseEntity<List<CommentDTO>> get(@PathVariable("ad") Long adId) {
		return new ResponseEntity<>(CommentConverter.fromEntityList(commentService.findByAd(adId), CommentConverter::fromEntity),
				HttpStatus.OK);
	}

	@GetMapping(value = "/vehicle/{id}")
	public ResponseEntity<List<CommentDTO>> getVehicleComments(@PathVariable("id") Long vehicleId) {
		return new ResponseEntity<>(CommentConverter.fromEntityList(commentService.getVehicleComments(vehicleId), CommentConverter::fromEntity),
				HttpStatus.OK);
	}

	@GetMapping(value = "/vehicle/{id}/avg")
	public ResponseEntity<Double> getVehicleAverage(@PathVariable("id") Long vehicleId) {
		return new ResponseEntity<>(commentService.getAverage(vehicleId),
				HttpStatus.OK);
	}
	
}
