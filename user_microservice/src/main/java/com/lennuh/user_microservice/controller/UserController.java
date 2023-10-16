package com.lennuh.user_microservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") long userId) {
        List<Car> cars = userService.getCars(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") long userId) {
        List<Bike> bikes = userService.getBikes(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/cars/{userId}")
    public ResponseEntity<Car> addCar(@PathVariable("userId") long userId, @RequestBody Car car) {
        Car createdCar = userService.addCar(userId, car);
        return ResponseEntity.ok(createdCar);
    }

    @PostMapping("/bikes/{userId}")
    public ResponseEntity<Bike> addBike(@PathVariable("userId") long userId, @RequestBody Bike bike) {
        Bike createdBike = userService.addBike(userId, bike);
        return ResponseEntity.ok(createdBike);
    }

    @GetMapping("{userId}/getall") 
    public ResponseEntity<Map<String, Object>> getUserAndVehicules(@PathVariable("userId") long userId) {
        Map<String, Object> userAndVehicules = userService.getUserAndVehicules(userId);
        if (userAndVehicules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userAndVehicules);
    }
}
