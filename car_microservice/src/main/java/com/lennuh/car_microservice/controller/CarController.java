package com.lennuh.car_microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lennuh.car_microservice.entity.Car;
import com.lennuh.car_microservice.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarController {
   @Autowired
    private CarService carService;
    
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
       List<Car> cars = carService.getAllCars(); 
       if (cars.isEmpty()) {
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok(cars);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") long id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createdCar = carService.addCar(car);
        return ResponseEntity.ok(createdCar);
    }

    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") long userId) {
        List<Car> cars = carService.getCarsByUserId(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
}
