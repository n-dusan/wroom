package xwsagent.wroomagent.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import xwsagent.wroomagent.domain.Comment;
import xwsagent.wroomagent.repository.CommentRepository;
import xwsagent.wroomagent.soap.clients.CommentsClient;

import java.util.List;

@Component
@Aspect
@Log4j2
public class CommentsAspect {

    private final CommentsClient commentsClient;
    private final CommentRepository commentRepository;

    public CommentsAspect(CommentsClient commentsClient, CommentRepository commentRepository) {
        this.commentsClient = commentsClient;
        this.commentRepository = commentRepository;
    }
    /**
     *  when loading ad-overview component on ui, sync comments (wroom -> monolith)
     */

    @Before("execution(* xwsagent.wroomagent.service.AdService.findAllActiveForUser(..))")
    public void syncComments(JoinPoint joinPoint) throws Throwable {
        log.info("sync=comment, action=started");

        List<Comment> comments = this.commentsClient.syncComments();
        for (Comment comment : comments) {
            this.commentRepository.save(comment);
        }

        log.info("sync=comment, action=complete");
    }

}
