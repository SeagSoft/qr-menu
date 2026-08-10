'use client';

import React from 'react';
import Image from 'next/image';
import { Product } from '@/lib/types';
import { Plus, Flame, Clock } from 'lucide-react';

interface ProductCardProps {
  product: Product;
  onSelectProduct: (product: Product) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({ product, onSelectProduct }) => {
  return (
    <div
      onClick={() => onSelectProduct(product)}
      className="group glass-card rounded-2xl overflow-hidden cursor-pointer flex flex-col justify-between h-full border border-slate-800/80 hover:border-orange-500/40 transition-all duration-300"
    >
      <div>
        {/* Product Image Banner */}
        <div className="relative w-full h-48 sm:h-52 overflow-hidden bg-slate-900">
          <img
            src={product.imageUrl}
            alt={product.name}
            className="w-full h-full object-cover group-hover:scale-108 transition-transform duration-500"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-slate-950 via-slate-950/20 to-transparent" />

          {/* Tags */}
          <div className="absolute top-3 left-3 flex flex-wrap gap-1.5 z-10">
            {(product as any).tags?.map((tag: any, idx: number) => (
              <span
                key={idx}
                className="px-2.5 py-1 rounded-full text-[10px] font-bold tracking-wider uppercase bg-slate-950/80 text-orange-400 border border-orange-500/30 backdrop-blur-md"
              >
                {tag}
              </span>
            ))}
          </div>

          {/* Prep Time / Calorie info */}
          <div className="absolute bottom-3 left-3 right-3 flex items-center justify-between text-xs text-slate-300 font-medium">
            {(product as any).prepTimeMinutes && (
              <span className="flex items-center gap-1 bg-slate-900/80 px-2 py-0.5 rounded-md backdrop-blur-md">
                <Clock className="w-3.5 h-3.5 text-orange-400" />
                {(product as any).prepTimeMinutes} mins
              </span>
            )}
            {(product as any).calories && (
              <span className="flex items-center gap-1 bg-slate-900/80 px-2 py-0.5 rounded-md backdrop-blur-md">
                <Flame className="w-3.5 h-3.5 text-rose-400" />
                {(product as any).calories} kcal
              </span>
            )}
          </div>
        </div>

        {/* Content */}
        <div className="p-4 sm:p-5">
          <h3 className="text-base sm:text-lg font-bold text-slate-100 group-hover:text-orange-400 transition-colors line-clamp-1">
            {product.name}
          </h3>
          <p className="text-xs sm:text-sm text-slate-400 mt-1.5 line-clamp-2 leading-relaxed">
            {product.description}
          </p>
        </div>
      </div>

      {/* Footer: Price & Add Button */}
      <div className="p-4 sm:p-5 pt-0 flex items-center justify-between mt-auto">
        <div>
          <span className="text-xs text-slate-400 block font-medium">Price</span>
          <span className="text-lg sm:text-xl font-extrabold text-white">
            ${Number((product as any).basePrice || product.price).toFixed(2)}
          </span>
        </div>

        <button
          onClick={(e) => {
            e.stopPropagation();
            onSelectProduct(product);
          }}
          className="flex items-center gap-1.5 px-3.5 py-2 rounded-xl gradient-btn text-white text-xs font-bold shadow-md hover:scale-105 transition-transform"
        >
          <Plus className="w-4 h-4" />
          <span>Add</span>
        </button>
      </div>
    </div>
  );
};
