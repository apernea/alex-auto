'use client';

import React, { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import CarDetail from '@/components/CarDetail';
import { Car, CarType } from '@/utils/types';

// Shape of the car object coming from the backend
interface BackendCar {
  id: number;
  make: string;
  model: string;
  year: number;
  color: string;
  price: number;
  description: string;
  kilometers: number;
  imageUrl: string;
  type: string;
  horsepower: number;
  fuelType: string;
  transmission: string;
  engineSize: number;
}

// Mapper function to transform backend car to frontend object
const mapBackendCarToFrontend = (backendCar: BackendCar): Car => ({
  id: backendCar.id.toString(),
  make: backendCar.make,
  model: backendCar.model,
  year: backendCar.year,
  km: backendCar.kilometers,
  price: backendCar.price,
  type: backendCar.type as CarType,
  mainImage: backendCar.imageUrl
  ? (backendCar.imageUrl.startsWith('http')
      ? backendCar.imageUrl
      : `${process.env.NEXT_PUBLIC_API_URL}${backendCar.imageUrl}`)
  : '',
  additionalImages: [], // Not available from backend
  description: backendCar.description,
  specifications: {
    engineSize: backendCar.engineSize,
    horsepower: backendCar.horsepower,
    fuelType: backendCar.fuelType,
    gearbox: backendCar.transmission as 'Auto' | 'Manual',
  },
});

export default function CarDetailPage() {
  const { id } = useParams();
  const router = useRouter();
  const [car, setCar] = useState<Car | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);

  useEffect(() => {
    if (!id) return;

    const fetchCar = async () => {
      try {
        setLoading(true);
        const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/v1/cars/${id}`);
        if (!response.ok) {
          throw new Error('Car not found');
        }
        const backendCar: BackendCar = await response.json();
        const frontendCar = mapBackendCarToFrontend(backendCar);
        setCar(frontendCar);
      } catch (err) {
        setError(true);
      } finally {
        setLoading(false);
      }
    };

    fetchCar();
  }, [id]);

  if (loading) {
    return <div className="p-20 text-center text-lg font-semibold">Loading...</div>;
  }
  
  if (error || !car) {
    return <div className="p-20 text-center text-lg font-semibold text-red-600">Car not found or an error occurred.</div>;
  }

  return (
    <div className="bg-white min-h-screen">
      <CarDetail car={car} onBack={() => router.back()} />
    </div>
  );
}
