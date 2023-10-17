package com.lennuh.user_microservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lennuh.user_microservice.model.Car;

@FeignClient(name = "car_microservice")
public interface CarFeignClient {

    @PostMapping("/cars")
    public Car createCar(Car car);

    @GetMapping("/cars/byuser/{userId}")
    public List<Car> getCarsByUserId(@PathVariable("userId") long userId);    
}
