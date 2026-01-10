package com.alexauto.dto;

import lombok.Data;

@Data
public class CarResponseDTO {
    private Long id;
    private String make;
    private String model;
    private int year;
    private String color;
    private double price;
    private String description;
    private int kilometers;
    private String imageUrl;
    private String type;
    private int horsepower;
    private String fuelType;
    private String transmission;
    private int engineSize;
}