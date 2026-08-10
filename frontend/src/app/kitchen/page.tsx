'use client';

import React, { useState, useEffect } from 'react';
import { Navbar } from '@/components/Navbar';
import { MOCK_ORDERS } from '@/lib/mockData';
import { Order, OrderStatus } from '@/lib/types';
import { fetchActiveKitchenOrders, updateOrderStatusInBackend } from '@/lib/api';
import { ChefHat, Clock, CheckCircle2, Play, Check, Plus, RefreshCw, Lock, LogOut, ShieldCheck } from 'lucide-react';
import { motion } from 'framer-motion';

export default function KitchenPage() {
  const [isStaffAuthenticated, setIsStaffAuthenticated] = useState(false);
  const [staffPin, setStaffPin] = useState('');
  const [staffPinError, setStaffPinError] = useState<string | null>(null);

  const [orders, setOrders] = useState<Order[]>(MOCK_ORDERS);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');
  const [loading, setLoading] = useState<boolean>(false);
  const [isRefreshing, setIsRefreshing] = useState<boolean>(false);

  useEffect(() => {
    // Check staff session in localStorage
    const savedStaffSession = localStorage.getItem('qr_staff_session');
    if (savedStaffSession === 'ACTIVE') {
      setIsStaffAuthenticated(true);
    }
  }, []);

  // Fetch backend orders or fallback to mock
  const loadOrders = async () => {
    setIsRefreshing(true);
    const apiOrders = await fetchActiveKitchenOrders(1);
    if (apiOrders && Array.isArray(apiOrders)) {
      setOrders(apiOrders);
    }
    setIsRefreshing(false);
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const handleStaffLogin = (e: React.FormEvent) => {
    e.preventDefault();
    const pin = staffPin.trim();
    const storedRosterStr = typeof window !== 'undefined' ? localStorage.getItem('qr_staff_list') : null;
    const roster = storedRosterStr ? JSON.parse(storedRosterStr) : [
      { name: 'Gordon Ramsay', pin: '1234', isOnDuty: true },
      { name: 'Alex Morgan', pin: '2345', isOnDuty: true },
      { name: 'Marco Pierre', pin: '3456', isOnDuty: false },
      { name: 'Sarah Jenkins', pin: '4567', isOnDuty: true }
    ];

    const foundMember = roster.find((s: any) => s.pin === pin);

    if (foundMember) {
      if (!foundMember.isOnDuty) {
        setStaffPinError(`⛔ Access Denied: Staff member "${foundMember.name}" is currently OFF DUTY. Ask your manager to activate your shift in the Admin Panel.`);
        return;
      }
      setIsStaffAuthenticated(true);
      localStorage.setItem('qr_staff_session', 'ACTIVE');
      setStaffPinError(null);
      return;
    }

    if (['9999', '0000'].includes(pin)) {
      setIsStaffAuthenticated(true);
      localStorage.setItem('qr_staff_session', 'ACTIVE');
      setStaffPinError(null);
      return;
    }

    setStaffPinError('Invalid Staff PIN code. Default Staff PIN: 1234');
  };

  const handleStaffLogout = () => {
    setIsStaffAuthenticated(false);
    localStorage.removeItem('qr_staff_session');
    setStaffPin('');
  };

  const handleUpdateStatus = async (orderId: number, newStatus: OrderStatus) => {
    // Optimistic UI update
    setOrders(prev =>
      prev.map(o => (o.id === orderId ? { ...o, status: newStatus } : o))
    );
    await updateOrderStatusInBackend(1, orderId, newStatus);
  };

  const handleAddDemoOrder = () => {
    const newDemoOrder: Order = {
      id: Date.now(),
      orderNumber: `ORD-${Math.floor(1000 + Math.random() * 9000)}`,
      tableId: 100,
      tableName: `Table 0${Math.floor(1 + Math.random() * 5)}`,
      branchId: 10,
      branchName: "Bosphorus Main Branch",
      items: [
        { id: 1, productName: "Truffle Wagyu Ribeye Steak", quantity: 1, unitPrice: 850, totalPrice: 850, selectedVariantName: "Medium Rare" },
        { id: 2, productName: "Iced Caramel Macchiato", quantity: 1, unitPrice: 120, totalPrice: 120 }
      ],
      totalAmount: 970,
      status: "PENDING",
      createdAt: "Just now",
      updatedAt: "Just now",
      notes: "Demo order added by Kitchen Chef"
    };

    setOrders(prev => [newDemoOrder, ...prev]);
  };

  const filteredOrders = orders.filter(o => {
    if (filterStatus === 'ALL') return o.status !== 'SERVED' && o.status !== 'CANCELLED';
    return o.status === filterStatus;
  });

  const getStatusBadge = (status: OrderStatus) => {
    switch (status) {
      case 'PENDING':
        return <span className="bg-amber-500/20 text-amber-400 border border-amber-500/30 px-2.5 py-1 rounded-full text-xs font-bold animate-pulse">New Ticket</span>;
      case 'PREPARING':
        return <span className="bg-orange-500/20 text-orange-400 border border-orange-500/30 px-2.5 py-1 rounded-full text-xs font-bold">Cooking...</span>;
      case 'READY':
        return <span className="bg-emerald-500/20 text-emerald-400 border border-emerald-500/30 px-2.5 py-1 rounded-full text-xs font-bold">Ready to Serve</span>;
      case 'SERVED':
        return <span className="bg-slate-800 text-slate-400 px-2.5 py-1 rounded-full text-xs font-bold">Served</span>;
      default:
        return null;
    }
  };

  // Staff Login Lock Screen
  if (!isStaffAuthenticated) {
    return (
      <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex items-center justify-center p-4">
        <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel max-w-md w-full p-8 rounded-3xl border border-slate-800 text-center space-y-6">
          <div className="w-16 h-16 rounded-2xl bg-orange-500/20 text-orange-400 border border-orange-500/30 flex items-center justify-center mx-auto">
            <Lock className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Kitchen Display System Access</h1>
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
              <ShieldCheck className="w-5 h-5" /> Authenticate & Access Kitchen KDS
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
      <Navbar activeView="kitchen" />

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 w-full flex-1">
        
        {/* Header Bar */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
          <div>
            <div className="flex items-center gap-2 text-xs font-semibold text-orange-400 mb-1">
              <ChefHat className="w-4 h-4" />
              <span>Real-Time Kitchen Display System (KDS)</span>
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

            {/* Add Demo Order Button */}
            <button
              onClick={handleAddDemoOrder}
              className="flex items-center gap-2 px-3.5 py-2 rounded-xl gradient-btn text-white text-xs font-bold shadow-md"
            >
              <Plus className="w-4 h-4" />
              Add Demo Order
            </button>

            {/* Exit / Lock Button */}
            <button
              onClick={handleStaffLogout}
              className="flex items-center gap-1.5 px-3.5 py-2 rounded-xl bg-slate-900 hover:bg-red-500/20 text-slate-300 hover:text-red-400 border border-slate-800 text-xs font-bold transition-all"
              title="Lock Kitchen KDS"
            >
              <LogOut className="w-4 h-4" />
              Exit KDS
            </button>

            {/* Status Filter Tabs */}
            <div className="flex items-center gap-1 bg-slate-900/90 p-1 rounded-xl border border-slate-800">
              {['ALL', 'PENDING', 'PREPARING', 'READY', 'SERVED'].map((status) => (
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

        {/* Orders Grid */}
        {filteredOrders.length === 0 ? (
          <div className="glass-panel p-12 rounded-3xl text-center text-slate-500 my-8">
            <CheckCircle2 className="w-12 h-12 mx-auto stroke-1 mb-3 text-emerald-500" />
            <p className="text-base font-bold text-slate-200">No tickets in this section</p>
            <p className="text-xs text-slate-500 mt-1">Click "Add Demo Order" above or place a customer order from a table QR menu.</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredOrders.map((order) => (
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
                      <span className="text-xs font-mono font-bold text-orange-400 block">{order.orderNumber}</span>
                      <h3 className="text-base font-extrabold text-white mt-0.5">{order.tableName}</h3>
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
                            <span className="font-bold text-slate-100 text-sm">{item.productName}</span>
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
                        <strong className="block text-[10px] uppercase font-bold tracking-wider text-amber-400 mb-0.5">Kitchen Note:</strong>
                        "{order.notes}"
                      </div>
                    )}
                  </div>
                </div>

                {/* Footer Action Buttons */}
                <div className="pt-3 border-t border-slate-800 flex items-center justify-between">
                  <div className="flex items-center gap-1.5 text-xs text-slate-400">
                    <Clock className="w-3.5 h-3.5 text-orange-400" />
                    <span>{order.createdAt}</span>
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
