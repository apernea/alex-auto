package com.alexauto.service;

import java.util.List;
import java.util.Optional;

import com.alexauto.model.Car;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getAllCarTypes();
    Optional<Car> getCarById(Long id);
    Car addCar(Car car);
    boolean updateCar(Car car);
    boolean deleteCar(Long id);

    /*
     * List<Expense> getExpensesByDate(String date);
    List<Expense> getExpensesByCategoryAndMonth(String category, String month);
    List<String> getAllExpenseCategories();
    Optional<Expense> getExpenseById(Long id);
    Expense addExpense(Expense expense);
    boolean updateExpense(Expense expense);
    boolean deleteExpenseById(Long id);
     */
}
