'use client';
import React from 'react';
import { MAKERS, CAR_TYPES } from '@/utils/constants';
import { FilterState } from '@/utils/types';

interface SidebarProps {
  filters: FilterState;
  setFilters: React.Dispatch<React.SetStateAction<FilterState>>;
  onClose?: () => void;
  isMobile?: boolean;
}

const Sidebar: React.FC<SidebarProps> = ({ filters, setFilters, onClose, isMobile }) => {
  const handleChange = (e: React.ChangeEvent<HTMLSelectElement | HTMLInputElement>) => {
    const { name, value } = e.target;
    setFilters(prev => ({ ...prev, [name]: value }));
  };

  const resetFilters = () => {
    setFilters({
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
  };

  const containerClasses = isMobile 
    ? "w-full h-full bg-white p-6 overflow-y-auto"
    : "w-full lg:w-80 flex-shrink-0 bg-white p-8 border-r border-gray-200 h-auto lg:h-[calc(100vh-64px)] lg:sticky lg:top-16 overflow-y-auto hidden lg:block";

  return (
    <aside className={containerClasses}>
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-xl font-black text-gray-900 tracking-tight uppercase">Filters</h2>
        <div className="flex items-center gap-4">
          <button 
            onClick={resetFilters}
            className="text-[10px] text-blue-600 hover:text-blue-800 font-black uppercase tracking-widest transition-colors"
          >
            Reset
          </button>
          {onClose && (
            <button onClick={onClose} className="lg:hidden p-2 -mr-2 text-gray-400 hover:text-gray-900">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          )}
        </div>
      </div>

      <div className="space-y-8 pb-10">
        {/* Make */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Make</label>
          <select 
            name="make"
            value={filters.make}
            onChange={handleChange}
            className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 transition-all outline-none font-bold appearance-none"
          >
            <option value="">All Manufacturers</option>
            {MAKERS.map(m => <option key={m} value={m}>{m}</option>)}
          </select>
        </div>

        {/* Type */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Body Style</label>
          <select 
            name="type"
            value={filters.type}
            onChange={handleChange}
            className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 transition-all outline-none font-bold appearance-none"
          >
            <option value="">All Body Styles</option>
            {CAR_TYPES.map(t => <option key={t} value={t}>{t}</option>)}
          </select>
        </div>

        {/* Price Max */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Max Budget</label>
          <div className="relative">
            <span className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 font-bold">$</span>
            <input 
              type="number"
              name="priceMax"
              value={filters.priceMax}
              onChange={handleChange}
              placeholder="e.g. 150000"
              className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 pl-8 transition-all outline-none font-bold"
            />
          </div>
        </div>

        {/* Year Range */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Model Year</label>
          <div className="grid grid-cols-2 gap-3">
            <input 
              type="number"
              name="yearMin"
              value={filters.yearMin}
              onChange={handleChange}
              placeholder="Min"
              className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 transition-all outline-none font-bold"
            />
            <input 
              type="number"
              name="yearMax"
              value={filters.yearMax}
              onChange={handleChange}
              placeholder="Max"
              className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 transition-all outline-none font-bold"
            />
          </div>
        </div>

        {/* KM Max */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Max Mileage (km)</label>
          <input 
            type="number"
            name="MaxKilometers"
            value={filters.maxKilometers}
            onChange={handleChange}
            placeholder="e.g. 20000"
            className="w-full bg-gray-50 border border-gray-100 text-gray-900 text-sm rounded-xl focus:ring-4 focus:ring-blue-500/5 focus:border-blue-500 block p-4 transition-all outline-none font-bold"
          />
        </div>

        {/* Transmission */}
        <div>
          <label className="block text-[10px] font-black text-gray-400 uppercase tracking-[0.2em] mb-3">Transmission</label>
          <div className="grid grid-cols-2 gap-2">
            {['Auto', 'Manual'].map((type) => (
              <button
                key={type}
                onClick={() => setFilters(prev => ({ ...prev, gearbox: filters.gearbox === type ? '' : type }))}
                className={`p-3 rounded-xl text-xs font-bold transition-all border ${filters.gearbox === type ? 'bg-blue-600 border-blue-600 text-white shadow-lg shadow-blue-100' : 'bg-gray-50 border-gray-100 text-gray-600 hover:border-blue-200'}`}
              >
                {type}
              </button>
            ))}
          </div>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
