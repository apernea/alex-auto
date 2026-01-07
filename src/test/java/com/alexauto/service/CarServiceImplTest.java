package com.alexauto.service;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.model.Car;
import com.alexauto.utils.CarDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.alexauto.exception.ResourceNotFoundException;

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
        Car foundCar = carService.getCarById(carId);

        // Then
        assertThat(foundCar)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", carId)
                .hasFieldOrPropertyWithValue("make", "Toyota");
    }

    @Test
    @DisplayName("getCarById() should throw when an invalid ID is provided")
    void getCarById_shouldThrow_whenInvalidId() {
        // Given
        Long carId = 999L;

        // Then
        assertThrows(ResourceNotFoundException.class, () -> carService.getCarById(carId));
    }

    @Test
    @DisplayName("addCar() should add a new car and assign an ID")
    void addCar_shouldAddCarAndReturnIt() {
        // Given
        Car newCar = new Car();
        newCar.setMake("TestMake");
        newCar.setModel("TestModel");
        newCar.setYear(2022);
        newCar.setPrice(20000.0);
        newCar.setType("Sedan");

        // When
        Car savedCar = carService.addCar(newCar);

        // Then
        assertThat(savedCar.getId()).isNotNull();
        assertThat(carService.getCars()).hasSize(16);
    }

    @Test
    @DisplayName("updateCar() should update an existing car")
    void updateCar_shouldUpdateExistingCar() {
        // Given
        Car carToUpdate = carService.getCarById(1L);
        carToUpdate.setPrice(99999.0);

        // When
        Car updated = carService.updateCar(1L, carToUpdate);

        // Then
        assertThat(updated).isNotNull();
        assertThat(updated.getPrice()).isEqualTo(99999.0);
        assertThat(carService.getCarById(1L).getPrice()).isEqualTo(99999.0);
    }

    @Test
    @DisplayName("updateCar() should throw when car does not exist")
    void updateCar_shouldThrow_whenCarDoesNotExist() {
        // Given
        Car nonExistentCar = new Car();

        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> carService.updateCar(999L, nonExistentCar));
    }

    @Test
    @DisplayName("deleteCar() should remove car and return the removed car when ID exists")
    void deleteCar_shouldRemoveCar_whenIdExists() {
        // When
        Car removed = carService.deleteCar(1L);

        // Then
        assertThat(removed).isNotNull();
        assertThat(removed.getId()).isEqualTo(1L);
        assertThrows(ResourceNotFoundException.class, () -> carService.getCarById(1L));
        assertThat(carService.getCars()).hasSize(14);
    }

    @Test
    @DisplayName("deleteCar() should throw when ID does not exist")
    void deleteCar_shouldThrow_whenIdDoesNotExist() {
        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> carService.deleteCar(999L));
        assertThat(carService.getCars()).hasSize(15);
    }

    @Test
    @DisplayName("getAllCarTypes() should return distinct types")
    void getAllCarTypes_shouldReturnDistinctTypes() {
        // When
        List<String> types = carService.getAllCarTypes();

        // Then
        assertThat(types).isNotEmpty();
        assertThat(types).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("searchCars() should filter by make")
    void searchCars_shouldFilterByMake() {
        // Given
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setMake("Toyota");
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Car> result = carService.searchCars(criteria, pageable);

        // Then
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent()).allMatch(car -> car.getMake().equalsIgnoreCase("Toyota"));
    }

    @Test
    @DisplayName("searchCars() should handle pagination")
    void searchCars_shouldHandlePagination() {
        // Given
        CarSearchCriteria criteria = new CarSearchCriteria();
        Pageable page1 = PageRequest.of(0, 5);
        Pageable page2 = PageRequest.of(1, 5);

        // When
        Page<Car> result1 = carService.searchCars(criteria, page1);
        Page<Car> result2 = carService.searchCars(criteria, page2);

        // Then
        assertThat(result1.getContent()).hasSize(5);
        assertThat(result2.getContent()).hasSize(5);
        assertThat(result1.getContent()).doesNotContainAnyElementsOf(result2.getContent());
    }
}
