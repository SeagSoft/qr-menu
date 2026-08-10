'use client';

import React from 'react';
import Link from 'next/link';
import { Utensils, QrCode, ChefHat, LayoutDashboard, ShoppingBag, Bell } from 'lucide-react';

interface NavbarProps {
  cartCount?: number;
  onOpenCart?: () => void;
  tableName?: string;
  activeView?: 'customer' | 'kitchen' | 'admin' | 'home';
  onCallWaiter?: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({
  cartCount = 0,
  onOpenCart,
  tableName = "Table 01",
  activeView = "customer",
  onCallWaiter
}) => {
  return (
    <header className="sticky top-0 z-40 w-full glass-panel border-b border-slate-800/80">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        
        {/* Brand Logo */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-10 h-10 rounded-xl gradient-btn flex items-center justify-center text-white shadow-lg shadow-orange-500/20 group-hover:scale-105 transition-transform">
            <Utensils className="w-5 h-5" />
          </div>
          <div>
            <span className="font-bold text-lg tracking-tight gradient-text">GourmetQR</span>
            <span className="hidden sm:inline-block ml-2 text-xs font-medium px-2 py-0.5 rounded-full bg-slate-800 text-orange-400 border border-orange-500/20">
              SaaS Platform
            </span>
          </div>
        </Link>

        {/* View Switcher / Quick Navigation */}
        <nav className="hidden md:flex items-center gap-1 bg-slate-900/80 p-1 rounded-xl border border-slate-800">
          <Link
            href="/t/tbl-001"
            className={`flex items-center gap-2 px-3.5 py-1.5 rounded-lg text-xs font-semibold transition-all ${
              activeView === 'customer'
                ? 'bg-gradient-to-r from-orange-500 to-rose-500 text-white shadow-md'
                : 'text-slate-400 hover:text-white hover:bg-slate-800'
            }`}
          >
            <QrCode className="w-3.5 h-3.5" />
            Customer Menu
          </Link>
          <Link
            href="/kitchen"
            className={`flex items-center gap-2 px-3.5 py-1.5 rounded-lg text-xs font-semibold transition-all ${
              activeView === 'kitchen'
                ? 'bg-gradient-to-r from-orange-500 to-rose-500 text-white shadow-md'
                : 'text-slate-400 hover:text-white hover:bg-slate-800'
            }`}
          >
            <ChefHat className="w-3.5 h-3.5" />
            Kitchen Display
          </Link>
          <Link
            href="/admin"
            className={`flex items-center gap-2 px-3.5 py-1.5 rounded-lg text-xs font-semibold transition-all ${
              activeView === 'admin'
                ? 'bg-gradient-to-r from-orange-500 to-rose-500 text-white shadow-md'
                : 'text-slate-400 hover:text-white hover:bg-slate-800'
            }`}
          >
            <LayoutDashboard className="w-3.5 h-3.5" />
            Admin Panel
          </Link>
        </nav>

        {/* Right Action Icons */}
        <div className="flex items-center gap-3">
          {activeView === 'customer' && (
            <>
              {/* Table Pill */}
              <div className="hidden sm:flex items-center gap-1.5 px-3 py-1 rounded-full bg-slate-800/80 text-slate-300 text-xs font-medium border border-slate-700">
                <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
                <span>{tableName}</span>
              </div>

              {/* Call Waiter Button */}
              {onCallWaiter && (
                <button
                  onClick={onCallWaiter}
                  className="flex items-center gap-1.5 px-3 py-1.5 rounded-xl bg-slate-800 hover:bg-slate-700 text-amber-400 text-xs font-medium border border-amber-500/20 transition-all hover:border-amber-500/40"
                  title="Call Waiter to Table"
                >
                  <Bell className="w-3.5 h-3.5" />
                  <span className="hidden xs:inline">Call Waiter</span>
                </button>
              )}

              {/* Cart Button */}
              {onOpenCart && (
                <button
                  onClick={onOpenCart}
                  className="relative flex items-center justify-center p-2.5 rounded-xl gradient-btn text-white shadow-md"
                >
                  <ShoppingBag className="w-5 h-5" />
                  {cartCount > 0 && (
                    <span className="absolute -top-1.5 -right-1.5 bg-rose-500 text-white text-[10px] font-bold w-5 h-5 rounded-full flex items-center justify-center border-2 border-slate-900 animate-bounce">
                      {cartCount}
                    </span>
                  )}
                </button>
              )}
            </>
          )}
        </div>
      </div>
    </header>
  );
};
