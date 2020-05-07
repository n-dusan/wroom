package xwsagent.wroomagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xwsagent.wroomagent.converter.PriceListConverter;
import xwsagent.wroomagent.dto.PriceListDTO;
import xwsagent.wroomagent.service.PriceListService;

import java.util.List;

@RestController
@RequestMapping(value="api/price-list")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

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
    public ResponseEntity<PriceListDTO> save(@RequestBody PriceListDTO price) {
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
    public ResponseEntity<PriceListDTO> update(@PathVariable("id") Long id, @RequestBody PriceListDTO price) {
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
        return new ResponseEntity<>("Server successfully deleted!", HttpStatus.OK);
    }

}
