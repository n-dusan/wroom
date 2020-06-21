package xwsagent.wroomagent.soap.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.service.AdService;
import xwsagent.wroomagent.soap.converters.CommentSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.*;

import java.util.ArrayList;
import java.util.List;

public class CommentsClient extends WebServiceGatewaySupport {

    @Autowired
    private AdService adService;

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	public CommentResponse reply(Comment entity) {
		CommentRequest request = new CommentRequest();
		request.setComment(CommentSoapConverter.toSoapRequest(entity));
		
		System.out.println(">>>>>> Sening reply to wroom");
		CommentResponse response = (CommentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
	/**
	 * sends owner_email
	 * @return list of comments that belong to that user
	 */
	public List<Comment> syncComments() {
		CommentListRequest request = new CommentListRequest();
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		CommentListResponse response = (CommentListResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		List<CommentSoap> commentSoapList = response.getComment();

		List<Comment> commentList = new ArrayList<>();
        for (CommentSoap commentSoap : commentSoapList) {
            Comment comment = CommentSoapConverter.fromSoapRequest(commentSoap);
            comment.setAd(adService.findById(commentSoap.getAdId()));
            commentList.add(comment);
        }
		return commentList;
	}
	
}
