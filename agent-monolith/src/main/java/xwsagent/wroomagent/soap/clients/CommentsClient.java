package xwsagent.wroomagent.soap.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.repository.CommentRepository;
import xwsagent.wroomagent.service.AdService;
import xwsagent.wroomagent.service.CommentService;
import xwsagent.wroomagent.soap.converters.CommentSoapConverter;
import xwsagent.wroomagent.soap.xsd.CommentListRequest;
import xwsagent.wroomagent.soap.xsd.CommentListResponse;
import xwsagent.wroomagent.soap.xsd.CommentReplyRequest;
import xwsagent.wroomagent.soap.xsd.CommentResponse;
import xwsagent.wroomagent.soap.xsd.CommentSoap;
import xwsagent.wroomagent.soap.xsd.CommentUpdateRequest;
import xwsagent.wroomagent.soap.xsd.CommentUpdateResponse;

@Log4j2
public class CommentsClient extends WebServiceGatewaySupport {

    @Autowired
    private AdService adService;

    @Autowired
	private CommentService commentService;

    @Autowired
	private CommentRepository commentRepository;

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	public CommentResponse reply(Comment entity, Long parentId) {
		CommentReplyRequest request = new CommentReplyRequest();
		request.setComment(CommentSoapConverter.toSoapRequest(entity));
		request.setParentId(parentId);
		
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
			
			if(comment.isReply()) {
				comment.setId(commentSoap.getLocalId());
				comment.setApproved(commentSoap.isApproved());
				Comment saved = this.commentRepository.save(comment);
				continue;
			}
			
			Comment saved = this.commentRepository.save(comment);

			if(!comment.isReply()) {
				if(commentSoap.getLocalId() == null) {
					//notify microservice of new entry (set local id)
					updateCommentId(commentSoap.getId(), saved.getId());
				}
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
