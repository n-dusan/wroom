package xwsagent.wroomagent.soap.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.soap.converters.CommentSoapConverter;
import xwsagent.wroomagent.soap.xsd.SendCommentRequest;
import xwsagent.wroomagent.soap.xsd.SendCommentResponse;

public class CommentsClient extends WebServiceGatewaySupport{
	public SendCommentResponse send(Comment entity) {
		SendCommentRequest request = new SendCommentRequest();
		request.setComment(CommentSoapConverter.toSoapRequest(entity));
		
		System.out.println(">>>>>> Sending comment to wroom");
		SendCommentResponse response = (SendCommentResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		System.out.println(">>>>>> Sent");
		
		return response;
	}
}
