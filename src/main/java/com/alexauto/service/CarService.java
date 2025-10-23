package com.alexauto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.alexauto.model.Car;

public interface CarService {
    List<Car> getCars();
    List<String> getAllCarTypes();
    Optional<Car> getCarById(Long id);
    Car addCar(Car car);
    boolean updateCar(Car car);
    boolean deleteCar(Long id);
    List<Car> getCarsByType(String type);
    Page<Car> searchCars(String make, String model, Integer minYear, Integer maxYear,
                         Double maxPrice, String type, String color,
                         Integer minKilometers, Integer maxKilometers, // <-- ADD
                         Pageable pageable);
}
