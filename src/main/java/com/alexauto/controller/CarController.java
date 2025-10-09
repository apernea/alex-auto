package com.alexauto.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alexauto.model.Car;
import com.alexauto.service.CarService;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/car/id/{id}")
    public ResponseEntity<Optional<Car>> getCarById(@PathVariable Long id){
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @GetMapping("/car/all")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> allCars = carService.getCars();

        return ResponseEntity.ok(allCars);
    }

    @GetMapping("/car/types")
    public ResponseEntity<List<Car>> getAllCarTypes() {
        List<Car> allCarTypes = carService.getAllCarTypes();
        return ResponseEntity.ok(allCarTypes);
    }

    @PostMapping("/car/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car createdCar = carService.addCar(car);
        return ResponseEntity.ok(createdCar);
    }

    @PutMapping("/car/update/id/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        Optional<Car> newCar = carService.getCarById(id);
        if(newCar.isPresent()) {
            carService.deleteCar(newCar.get().getId());
            carService.addCar(car);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/car/delete/id/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        if(car.isPresent()) {
            carService.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
