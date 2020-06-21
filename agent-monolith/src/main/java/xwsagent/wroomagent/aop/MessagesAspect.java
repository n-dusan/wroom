package xwsagent.wroomagent.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import xwsagent.wroomagent.domain.Comment;

import java.util.List;

@Component
@Aspect
@Log4j2
public class MessagesAspect {


    /**
     *  when loading inbox component on ui, sync messages (wroom -> monolith)
     */
    @Before("execution(* xwsagent.wroomagent.service.MessageService.getReceived(..))")
    public void syncComments(JoinPoint joinPoint) throws Throwable {
        log.info("sync=messages, action=started");



        log.info("sync=messages, action=complete");
    }
}
