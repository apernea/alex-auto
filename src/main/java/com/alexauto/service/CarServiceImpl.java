package com.alexauto.service;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.alexauto.model.Car;
import com.alexauto.utils.CarDataLoader;

@Service
@Profile("json")
public class CarServiceImpl implements CarService {

    private static final AtomicLong idCounter = new AtomicLong();

    public CarServiceImpl() {
        List<Car> cars = CarDataLoader.getCars();
        if (!cars.isEmpty()) {
            long maxId = cars.stream()
                .mapToLong(Car::getId)
                .max()
                .orElse(0L);
            idCounter.set(maxId);
        }
    }

    @Override
    public List<Car> getCars() {
        return CarDataLoader.getCars(); // Placeholder return
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        // Implementation logic to retrieve a car by its ID
        return Optional.of(new Car()); // Placeholder return
    }

    @Override
    public Car addCar(Car car) {
        // Implementation logic to create a new car
        return car; // Placeholder return
    }

    @Override
    public boolean updateCar(Car car) {
        // Implementation logic to update an existing car
        return true; // Placeholder return
    }

    @Override
    public boolean deleteCar(Long id) {
        // Implementation logic to delete a car by its ID
        return false; // Placeholder return
    }

    @Override
    public List<Car> getAllCarTypes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCarTypes'");
    }

    @Override
    public List<Car> getCarsByType(String type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCarsByType'");
    }

}
