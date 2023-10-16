package com.lennuh.user_microservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lennuh.user_microservice.entity.User;
import com.lennuh.user_microservice.feignclients.BikeFeignClient;
import com.lennuh.user_microservice.feignclients.CarFeignClient;
import com.lennuh.user_microservice.model.Bike;
import com.lennuh.user_microservice.model.Car;
import com.lennuh.user_microservice.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<Car> getCars(long userId) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8082/cars/byuser/" + userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(long userId) {
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8083/bikes/byuser/" + userId, List.class);
        return bikes;
    }

    public Car addCar(long userId, Car car) {
        car.setUserId(userId);
        Car newCar = carFeignClient.createCar(car);
        return newCar;    
    }

    public Bike addBike(long userId, Bike bike) {
        bike.setUserId(userId);
        Bike newBike = bikeFeignClient.createBike(bike);
        return newBike;    
    }

    public Map<String, Object> getUserAndVehicules(long id) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            result.put("Message", "This user does not exist");
            return null;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCarsByUserId(id);
        if (cars.isEmpty()) {
            result.put("Message", "This user does not have any car");
        } else {
            result.put("Cars", cars);
        }
        List<Bike> bikes = bikeFeignClient.getBikesByUserId(id);
        if (bikes.isEmpty()) {
            result.put("Message", "This user does not have any bike");
        } else {
            result.put("Bikes", bikes);
        }
        return result;
    } 
}
