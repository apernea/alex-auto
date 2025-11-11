package com.alexauto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alexauto.dto.CarSearchCriteria;
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
        return carRepository.findAll().stream()
                .map(Car::getType)
                .distinct()
                .toList();
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public boolean updateCar(Car car) {
        if(carRepository.existsById(car.getId())){
            carRepository.save(car);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(Long id) {
        if(carRepository.existsById(id)){
            carRepository.deleteById(id);
            return true;
        }
        return false;
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