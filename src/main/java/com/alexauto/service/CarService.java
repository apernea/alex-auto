package com.alexauto.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.model.Car;

public interface CarService {
    List<Car> getCars();
    List<String> getAllCarTypes();
    Car getCarById(Long id);
    Car addCar(Car car);
    boolean updateCar(Car car);
    boolean deleteCar(Long id);
    List<Car> getCarsByType(String type);
    Page<Car> searchCars(CarSearchCriteria criteria, Pageable pageable);
}
