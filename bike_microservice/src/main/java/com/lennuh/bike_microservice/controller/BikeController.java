package com.lennuh.bike_microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lennuh.bike_microservice.entity.Bike;
import com.lennuh.bike_microservice.service.BikeService;

@RestController
@RequestMapping("/bikes")
public class BikeController {
   @Autowired
    private BikeService bikeService;
    
    @GetMapping
    public ResponseEntity<List<Bike>> getAllBikes() {
       List<Bike> bikes = bikeService.getAllBikes(); 
       if (bikes.isEmpty()) {
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok(bikes);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") long id) {
        Bike bike = bikeService.getBikeById(id);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike) {
        Bike createdBike = bikeService.addBike(bike);
        return ResponseEntity.ok(createdBike);
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") long userId) {
        List<Bike> bikes = bikeService.getBikesByUserId(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
}
