package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.soap.converters.RentRequestSoapConverter;
import xwsagent.wroomagent.soap.xsd.CommentRequest;
import xwsagent.wroomagent.soap.xsd.CommentResponse;

public class CommentsClient extends WebServiceGatewaySupport {

	public CommentResponse send(Comment entity) {
		CommentRequest request = new CommentRequest();
//		request.setComment(CommentSoapConverter.toSoapComment(entity));
		
		System.out.println(">>>>>> Sening comment to wroom");
		CommentResponse response = (CommentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
	
}
