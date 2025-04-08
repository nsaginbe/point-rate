package org.nurgisa.pointrate.controller;

import org.nurgisa.pointrate.model.Point;
import org.nurgisa.pointrate.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping()
    public List<Point> getPoints() {
        return pointService.findAll();
    }
}
