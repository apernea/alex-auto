package com.alexauto.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.dto.CarResponseDTO;
import com.alexauto.model.Car;

public interface CarService {
    List<CarResponseDTO> getCars();
    List<String> getAllCarTypes();
    List<String> getAllCarColors();
    CarResponseDTO getCarById(Long id);
    CarResponseDTO addCar(Car car);
    CarResponseDTO updateCar(Long id, Car car);
    CarResponseDTO deleteCar(Long id);
    List<CarResponseDTO> getCarsByType(String type);
    Page<CarResponseDTO> searchCars(CarSearchCriteria criteria, Pageable pageable);
}
