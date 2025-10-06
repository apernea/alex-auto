package com.alexauto.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("db")
public class CarServiceImplDb implements CarService {

}
