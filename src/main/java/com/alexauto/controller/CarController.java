package com.alexauto.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alexauto.model.Car;
import com.alexauto.service.CarService;
import com.alexauto.dto.CarSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id){
        Car car = carService.getCarById(id);
        if(car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> allCars = carService.getCars();
        return ResponseEntity.ok(allCars);
    }

    @GetMapping("/cars/types")
    public ResponseEntity<List<String>> getAllCarTypes() {
        List<String> allCarTypes = carService.getAllCarTypes();
        return ResponseEntity.ok(allCarTypes);
    }

    @GetMapping("/cars/search")
    public ResponseEntity<Page<Car>> searchCars(CarSearchCriteria criteria, @RequestParam int page, @RequestParam int size) {
        Page<Car> carsPage = carService.searchCars(criteria, PageRequest.of(page, size));
        return ResponseEntity.ok(carsPage);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@Valid @RequestBody Car car) {
        Car createdCar = carService.addCar(car);
        return ResponseEntity.ok(createdCar);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @Valid @RequestBody Car car) {
        Car newCar = carService.getCarById(id);
        if(newCar != null) {
            carService.deleteCar(newCar.getId());
            carService.addCar(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        Car car = carService.getCarById(id);
        if(car != null) {
            carService.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
