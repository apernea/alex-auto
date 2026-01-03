package com.alexauto.service;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.alexauto.dto.CarSearchCriteria;
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
            List<Car> cars = CarDataLoader.getCars();
            int index = cars.indexOf(existingCar.get());
            cars.set(index, car);
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

    @Override
    public Page<Car> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        List<Car> allCars = CarDataLoader.getCars();

        List<Car> filteredCars = allCars.stream()
                .filter(car -> {
                    if (criteria.getMake() != null && !criteria.getMake().isEmpty()) {
                        return car.getMake().toLowerCase().contains(criteria.getMake().toLowerCase());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getModel() != null && !criteria.getModel().isEmpty()) {
                        return car.getModel().toLowerCase().contains(criteria.getModel().toLowerCase());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMinYear() != null) {
                        return car.getYear() >= criteria.getMinYear();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxYear() != null) {
                        return car.getYear() <= criteria.getMaxYear();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxPrice() != null) {
                        return car.getPrice() <= criteria.getMaxPrice();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                        return car.getType().equalsIgnoreCase(criteria.getType());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getColor() != null && !criteria.getColor().isEmpty()) {
                        return car.getColor().equalsIgnoreCase(criteria.getColor());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMinKilometers() != null) {
                        return car.getKilometers() >= criteria.getMinKilometers();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxKilometers() != null) {
                        return car.getKilometers() <= criteria.getMaxKilometers();
                    }
                    return true;
                })
                .collect(Collectors.toList()); 

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredCars.size());

        List<Car> pageContent = List.of(); 
        if (start <= end) {
            pageContent = filteredCars.subList(start, end);
        }

        return new PageImpl<>(pageContent, pageable, filteredCars.size());
    }

}