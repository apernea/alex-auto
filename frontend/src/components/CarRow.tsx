'use client';
import React from 'react';
import { Car } from '@/utils/types';

interface CarRowProps {
  car: Car;
  onClick: (id: string) => void;
}

const CarRow: React.FC<CarRowProps> = ({ car, onClick }) => {
  return (
    <div 
      onClick={() => onClick(car.id)}
      className="bg-white border-b border-gray-100 hover:bg-gray-50 transition-colors cursor-pointer group flex flex-col md:flex-row items-center p-4 md:p-6 gap-4 md:gap-8"
    >
      {/* Main Image */}
      <div className="w-full md:w-48 h-32 flex-shrink-0 overflow-hidden rounded-lg shadow-sm">
        <img 
          src={car.mainImage} 
          alt={`${car.make} ${car.model}`}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
        />
      </div>

      {/* Brand & Model */}
      <div className="flex-1 min-w-0 text-center md:text-left">
        <div className="flex flex-col">
          <h3 className="text-xl font-bold text-gray-900 truncate">
          <span className="text-xs font-bold text-blue-600 uppercase tracking-wider mb-1">{car.make}</span>
            {car.model}
          </h3>
          <span className="text-sm text-gray-500 md:hidden mt-1">{car.year} • {car.km.toLocaleString()} km</span>
        </div>
      </div>

      {/* Year (Desktop) */}
      <div className="hidden md:block w-24 text-center">
        <span className="text-sm font-semibold text-gray-700">{car.year}</span>
      </div>

      {/* KM (Desktop) */}
      <div className="hidden md:block w-32 text-center">
        <span className="text-sm font-semibold text-gray-700">{car.km.toLocaleString()} km</span>
      </div>

      {/* Type (Desktop) */}
      <div className="hidden md:block w-28 text-center">
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">
          {car.type}
        </span>
      </div>

      {/* Price */}
      <div className="w-full md:w-32 text-center md:text-right">
        <span className="text-xl font-black text-gray-900">
          ${car.price.toLocaleString()}
        </span>
      </div>

      {/* Action */}
      <div className="w-full md:w-24 text-center">
        <button className="text-sm font-bold text-blue-600 hover:text-blue-800 transition-colors">
          View Details
        </button>
      </div>
    </div>
  );
};

export default CarRow;
