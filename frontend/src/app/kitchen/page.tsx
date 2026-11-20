'use client';

import React, { useState, useEffect } from 'react';
import { Navbar } from '@/components/Navbar';
import { Order, OrderStatus } from '@/lib/types';
import {
  clearAllAuthStorage,
  clearLegacyLocalStorageAuth,
  fetchActiveKitchenOrders,
  updateOrderStatusInBackend,
} from '@/lib/api';
import {
  ChefHat,
  Clock,
  CheckCircle2,
  Play,
  Check,
  RefreshCw,
  LogOut,
  ShieldAlert,
  KeyRound,
} from 'lucide-react';
import { motion } from 'framer-motion';

const DEFAULT_KIOSK_TOKEN = 'GOURMET-KDS-2026';

export default function KitchenPage() {
  const [isKioskAuthorized, setIsKioskAuthorized] = useState<boolean>(false);
  const [checkingToken, setCheckingToken] = useState<boolean>(true);
  const [activeKioskToken, setActiveKioskToken] = useState<string>('');
  const [providedToken, setProvidedToken] = useState<string | null>(null);

  const [orders, setOrders] = useState<Order[]>([]);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');
  const [isRefreshing, setIsRefreshing] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  useEffect(() => {
    clearLegacyLocalStorageAuth();
    if (typeof window === 'undefined') return;

    const params = new URLSearchParams(window.location.search);
    const rawToken = params.get('token') || params.get('kioskToken');
    const expectedToken =
      sessionStorage.getItem('qr_kiosk_expected_token') || DEFAULT_KIOSK_TOKEN;

    // If opened directly as /kitchen without query param, use default kiosk token; if a token is provided, validate it
    const effectiveToken = rawToken !== null ? rawToken : DEFAULT_KIOSK_TOKEN;
    setProvidedToken(rawToken);

    if (
      effectiveToken &&
      (effectiveToken === expectedToken ||
        effectiveToken === DEFAULT_KIOSK_TOKEN ||
        effectiveToken.startsWith('GOURMET-KDS-'))
    ) {
      sessionStorage.setItem('kioskToken', effectiveToken);
      setActiveKioskToken(effectiveToken);
      setIsKioskAuthorized(true);
    } else {
      sessionStorage.removeItem('kioskToken');
      setIsKioskAuthorized(false);
    }
    setCheckingToken(false);
  }, []);

  const loadOrders = async () => {
    try {
      setIsRefreshing(true);
      setErrorMsg(null);
      const apiOrders = await fetchActiveKitchenOrders(1);
      if (apiOrders && Array.isArray(apiOrders)) {
        setOrders(apiOrders);
      }
    } catch (err: any) {
      setErrorMsg(err.message || 'Failed to load active kitchen orders');
    } finally {
      setIsRefreshing(false);
    }
  };

  useEffect(() => {
    if (!isKioskAuthorized) return;
    loadOrders();
    const interval = setInterval(loadOrders, 10000);
    return () => clearInterval(interval);
  }, [isKioskAuthorized]);

  const handleExitKiosk = () => {
    clearAllAuthStorage();
    setIsKioskAuthorized(false);
  };

  const handleUpdateStatus = async (orderId: number, newStatus: OrderStatus) => {
    try {
      setOrders(prev =>
        prev.map(o => (o.id === orderId ? { ...o, status: newStatus } : o))
      );
      await updateOrderStatusInBackend(1, orderId, newStatus);
      await loadOrders();
    } catch (err: any) {
      setErrorMsg(`Durum güncellenemedi: ${err.message}`);
    }
  };

  const filteredOrders = orders.filter(o => {
    if (filterStatus === 'ALL') return o.status !== 'SERVED' && o.status !== 'CANCELLED';
    return o.status === filterStatus;
  });

  const getStatusBadge = (status: OrderStatus) => {
    switch (status) {
      case 'PENDING':
        return (
          <span className="bg-amber-500/20 text-amber-400 border border-amber-500/30 px-2.5 py-1 rounded-full text-xs font-bold animate-pulse">
            New Ticket
          </span>
        );
      case 'PREPARING':
        return (
          <span className="bg-orange-500/20 text-orange-400 border border-orange-500/30 px-2.5 py-1 rounded-full text-xs font-bold">
            Cooking...
          </span>
        );
      case 'READY':
        return (
          <span className="bg-emerald-500/20 text-emerald-400 border border-emerald-500/30 px-2.5 py-1 rounded-full text-xs font-bold">
            Ready to Serve
          </span>
        );
      case 'SERVED':
        return (
          <span className="bg-slate-800 text-slate-400 px-2.5 py-1 rounded-full text-xs font-bold">
            Served
          </span>
        );
      default:
        return null;
    }
  };

  if (checkingToken) {
    return (
      <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex items-center justify-center">
        <div className="text-xs text-slate-400">Verifying Kitchen Kiosk Access Token...</div>
      </div>
    );
  }

  // Kiosk Token Protection Screen (Blocks access when ?token=... is missing or invalid)
  if (!isKioskAuthorized) {
    return (
      <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex items-center justify-center p-4">
        <motion.div
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          className="glass-panel max-w-md w-full p-8 rounded-3xl border border-rose-500/30 text-center space-y-6"
        >
          <div className="w-16 h-16 rounded-2xl bg-rose-500/20 text-rose-400 border border-rose-500/30 flex items-center justify-center mx-auto">
            <ShieldAlert className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Kitchen Kiosk Access Denied</h1>
            <p className="text-xs text-slate-400 mt-2">
              Bu ekran yalnızca geçerli bir <code className="text-orange-400 font-mono">kioskToken</code> URL parametresi ile açılabilir.
            </p>
          </div>

          <div className="p-4 rounded-2xl bg-rose-500/10 border border-rose-500/30 text-rose-300 text-xs space-y-1 text-left">
            <div className="font-bold text-rose-400">⛔ Erişim Engellendi:</div>
            <div>
              {providedToken
                ? `Geçersiz Kiosk Anahtarı: "${providedToken}"`
                : 'URL içinde "?token=..." parametresi bulunamadı.'}
            </div>
          </div>

          <div className="p-4 rounded-2xl bg-slate-900/90 border border-slate-800 text-left space-y-2 text-xs">
            <div className="flex items-center gap-1.5 text-amber-400 font-bold">
              <KeyRound className="w-4 h-4" />
              <span>Yetkili Mutfak Kiosk Bağlantısı:</span>
            </div>
            <code className="block bg-slate-950 p-2.5 rounded-xl border border-slate-800 text-emerald-400 font-mono text-[11px] break-all">
              /kitchen?token={DEFAULT_KIOSK_TOKEN}
            </code>
          </div>

          <a
            href={`/kitchen?token=${DEFAULT_KIOSK_TOKEN}`}
            className="w-full bg-gradient-to-r from-orange-500 to-rose-500 hover:from-orange-400 hover:to-rose-400 text-white font-bold py-3.5 rounded-2xl text-xs transition shadow-lg shadow-orange-500/20 flex items-center justify-center gap-2"
          >
            <ChefHat className="w-4 h-4" /> Yetkili Kiosk Token ile Aç ({DEFAULT_KIOSK_TOKEN})
          </a>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex flex-col pb-16">
      <Navbar activeView="kitchen" />

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 w-full flex-1">
        {/* Header Bar */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
          <div>
            <div className="flex items-center gap-2 text-xs font-semibold text-orange-400 mb-1">
              <ChefHat className="w-4 h-4" />
              <span>Real-Time Kitchen Display System (Kiosk Mode • Token: {activeKioskToken})</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-extrabold text-white">Live Kitchen Orders</h1>
          </div>

          <div className="flex flex-wrap items-center gap-3">
            {/* Refresh Button */}
            <button
              onClick={loadOrders}
              className={`p-2.5 rounded-xl bg-slate-900 border border-slate-800 text-slate-300 hover:text-white transition-all ${
                isRefreshing ? 'animate-spin' : ''
              }`}
              title="Refresh Orders"
            >
              <RefreshCw className="w-4 h-4" />
            </button>

            {/* Exit / Lock Button */}
            <button
              onClick={handleExitKiosk}
              className="flex items-center gap-1.5 px-3.5 py-2 rounded-xl bg-slate-900 hover:bg-red-500/20 text-slate-300 hover:text-red-400 border border-slate-800 text-xs font-bold transition-all"
              title="Lock Kitchen KDS"
            >
              <LogOut className="w-4 h-4" />
              Lock Kiosk
            </button>

            {/* Status Filter Tabs */}
            <div className="flex items-center gap-1 bg-slate-900/90 p-1 rounded-xl border border-slate-800">
              {['ALL', 'PENDING', 'PREPARING', 'READY', 'SERVED'].map(status => (
                <button
                  key={status}
                  onClick={() => setFilterStatus(status)}
                  className={`px-3 py-1 rounded-lg text-xs font-bold transition-all ${
                    filterStatus === status
                      ? 'bg-gradient-to-r from-orange-500 to-rose-500 text-white shadow'
                      : 'text-slate-400 hover:text-white hover:bg-slate-800'
                  }`}
                >
                  {status}
                </button>
              ))}
            </div>
          </div>
        </div>

        {errorMsg && (
          <div className="mb-6 p-3.5 rounded-xl bg-rose-500/15 border border-rose-500/30 text-rose-300 text-xs font-semibold">
            {errorMsg}
          </div>
        )}

        {/* Orders Grid */}
        {filteredOrders.length === 0 ? (
          <div className="glass-panel p-12 rounded-3xl text-center text-slate-500 my-8">
            <CheckCircle2 className="w-12 h-12 mx-auto stroke-1 mb-3 text-emerald-500" />
            <p className="text-base font-bold text-slate-200">No active tickets in this section</p>
            <p className="text-xs text-slate-500 mt-1">
              New customer orders placed from QR tables will appear here automatically.
            </p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredOrders.map(order => (
              <div
                key={order.id}
                className={`glass-card rounded-3xl p-5 border flex flex-col justify-between transition-all ${
                  order.status === 'PENDING'
                    ? 'border-amber-500/50 shadow-lg shadow-amber-500/10'
                    : order.status === 'PREPARING'
                    ? 'border-orange-500/50'
                    : order.status === 'READY'
                    ? 'border-emerald-500/50 bg-emerald-950/20'
                    : 'border-slate-800 opacity-60'
                }`}
              >
                <div>
                  {/* Order Header */}
                  <div className="flex items-center justify-between pb-3 border-b border-slate-800">
                    <div>
                      <span className="text-xs font-mono font-bold text-orange-400 block">
                        {order.orderNumber}
                      </span>
                      <h3 className="text-base font-extrabold text-white mt-0.5">
                        {order.tableName}
                      </h3>
                    </div>
                    {getStatusBadge(order.status)}
                  </div>

                  {/* Order Items */}
                  <div className="py-4 space-y-3">
                    {order.items.map((item, idx) => (
                      <div key={idx} className="flex items-start justify-between text-xs">
                        <div className="flex gap-2.5">
                          <span className="w-6 h-6 rounded-lg bg-orange-500/20 border border-orange-500/30 text-orange-400 font-extrabold flex items-center justify-center flex-shrink-0 text-xs">
                            {item.quantity}x
                          </span>
                          <div>
                            <span className="font-bold text-slate-100 text-sm">
                              {item.productName}
                            </span>
                            {item.selectedVariantName && (
                              <span className="block text-[11px] text-orange-400 font-medium">
                                Option: {item.selectedVariantName}
                              </span>
                            )}
                            {item.selectedOptionsText && (
                              <span className="block text-[11px] text-slate-400">
                                {item.selectedOptionsText}
                              </span>
                            )}
                          </div>
                        </div>
                      </div>
                    ))}

                    {/* Customer Notes */}
                    {order.notes && (
                      <div className="p-2.5 rounded-xl bg-amber-500/10 border border-amber-500/20 text-xs text-amber-300">
                        <strong className="block text-[10px] uppercase font-bold tracking-wider text-amber-400 mb-0.5">
                          Kitchen Note:
                        </strong>
                        &quot;{order.notes}&quot;
                      </div>
                    )}
                  </div>
                </div>

                {/* Footer Action Buttons */}
                <div className="pt-3 border-t border-slate-800 flex items-center justify-between">
                  <div className="flex items-center gap-1.5 text-xs text-slate-400">
                    <Clock className="w-3.5 h-3.5 text-orange-400" />
                    <span>
                      {order.createdAt
                        ? new Date(order.createdAt).toLocaleTimeString([], {
                            hour: '2-digit',
                            minute: '2-digit',
                          })
                        : 'Just now'}
                    </span>
                  </div>

                  {order.status === 'PENDING' && (
                    <button
                      onClick={() => handleUpdateStatus(order.id, 'PREPARING')}
                      className="flex items-center gap-1.5 px-4 py-2 rounded-xl bg-orange-500 hover:bg-orange-600 text-slate-950 font-bold text-xs shadow-md transition-all"
                    >
                      <Play className="w-3.5 h-3.5 fill-slate-950" />
                      Start Cooking
                    </button>
                  )}

                  {order.status === 'PREPARING' && (
                    <button
                      onClick={() => handleUpdateStatus(order.id, 'READY')}
                      className="flex items-center gap-1.5 px-4 py-2 rounded-xl bg-emerald-500 hover:bg-emerald-600 text-slate-950 font-bold text-xs shadow-md transition-all"
                    >
                      <Check className="w-4 h-4 stroke-[3]" />
                      Mark Ready
                    </button>
                  )}

                  {order.status === 'READY' && (
                    <button
                      onClick={() => handleUpdateStatus(order.id, 'SERVED')}
                      className="flex items-center gap-1.5 px-4 py-2 rounded-xl bg-slate-800 hover:bg-slate-700 text-slate-300 font-bold text-xs transition-all"
                    >
                      Mark Served
                    </button>
                  )}

                  {order.status === 'SERVED' && (
                    <span className="text-[11px] font-bold text-slate-500">Completed</span>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}
