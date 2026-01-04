// CarSearchCriteria.java
package com.alexauto.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CarSearchCriteria {
    @Size(max = 50, message = "Make cannot exceed 50 characters")
    private String make;

    @Size(max = 50, message = "Model cannot exceed 50 characters")
    private String model;

    @Min(value = 1900, message = "Year must be 1900 or later")
    private Integer minYear;

    @Max(value = 2025, message = "Year must be 2025 or earlier")
    private Integer maxYear;

    @Positive(message = "Price must be positive")
    private Double minPrice;

    @Positive(message = "Price must be positive")
    private Double maxPrice;

    @Size(max = 50, message = "Type cannot exceed 50 characters")
    private String type;

    @Size(max = 50, message = "Color cannot exceed 50 characters")
    private String color;

    @Min(value = 0, message = "Kilometers can't be negative")
    private Integer minKilometers;
    
    @Min(value = 0, message = "Kilometers can't be negative")
    private Integer maxKilometers;
}
