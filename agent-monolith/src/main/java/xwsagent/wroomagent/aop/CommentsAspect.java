package xwsagent.wroomagent.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.repository.CommentRepository;
import xwsagent.wroomagent.service.CommentService;
import xwsagent.wroomagent.soap.clients.CommentsClient;

@Component
@Aspect
@Log4j2
public class CommentsAspect {

    private final CommentsClient commentsClient;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public CommentsAspect(CommentsClient commentsClient, CommentRepository commentRepository, CommentService commentService) {
        this.commentsClient = commentsClient;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    /**
     *  when loading ad-overview component on ui, sync comments (wroom -> monolith)
     */
    @Before("execution(* xwsagent.wroomagent.service.AdService.findAllActiveForUser(..))")
    public void syncComments(JoinPoint joinPoint) throws Throwable {
        log.info("sync=comment, action=started");

        this.commentsClient.syncComments();

        log.info("sync=comment, action=complete");
    }

}
