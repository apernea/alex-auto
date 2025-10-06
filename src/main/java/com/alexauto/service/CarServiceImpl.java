package com.alexauto.service;
import org.springframework.stereotype.Service;

import java.util.List;
import com.alexauto.model.Car;

@Service
public class CarServiceImpl implements CarService {
    @Override
    public List<Car> getAllCars() {
        // Implementation logic to retrieve all cars
        return List.of(); // Placeholder return
    }

    @Override
    public Car getCarById(Long id) {
        // Implementation logic to retrieve a car by its ID
        return new Car(); // Placeholder return
    }

    @Override
    public Car createCar(Car car) {
        // Implementation logic to create a new car
        return car; // Placeholder return
    }

    @Override
    public Car updateCar(Long id, Car car) {
        // Implementation logic to update an existing car
        return car; // Placeholder return   
    }

    @Override
    public void deleteCar(Long id) {
        // Implementation logic to delete a car by its ID
    }

}
