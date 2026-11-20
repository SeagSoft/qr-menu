'use client';

import React, { useState, useEffect } from 'react';
import { Navbar } from '@/components/Navbar';
import { Order, OrderStatus } from '@/lib/types';
import { api, clearAllAuthStorage, clearLegacyLocalStorageAuth, fetchActiveKitchenOrders, updateOrderStatusInBackend } from '@/lib/api';
import { ShoppingBag, Clock, CheckCircle2, Play, Check, Filter, Search, Lock, LogOut, ShieldCheck } from 'lucide-react';
import { motion } from 'framer-motion';

export default function StaffOrdersPage() {
  const [isStaffAuthenticated, setIsStaffAuthenticated] = useState(false);
  const [staffPin, setStaffPin] = useState('');
  const [staffPinError, setStaffPinError] = useState<string | null>(null);

  const [orders, setOrders] = useState<Order[]>([]);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');
  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    clearLegacyLocalStorageAuth();
    const savedStaffSession = sessionStorage.getItem('qr_staff_session');
    const savedToken = sessionStorage.getItem('token');
    if (savedStaffSession === 'ACTIVE' && savedToken) {
      setIsStaffAuthenticated(true);
    } else {
      setIsStaffAuthenticated(false);
    }
  }, []);

  const loadOrders = async () => {
    try {
      const apiOrders = await fetchActiveKitchenOrders(1);
      if (apiOrders && Array.isArray(apiOrders)) {
        setOrders(apiOrders);
      }
    } catch (err) {
      console.error('Failed to load staff orders', err);
    }
  };

  useEffect(() => {
    if (!isStaffAuthenticated) return;
    loadOrders();
  }, [isStaffAuthenticated]);

  const handleStaffLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setStaffPinError(null);
    const pin = staffPin.trim();
    try {
      const authRes = await api.login({
        email: 'waiter@gourmet.com',
        password: pin,
      });
      sessionStorage.setItem('token', authRes.accessToken);
      sessionStorage.setItem('qr_staff_session', 'ACTIVE');
      sessionStorage.setItem('staffUser', JSON.stringify(authRes.user));
      setIsStaffAuthenticated(true);
    } catch (err: any) {
      setStaffPinError(err.message || 'Invalid Staff PIN code. Default Staff PIN: 1234');
    }
  };

  const handleStaffLogout = () => {
    clearAllAuthStorage();
    setIsStaffAuthenticated(false);
    setStaffPin('');
  };

  const handleUpdateStatus = async (orderId: number, newStatus: OrderStatus) => {
    setOrders(prev =>
      prev.map(o => (o.id === orderId ? { ...o, status: newStatus } : o))
    );
    await updateOrderStatusInBackend(1, orderId, newStatus);
  };

  const filteredOrders = orders.filter(o => {
    const matchesStatus = filterStatus === 'ALL' || o.status === filterStatus;
    const matchesSearch = o.orderNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
      o.tableName.toLowerCase().includes(searchTerm.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  // Staff Login Lock Screen
  if (!isStaffAuthenticated) {
    return (
      <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex items-center justify-center p-4">
        <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel max-w-md w-full p-8 rounded-3xl border border-slate-800 text-center space-y-6">
          <div className="w-16 h-16 rounded-2xl bg-orange-500/20 text-orange-400 border border-orange-500/30 flex items-center justify-center mx-auto">
            <Lock className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Staff Orders Stream Access</h1>
            <p className="text-xs text-slate-400 mt-1">Staff Security Login Portal • Enter Staff PIN</p>
          </div>

          {staffPinError && (
            <div className="p-3 rounded-xl bg-red-500/10 border border-red-500/30 text-red-400 text-xs">
              {staffPinError}
            </div>
          )}

          <form onSubmit={handleStaffLogin} className="space-y-4 text-left">
            <div>
              <label className="text-xs font-bold uppercase tracking-wider text-slate-400 block mb-1.5">
                Staff Security PIN Code
              </label>
              <input
                type="password"
                maxLength={4}
                required
                value={staffPin}
                onChange={e => setStaffPin(e.target.value)}
                placeholder="•••• (Default: 1234)"
                className="w-full bg-slate-900 border border-slate-800 rounded-2xl px-4 py-3 text-center text-xl tracking-[0.5em] font-mono text-white placeholder:tracking-normal placeholder:text-xs placeholder-slate-500 focus:outline-none focus:border-orange-500"
              />
            </div>

            <button
              type="submit"
              className="w-full bg-gradient-to-r from-orange-500 to-rose-500 hover:from-orange-400 hover:to-rose-400 text-white font-bold py-3.5 rounded-2xl text-sm transition shadow-lg shadow-orange-500/20 flex items-center justify-center gap-2"
            >
              <ShieldCheck className="w-5 h-5" /> Authenticate & Access Live Orders
            </button>
          </form>

          <div className="pt-2 text-slate-500 text-[11px]">
            <span>Staff PIN: </span>
            <code className="text-orange-400 font-mono font-bold">1234</code>
          </div>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex flex-col pb-16">
      <Navbar activeView="admin" />

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 w-full flex-1">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
          <div>
            <div className="flex items-center gap-2 text-xs font-semibold text-orange-400 mb-1">
              <ShoppingBag className="w-4 h-4" />
              <span>Staff Order Management</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-extrabold text-white">Live Orders Stream</h1>
          </div>

          <div className="flex flex-wrap items-center gap-3">
            <div className="relative">
              <Search className="w-4 h-4 absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" />
              <input
                type="text"
                placeholder="Search order or table..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="bg-slate-900/90 border border-slate-800 rounded-xl pl-9 pr-4 py-2 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-orange-500"
              />
            </div>

            <button
              onClick={handleStaffLogout}
              className="flex items-center gap-1.5 px-3.5 py-2 rounded-xl bg-slate-900 hover:bg-red-500/20 text-slate-300 hover:text-red-400 border border-slate-800 text-xs font-bold transition-all"
              title="Lock Orders Stream"
            >
              <LogOut className="w-4 h-4" />
              Exit Staff
            </button>

            <div className="flex items-center gap-1 bg-slate-900/90 p-1 rounded-xl border border-slate-800">
              {['ALL', 'PENDING', 'PREPARING', 'READY', 'SERVED'].map((status) => (
                <button
                  key={status}
                  onClick={() => setFilterStatus(status)}
                  className={`px-3 py-1 rounded-lg text-xs font-bold transition-all ${filterStatus === status
                      ? 'gradient-btn text-white shadow'
                      : 'text-slate-400 hover:text-white hover:bg-slate-800'
                    }`}
                >
                  {status}
                </button>
              ))}
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredOrders.map((order) => (
            <div key={order.id} className="glass-card rounded-3xl p-5 border border-slate-800 flex flex-col justify-between">
              <div>
                <div className="flex items-center justify-between pb-3 border-b border-slate-800">
                  <div>
                    <span className="text-xs font-mono font-bold text-orange-400 block">{order.orderNumber}</span>
                    <h3 className="text-base font-extrabold text-white mt-0.5">{order.tableName}</h3>
                  </div>
                  <span className="px-2.5 py-1 rounded-full text-xs font-bold bg-slate-800 text-orange-400 border border-orange-500/20">
                    {order.status}
                  </span>
                </div>

                <div className="py-4 space-y-3">
                  {order.items.map((item, idx) => (
                    <div key={idx} className="flex items-start justify-between text-xs">
                      <div className="flex gap-2.5">
                        <span className="w-6 h-6 rounded-lg bg-orange-500/20 text-orange-400 font-extrabold flex items-center justify-center flex-shrink-0">
                          {item.quantity}x
                        </span>
                        <div>
                          <span className="font-bold text-slate-100 text-sm">{item.productName}</span>
                          {item.selectedVariantName && (
                            <span className="block text-[11px] text-orange-400">Option: {item.selectedVariantName}</span>
                          )}
                        </div>
                      </div>
                      <span className="font-mono text-slate-300">₺{item.totalPrice}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="pt-3 border-t border-slate-800 flex items-center justify-between">
                <span className="text-xs font-extrabold text-white">Total: ₺{order.totalAmount}</span>
                {order.status === 'READY' && (
                  <button
                    onClick={() => handleUpdateStatus(order.id, 'SERVED')}
                    className="px-3 py-1.5 rounded-xl bg-emerald-500 hover:bg-emerald-600 text-slate-950 text-xs font-bold"
                  >
                    Mark Served
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
