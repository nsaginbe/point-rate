package org.nurgisa.pointrate.controller;

import org.nurgisa.pointrate.dto.PointDTO;
import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.service.GeocoderService;
import org.nurgisa.pointrate.util.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/geocoder")
public class GeocoderController {

    private final GeocoderService geocoderService;
    private final Mapper mapper;

    public GeocoderController(GeocoderService geocoderService, Mapper mapper) {
        this.geocoderService = geocoderService;
        this.mapper = mapper;
    }

    @GetMapping("/address")
    public ResponseEntity<String> getAddress(@RequestParam("lat") double lat,
                                             @RequestParam("lon") double lon) {

        return ResponseEntity.ok(geocoderService.getAddress(lat, lon));
    }

    @GetMapping("/location")
    public ResponseEntity<PointDTO> getPoint(@RequestParam("address") String address) {

        Point point = geocoderService.getPoint(address);

        PointDTO pointDTO = mapper.convertToPointDTO(point);
        pointDTO.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(pointDTO);
    }
}
