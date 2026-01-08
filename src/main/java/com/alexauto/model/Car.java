package com.alexauto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Make is mandatory")
    @Size(max = 50, message = "Make cannot exceed 50 characters")
    private String make;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 50, message = "Model cannot exceed 50 characters")
    private String model;

    @Min(value = 1900, message = "Year must be 1900 or later")
    private int year;

    @NotBlank(message = "Color is mandatory")
    private String color;

    @Positive(message = "Price must be positive")
    private double price;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Min(value = 0, message = "Kilometers must be equal or greater than 0")
    private int kilometers;

    private String imageUrl;
    
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Min(value = 1, message = "Horsepower must be at least 1")
    private int horsepower;

    @NotBlank(message = "Fuel type is mandatory")
    private String fuelType;

    @NotBlank(message = "Transmission is mandatory")
    private String transmission;

    @Min(value = 0, message ="Engine size must be at least 0")
    private int engineSize;
}
