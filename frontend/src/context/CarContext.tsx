'use client';
import React, { createContext, useContext, useState } from 'react';
import { Car } from '@/utils/types';

const CarContext = createContext<{
  cars: Car[];
  addCar: (car: Car) => void;
}>({ cars: [], addCar: () => {} });

export const CarProvider = ({ children }: { children: React.ReactNode }) => {
  const [cars, setCars] = useState<Car[]>([]);
  const addCar = (newCar: Car) => setCars((prev) => [newCar, ...prev]);

  return (
    <CarContext.Provider value={{ cars, addCar }}>
      {children}
    </CarContext.Provider>
  );
};

export const useCars = () => useContext(CarContext);