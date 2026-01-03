package com.alexauto.service;

import com.alexauto.model.Car;
import com.alexauto.utils.CarDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CarServiceImplTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        // Initialize the data loader to populate the in-memory data source
        CarDataLoader dataLoader = new CarDataLoader();
        dataLoader.init();

        // We instantiate the service directly, as it has no external dependencies
        // that need to be mocked for these unit tests.
        carService = new CarServiceImpl();
    }

    @Test
    @DisplayName("getCars() should return the complete list of cars from the data source")
    void getCars_shouldReturnAllCars() {
        // When
        List<Car> cars = carService.getCars();

        // Then
        assertThat(cars)
                .isNotNull()
                .hasSize(15);
    }

    @Test
    @DisplayName("getCarById() should return the car when a valid ID is provided")
    void getCarById_shouldReturnCar_whenValidId() {
        // Given
        Long carId = 1L;

        // When
        Optional<Car> foundCar = carService.getCarById(carId);

        // Then
        assertThat(foundCar)
                .isPresent()
                .hasValueSatisfying(car -> {
                    assertThat(car.getId()).isEqualTo(carId);
                    assertThat(car.getMake()).isEqualTo("Toyota");
                });
    }

    @Test
    @DisplayName("getCarById() should return empty when an invalid ID is provided")
    void getCarById_shouldReturnEmpty_whenInvalidId() {
        // Given
        Long carId = 999L;

        // When
        Optional<Car> foundCar = carService.getCarById(carId);

        // Then
        assertThat(foundCar).isNotPresent();
    }
}
