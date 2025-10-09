package com.alexauto.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alexauto.model.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.*;

@Component
public class CarDataLoader {
    @Getter
    private static List<Car> cars = new ArrayList<>();

    @PostConstruct
    public void init(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/cars.json");

        if (is == null) {
            throw new RuntimeException("Resource 'cars.json' not found.");
        }

        try {
            cars = mapper.readValue(is, new TypeReference<List<Car>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Error reading 'cars.json'", e);
        }
    }
}
