package com.alexauto.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alexauto.model.Car;
import com.alexauto.service.CarService;
import com.alexauto.dto.CarResponseDTO;
import com.alexauto.dto.CarSearchCriteria;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = "${app.cors.origins:http://localhost:3000}")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable Long id){
        CarResponseDTO car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDTO>> getAllCars() {
        List<CarResponseDTO> allCars = carService.getCars();
        return ResponseEntity.ok(allCars);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllCarTypes() {
        List<String> allCarTypes = carService.getAllCarTypes();
        return ResponseEntity.ok(allCarTypes);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAllCarColors() {
        List<String> allCarColors = carService.getAllCarColors();
        return ResponseEntity.ok(allCarColors);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CarResponseDTO>> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        Page<CarResponseDTO> carsPage = carService.searchCars(criteria, pageable);
        return ResponseEntity.ok(carsPage);
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> addCar(@Valid @RequestBody Car car) {
        CarResponseDTO createdCar = carService.addCar(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        CarResponseDTO updatedCar = carService.updateCar(id, car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
