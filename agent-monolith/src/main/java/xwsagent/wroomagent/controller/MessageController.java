package xwsagent.wroomagent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.MessageConverter;
import xwsagent.wroomagent.domain.dto.MessageDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.MessageService;
import xwsagent.wroomagent.util.RequestCounter;

@RestController
@RequestMapping(value = EndpointConfig.MESSAGES_BASE_URL)
@Log4j2
public class MessageController {

	private static final String LOG_SEND_MESSAGE = "action=send_message user=%s times=%s ";
	private static final String LOG_GET_RECEIVED = "action=get_received user=%s times=%s ";
	private static final String LOG_GET_SENT = "action=get_sent user=%s times=%s ";

	private final RequestCounter requestCounter;
	private final MessageService messageService;

	public MessageController(RequestCounter requestCounter, MessageService messageService) {
		super();
		this.requestCounter = requestCounter;
		this.messageService = messageService;
	}

	@PreAuthorize("hasAuthority('ROLE_CHATTING_USER')")
	@PostMapping
	public ResponseEntity<?> send(@RequestBody MessageDTO dto, Authentication auth) {
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
		String logContent = String.format(LOG_SEND_MESSAGE, principal.getUsername(),
				requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(MessageConverter.fromEntity(this.messageService.send(dto, principal.getUsername(), principal.getId())),
				HttpStatus.CREATED);
	}

	@GetMapping(value = "/received")
	public ResponseEntity<?> received(Authentication auth) {
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
		String logContent = String.format(LOG_GET_RECEIVED, principal.getUsername(),
				requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(MessageConverter.fromEntityList(this.messageService.getReceived(principal.getUsername()),
				MessageConverter::fromEntity), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/sent")
	public ResponseEntity<?> sent(Authentication auth) {
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
		String logContent = String.format(LOG_GET_SENT, principal.getUsername(),
				requestCounter.get(EndpointConfig.RENT_BASE_URL));
		log.info(logContent);
		return new ResponseEntity<>(MessageConverter.fromEntityList(this.messageService.getSent(principal.getUsername()),
				MessageConverter::fromEntity), HttpStatus.CREATED);
	}
	
}
