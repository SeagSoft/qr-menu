'use client';

import React from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import {
  Utensils,
  ChefHat,
  LayoutDashboard,
  ShoppingBag,
  Bell,
  ClipboardList,
  Users,
  Receipt,
} from 'lucide-react';

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
  tableName = 'Table 01',
  activeView = 'customer',
  onCallWaiter,
}) => {
  const pathname = usePathname();

  return (
    <header className="sticky top-0 z-40 w-full glass-panel border-b border-zinc-800/80 bg-zinc-950/90 backdrop-blur-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        {/* Brand Logo */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-10 h-10 rounded-xl bg-purple-600/20 border border-purple-500/30 flex items-center justify-center text-purple-400 shadow-lg group-hover:scale-105 transition-transform">
            <Utensils className="w-5 h-5" />
          </div>
          <div>
            <span className="font-bold text-lg tracking-tight text-white">GourmetQR</span>
            <span className="hidden sm:inline-block ml-2 text-[10px] font-bold uppercase px-2 py-0.5 rounded-full bg-zinc-900 text-purple-400 border border-purple-500/30">
              Operasyon
            </span>
          </div>
        </Link>

        {/* Internal Staff & Operations Navigation (No Customer QR links) */}
        {activeView !== 'customer' && (
          <nav className="flex items-center gap-1 bg-zinc-900/90 p-1 rounded-xl border border-zinc-800 overflow-x-auto">
            <Link
              href="/staff/orders"
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-bold transition-all whitespace-nowrap ${
                pathname === '/staff/orders'
                  ? 'bg-purple-600 text-white shadow-md'
                  : 'text-zinc-400 hover:text-white hover:bg-zinc-800'
              }`}
            >
              <ClipboardList className="w-3.5 h-3.5" />
              Sipariş Takibi
            </Link>
            <Link
              href="/staff/tables"
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-bold transition-all whitespace-nowrap ${
                pathname === '/staff/tables'
                  ? 'bg-purple-600 text-white shadow-md'
                  : 'text-zinc-400 hover:text-white hover:bg-zinc-800'
              }`}
            >
              <Users className="w-3.5 h-3.5" />
              Masa Düzeni
            </Link>
            <Link
              href="/cashier"
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-bold transition-all whitespace-nowrap ${
                pathname === '/cashier'
                  ? 'bg-purple-600 text-white shadow-md'
                  : 'text-zinc-400 hover:text-white hover:bg-zinc-800'
              }`}
            >
              <Receipt className="w-3.5 h-3.5" />
              Kasa Paneli
            </Link>
            <Link
              href="/kitchen?token=GOURMET-KDS-2026"
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-bold transition-all whitespace-nowrap ${
                pathname === '/kitchen' || activeView === 'kitchen'
                  ? 'bg-amber-500 text-zinc-950 shadow-md'
                  : 'text-zinc-400 hover:text-white hover:bg-zinc-800'
              }`}
            >
              <ChefHat className="w-3.5 h-3.5" />
              Mutfak (KDS)
            </Link>
            <Link
              href="/admin/dashboard"
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-bold transition-all whitespace-nowrap ${
                pathname?.startsWith('/admin')
                  ? 'bg-emerald-600 text-white shadow-md'
                  : 'text-zinc-400 hover:text-white hover:bg-zinc-800'
              }`}
            >
              <LayoutDashboard className="w-3.5 h-3.5" />
              Yönetici
            </Link>
          </nav>
        )}

        {/* Right Action Icons for Customer View */}
        <div className="flex items-center gap-3">
          {activeView === 'customer' && (
            <>
              <div className="hidden sm:flex items-center gap-1.5 px-3 py-1 rounded-full bg-zinc-900 text-zinc-300 text-xs font-medium border border-zinc-800">
                <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
                <span>{tableName}</span>
              </div>

              {onCallWaiter && (
                <button
                  onClick={onCallWaiter}
                  className="flex items-center gap-1.5 px-3 py-1.5 rounded-xl bg-zinc-900 hover:bg-zinc-800 text-amber-400 text-xs font-medium border border-amber-500/20 transition-all"
                  title="Call Waiter to Table"
                >
                  <Bell className="w-3.5 h-3.5" />
                  <span className="hidden xs:inline">Garson Çağır</span>
                </button>
              )}

              {onOpenCart && (
                <button
                  onClick={onOpenCart}
                  className="relative flex items-center justify-center p-2.5 rounded-xl bg-purple-600 text-white shadow-md"
                >
                  <ShoppingBag className="w-5 h-5" />
                  {cartCount > 0 && (
                    <span className="absolute -top-1.5 -right-1.5 bg-rose-500 text-white text-[10px] font-bold w-5 h-5 rounded-full flex items-center justify-center border-2 border-zinc-950">
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
