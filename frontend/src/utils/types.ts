
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
  brand: string;
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
  brand: string;
  model: string;
  yearMin: string;
  yearMax: string;
  kmMax: string;
  priceMax: string;
  type: string;
  hpMin: string;
  hpMax: string;
  fuelType: string;
  gearbox: string;
}
