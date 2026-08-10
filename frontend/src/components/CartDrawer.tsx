'use client';

import React from 'react';
import { CartItem } from '@/lib/types';
import { X, Trash2, ShoppingBag, ArrowRight, Utensils } from 'lucide-react';
import confetti from 'canvas-confetti';

interface CartDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  cartItems: CartItem[];
  onUpdateQuantity: (cartItemId: string, newQuantity: number) => void;
  onRemoveItem: (cartItemId: string) => void;
  onPlaceOrder: () => void;
  tableName?: string;
}

export const CartDrawer: React.FC<CartDrawerProps> = ({
  isOpen,
  onClose,
  cartItems,
  onUpdateQuantity,
  onRemoveItem,
  onPlaceOrder,
  tableName = "Table 01"
}) => {
  if (!isOpen) return null;

  const subtotal = cartItems.reduce((acc, item) => acc + item.totalPrice, 0);
  const serviceFee = subtotal > 0 ? Math.round(subtotal * 0.05) : 0;
  const totalAmount = subtotal + serviceFee;

  const handleCheckout = () => {
    // Trigger festive confetti burst
    confetti({
      particleCount: 120,
      spread: 70,
      origin: { y: 0.6 }
    });
    onPlaceOrder();
  };

  return (
    <div className="fixed inset-0 z-50 overflow-hidden bg-slate-950/80 backdrop-blur-md animate-fade-in flex justify-end">
      <div className="w-full max-w-md bg-slate-900 border-l border-slate-800 h-full flex flex-col shadow-2xl">
        
        {/* Drawer Header */}
        <div className="p-5 border-b border-slate-800 flex items-center justify-between bg-slate-950">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg bg-orange-500/20 text-orange-400 flex items-center justify-center">
              <ShoppingBag className="w-4 h-4" />
            </div>
            <div>
              <h2 className="text-base font-bold text-white">Your Order</h2>
              <p className="text-xs text-slate-400">{tableName} • {cartItems.length} items</p>
            </div>
          </div>

          <button
            onClick={onClose}
            className="w-8 h-8 rounded-lg bg-slate-800 text-slate-400 hover:text-white flex items-center justify-center border border-slate-700"
          >
            <X className="w-4 h-4" />
          </button>
        </div>

        {/* Item List */}
        <div className="flex-1 overflow-y-auto p-5 space-y-4">
          {cartItems.length === 0 ? (
            <div className="h-full flex flex-col items-center justify-center text-center text-slate-500 py-12">
              <Utensils className="w-12 h-12 stroke-[1.5] mb-3 text-slate-600" />
              <p className="text-sm font-semibold text-slate-300">Your cart is empty</p>
              <p className="text-xs text-slate-500 mt-1 max-w-xs">
                Browse our gourmet menu and add delicious dishes to your table order.
              </p>
            </div>
          ) : (
            cartItems.map((item) => (
              <div
                key={item.cartItemId}
                className="glass-card p-3.5 rounded-xl border border-slate-800 flex items-start gap-3"
              >
                <img
                  src={item.product.imageUrl}
                  alt={item.product.name}
                  className="w-16 h-16 rounded-lg object-cover bg-slate-800"
                />

                <div className="flex-1 min-w-0">
                  <div className="flex items-start justify-between gap-1">
                    <h4 className="text-sm font-bold text-white truncate">{item.product.name}</h4>
                    <button
                      onClick={() => onRemoveItem(String(item.cartItemId))}
                      className="text-slate-500 hover:text-rose-400 transition-colors p-1"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  </div>

                  {item.selectedVariant && (
                    <p className="text-[11px] text-orange-400 font-medium">{item.selectedVariant.name}</p>
                  )}

                  {item.selectedOptions.length > 0 && (
                    <p className="text-[11px] text-slate-400 line-clamp-1">
                      {item.selectedOptions.map(o => o.name).join(', ')}
                    </p>
                  )}

                  {(item as any).specialInstructions && (
                    <p className="text-[10px] text-amber-400 italic mt-0.5 line-clamp-1">
                      "{(item as any).specialInstructions}"
                    </p>
                  )}

                  <div className="flex items-center justify-between mt-2">
                    <span className="text-xs font-bold text-white">₺{item.totalPrice.toLocaleString()}</span>

                    {/* Quantity controls */}
                    <div className="flex items-center gap-2 bg-slate-950 px-2 py-1 rounded-lg border border-slate-800 text-xs">
                      <button
                        onClick={() => onUpdateQuantity(String(item.cartItemId), item.quantity - 1)}
                        className="text-slate-400 hover:text-white font-bold"
                      >
                        -
                      </button>
                      <span className="font-bold text-white px-1">{item.quantity}</span>
                      <button
                        onClick={() => onUpdateQuantity(String(item.cartItemId), item.quantity + 1)}
                        className="text-slate-400 hover:text-white font-bold"
                      >
                        +
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>

        {/* Pricing Summary & Checkout */}
        {cartItems.length > 0 && (
          <div className="p-5 bg-slate-950 border-t border-slate-800 space-y-3">
            <div className="space-y-1.5 text-xs">
              <div className="flex justify-between text-slate-400">
                <span>Subtotal</span>
                <span>₺{subtotal.toLocaleString()}</span>
              </div>
              <div className="flex justify-between text-slate-400">
                <span>Service Charge (5%)</span>
                <span>₺{serviceFee.toLocaleString()}</span>
              </div>
              <div className="flex justify-between text-sm font-extrabold text-white pt-2 border-t border-slate-800">
                <span>Total Amount</span>
                <span className="text-orange-400">₺{totalAmount.toLocaleString()}</span>
              </div>
            </div>

            <button
              onClick={handleCheckout}
              className="w-full flex items-center justify-center gap-2 py-3.5 rounded-xl gradient-btn text-white font-bold text-sm shadow-xl"
            >
              <span>Send Order to Kitchen</span>
              <ArrowRight className="w-4 h-4" />
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
