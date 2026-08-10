'use client';

import React, { useState } from 'react';
import { Product, ProductVariant, ProductOption } from '@/lib/types';
import { X, Minus, Plus, ShoppingBag, Check } from 'lucide-react';

interface ProductDetailModalProps {
  product: Product | null;
  onClose: () => void;
  onAddToCart: (
    product: Product,
    selectedVariant: ProductVariant | undefined,
    selectedOptions: any[],
    quantity: number,
    specialInstructions: string
  ) => void;
}

export const ProductDetailModal: React.FC<ProductDetailModalProps> = ({
  product,
  onClose,
  onAddToCart
}) => {
  if (!product) return null;

  const [selectedVariant, setSelectedVariant] = useState<ProductVariant | undefined>(
    product.variants && product.variants.length > 0 ? product.variants[0] : undefined
  );
  const [selectedOptions, setSelectedOptions] = useState<any[]>([]);
  const [quantity, setQuantity] = useState<number>(1);
  const [instructions, setInstructions] = useState<string>('');

  const calculateTotalPrice = () => {
    let price = Number((product as any).basePrice || product.price);
    if (selectedVariant) {
      price += Number((selectedVariant as any).priceModifier || selectedVariant.price);
    }
    selectedOptions.forEach((opt: any) => {
      price += Number(opt.priceModifier || 0);
    });
    return price * quantity;
  };

  const handleOptionToggle = (group: any, option: ProductOption) => {
    const exists = selectedOptions.some(opt => opt.optionId === option.id);
    if (exists) {
      setSelectedOptions(selectedOptions.filter(opt => opt.optionId !== option.id));
    } else {
      if (group.maxSelections === 1) {
        // Replace previous option from same group
        const filtered = selectedOptions.filter(opt => opt.optionGroupId !== group.id);
        setSelectedOptions([
          ...filtered,
          { optionGroupId: group.id, optionId: option.id, name: option.name, priceModifier: option.priceModifier }
        ]);
      } else {
        setSelectedOptions([
          ...selectedOptions,
          { optionGroupId: group.id, optionId: option.id, name: option.name, priceModifier: option.priceModifier }
        ]);
      }
    }
  };

  const handleAdd = () => {
    onAddToCart(product, selectedVariant, selectedOptions, quantity, instructions);
    onClose();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 sm:p-6 bg-slate-950/80 backdrop-blur-md animate-fade-in">
      <div className="relative w-full max-w-xl max-h-[90vh] glass-panel rounded-3xl overflow-hidden flex flex-col shadow-2xl border border-slate-700/60">
        
        {/* Top Close Button */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 z-20 w-9 h-9 rounded-full bg-slate-900/80 text-slate-300 hover:text-white flex items-center justify-center border border-slate-700 hover:bg-slate-800 transition-all"
        >
          <X className="w-5 h-5" />
        </button>

        {/* Hero Image */}
        <div className="relative w-full h-56 sm:h-64 bg-slate-900 flex-shrink-0">
          <img
            src={product.imageUrl}
            alt={product.name}
            className="w-full h-full object-cover"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-slate-950 via-slate-950/30 to-transparent" />
          
          <div className="absolute bottom-4 left-6 right-6">
            <h2 className="text-xl sm:text-2xl font-extrabold text-white">{product.name}</h2>
            <p className="text-xs sm:text-sm text-slate-300 mt-1 line-clamp-2">{product.description}</p>
          </div>
        </div>

        {/* Scrollable Customization Options */}
        <div className="p-6 overflow-y-auto space-y-6">
          {/* Variants Selection */}
          {product.variants && product.variants.length > 0 && (
            <div>
              <label className="text-xs font-bold text-slate-400 uppercase tracking-wider block mb-3">
                Choose Size / Cooking Preference
              </label>
              <div className="grid grid-cols-2 sm:grid-cols-3 gap-2.5">
                {product.variants.map((variant) => {
                  const isSelected = selectedVariant?.id === variant.id;
                  return (
                    <button
                      key={variant.id}
                      onClick={() => setSelectedVariant(variant)}
                      className={`flex items-center justify-between p-3 rounded-xl border text-xs font-semibold transition-all ${
                        isSelected
                          ? 'border-orange-500 bg-orange-500/10 text-orange-400'
                          : 'border-slate-800 bg-slate-900/60 text-slate-300 hover:border-slate-700'
                      }`}
                    >
                      {(variant as any).priceModifier !== undefined && (variant as any).priceModifier !== 0 && (
                        <span className="text-[11px] text-slate-400">
                          {(variant as any).priceModifier > 0 ? `+₺${(variant as any).priceModifier}` : `-₺${Math.abs((variant as any).priceModifier)}`}
                        </span>
                      )}
                    </button>
                  );
                })}
              </div>
            </div>
          )}

          {/* Option Groups */}
          {product.optionGroups?.map((group) => (
            <div key={group.id}>
              <div className="flex items-center justify-between mb-3">
                <span className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                  {group.name}
                </span>
                {(group as any).isRequired && (
                  <span className="text-[10px] bg-rose-500/20 text-rose-400 px-2 py-0.5 rounded font-bold">
                    Required
                  </span>
                )}
              </div>
              <div className="space-y-2">
                {group.options.map((option) => {
                  const isSelected = selectedOptions.some(opt => opt.optionId === option.id);
                  return (
                    <button
                      key={option.id}
                      onClick={() => handleOptionToggle(group, option)}
                      className={`w-full flex items-center justify-between p-3 rounded-xl border text-xs font-medium transition-all ${
                        isSelected
                          ? 'border-orange-500 bg-orange-500/10 text-orange-400'
                          : 'border-slate-800 bg-slate-900/60 text-slate-300 hover:border-slate-700'
                      }`}
                    >
                      <div className="flex items-center gap-2.5">
                        <div
                          className={`w-4 h-4 rounded ${
                            (group.maxSelect === 1 || (group as any).maxSelections === 1) ? 'rounded-full' : 'rounded'
                          } border flex items-center justify-center ${
                            isSelected ? 'bg-orange-500 border-orange-500 text-white' : 'border-slate-600'
                          }`}
                        >
                          {isSelected && <Check className="w-3 h-3 stroke-[3]" />}
                        </div>
                        <span>{option.name}</span>
                      </div>
                      {option.priceModifier > 0 && (
                        <span className="text-slate-400 font-bold">+₺{option.priceModifier}</span>
                      )}
                    </button>
                  );
                })}
              </div>
            </div>
          ))}

          {/* Special Instructions Input */}
          <div>
            <label className="text-xs font-bold text-slate-400 uppercase tracking-wider block mb-2">
              Special Requests & Allergies
            </label>
            <textarea
              value={instructions}
              onChange={(e) => setInstructions(e.target.value)}
              placeholder="e.g. Extra sauce, no salt, allergy warnings..."
              className="w-full bg-slate-900/80 border border-slate-800 rounded-xl p-3 text-xs text-slate-200 placeholder-slate-500 focus:outline-none focus:border-orange-500 transition-colors resize-none h-20"
            />
          </div>
        </div>

        {/* Sticky Action Footer */}
        <div className="p-4 sm:p-6 bg-slate-950 border-t border-slate-800 flex items-center justify-between gap-4">
          {/* Quantity Controls */}
          <div className="flex items-center gap-3 bg-slate-900 p-1.5 rounded-xl border border-slate-800">
            <button
              onClick={() => setQuantity(Math.max(1, quantity - 1))}
              className="w-8 h-8 rounded-lg bg-slate-800 text-slate-300 hover:text-white flex items-center justify-center transition-colors"
            >
              <Minus className="w-4 h-4" />
            </button>
            <span className="text-sm font-bold text-white w-5 text-center">{quantity}</span>
            <button
              onClick={() => setQuantity(quantity + 1)}
              className="w-8 h-8 rounded-lg bg-slate-800 text-slate-300 hover:text-white flex items-center justify-center transition-colors"
            >
              <Plus className="w-4 h-4" />
            </button>
          </div>

          {/* Add to Cart Button */}
          <button
            onClick={handleAdd}
            className="flex-1 flex items-center justify-between px-5 py-3 rounded-2xl gradient-btn text-white font-bold text-sm shadow-xl"
          >
            <span className="flex items-center gap-2">
              <ShoppingBag className="w-4 h-4" />
              Add to Order
            </span>
            <span className="font-extrabold text-base">₺{calculateTotalPrice().toLocaleString()}</span>
          </button>
        </div>
      </div>
    </div>
  );
};
