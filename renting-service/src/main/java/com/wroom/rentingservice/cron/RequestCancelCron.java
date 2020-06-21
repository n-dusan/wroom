package com.wroom.rentingservice.cron;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.service.RentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class RequestCancelCron {

    private final RentsService rentsService;

    public RequestCancelCron(RentsService rentsService) {
        this.rentsService = rentsService;
    }


    //every 12h
    @Scheduled(cron = "0 0 */12 * * *")
    public void cancelPending() {
        log.info("Running 12h CRON");
        List<RentRequest> requestList = rentsService.findAll();

        for (RentRequest rentRequest : requestList) {
            if(rentRequest.getStatus() == RequestStatus.RESERVED) {
                rentsService.decline(rentRequest.getId());
            }
        }
    }



    //every 24h at 00:00
    @Scheduled(cron = "0 0 0 * * *")
    public void cancelAll() {
        log.info("Running 24h CRON");
        List<RentRequest> requestList = rentsService.findAll();

        for (RentRequest rentRequest : requestList) {
            if(rentRequest.getStatus() == RequestStatus.PENDING || rentRequest.getStatus() == RequestStatus.RESERVED) {
                rentsService.decline(rentRequest.getId());
            }
        }
    }
}
