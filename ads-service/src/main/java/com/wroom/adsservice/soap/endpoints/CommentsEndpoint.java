package com.wroom.adsservice.soap.endpoints;

import com.wroom.adsservice.domain.Comment;
import com.wroom.adsservice.repository.CommentRepository;
import com.wroom.adsservice.service.CommentService;
import com.wroom.adsservice.soap.converters.CommentSoapConverter;
import com.wroom.adsservice.soap.xsd.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
@Log4j2
public class CommentsEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";


	@Autowired
	private CommentService commentService;

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CommentRequest")
	@ResponsePayload
	public CommentResponse sendMessageToService(@RequestPayload CommentRequest request) {
		log.info(">>>Received a comment");
		
		CommentResponse response = new CommentResponse();
//		response.setMessage(MessagesConverter.toSoapMessage(this.messageRepository.save(MessagesConverter.fromSoapMessage(request.getMessage()))));
		
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
