package com.lennuh.user_microservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lennuh.user_microservice.model.Bike;

@FeignClient(name = "bike-microservice")
public interface BikeFeignClient {

    @PostMapping("/bikes")
    public Bike createBike(Bike bike);
    
    @GetMapping("/bikes/byuser/{userId}")
    public List<Bike> getBikesByUserId(@PathVariable("userId") long userId);    
    
}
