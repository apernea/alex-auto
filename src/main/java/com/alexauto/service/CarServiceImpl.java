package com.alexauto.service;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.alexauto.dto.CarSearchCriteria;
import com.alexauto.dto.CarResponseDTO;
import com.alexauto.model.Car;
import com.alexauto.utils.CarDataLoader;
import com.alexauto.exception.ResourceNotFoundException;

@Service
@Profile("json")
public class CarServiceImpl implements CarService {

    private static final AtomicLong idCounter = new AtomicLong();

    public CarServiceImpl() {
        List<Car> cars = CarDataLoader.getCars();
        if (cars != null && !cars.isEmpty()) {
            long maxId = cars.stream()
                .mapToLong(Car::getId)
                .max()
                .orElse(0L);
            idCounter.set(maxId);
        }
    }

    @Override
    public List<CarResponseDTO> getCars() {
        List<Car> cars = CarDataLoader.getCars();
        return (cars != null ? cars : List.<Car>of()).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public CarResponseDTO getCarById(Long id) {
        return CarDataLoader.getCars().stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Car not found: " + id));
    }

    @Override
    public CarResponseDTO addCar(Car car) {
        car.setId(idCounter.incrementAndGet());
        CarDataLoader.getCars().add(car);
        return convertToDTO(car);
    }

    @Override
    public CarResponseDTO updateCar(Long id, Car car) {
        if (car == null) {
            throw new IllegalArgumentException("car must not be null");
        }

        List<Car> cars = CarDataLoader.getCars();
        if (cars == null || cars.isEmpty()) {
            throw new ResourceNotFoundException("Car not found: " + id);
        }

        car.setId(id);

        for (int i = 0; i < cars.size(); i++) {
            Car existing = cars.get(i);
            if (existing != null && id.equals(existing.getId())) {
                synchronized (cars) {
                    cars.set(i, car);
                }
                return convertToDTO(car);
            }
        }

        throw new ResourceNotFoundException("Car not found: " + id);
    }

    @Override
    public CarResponseDTO deleteCar(Long id) {
        CarResponseDTO existingCar = getCarById(id); // throws if not found
        List<Car> cars = CarDataLoader.getCars();
        if (cars != null) {
            synchronized (cars) {
                cars.removeIf(c -> c != null && id.equals(c.getId()));
            }
        }
        return existingCar;
    }

    @Override
    public List<String> getAllCarTypes() {
        return CarDataLoader.getCars().stream()
                .map(Car::getType)
                .distinct()
                .toList();
    }

    @Override
    public List<CarResponseDTO> getCarsByType(String type) {
        return CarDataLoader.getCars().stream()
                .filter(car -> car.getType().equals(type))
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Page<CarResponseDTO> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        List<Car> allCars = CarDataLoader.getCars();

        List<Car> filteredCars = allCars.stream()
                .filter(car -> {
                    if (criteria.getMake() != null && !criteria.getMake().isEmpty()) {
                        return car.getMake().toLowerCase().contains(criteria.getMake().toLowerCase());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getModel() != null && !criteria.getModel().isEmpty()) {
                        return car.getModel().toLowerCase().contains(criteria.getModel().toLowerCase());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMinYear() != null) {
                        return car.getYear() >= criteria.getMinYear();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxYear() != null) {
                        return car.getYear() <= criteria.getMaxYear();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxPrice() != null) {
                        return car.getPrice() <= criteria.getMaxPrice();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                        return car.getType().equalsIgnoreCase(criteria.getType());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getColor() != null && !criteria.getColor().isEmpty()) {
                        return car.getColor().equalsIgnoreCase(criteria.getColor());
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMinKilometers() != null) {
                        return car.getKilometers() >= criteria.getMinKilometers();
                    }
                    return true;
                })
                .filter(car -> {
                    if (criteria.getMaxKilometers() != null) {
                        return car.getKilometers() <= criteria.getMaxKilometers();
                    }
                    return true;
                })
                .collect(Collectors.toList()); 

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredCars.size());

        List<CarResponseDTO> pageContent = List.of(); 
        if (start <= end) {
            pageContent = filteredCars.subList(start, end).stream()
                    .map(this::convertToDTO)
                    .toList();
        }

        return new PageImpl<>(pageContent, pageable, filteredCars.size());
    }

    @Override
    public List<String> getAllCarColors() {
        return CarDataLoader.getCars().stream()
                .map(Car::getColor)
                .distinct()
                .toList();
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