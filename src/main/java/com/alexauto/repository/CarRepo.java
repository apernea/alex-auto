package com.alexauto.repository;

import com.alexauto.model.Car;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    List<Car> findByType(String type);
}
