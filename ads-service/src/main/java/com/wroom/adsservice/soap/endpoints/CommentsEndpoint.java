package com.wroom.adsservice.soap.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.service.AdService;
import com.wroom.adsservice.service.CommentService;
import com.wroom.adsservice.soap.converters.CommentSoapConverter;
import com.wroom.adsservice.soap.xsd.CommentListRequest;
import com.wroom.adsservice.soap.xsd.CommentListResponse;
import com.wroom.adsservice.soap.xsd.CommentRequest;
import com.wroom.adsservice.soap.xsd.CommentResponse;
import com.wroom.adsservice.soap.xsd.CommentSoap;

import lombok.extern.log4j.Log4j2;

@Endpoint
@Log4j2
public class CommentsEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";


	@Autowired
	private CommentService commentService;
	@Autowired
	private AdService adService;

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CommentRequest")
	@ResponsePayload
	public CommentResponse sendComment(@RequestPayload CommentRequest request) {
		log.info(">>>Received a comment");
		
		CommentResponse response = new CommentResponse();
		
		//reply
//		try {
//			Comment reply = this.adService.addReply(CommentSoapConverter.fromSoapRequest(request.getComment()));
//			response.setComment(CommentSoapConverter.toSoapRequest(reply));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		log.info(">>>Comment saved.");
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CommentListRequest")
	@ResponsePayload
	public CommentListResponse sync(@RequestPayload CommentListRequest request) {
		log.info("sync=comments action=started");

		List<Comment> commentList = this.commentService.findCommentsByOwnerEmail(request.getCompanyEmail());

		CommentListResponse response = new CommentListResponse();

		for (Comment comment : commentList) {
			response.getComment().add(CommentSoapConverter.toSoapRequest(comment));
		}

		List<CommentSoap> soapList = CommentSoapConverter.toEntityList(commentList, CommentSoapConverter::toSoapRequest);
		response.setComment(soapList);

		log.info("sync=comments action=ended");
		return response;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CommentUpdateRequest")
	@ResponsePayload
	public CommentUpdateResponse updateId(@RequestPayload CommentUpdateRequest request) {
		log.info("update=comments action=started");

		this.commentService.updateLocalId(request.getId(), request.getLocalId());


		CommentUpdateResponse response = new CommentUpdateResponse();
		response.setId(request.getId());
		response.setLocalId(request.getLocalId());

		log.info("sync=comments action=ended");
		return response;
	}
}
