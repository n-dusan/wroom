package xwsagent.wroomagent.soap.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.repository.CommentRepository;
import xwsagent.wroomagent.service.AdService;
import xwsagent.wroomagent.service.CommentService;
import xwsagent.wroomagent.soap.converters.CommentSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CommentsClient extends WebServiceGatewaySupport {

    @Autowired
    private AdService adService;

    @Autowired
	private CommentService commentService;

    @Autowired
	private CommentRepository commentRepository;

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	public CommentResponse reply(Comment entity) {
		CommentRequest request = new CommentRequest();
		request.setComment(CommentSoapConverter.toSoapRequest(entity));
		
		log.info(">>>>>> Sending comment to wroom");
		CommentResponse response = (CommentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info(">>>>>> Sent");
		
		return response;
	}
	
	/**
	 * sends owner_email
	 * @return list of comments that belong to that user
	 */
	public void syncComments() {
		CommentListRequest request = new CommentListRequest();
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		CommentListResponse response = (CommentListResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		List<CommentSoap> commentSoapList = response.getComment();


        for (CommentSoap commentSoap : commentSoapList) {

			Comment comment = CommentSoapConverter.fromSoapRequest(commentSoap);
			comment.setAd(adService.findById(commentSoap.getAdId()));
			Comment saved = this.commentRepository.save(comment);

			if(commentSoap.getLocalId() == null) {
				//notify microservice of new entry (set local id)
				updateCommentId(commentSoap.getId(), saved.getId());
			}
        }
	}

	/**
	 *
	 * @param id wroom id
	 * @param localId local monolith id
	 */
	public void updateCommentId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		CommentUpdateRequest request = new CommentUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);

		CommentUpdateResponse response = (CommentUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a comment with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}
	
}
