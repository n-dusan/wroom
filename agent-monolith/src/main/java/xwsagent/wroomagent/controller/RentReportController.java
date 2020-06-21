package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.RentReportConverter;
import xwsagent.wroomagent.domain.dto.RentReportDTO;
import xwsagent.wroomagent.service.RentReportService;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConfig.REPORT_BASE_URL)
@Log4j2
public class RentReportController {

    private final RentReportService rentReportService;

    public RentReportController(RentReportService rentReportService) {
        this.rentReportService = rentReportService;
    }


    @PostMapping
    public ResponseEntity<RentReportDTO> create(@RequestBody RentReportDTO rentReportDTO) {
        System.out.println("What did i get" + rentReportDTO);
        System.out.println("note" + rentReportDTO.getNote());

        return new ResponseEntity<>(RentReportConverter.fromEntity(rentReportService.save(rentReportDTO)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RentReportDTO>> get() {
        return new ResponseEntity<>(RentReportConverter.fromEntityList(rentReportService.findAll(), RentReportConverter::fromEntity),
                HttpStatus.OK);

    }

    @GetMapping("/chart/{vehicle_id}")
    public ResponseEntity<List<RentReportDTO>> getReportsForVehicle(@PathVariable("vehicle_id") Long id) {
        return new ResponseEntity<>(RentReportConverter.fromEntityList(rentReportService.getReportsForVehicle(id), RentReportConverter::fromEntity),
                HttpStatus.OK);
    }


}
