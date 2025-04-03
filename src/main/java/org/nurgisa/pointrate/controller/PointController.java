package org.nurgisa.pointrate.controller;

import org.nurgisa.pointrate.model.Point;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/points")
public class PointController {

    @PostMapping("/add")
    public void addPoint(@RequestBody Point point) {

    }
}
