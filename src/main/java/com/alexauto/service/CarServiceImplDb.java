package com.alexauto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.exception.ResourceNotFoundException;
import com.alexauto.model.Car;
import com.alexauto.repository.CarRepo;
import com.alexauto.repository.CarSpecification;

@Service
@Profile("db")
public class CarServiceImplDb implements CarService {
    private final CarRepo carRepository;

    public CarServiceImplDb(CarRepo carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public List<String> getAllCarTypes() {
        return carRepository.findDistinctTypes();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Long id, Car car) {
        if (car == null) {
            throw new IllegalArgumentException("car must not be null");
        }

        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found: " + id);
        }

        car.setId(id);
        return carRepository.save(car);
    }


    @Override
    public Car deleteCar(Long id) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
        carRepository.deleteById(id);
        return existing;
    }

    @Override
    public List<Car> getCarsByType(String type) {
        return carRepository.findByType(type);
    }
    
    @Override
    public Page<Car> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        Specification<Car> spec = CarSpecification.findByCriteria(criteria);

        return carRepository.findAll(spec, pageable);
    }

}