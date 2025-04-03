package org.nurgisa.pointrate.controller;

import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.exception.AddressNotFoundException;
import org.nurgisa.pointrate.service.GeocoderService;
import org.nurgisa.pointrate.util.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/geocoder")
public class GeocoderController {

    private final GeocoderService geocoderService;

    public GeocoderController(GeocoderService geocoderService) {
        this.geocoderService = geocoderService;
    }

    @GetMapping("/address")
    public ResponseEntity<String> getAddress(@RequestParam("lat") double lat,
                                             @RequestParam("lon") double lon) {

        return ResponseEntity.ok(geocoderService.getAddress(lat, lon));
    }

    @GetMapping("/location")
    public ResponseEntity<PointDTO> getPoint(@RequestParam("address") String address) {

        PointDTO pointDTO = geocoderService.getPoint(address);
        pointDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(pointDTO);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(AddressNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return ResponseEntity.badRequest()
                .body(response);
    }
}
