'use client';
import React from 'react';
import { Car } from '@/utils/types';

interface CarCardProps {
  car: Car;
  onClick: (id: string) => void;
}

const CarCard: React.FC<CarCardProps> = ({ car, onClick }) => {
  return (
    <div 
      onClick={() => onClick(car.id)}
      className="bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow cursor-pointer overflow-hidden border border-gray-100 group"
    >
      <div className="relative h-48 overflow-hidden">
        <img 
          src={car.mainImage} 
          alt={`${car.brand} ${car.model}`}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
        />
        <div className="absolute top-3 left-3 bg-white/90 backdrop-blur-sm px-2 py-1 rounded text-xs font-bold uppercase tracking-wider text-gray-800">
          {car.type}
        </div>
      </div>
      
      <div className="p-4">
        <div className="flex justify-between items-start mb-1">
          <h3 className="font-bold text-lg text-gray-900 leading-tight">
            {car.brand} {car.model}
          </h3>
          <span className="text-blue-600 font-bold text-lg">
            ${car.price.toLocaleString()}
          </span>
        </div>
        
        <p className="text-gray-500 text-sm mb-4">
          {car.year} • {car.km.toLocaleString()} km
        </p>

        <div className="flex items-center justify-between pt-4 border-t border-gray-50">
          <div className="flex space-x-2">
            <span className="text-[10px] bg-gray-100 px-2 py-1 rounded text-gray-600">{car.specifications.horsepower} HP</span>
            <span className="text-[10px] bg-gray-100 px-2 py-1 rounded text-gray-600">{car.specifications.gearbox}</span>
          </div>
          <button className="text-xs font-semibold text-blue-600 hover:underline">
            View Details →
          </button>
        </div>
      </div>
    </div>
  );
};

export default CarCard;
