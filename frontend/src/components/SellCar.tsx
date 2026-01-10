'use client';
import React, { useState } from 'react';
import { MAKERS, CAR_TYPES } from '@/utils/constants';
import { Car, CarType } from '@/utils/types';

type SellCarPayload = Omit<Car, 'id'> & {
  mainImageFile?: File | null;
  additionalImageFiles?: File[];
};

interface SellCarProps {
  onBack: () => void;
  onAddCar: (car: SellCarPayload) => void;
}

const SellCar: React.FC<SellCarProps> = ({ onBack, onAddCar }) => {
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [imageFiles, setImageFiles] = useState<File[]>([]);
  const [imagePreviews, setImagePreviews] = useState<string[]>([]);
  const [formData, setFormData] = useState({
    make: '',
    model: '',
    year: new Date().getFullYear().toString(),
    type: CarType.SEDAN,
    km: '',
    price: '',
    description: '',
    fuelType: 'Gasoline',
    gearbox: 'Auto' as 'Auto' | 'Manual',
    engineSize: '',
    horsepower: '',
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    setImageFiles(prev => [...prev, file]);
    setImagePreviews(prev => [...prev, URL.createObjectURL(file)]);

    e.target.value = '';
  };


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const engineSizeNum = parseInt(formData.engineSize) || 0;

    const payload: SellCarPayload = {
      // Car fields (no id here)
      make: formData.make,
      model: formData.model,
      year: parseInt(formData.year) || 1900,
      km: parseInt(formData.km) || 0,
      price: parseInt(formData.price) || 0,
      type: formData.type as CarType,
      mainImage: imagePreviews[0] || 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?auto=format&fit=crop&q=80&w=800',
      additionalImages: imagePreviews.slice(1),
      description: formData.description,
      specifications: {
        engineSize: engineSizeNum,
        horsepower: parseInt(formData.horsepower) || 1,
        fuelType: engineSizeNum === 0 ? 'Electric' : formData.fuelType,
        gearbox: formData.gearbox,
      },

      mainImageFile: imageFiles[0] ?? null,
      additionalImageFiles: imageFiles.slice(1),
    };

    onAddCar(payload);
    setIsSubmitted(true);
  };

  if (isSubmitted) {
    return (
      <div className="max-w-3xl mx-auto px-6 py-20 text-center">
        <div className="bg-green-50 w-24 h-24 rounded-full flex items-center justify-center mx-auto mb-8">
          <svg className="w-12 h-12 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7"></path></svg>
        </div>
        <h1 className="text-4xl font-black text-gray-900 mb-4 tracking-tight">Listing Submitted!</h1>
        <p className="text-gray-500 text-lg mb-10 max-w-md mx-auto">Your vehicle has been added to our inventory. You can now view it in the catalog.</p>
        <button 
          onClick={onBack}
          className="bg-blue-600 text-white px-10 py-4 rounded-xl font-bold hover:bg-blue-700 transition-all shadow-lg shadow-blue-200"
        >
          Return to Inventory
        </button>
      </div>
    );
  }

  return (
    <div className="max-w-5xl mx-auto px-6 py-12">
      <div className="mb-12">
        <button 
          onClick={onBack}
          className="mb-6 flex items-center text-gray-500 hover:text-gray-900 font-bold text-xs uppercase tracking-widest transition-colors"
        >
          <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"></path></svg>
          Cancel Listing
        </button>
        <h1 className="text-5xl font-black text-gray-900 tracking-tighter">Sell Your Vehicle</h1>
        <p className="text-gray-500 text-lg mt-2 font-medium">List your car on the world&apos;s most exclusive marketplace.</p>
      </div>

      <form onSubmit={handleSubmit} className="space-y-10">
        <section className="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
          <div className="flex items-center space-x-3 mb-8">
            <span className="bg-blue-600 text-white w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold">1</span>
            <h2 className="text-2xl font-bold text-gray-900">Visual Gallery</h2>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <label className="aspect-square border-2 border-dashed border-gray-200 rounded-2xl flex flex-col items-center justify-center cursor-pointer hover:border-blue-400 hover:bg-blue-50 transition-all group">
              <svg className="w-10 h-10 text-gray-300 group-hover:text-blue-500 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4v16m8-8H4"></path></svg>
              <span className="text-xs font-bold text-gray-400 group-hover:text-blue-600 uppercase">Add Photo</span>
              <input type="file" className="hidden" onChange={handleImageUpload} accept="image/*" />
            </label>
            {imagePreviews.map((img, i) => (
              <div key={i} className="aspect-square rounded-2xl overflow-hidden shadow-sm relative group">
                <img src={img} className="w-full h-full object-cover" alt="Uploaded car" />
                <button type="button" onClick={() => {
                    setImageFiles(prev => prev.filter((_, idx) => idx !== i));
                    setImagePreviews(prev => {
                      const url = prev[i];
                      if (url) URL.revokeObjectURL(url);
                      return prev.filter((_, idx) => idx !== i);
                    });
                  }} className="absolute top-2 right-2 bg-red-500 text-white p-1 rounded-full opacity-0 group-hover:opacity-100 transition-opacity">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                </button>
              </div>
            ))}
          </div>
        </section>

        <section className="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
          <div className="flex items-center space-x-3 mb-8">
            <span className="bg-blue-600 text-white w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold">2</span>
            <h2 className="text-2xl font-bold text-gray-900">Essential Details</h2>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Make</label>
              <select name="make" required value={formData.make} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black">
                <option value="">Select Make</option>
                {MAKERS.map(m => <option key={m} value={m}>{m}</option>)}
              </select>
            </div>
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Model</label>
              <input name="model" required type="text" value={formData.model} onChange={handleInputChange} placeholder="e.g. Model S Plaid" className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black" />
            </div>
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Year</label>
              <input name="year" required type="number" value={formData.year} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black" />
            </div>
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Body Type</label>
              <select name="type" required value={formData.type} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black">
                {CAR_TYPES.map(t => <option key={t} value={t}>{t}</option>)}
              </select>
            </div>
          </div>
        </section>

        <section className="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
          <div className="flex items-center space-x-3 mb-8">
            <span className="bg-blue-600 text-white w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold">3</span>
            <h2 className="text-2xl font-bold text-gray-900">Pricing & Condition</h2>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Current Mileage (km)</label>
              <input name="km" required type="number" value={formData.km} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black" />
            </div>
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Asking Price ($)</label>
              <input name="price" required type="number" value={formData.price} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold text-black" />
            </div>
          </div>
        </section>

        <section className="bg-white p-8 md:p-10 rounded-3xl shadow-sm border border-gray-100">
          <div className="flex items-center space-x-3 mb-8">
            <span className="bg-blue-600 text-white w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold">4</span>
            <h2 className="text-2xl font-bold text-gray-900">The Story & Specs</h2>
          </div>
          <div className="space-y-8">
            <div>
              <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Description</label>
              <textarea name="description" required rows={5} value={formData.description} onChange={handleInputChange} placeholder="Tell potential buyers about the vehicle..." className="w-full bg-gray-50 border border-gray-100 p-4 rounded-xl outline-none font-semibold resize-none text-black"></textarea>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              <div>
                <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Fuel Type</label>
                <input name="fuelType" required type="text" value={formData.fuelType} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-3 rounded-xl outline-none font-semibold text-black" />
              </div>
              <div>
                <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Gearbox</label>
                <select name="gearbox" required value={formData.gearbox} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-3 rounded-xl outline-none font-semibold text-black">
                  <option value="Auto">Auto</option>
                  <option value="Manual">Manual</option>
                </select>
              </div>
              <div>
                <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Engine Size</label>
                <input name="engineSize" required type="text" value={formData.engineSize} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-3 rounded-xl outline-none font-semibold text-black" />
              </div>
              <div>
                <label className="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-3">Horsepower (HP)</label>
                <input name="horsepower" required type="number" value={formData.horsepower} onChange={handleInputChange} className="w-full bg-gray-50 border border-gray-100 p-3 rounded-xl outline-none font-semibold text-black" />
              </div>
            </div>
          </div>
        </section>

        <div className="pt-6">
          <button type="submit" className="w-full md:w-auto bg-gray-900 text-white px-12 py-5 rounded-2xl font-black text-lg hover:bg-black transition-all transform hover:scale-[1.02] shadow-xl shadow-gray-200">
            Publish My Listing
          </button>
        </div>
      </form>
    </div>
  );
};

export default SellCar;