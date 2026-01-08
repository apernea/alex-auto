'use client';
import React, { useState } from 'react';
import { Car } from '@/utils/types';

interface CarDetailProps {
  car: Car;
  onBack: () => void;
}

const CarDetail: React.FC<CarDetailProps> = ({ car, onBack }) => {
  const [activeImage, setActiveImage] = useState(car.mainImage);
  const images = [car.mainImage, ...car.additionalImages];

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <button 
        onClick={onBack}
        className="mb-6 flex items-center text-gray-600 hover:text-gray-900 font-medium transition-colors"
      >
        <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
        Back to listings
      </button>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left Column: Images */}
        <div className="lg:col-span-2 space-y-4">
          <div className="aspect-video bg-gray-200 rounded-2xl overflow-hidden shadow-lg border border-gray-100">
            <img src={activeImage} alt="Car highlight" className="w-full h-full object-cover transition-opacity duration-300" />
          </div>
          <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-hide">
            {images.map((img, idx) => (
              <button 
                key={idx}
                onClick={() => setActiveImage(img)}
                className={`flex-shrink-0 w-24 h-24 rounded-lg overflow-hidden border-2 transition-all ${activeImage === img ? 'border-blue-600 opacity-100' : 'border-transparent opacity-60 hover:opacity-100'}`}
              >
                <img src={img} alt={`Thumbnail ${idx}`} className="w-full h-full object-cover" />
              </button>
            ))}
          </div>

          <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 mt-8">
            <h2 className="text-2xl font-bold mb-4">Description</h2>
            <p className="text-gray-600 leading-relaxed text-lg">{car.description}</p>
            
            <div className="mt-8">
              <h2 className="text-2xl font-bold mb-6">Specifications</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4">
                {Object.entries(car.specifications).map(([key, value]) => (
                  <div key={key} className="flex justify-between items-center py-3 border-b border-gray-50">
                    <span className="text-gray-500 capitalize">{key.replace(/([A-Z])/g, ' $1').trim()}</span>
                    <span className="font-semibold text-gray-900">{value}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>

        {/* Right Column: Pricing & Quick Actions */}
        <div className="space-y-6">
          <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 sticky top-8">
            <div className="mb-6">
              <span className="text-gray-500 text-sm uppercase tracking-widest font-bold">{car.make}</span>
              <h1 className="text-4xl font-black text-gray-900 mt-1">{car.model}</h1>
              <div className="flex items-center mt-2 text-gray-600 space-x-3">
                <span>{car.year}</span>
                <span>•</span>
                <span>{car.km.toLocaleString()} km</span>
              </div>
            </div>

            <div className="text-5xl font-black text-blue-600 mb-8">
              ${car.price.toLocaleString()}
            </div>

            <div className="space-y-3">
              <button className="w-full bg-blue-600 text-white font-bold py-4 rounded-xl hover:bg-blue-700 transition-colors shadow-lg shadow-blue-200">
                Inquiry Now
              </button>
              <button className="w-full bg-white text-gray-800 border border-gray-200 font-bold py-4 rounded-xl hover:bg-gray-50 transition-colors">
                Book Test Drive
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CarDetail;
