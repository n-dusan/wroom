package xwsagent.wroomagent.soap.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.repository.MessageRepository;
import xwsagent.wroomagent.soap.converters.MessagesConverter;
import xwsagent.wroomagent.soap.xsd.Message;
import xwsagent.wroomagent.soap.xsd.MessageListRequest;
import xwsagent.wroomagent.soap.xsd.MessageListResponse;
import xwsagent.wroomagent.soap.xsd.MessageUpdateRequest;
import xwsagent.wroomagent.soap.xsd.MessageUpdateResponse;
import xwsagent.wroomagent.soap.xsd.SendMessageRequest;
import xwsagent.wroomagent.soap.xsd.SendMessageResponse;

@Log4j2
public class MessagesClient extends WebServiceGatewaySupport {

	public static final String MONOLITH_USER_EMAIL = "zika@maildrop.cc";

	@Autowired
	private MessageRepository messageRepository;

	public SendMessageResponse send(xwsagent.wroomagent.domain.Message entity) {
		SendMessageRequest request = new SendMessageRequest();
		request.setMessage(MessagesConverter.toSoapMessage(entity));
		request.getMessage().setId(entity.getId());
		
		SendMessageResponse response = (SendMessageResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		return response;
	}

	public void syncMessages() {
		MessageListRequest request = new MessageListRequest();
		request.setCompanyEmail(MONOLITH_USER_EMAIL);

		MessageListResponse response = (MessageListResponse) getWebServiceTemplate().marshalSendAndReceive(request);

		List<Message> messageSoapList = response.getMessages();

		for (Message messageSoap : messageSoapList) {

			xwsagent.wroomagent.domain.Message message = MessagesConverter.fromSoapMessage(messageSoap);
			xwsagent.wroomagent.domain.Message saved = this.messageRepository.save(message);

			if (messageSoap.getLocalId() == null) {
				// notify microservice of new entry (set local id)
				updateMessageId(messageSoap.getId(), saved.getId());
			}

		}
	}

	/**
	 *
	 * @param id      wroom id
	 * @param localId local monolith id
	 */
	public void updateMessageId(Long id, Long localId) {
		log.info("Updating microservice entry with " + id + " local: " + localId);
		MessageUpdateRequest request = new MessageUpdateRequest();
		request.setId(id);
		request.setLocalId(localId);
		MessageUpdateResponse response = (MessageUpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		log.info("Updated a message with " + response.getId() + "id and  " + response.getLocalId() + " local id");
	}

}
