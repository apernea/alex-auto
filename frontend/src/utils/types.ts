
export enum CarType {
  SEDAN = 'Sedan',
  SUV = 'SUV',
  COUPE = 'Coupe',
  HATCHBACK = 'Hatchback',
  TRUCK = 'Truck',
  CONVERTIBLE = 'Convertible'
}

export interface Car {
  id: string;
  make: string;
  model: string;
  year: number;
  km: number;
  price: number;
  type: CarType;
  mainImage: string;
  additionalImages: string[];
  description: string;
  specifications: {
    engineSize: number;
    horsepower: number;
    fuelType: string;
    gearbox: 'Auto' | 'Manual';
  };
}

export interface FilterState {
  make: string;
  model: string;
  yearMin: string;
  yearMax: string;
  maxKilometers: string;
  priceMax: string;
  type: string;
  color: string;
  hpMin: string;
  hpMax: string;
  fuelType: string;
  gearbox: string;
}
