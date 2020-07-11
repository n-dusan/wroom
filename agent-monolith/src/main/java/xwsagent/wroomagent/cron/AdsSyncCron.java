package xwsagent.wroomagent.cron;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xwsagent.wroomagent.soap.clients.AdsClient;

@Component
@Log4j2
public class AdsSyncCron {

    private final AdsClient adsClient;

    public AdsSyncCron(AdsClient adsClient) {
        this.adsClient = adsClient;
    }

    @Scheduled(cron = "0 0 */12 * * *")
    public void sync() {
        log.info("action=ads-sync-cron status=started");
        this.adsClient.sync();
        log.info("action=ads-sync-cron status=ended");
    }
}
