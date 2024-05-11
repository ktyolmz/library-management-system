package com.ktyolmz.library.controller;

import com.ktyolmz.library.entity.Location;
import com.ktyolmz.library.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @Transactional
    public Location getLocation(@RequestParam Long bookId){return locationService.getLocation(bookId);}
}
