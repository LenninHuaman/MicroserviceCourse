package com.lennuh.user_microservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lennuh.user_microservice.entity.User;
import com.lennuh.user_microservice.model.Bike;
import com.lennuh.user_microservice.model.Car;
import com.lennuh.user_microservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
       List<User> users = userService.getAllUsers(); 
       if (users.isEmpty()) {
           return ResponseEntity.noContent().build();
       }
        return ResponseEntity.ok(users);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") long userId) {
        List<Car> cars = userService.getCars(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") long userId) {
        List<Bike> bikes = userService.getBikes(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackAddCar")
    @PostMapping("/cars/{userId}")
    public ResponseEntity<Car> addCar(@PathVariable("userId") long userId, @RequestBody Car car) {
        Car createdCar = userService.addCar(userId, car);
        return ResponseEntity.ok(createdCar);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackAddBike")
    @PostMapping("/bikes/{userId}")
    public ResponseEntity<Bike> addBike(@PathVariable("userId") long userId, @RequestBody Bike bike) {
        Bike createdBike = userService.addBike(userId, bike);
        return ResponseEntity.ok(createdBike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("{userId}/getall") 
    public ResponseEntity<Map<String, Object>> getUserAndVehicules(@PathVariable("userId") long userId) {
        Map<String, Object> userAndVehicules = userService.getUserAndVehicules(userId);
        if (userAndVehicules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userAndVehicules);
    }

    //Fallback reponse methods    
    private ResponseEntity<List<Car>> fallbackGetCars(@PathVariable("userId") long userId, RuntimeException e) {
        return new ResponseEntity("No cars available", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackGetBikes(@PathVariable("userId") long userId, RuntimeException e) {
        return new ResponseEntity("No bikes available", HttpStatus.OK);
    }
    private ResponseEntity<Car> fallbackAddCar(@PathVariable("userId") long userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("You can not add a car now", HttpStatus.OK);
    }
    private ResponseEntity<Bike> fallbackAddBike(@PathVariable("userId") long userId, @RequestBody Bike bike, RuntimeException e) {
         return new ResponseEntity("You can not add a bike now", HttpStatus.OK);
    }
    private ResponseEntity<Map<String, Object>> fallbackGetAll(@PathVariable("userId") long userId, RuntimeException e) {
        return new ResponseEntity("One or more vehicules are not available", HttpStatus.OK);
    }
}
