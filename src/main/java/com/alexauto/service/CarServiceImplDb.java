package com.alexauto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.dto.CarResponseDTO;
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
    public List<CarResponseDTO> getCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<String> getAllCarTypes() {
        return carRepository.findDistinctTypes();
    }

    @Override
    public List<String> getAllCarColors() {
        return carRepository.findDistinctColors();
    }

    @Override
    public CarResponseDTO getCarById(Long id) {
        return carRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
    }

    @Override
    public CarResponseDTO addCar(Car car) {
        return convertToDTO(carRepository.save(car));
    }

    @Override
    public CarResponseDTO updateCar(Long id, Car car) {
        if (car == null) {
            throw new IllegalArgumentException("car must not be null");
        }

        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found: " + id);
        }

        car.setId(id);
        return convertToDTO(carRepository.save(car));
    }


    @Override
    public CarResponseDTO deleteCar(Long id) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
        carRepository.deleteById(id);
        return convertToDTO(existing);
    }

    @Override
    public List<CarResponseDTO> getCarsByType(String type) {
        return carRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    @Override
    public Page<CarResponseDTO> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        Specification<Car> spec = CarSpecification.findByCriteria(criteria);

        return carRepository.findAll(spec, pageable).map(this::convertToDTO);
    }

    private CarResponseDTO convertToDTO(Car car) {
        if (car == null) return null;
        CarResponseDTO dto = new CarResponseDTO();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setYear(car.getYear());
        dto.setColor(car.getColor());
        dto.setPrice(car.getPrice());
        dto.setDescription(car.getDescription());
        dto.setKilometers(car.getKilometers());
        dto.setImageUrl(car.getImageUrl());
        dto.setType(car.getType());
        dto.setHorsepower(car.getHorsepower());
        dto.setFuelType(car.getFuelType());
        dto.setTransmission(car.getTransmission());
        dto.setEngineSize(car.getEngineSize());
        return dto;
    }
}