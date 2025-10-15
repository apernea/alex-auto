package com.alexauto.repository;

import com.alexauto.model.Car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    List<Car> findByType(String type);
}
