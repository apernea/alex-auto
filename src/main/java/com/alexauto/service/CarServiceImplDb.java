package com.alexauto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alexauto.model.Car;

@Service
@Profile("db")
public class CarServiceImplDb implements CarService {

    @Override
    public List<Car> getCars() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCars'");
    }

    @Override
    public List<Car> getAllCarTypes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCarTypes'");
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCarById'");
    }

    @Override
    public Car addCar(Car car) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCar'");
    }

    @Override
    public boolean updateCar(Car car) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCar'");
    }

    @Override
    public boolean deleteCar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCar'");
    }

    @Override
    public List<Car> getCarsByType(String type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCarsByType'");
    }

}
