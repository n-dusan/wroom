package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.soap.converters.CommentSoapConverter;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.CommentListRequest;
import xwsagent.wroomagent.soap.xsd.CommentListResponse;
import xwsagent.wroomagent.soap.xsd.CommentRequest;
import xwsagent.wroomagent.soap.xsd.CommentResponse;

import java.util.List;

public class CommentsClient extends WebServiceGatewaySupport {

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	public CommentResponse send(Comment entity) {
		CommentRequest request = new CommentRequest();
//		request.setComment(CommentSoapConverter.toSoapComment(entity));
		
		System.out.println(">>>>>> Sening comment to wroom");
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

		return CommentSoapConverter.fromEntityList(response.getComment(), CommentSoapConverter::fromSoapRequest);
	}
	
}
