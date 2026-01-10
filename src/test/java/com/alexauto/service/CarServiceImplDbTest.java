package com.alexauto.service;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.dto.CarResponseDTO;
import com.alexauto.model.Car;
import com.alexauto.repository.CarRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.alexauto.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class CarServiceImplDbTest {

    @Mock
    private CarRepo carRepository;

    @InjectMocks
    private CarServiceImplDb carService;

    @Test
    @DisplayName("getCars() should return list from repository")
    void getCars_shouldReturnList() {
        // Given
        Car car = new Car();
        when(carRepository.findAll()).thenReturn(List.of(car));

        // When
        List<CarResponseDTO> result = carService.getCars();

        // Then
        assertThat(result).hasSize(1);
        verify(carRepository).findAll();
    }

    @Test
    @DisplayName("getCarById() should return car when found")
    void getCarById_shouldReturnCar() {
        // Given
        Long id = 1L;
        Car car = new Car();
        car.setId(id);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        // When
        CarResponseDTO result = carService.getCarById(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("getCarById() should return empty when not found")
    void getCarById_shouldReturnEmpty_whenNotFound() {
        // Given
        Long id = 999L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());
        
        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> carService.getCarById(id));
        verify(carRepository).findById(id);
    }

    @Test
    @DisplayName("addCar() should save and return car")
    void addCar_shouldSaveCar() {
        // Given
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        // When
        CarResponseDTO result = carService.addCar(car);

        // Then
        assertThat(result.getId()).isEqualTo(car.getId());
        verify(carRepository).save(car);
    }

    @Test
    @DisplayName("updateCar() should return true if car exists")
    void updateCar_shouldReturnTrue_whenExists() {
        // Given
        Long id = 1L;
        Car car = new Car();
        car.setId(id);
        when(carRepository.existsById(id)).thenReturn(true);
        when(carRepository.save(car)).thenReturn(car);

        // When
        CarResponseDTO result = carService.updateCar(id, car);

        // Then
        assertThat(result.getId()).isEqualTo(id);
        verify(carRepository).save(car);
    }

    @Test
    @DisplayName("updateCar() should return false when car doesn't exist")
    void updateCar_shouldReturnFalse_whenIdIsNull() {
        // Given
        Car car = new Car();
        // id is null
        car.setId(null);
        // When / Then - attempting to update with a null id should throw ResourceNotFoundException
        when(carRepository.existsById(null)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> carService.updateCar(null, car));
        verify(carRepository).existsById(null);
        verify(carRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteCar() should return true if car exists")
    void deleteCar_shouldReturnTrue_whenExists() {
        // Given
        Long id = 1L;
        Car car = new Car();
        car.setId(id);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        // When
        CarResponseDTO result = carService.deleteCar(id);

        // Then
        assertThat(result.getId()).isEqualTo(id);
        verify(carRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteCar() should return false if car does not exist")
    void deleteCar_shouldReturnFalse_whenNotExists() {
        // Given
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> carService.deleteCar(id));
        verify(carRepository).findById(id);
        verify(carRepository, never()).deleteById(any());
        verifyNoMoreInteractions(carRepository);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("searchCars() should call repository with specification")
    void searchCars_shouldCallRepository() {
        // Given
        CarSearchCriteria criteria = new CarSearchCriteria();
        Pageable pageable = Pageable.unpaged();
        Page<Car> page = new PageImpl<>(Collections.emptyList());
        
        when(carRepository.findAll((Specification<Car>) any(Specification.class), eq(pageable))).thenReturn(page);

        // When
        Page<CarResponseDTO> result = carService.searchCars(criteria, pageable);

        // Then
        assertThat(result).isNotNull();
        verify(carRepository).findAll((Specification<Car>) any(Specification.class), eq(pageable));
    }
}
