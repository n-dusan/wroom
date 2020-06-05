package xwsagent.wroomagent.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xwsagent.wroomagent.config.EndpointConfig;
import xwsagent.wroomagent.converter.PriceListConverter;
import xwsagent.wroomagent.domain.dto.PriceListDTO;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.service.PriceListService;
import xwsagent.wroomagent.util.RequestCounter;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value = EndpointConfig.PRICE_LIST_BASE_URL)
@Log4j2
public class PriceListController {

    private static final String LOG_CREATE = "action=create user=%s times=%s";
    private static final String LOG_UPDATE = "action=update user=%s times=%s";

    private final PriceListService priceListService;
    private final RequestCounter requestCounter;

    public PriceListController(PriceListService priceListService, RequestCounter requestCounter) {
        this.priceListService = priceListService;
        this.requestCounter = requestCounter;
    }

    /**
     * GET /api/price-list
     * @return all price lists
     */
    @GetMapping
    public ResponseEntity<List<PriceListDTO>> getAllActive() {
        return new ResponseEntity<>(
                PriceListConverter.fromEntityList(priceListService.getAllActive(), PriceListConverter::fromEntity),
                HttpStatus.OK
        );
    }

    /**
     * GET /api/price-list/all
     * @return all price-lists, even deleted
     */
    @GetMapping(value="/all", produces = "application/json")
    public ResponseEntity<List<PriceListDTO>> getAll() {
        return new ResponseEntity<>(
                PriceListConverter.fromEntityList(priceListService.getAll(), PriceListConverter::fromEntity),
                HttpStatus.OK
        );
    }

    /**
     * GET /api/price-list/{id}
     * @param id
     * @return concrete price-list
     */

    @GetMapping(value="/{id}")
    public ResponseEntity<PriceListDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                PriceListConverter.fromEntity(priceListService.findById(id)),
                HttpStatus.OK
        );
    }

    /**
     * POST /api/price-list
     * @param price: dto
     * @return created price
     */
    @PostMapping
    public ResponseEntity<PriceListDTO> save(@Valid @RequestBody PriceListDTO price, Authentication auth) {
        String logContent = String.format(LOG_CREATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.PRICE_LIST_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                PriceListConverter.fromEntity(priceListService.save(PriceListConverter.toEntity(price))),
                HttpStatus.OK
        );
    }

    /**
     * PUT /api/price-list/{id}
     * @param id
     * @param price  dto with update information
     * @return edited price-list
     */
    @PutMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<PriceListDTO> update(@PathVariable("id") Long id, @Valid @RequestBody PriceListDTO price, Authentication auth) {
        String logContent = String.format(LOG_UPDATE, ((UserPrincipal) auth.getPrincipal()).getUsername(), requestCounter.get(EndpointConfig.PRICE_LIST_BASE_URL));
        log.info(logContent);
        return new ResponseEntity<>(
                PriceListConverter.fromEntity(priceListService.update(id, price)),
                HttpStatus.OK
        );
    }

    /**
     * DELETE /api/price-list/id
     * @param id
     * @return text
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable Long id) {
        priceListService.delete(id);
        return new ResponseEntity<>("Price list successfully deleted!", HttpStatus.OK);
    }

}
