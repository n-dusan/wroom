package xwsagent.wroomagent.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.soap.clients.MessagesClient;

@Component
@Aspect
@Log4j2
public class MessagesAspect {

	private final MessagesClient messagesClient;
	
    public MessagesAspect(MessagesClient messagesClient) {
		super();
		this.messagesClient = messagesClient;
	}



	/**
     *  when loading inbox component on ui, sync messages (wroom -> monolith)
     */
    @Before("execution(* xwsagent.wroomagent.service.MessageService.getReceived(..))")
    public void syncComments(JoinPoint joinPoint) throws Throwable {
        log.info("sync=messages, action=started");

        this.messagesClient.syncMessages();

        log.info("sync=messages, action=complete");
    }
}
