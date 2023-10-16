package com.lennuh.bike_microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lennuh.bike_microservice.entity.Bike;
import com.lennuh.bike_microservice.repository.BikeRepository;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(long id) {
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike addBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    public Bike editBike(Bike bike) {
        Bike existingBike = bikeRepository.findById(bike.getId()).orElse(null);
        if (existingBike == null) {
            return null;
        }
        return bikeRepository.save(existingBike);
    }

    public void deleteBike(long id) {
        bikeRepository.deleteById(id);
    }

    public List<Bike> getBikesByUserId(long userId) {
        return bikeRepository.findByUserId(userId);
    }
}
