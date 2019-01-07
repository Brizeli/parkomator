package com.next.parkomator.controller;

import com.next.parkomator.model.SensorData;
import com.next.parkomator.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class RootController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/nearby/{radius}/{latlong}")
    public Resources<SensorData> getNearbyLocations(@PathVariable Integer radius, @PathVariable String latlong) {
        double[] ll = Arrays.stream(latlong.split("[^\\w.]+")).mapToDouble(Double::parseDouble).toArray();
        return new Resources<>(locationService.getNearbyLocations(ll[0], ll[1], radius));
    }
}
