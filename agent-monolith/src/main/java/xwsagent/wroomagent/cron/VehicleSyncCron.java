package xwsagent.wroomagent.cron;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xwsagent.wroomagent.soap.clients.VehicleClient;

@Component
@Log4j2
public class VehicleSyncCron {

    private VehicleClient vehicleClient;

    public VehicleSyncCron(VehicleClient vehicleClient) {
        this.vehicleClient = vehicleClient;
    }

    @Scheduled(cron = "0 0 */12 * * *")
    public void sync() {
        log.info("action=vehicle-sync-cron status=started");
        this.vehicleClient.sync();
        log.info("action=vehicle-sync-cron status=ended");
    }
}
