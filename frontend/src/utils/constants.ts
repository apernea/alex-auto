
import { Car, CarType } from './types';

export const BRANDS = ['Tesla', 'BMW', 'Mercedes', 'Audi', 'Porsche', 'Ford', 'Toyota', 'Honda', 'Ferrari', 'Lamborghini'];
export const CAR_TYPES = Object.values(CarType);

export const MOCK_CARS: Car[] = [
  {
    id: '1',
    brand: 'Tesla',
    model: 'Model S Plaid',
    year: 2023,
    km: 1200,
    price: 115000,
    type: CarType.SEDAN,
    mainImage: 'https://images.unsplash.com/photo-1617788138017-80ad40651399?auto=format&fit=crop&q=80&w=800',
    additionalImages: [
      'https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&q=80&w=800',
    ],
    description: 'Experience the future of driving with the Model S Plaid. Insane acceleration, sustainable tech, and industry-leading range.',
    specifications: {
      engineSize: 0,
      horsepower: 1020,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '2',
    brand: 'Porsche',
    model: '911 Carrera S',
    year: 2022,
    km: 8500,
    price: 135000,
    type: CarType.COUPE,
    mainImage: 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The definitive sports car. Balanced, powerful, and iconic.',
    specifications: {
      engineSize: 3000,
      horsepower: 450,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '3',
    brand: 'BMW',
    model: 'X5 M50i',
    year: 2021,
    km: 24000,
    price: 82000,
    type: CarType.SUV,
    mainImage: 'https://images.unsplash.com/photo-1555215695-3004980ad54e?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'Luxury meets performance in this powerful SUV.',
    specifications: {
      engineSize: 4400,
      horsepower: 523,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '4',
    brand: 'Mercedes',
    model: 'EQS 580',
    year: 2023,
    km: 500,
    price: 142000,
    type: CarType.SEDAN,
    mainImage: 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The pinnacle of electric luxury.',
    specifications: {
      engineSize: 0,
      horsepower: 516,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '5',
    brand: 'Audi',
    model: 'RS6 Avant',
    year: 2022,
    km: 15000,
    price: 128000,
    type: CarType.HATCHBACK,
    mainImage: 'https://images.unsplash.com/photo-1606152421802-db97b9c7a11b?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The ultimate daily driver.',
    specifications: {
      engineSize: 4000,
      horsepower: 591,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '6',
    brand: 'Ford',
    model: 'F-150 Lightning',
    year: 2023,
    km: 2000,
    price: 78000,
    type: CarType.TRUCK,
    mainImage: 'https://images.unsplash.com/photo-1594235372338-7634f1ecbe02?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'Work and play with pure electric torque.',
    specifications: {
      engineSize: 0,
      horsepower: 580,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '7',
    brand: 'Ferrari',
    model: '296 GTB',
    year: 2024,
    km: 150,
    price: 320000,
    type: CarType.COUPE,
    mainImage: 'https://images.unsplash.com/photo-1592198084033-aade902d1aae?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The evolution of Ferraris mid-rear-engined two-seater sports berlinetta concept.',
    specifications: {
      engineSize: 2900,
      horsepower: 818,
      fuelType: 'Hybrid',
      gearbox: 'Auto'
    }
  },
  {
    id: '8',
    brand: 'Lamborghini',
    model: 'Urus Performante',
    year: 2023,
    km: 3400,
    price: 265000,
    type: CarType.SUV,
    mainImage: 'https://images.unsplash.com/photo-1544636331-e26879cd4d9b?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'Designed for drivers who want the ultimate in SUV performance.',
    specifications: {
      engineSize: 4000,
      horsepower: 657,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '9',
    brand: 'Toyota',
    model: 'Supra GR',
    year: 2022,
    km: 12000,
    price: 58000,
    type: CarType.COUPE,
    mainImage: 'https://images.unsplash.com/photo-1580273916550-e323be2ae537?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'A legend reborn with precision handling.',
    specifications: {
      engineSize: 3000,
      horsepower: 382,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '10',
    brand: 'Audi',
    model: 'e-tron GT',
    year: 2023,
    km: 1100,
    price: 104000,
    type: CarType.SEDAN,
    mainImage: 'https://images.unsplash.com/photo-1614162692292-7ac56d7f7f1e?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'A masterclass in electric grand touring.',
    specifications: {
      engineSize: 0,
      horsepower: 522,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '11',
    brand: 'Honda',
    model: 'Civic Type R',
    year: 2023,
    km: 4500,
    price: 46000,
    type: CarType.HATCHBACK,
    mainImage: 'https://images.unsplash.com/photo-1594070319944-7c0c638061df?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The ultimate hot hatch for the track and the road.',
    specifications: {
      engineSize: 2000,
      horsepower: 315,
      fuelType: 'Gasoline',
      gearbox: 'Manual'
    }
  },
  {
    id: '12',
    brand: 'Mercedes',
    model: 'G 63 AMG',
    year: 2022,
    km: 18000,
    price: 185000,
    type: CarType.SUV,
    mainImage: 'https://images.unsplash.com/photo-1520050206274-a1af4463d84d?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'Unmatched presence and off-road capability.',
    specifications: {
      engineSize: 4000,
      horsepower: 577,
      fuelType: 'Gasoline',
      gearbox: 'Auto'
    }
  },
  {
    id: '13',
    brand: 'Ford',
    model: 'Mustang Mach-E GT',
    year: 2023,
    km: 5600,
    price: 62000,
    type: CarType.SUV,
    mainImage: 'https://images.unsplash.com/photo-1605515298946-d062f2e9da53?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'All-electric performance with Mustang DNA.',
    specifications: {
      engineSize: 0,
      horsepower: 480,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '14',
    brand: 'Tesla',
    model: 'Model 3 Performance',
    year: 2024,
    km: 200,
    price: 54000,
    type: CarType.SEDAN,
    mainImage: 'https://images.unsplash.com/photo-1536700503339-1e4b06520771?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'Quick, agile, and technologically superior.',
    specifications: {
      engineSize: 0,
      horsepower: 450,
      fuelType: 'Electric',
      gearbox: 'Auto'
    }
  },
  {
    id: '15',
    brand: 'Toyota',
    model: 'Land Cruiser 300',
    year: 2023,
    km: 5000,
    price: 98000,
    type: CarType.SUV,
    mainImage: 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?auto=format&fit=crop&q=80&w=800',
    additionalImages: [],
    description: 'The king of every terrain.',
    specifications: {
      engineSize: 3300,
      horsepower: 304,
      fuelType: 'Diesel',
      gearbox: 'Auto'
    }
  }
];
