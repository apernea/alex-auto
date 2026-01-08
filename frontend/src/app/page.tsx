'use client';
import React, { useState, useMemo, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Sidebar from '@/components/Sidebar';
import CarRow from '@/components/CarRow';
import { FilterState, Car, CarType } from '@/utils/types';

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

// Mapper function to transform backend car to frontend car
const mapBackendCarToFrontend = (backendCar: BackendCar): Car => ({
  id: backendCar.id.toString(),
  make: backendCar.make,
  model: backendCar.model,
  year: backendCar.year,
  km: backendCar.kilometers,
  price: backendCar.price,
  type: backendCar.type as CarType,
  mainImage: backendCar.imageUrl,
  additionalImages: [], 
  description: backendCar.description,
  specifications: {
    engineSize: backendCar.engineSize,
    horsepower: backendCar.horsepower,
    fuelType: backendCar.fuelType,
    gearbox: backendCar.transmission as 'Auto' | 'Manual',
  },
});

const ITEMS_PER_PAGE = 10;

const HomePage: React.FC = () => {
  const router = useRouter();
  const [cars, setCars] = useState<Car[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [isMobileFilterOpen, setIsMobileFilterOpen] = useState(false);
  const [sortOption, setSortOption] = useState('price-asc');
  const [filters, setFilters] = useState<FilterState>({
    make: '',
    model: '',
    yearMin: '',
    yearMax: '',
    maxKilometers: '',
    priceMax: '',
    type: '',
    hpMin: '',
    hpMax: '',
    fuelType: '',
    gearbox: '',
  });
  const [totalPages, setTotalPages] = useState(1);
  const [totalItems, setTotalItems] = useState(0);

  type Page<T> = {
      content: T[];
      totalElements: number;
      totalPages: number;
      number: number; // 0-based
      size: number;
    };

  useEffect(() => {

    const fetchCars = async () => {
      try {
        const params = new URLSearchParams();

        if (filters.make) params.append('make', filters.make);
        if (filters.model) params.append('model', filters.model);
        if (filters.yearMin) params.append('minYear', filters.yearMin);
        if (filters.yearMax) params.append('maxYear', filters.yearMax);
        if (filters.priceMax) params.append('maxPrice', filters.priceMax);
        if (filters.maxKilometers) params.append('maxKm', filters.maxKilometers);
        if (filters.type) params.append('type', filters.type);
        if (filters.hpMin) params.append('minHp', filters.hpMin);
        if (filters.hpMax) params.append('maxHp', filters.hpMax);
        if (filters.fuelType) params.append('fuelType', filters.fuelType);
        if (filters.gearbox) params.append('transmission', filters.gearbox);

        params.append('page', (currentPage - 1).toString());
        params.append('size', ITEMS_PER_PAGE.toString());

        const response = await fetch(
          `${process.env.NEXT_PUBLIC_API_URL}/cars/search?${params.toString()}`
        );

        if (!response.ok) {
          // optional: log backend error body for easier debugging
          const text = await response.text().catch(() => "");
          throw new Error(`Network response was not ok (${response.status}). ${text}`);
        }

        const page: Page<BackendCar> = await response.json();
        console.log("cars page:", page);

        if (!Array.isArray(page.content)) {
          throw new Error("API response is not a paged result with a content array");
        }

        const frontendCars = page.content.map(mapBackendCarToFrontend);
        setCars(frontendCars);

        // If you have pagination UI, you probably want these too:
        setTotalPages(page.totalPages);
        setTotalItems(page.totalElements);
      } catch (error) {
        console.error("Failed to fetch cars:", error);
      }
    };


    fetchCars();
  }, [filters, currentPage]);

  // Reset to page 1 when filters change
  useEffect(() => {
    setCurrentPage(1);
  }, [filters]);

  const sortedCars = useMemo(() => {
    const sorted = [...cars]; // Create a new array to avoid mutating state directly
    switch (sortOption) {
      case 'price-asc':
        sorted.sort((a, b) => a.price - b.price);
        break;
      case 'price-desc':
        sorted.sort((a, b) => b.price - a.price);
        break;
      case 'year-desc':
        sorted.sort((a, b) => b.year - a.year);
        break;
      case 'km-asc':
        sorted.sort((a, b) => a.km - b.km);
        break;
    }
    return sorted;
  }, [cars, sortOption]);

  const handleCarClick = (id: string) => {
    router.push(`/car/${id}`);
    window.scrollTo(0, 0);
  };
  
  return (
    <div className="min-h-screen bg-[#f8fafc]">
      <div className="flex flex-col lg:flex-row max-w-[1600px] mx-auto min-h-screen">
        {/* Mobile Filter Drawer Backdrop */}
        <div className={`fixed inset-0 z-[60] lg:hidden transition-opacity duration-300 ${isMobileFilterOpen ? 'opacity-100 pointer-events-auto' : 'opacity-0 pointer-events-none'}`}>
          <div className="absolute inset-0 bg-black/40 backdrop-blur-sm" onClick={() => setIsMobileFilterOpen(false)} />
          <div className={`absolute top-0 left-0 bottom-0 w-[85%] max-w-sm transform transition-transform duration-300 ease-in-out ${isMobileFilterOpen ? 'translate-x-0' : '-translate-x-full'}`}>
            <Sidebar filters={filters} setFilters={setFilters} onClose={() => setIsMobileFilterOpen(false)} isMobile />
          </div>
        </div>

        <Sidebar filters={filters} setFilters={setFilters} />

        <main className="flex-1 min-w-0 p-4 md:p-6 lg:p-10 bg-white/50 backdrop-blur-3xl">
          <div className="mb-8 flex flex-col md:flex-row md:items-end justify-between gap-6">
            <div>
              <div className="flex items-center gap-4 mb-2">
                <h1 className="text-3xl md:text-4xl font-black text-gray-900 tracking-tight">Inventory</h1>
                <button 
                  onClick={() => setIsMobileFilterOpen(true)}
                  className="lg:hidden flex items-center gap-2 bg-white border border-gray-200 px-4 py-2 rounded-xl text-xs font-black uppercase tracking-widest shadow-sm hover:bg-gray-50 active:scale-95 transition-all"
                >
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"/></svg>
                  Filter
                </button>
              </div>
              <p className="text-gray-500 font-medium italic">Discover {totalItems} curated luxury vehicles</p>
            </div>
            
            <div className="flex items-center space-x-3 bg-white p-3 px-5 rounded-2xl border border-gray-100 shadow-sm self-start md:self-auto">
              <span className="text-[10px] font-black text-gray-400 uppercase tracking-[0.2em]">Sort</span>
              <select 
                value={sortOption}
                onChange={(e) => setSortOption(e.target.value)}
                className="bg-transparent text-sm font-black text-gray-900 focus:outline-none cursor-pointer appearance-none border-none"
              >
                <option value="price-asc">Price: Low to High</option>
                <option value="price-desc">Price: High to Low</option>
                <option value="year-desc">Year: Newest</option>
                <option value="km-asc">Mileage: Lowest</option>
              </select>
            </div>
          </div>

          {sortedCars.length > 0 ? (
            <>
              <div className="bg-white rounded-[2.5rem] shadow-2xl shadow-gray-200/40 border border-gray-100 overflow-hidden mb-10">
                <div className="overflow-x-auto scrollbar-hide">
                  <div className="min-w-[800px] lg:min-w-0">
                    <div className="hidden md:flex items-center bg-gray-50/40 px-8 py-6 border-b border-gray-100 text-[10px] font-black text-gray-400 uppercase tracking-[0.25em] gap-8">
                      <div className="w-48 flex-shrink-0">Preview</div>
                      <div className="flex-1">Vehicle Specs</div>
                      <div className="w-24 text-center">Year</div>
                      <div className="w-32 text-center">Mileage</div>
                      <div className="w-28 text-center">Body</div>
                      <div className="w-32 text-right">Price</div>
                      <div className="w-24"></div>
                    </div>
                    <div className="divide-y divide-gray-50">
                      {sortedCars.map(car => (
                        <CarRow key={car.id} car={car} onClick={handleCarClick} />
                      ))}
                    </div>
                  </div>
                </div>
              </div>

              {totalPages > 1 && (
                <div className="flex items-center justify-between bg-white px-8 py-5 rounded-3xl border border-gray-100 shadow-sm mb-12">
                   <div className="flex-1 flex justify-between sm:hidden">
                    <button
                      onClick={() => { setCurrentPage(p => Math.max(1, p - 1)); window.scrollTo(0, 0); }}
                      disabled={currentPage === 1}
                      className="inline-flex items-center px-6 py-3 border border-gray-100 text-xs font-black uppercase tracking-widest rounded-xl text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-30 transition-all"
                    >
                      Prev
                    </button>
                    <button
                      onClick={() => { setCurrentPage(p => Math.min(totalPages, p + 1)); window.scrollTo(0, 0); }}
                      disabled={currentPage === totalPages}
                      className="ml-3 inline-flex items-center px-6 py-3 border border-gray-100 text-xs font-black uppercase tracking-widest rounded-xl text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-30 transition-all"
                    >
                      Next
                    </button>
                  </div>
                  <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-400">
                        Showing <span className="font-black text-gray-900">{(currentPage - 1) * ITEMS_PER_PAGE + 1}</span> to <span className="font-black text-gray-900">{Math.min(currentPage * ITEMS_PER_PAGE, totalItems)}</span> of <span className="font-black text-gray-900">{sortedCars.length}</span> results
                      </p>
                    </div>
                    <div>
                      <nav className="relative z-0 inline-flex rounded-xl shadow-sm overflow-hidden border border-gray-100" aria-label="Pagination">
                        <button
                          onClick={() => { setCurrentPage(p => Math.max(1, p - 1)); window.scrollTo(0, 0); }}
                          disabled={currentPage === 1}
                          className="relative inline-flex items-center px-4 py-3 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 disabled:opacity-30 transition-all"
                        >
                          <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 19l-7-7 7-7"/></svg>
                        </button>
                        {[...Array(totalPages)].map((_, idx) => (
                          <button
                            key={idx + 1}
                            onClick={() => { setCurrentPage(idx + 1); window.scrollTo(0, 0); }}
                            className={`relative inline-flex items-center px-5 py-3 text-xs font-black uppercase tracking-widest transition-all ${currentPage === idx + 1 ? 'z-10 bg-blue-600 text-white' : 'bg-white text-gray-500 hover:bg-gray-50'}`}
                          >
                            {idx + 1}
                          </button>
                        ))}
                        <button
                          onClick={() => { setCurrentPage(p => Math.min(totalPages, p + 1)); window.scrollTo(0, 0); }}
                          disabled={currentPage === totalPages}
                          className="relative inline-flex items-center px-4 py-3 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 disabled:opacity-30 transition-all"
                        >
                           <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5l7 7-7 7"/></svg>
                        </button>
                      </nav>
                    </div>
                  </div>
                </div>
              )}
            </>
          ) : (
            <div className="py-24 text-center bg-white rounded-[3rem] border-2 border-dashed border-gray-100">
              <div className="bg-gray-50 w-24 h-24 rounded-full flex items-center justify-center mx-auto mb-8">
                <svg className="w-10 h-10 text-gray-200" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9.172 9.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
              </div>
              <h3 className="text-2xl font-black text-gray-900 tracking-tight">No results matched your search</h3>
              <p className="text-gray-500 mt-2 font-medium">Try adjusting your filters or search criteria.</p>
              <button 
                onClick={() => setFilters({ make: '', model: '', yearMin: '', yearMax: '', maxKilometers: '', priceMax: '', type: '', hpMin: '', hpMax: '', fuelType: '', gearbox: '' })}
                className="mt-10 bg-gray-900 text-white px-10 py-4 rounded-2xl font-black text-xs uppercase tracking-widest hover:bg-blue-600 transition-all transform hover:scale-105"
              >
                Reset All Filters
              </button>
            </div>
          )}
        </main>
      </div>
    </div>
  );
};

export default HomePage;