package com.alexauto.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.alexauto.model.Car;
import com.alexauto.dto.CarSearchCriteria;

public class CarSpecification {

    public static Specification<Car> findByCriteria(CarSearchCriteria criteria) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getMake() != null && !criteria.getMake().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("make")),
                        "%" + criteria.getMake().toLowerCase() + "%"
                ));
            }

            if (criteria.getModel() != null && !criteria.getModel().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("model")),
                        "%" + criteria.getModel().toLowerCase() + "%"
                ));
            }

            if (criteria.getMinYear() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), criteria.getMinYear()));
            }

            if (criteria.getMaxYear() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("year"), criteria.getMaxYear()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("type")),
                        criteria.getType().toLowerCase()
                ));
            }

            if (criteria.getColor() != null && !criteria.getColor().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("color")),
                        criteria.getColor().toLowerCase()
                ));
            }

            if (criteria.getMinKilometers() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("kilometers"), criteria.getMinKilometers()));
            }

            if (criteria.getMaxKilometers() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("kilometers"), criteria.getMaxKilometers()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
