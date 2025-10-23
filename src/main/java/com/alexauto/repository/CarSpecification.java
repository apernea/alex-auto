package com.alexauto.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.alexauto.model.Car;

public class CarSpecification {

    @SuppressWarnings("unused")
    public static Specification<Car> findByCriteria(
            String make, String model,
            Integer minYear, Integer maxYear,
            Double maxPrice,
            String type, String color,
            Integer minKilometers, Integer maxKilometers) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (make != null && !make.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("make")),
                        "%" + make.toLowerCase() + "%"
                ));
            }

            if (model != null && !model.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("model")),
                        "%" + model.toLowerCase() + "%"
                ));
            }

            if (minYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear));
            }

            if (maxYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (type != null && !type.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("type")),
                        type.toLowerCase()
                ));
            }

            if (color != null && !color.isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("color")),
                        color.toLowerCase()
                ));
            }

            if (minKilometers != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("kilometers"), minKilometers));
            }

            if (maxKilometers != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("kilometers"), maxKilometers));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
