package com.alexauto.service;

import java.util.List;
import java.util.Optional;

import com.alexauto.model.Car;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getAllCarTypes();
    Optional<Car> getCarById(Long id);
    Car addCar(Car car);
    boolean updateCar(Car car);
    boolean deleteCar(Long id);
}
