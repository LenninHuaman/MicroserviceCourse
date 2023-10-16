package com.lennuh.car_microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lennuh.car_microservice.entity.Car;
import com.lennuh.car_microservice.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Car editCar(Car car) {
        Car existingCar = carRepository.findById(car.getId()).orElse(null);
        if (existingCar == null) {
            return null;
        }
        return carRepository.save(existingCar);
    }

    public void deleteCar(long id) {
        carRepository.deleteById(id);
    }

    public List<Car> getCarsByUserId(long userId) {
        return carRepository.findByUserId(userId);
    }
}
