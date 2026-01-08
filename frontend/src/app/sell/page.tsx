'use client';
import React from 'react';
import { useRouter } from 'next/navigation';
import SellCar from '@/components/SellCar';
import { Car } from '@/utils/types';

type SellCarPayload = Omit<Car, 'id'> & {
  mainImageFile?: File | null;
  additionalImageFiles?: File[];
};

async function uploadImage(file: File): Promise<string> {
  const fd = new FormData();
  fd.append('file', file);

  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/uploads`, {
    method: 'POST',
    body: fd,
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`Image upload failed (${res.status}): ${text}`);
  }

  const data = (await res.json()) as { url: string };
  if (!data?.url) throw new Error('Upload response missing url');

  return data.url;
}

const SellPage: React.FC = () => {
  const router = useRouter();

  const handleAddCar = async (newCar: SellCarPayload) => {
  try {
      let uploadedImageUrl = '';

      if (newCar.mainImageFile) {
        uploadedImageUrl = await uploadImage(newCar.mainImageFile);
      }

      const backendCarPayload = {
        make: newCar.make,
        model: newCar.model,
        year: newCar.year,
        color: 'Unknown',
        price: newCar.price,
        description: newCar.description,
        kilometers: newCar.km,
        imageUrl: uploadedImageUrl || '',  
        type: newCar.type,
        horsepower: newCar.specifications.horsepower,
        fuelType: newCar.specifications.fuelType,
        transmission: newCar.specifications.gearbox,
        engineSize: newCar.specifications.engineSize,
      };

      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/cars`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(backendCarPayload),
      });

      if (!response.ok) {
        const text = await response.text().catch(() => '');
        throw new Error(`Failed to add car (${response.status}): ${text}`);
      }

      router.push('/');
    } catch (error) {
      console.error('Error adding car:', error);
    }
  };


  const handleBack = () => {
    router.back();
  };

  return (
    <div className="min-h-screen bg-[#f8fafc]">
      <SellCar onBack={handleBack} onAddCar={handleAddCar} />
    </div>
  );
};

export default SellPage;
