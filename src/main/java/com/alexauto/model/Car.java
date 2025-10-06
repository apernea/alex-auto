package com.alexauto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long product_id;

    private String make;
    private String model;
    private int year;
    private String color;
    private double price;
    private String description;
    private String image_url;
}
