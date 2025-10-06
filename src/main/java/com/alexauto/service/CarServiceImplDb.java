package com.alexauto.service;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alexauto.model.Car;

@Service
@Profile("db")
public class CarServiceImplDb implements CarService {
    @Override
    public List<Car> getAllCars() {
        // Implementation logic to retrieve all cars from the database
        return List.of(); // Placeholder return
    }

    @Override
    public Car getCarById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCarById'");
    }

    @Override
    public Car createCar(Car car) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCar'");
    }

    @Override
    public Car updateCar(Long id, Car car) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCar'");
    }

    @Override
    public void deleteCar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCar'");
    }
}
