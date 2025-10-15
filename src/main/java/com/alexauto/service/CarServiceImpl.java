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
        return CarDataLoader.getCars();
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return CarDataLoader.getCars().stream()
            .filter(c -> c.getId().equals(id))
            .findFirst();
    }

    @Override
    public Car addCar(Car car) {
        car.setId(idCounter.incrementAndGet());
        CarDataLoader.getCars().add(car);
        return car;
    }

    @Override
    public boolean updateCar(Car car) {
        Optional<Car> existingCar = getCarById(car.getId());
        if (existingCar.isPresent()) {
            CarDataLoader.getCars().remove(existingCar.get());
            CarDataLoader.getCars().add(car);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(Long id) {
        Optional<Car> existingCar = getCarById(id);
        if (existingCar.isPresent()) {
            CarDataLoader.getCars().remove(existingCar.get());
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAllCarTypes() {
        return CarDataLoader.getCars().stream()
                .map(Car::getType)
                .distinct()
                .toList();
    }

    @Override
    public List<Car> getCarsByType(String type) {
        return CarDataLoader.getCars().stream()
                .filter(car -> car.getType().equals(type))
                .toList();
    }

}