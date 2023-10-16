package com.lennuh.user_microservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lennuh.user_microservice.model.Car;

@FeignClient(name = "car-microservice", url = "http://localhost:8082/cars")
public interface CarFeignClient {

    @PostMapping
    public Car createCar(Car car);

    @GetMapping("/byuser/{userId}")
    public List<Car> getCarsByUserId(@PathVariable("userId") long userId);    
}
