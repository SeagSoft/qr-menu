import Link from 'next/link';
import { Utensils, ChefHat, ShieldCheck, ArrowRight, Receipt } from 'lucide-react';

export default function Home() {
  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 flex flex-col items-center justify-center p-6 relative overflow-hidden">
      {/* Background Glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-purple-600/15 blur-[120px] rounded-full pointer-events-none" />

      <main className="max-w-3xl w-full text-center space-y-8 relative z-10">
        <div>
          <div className="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-purple-500/10 border border-purple-500/20 text-purple-400 text-xs font-semibold mb-4">
            <Utensils className="w-3.5 h-3.5" /> Staff & Management Operations Portal
          </div>
          <h1 className="text-3xl sm:text-5xl font-black text-white tracking-tight">
            Restaurant Operations & Staff Control
          </h1>
          <p className="text-sm sm:text-base text-zinc-400 mt-3 max-w-lg mx-auto leading-relaxed">
            Manage live kitchen orders, cashier register sales, and branch performance settings.
          </p>
        </div>

        {/* Staff System Portals */}
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 text-left">
          {/* 1. Kitchen Display */}
          <Link href="/staff/dashboard" className="glass-card p-5 rounded-3xl border-zinc-800 hover:border-amber-500/50 transition group flex flex-col justify-between">
            <div>
              <div className="w-10 h-10 rounded-2xl bg-amber-600/20 text-amber-400 flex items-center justify-center mb-3">
                <ChefHat className="w-5 h-5" />
              </div>
              <h3 className="font-bold text-sm text-white group-hover:text-amber-300 transition">Kitchen Display (KDS)</h3>
              <p className="text-xs text-zinc-400 mt-1">Live WebSocket order & waiter call tickets.</p>
            </div>
            <div className="mt-4 flex items-center text-xs font-semibold text-amber-400">
              Open KDS <ArrowRight className="w-3.5 h-3.5 ml-1" />
            </div>
          </Link>

          {/* 2. Cashier Register Login */}
          <Link href="/cashier" className="glass-card p-5 rounded-3xl border-zinc-800 hover:border-blue-500/50 transition group flex flex-col justify-between">
            <div>
              <div className="w-10 h-10 rounded-2xl bg-blue-600/20 text-blue-400 flex items-center justify-center mb-3">
                <Receipt className="w-5 h-5" />
              </div>
              <h3 className="font-bold text-sm text-white group-hover:text-blue-300 transition">Cashier Login</h3>
              <p className="text-xs text-zinc-400 mt-1">Served sales audit & receipt register portal.</p>
            </div>
            <div className="mt-4 flex items-center text-xs font-semibold text-blue-400">
              Cashier Portal <ArrowRight className="w-3.5 h-3.5 ml-1" />
            </div>
          </Link>

          {/* 3. Admin Control Panel */}
          <Link href="/admin/dashboard" className="glass-card p-5 rounded-3xl border-zinc-800 hover:border-emerald-500/50 transition group flex flex-col justify-between">
            <div>
              <div className="w-10 h-10 rounded-2xl bg-emerald-600/20 text-emerald-400 flex items-center justify-center mb-3">
                <ShieldCheck className="w-5 h-5" />
              </div>
              <h3 className="font-bold text-sm text-white group-hover:text-emerald-300 transition">Admin Panel</h3>
              <p className="text-xs text-zinc-400 mt-1">Branch management, sales & QR code generator.</p>
            </div>
            <div className="mt-4 flex items-center text-xs font-semibold text-emerald-400">
              Open Panel <ArrowRight className="w-3.5 h-3.5 ml-1" />
            </div>
          </Link>
        </div>
      </main>
    </div>
  );
}
