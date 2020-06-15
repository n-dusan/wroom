package com.wroom.rentingservice.cron;

import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.service.RentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
@Log4j2
public class RequestCompleteCron {

    private final RentsService rentsService;

    public RequestCompleteCron(RentsService rentsService) {
        this.rentsService = rentsService;
    }

    //every 24h at 00:00
    @Scheduled(cron = "0 0 0 * * *")
    public void completeRequests() {

        log.info("Running 24h complete cron");
        List<RentRequest> requestList = rentsService.findAll();

        Calendar now = Calendar.getInstance();

        for (RentRequest rentRequest : requestList) {
            if(rentRequest.getStatus() == RequestStatus.PAID && rentRequest.getToDate().before(now.getTime())) {
                rentsService.complete(rentRequest.getId());
            }
        }
    }
}
