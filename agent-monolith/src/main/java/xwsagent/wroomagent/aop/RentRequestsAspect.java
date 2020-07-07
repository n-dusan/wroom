package xwsagent.wroomagent.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import xwsagent.wroomagent.soap.clients.RentsClient;

@Component
@Aspect
@Log4j2
public class RentRequestsAspect {

    private final RentsClient rentsClient;

    public RentRequestsAspect(RentsClient rentsClient) {
        this.rentsClient = rentsClient;
    }

    @Before("execution(* xwsagent.wroomagent.service.RentsService.occupyList(..))")
    public void sync(JoinPoint joinPoint) throws Throwable {
        log.info("sync=rents, action=started");

        //sync reports
        this.rentsClient.syncReports();
        //sync bundles
        this.rentsClient.syncBundles();
        //sync requests
        this.rentsClient.syncRents();

        log.info("sync=rents, action=complete");
    }
}
