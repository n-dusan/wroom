package com.wroom.adsservice.controller;

import com.wroom.adsservice.config.EndpointConfig;
import com.wroom.adsservice.converter.PriceListConverter;
import com.wroom.adsservice.domain.dto.PriceListDTO;
import com.wroom.adsservice.jwt.UserPrincipal;
import com.wroom.adsservice.service.PriceListService;
import com.wroom.adsservice.utils.RequestCounter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    @GetMapping(value="/{user_id}/all", produces = "application/json")
    public ResponseEntity<List<PriceListDTO>> getUserAll(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<>(
                PriceListConverter.fromEntityList(priceListService.getUserAll(userId), PriceListConverter::fromEntity),
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