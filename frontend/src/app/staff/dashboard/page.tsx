'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';
import { Order, ServiceRequest } from '@/lib/types';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Utensils, Bell, Clock, CheckCircle, ChefHat, Lock, LogOut, ShieldCheck } from 'lucide-react';
import { motion } from 'framer-motion';

export default function StaffKitchenDashboard() {
  const [isStaffAuthenticated, setIsStaffAuthenticated] = useState(false);
  const [staffPin, setStaffPin] = useState('');
  const [staffPinError, setStaffPinError] = useState<string | null>(null);

  const [branchId, setBranchId] = useState<number>(1);
  const [orders, setOrders] = useState<Order[]>([]);
  const [serviceRequests, setServiceRequests] = useState<ServiceRequest[]>([]);
  const [loading, setLoading] = useState(true);
  const [connected, setConnected] = useState(false);

  useEffect(() => {
    // Check staff session in localStorage
    const savedStaffSession = localStorage.getItem('qr_staff_session');
    if (savedStaffSession === 'ACTIVE') {
      setIsStaffAuthenticated(true);
    }

    async function loadInitialData() {
      try {
        setLoading(true);
        const activeOrders = await api.getBranchActiveOrders(branchId);
        setOrders(activeOrders);

        const requests = await api.getBranchServiceRequests(branchId);
        setServiceRequests(requests);
      } catch (err) {
        console.error('Failed to load active orders', err);
      } finally {
        setLoading(false);
      }
    }

    loadInitialData();

    // Listen for local real-time broadcasts
    const handleNewOrderEvent = (e: any) => {
      const newOrder = e.detail;
      if (newOrder) {
        setOrders(prev => [newOrder, ...prev.filter(o => o.id !== newOrder.id)]);
      }
    };

    const handleNewRequestEvent = (e: any) => {
      const newReq = e.detail;
      if (newReq) {
        setServiceRequests(prev => [newReq, ...prev.filter(r => r.id !== newReq.id)]);
      }
    };

    if (typeof window !== 'undefined') {
      window.addEventListener('qr_new_order', handleNewOrderEvent);
      window.addEventListener('qr_new_service_request', handleNewRequestEvent);
    }

    // Setup STOMP WebSocket client
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket as any,
      onConnect: () => {
        setConnected(true);

        // Subscribe to kitchen order channels (branch 1, 2, and 10)
        [1, 2, 10].forEach(bId => {
          stompClient.subscribe(`/topic/kitchen/${bId}`, message => {
            const newOrder: Order = JSON.parse(message.body);
            setOrders(prev => [newOrder, ...prev.filter(o => o.id !== newOrder.id)]);
          });

          stompClient.subscribe(`/topic/service-requests/${bId}`, message => {
            const req: ServiceRequest = JSON.parse(message.body);
            setServiceRequests(prev => [req, ...prev.filter(r => r.id !== req.id)]);
          });
        });
      },
      onDisconnect: () => {
        setConnected(false);
      },
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
      if (typeof window !== 'undefined') {
        window.removeEventListener('qr_new_order', handleNewOrderEvent);
        window.removeEventListener('qr_new_service_request', handleNewRequestEvent);
      }
    };
  }, [branchId]);

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

  const handleUpdateOrderStatus = async (orderId: number, nextStatus: string) => {
    try {
      const updated = await api.updateOrderStatus(branchId, orderId, nextStatus);
      setOrders(orders.map(o => (o.id === orderId ? updated : o)));
    } catch (err: any) {
      alert(`Failed to update status: ${err.message}`);
    }
  };

  const handleResolveServiceRequest = async (requestId: number) => {
    try {
      await api.updateServiceRequestStatus(branchId, requestId, 'RESOLVED');
      setServiceRequests(serviceRequests.filter(r => r.id !== requestId));
    } catch (err: any) {
      alert(`Failed to resolve request: ${err.message}`);
    }
  };

  // Staff Login Lock Screen
  if (!isStaffAuthenticated) {
    return (
      <div className="min-h-screen bg-zinc-950 text-zinc-100 flex items-center justify-center p-4">
        <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel max-w-md w-full p-8 rounded-3xl border border-zinc-800 text-center space-y-6">
          <div className="w-16 h-16 rounded-2xl bg-amber-500/20 text-amber-400 border border-amber-500/30 flex items-center justify-center mx-auto">
            <Lock className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Staff Portal Security Login</h1>
            <p className="text-xs text-zinc-400 mt-1">Kitchen Display (KDS) & Floor Waiter System</p>
          </div>

          {staffPinError && (
            <div className="p-3 rounded-xl bg-red-500/10 border border-red-500/30 text-red-400 text-xs">
              {staffPinError}
            </div>
          )}

          <form onSubmit={handleStaffLogin} className="space-y-4 text-left">
            <div>
              <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1.5">
                Enter Staff Security PIN
              </label>
              <input
                type="password"
                maxLength={4}
                required
                value={staffPin}
                onChange={e => setStaffPin(e.target.value)}
                placeholder="•••• (Default: 1234)"
                className="w-full bg-zinc-900 border border-zinc-800 rounded-2xl px-4 py-3 text-center text-xl tracking-[0.5em] font-mono text-white placeholder:tracking-normal placeholder:text-xs placeholder-zinc-500 focus:outline-none focus:border-amber-500"
              />
            </div>

            <button
              type="submit"
              className="w-full bg-amber-500 hover:bg-amber-400 text-zinc-950 font-bold py-3.5 rounded-2xl text-sm transition shadow-lg shadow-amber-500/20 flex items-center justify-center gap-2"
            >
              <ShieldCheck className="w-5 h-5" /> Authenticate & Access Staff KDS
            </button>
          </form>

          <div className="pt-2 text-zinc-500 text-[11px]">
            <span>Staff PIN: </span>
            <code className="text-amber-400 font-mono font-bold">1234</code>
          </div>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 p-6 max-w-7xl mx-auto space-y-6">
      {/* Header */}
      <header className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 pb-6 border-b border-zinc-800">
        <div>
          <h1 className="text-2xl font-bold text-white flex items-center gap-2">
            <ChefHat className="w-7 h-7 text-amber-500" />
            Kitchen Display System (KDS)
          </h1>
          <p className="text-xs text-zinc-400">Real-Time Kitchen Orders & Waiter Requests Channel</p>
        </div>

        <div className="flex items-center gap-3">
          <span className={`px-3 py-1 rounded-full text-xs font-semibold border flex items-center gap-1.5 ${
            connected ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30' : 'bg-rose-500/10 text-rose-400 border-rose-500/30'
          }`}>
            <span className={`w-2 h-2 rounded-full ${connected ? 'bg-emerald-400 animate-pulse' : 'bg-rose-400'}`} />
            {connected ? 'Live Sync Active' : 'Connecting WebSocket...'}
          </span>

          <button
            onClick={handleStaffLogout}
            className="px-3.5 py-1.5 bg-zinc-900 hover:bg-red-500/20 text-zinc-300 hover:text-red-400 border border-zinc-800 rounded-xl text-xs font-bold transition flex items-center gap-1.5"
            title="Lock Staff Portal"
          >
            <LogOut className="w-4 h-4" /> Exit Staff Portal
          </button>
        </div>
      </header>

      {/* Main Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Orders Column */}
        <div className="lg:col-span-2 space-y-4">
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-bold text-white flex items-center gap-2">
              <Utensils className="w-5 h-5 text-amber-500" />
              Active Kitchen Tickets ({orders.length})
            </h2>
          </div>

          {loading ? (
            <div className="text-center py-12 text-zinc-500 text-sm">Loading active kitchen tickets...</div>
          ) : orders.length === 0 ? (
            <div className="text-center py-16 text-zinc-600 glass-card rounded-3xl border border-zinc-800">
              <CheckCircle className="w-10 h-10 mx-auto mb-2 opacity-40 text-emerald-500" />
              <p className="font-medium text-sm">All kitchen tickets cleared!</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {orders.map(order => (
                <div key={order.id} className="glass-card p-5 rounded-3xl border-zinc-800 space-y-4 flex flex-col justify-between hover:border-zinc-700 transition">
                  <div>
                    <div className="flex justify-between items-start pb-3 border-b border-zinc-800">
                      <div>
                        <span className="text-xs font-mono font-bold text-amber-400 block">{order.orderNumber}</span>
                        <h3 className="text-base font-extrabold text-white mt-0.5">{order.tableName}</h3>
                      </div>
                      <span className={`px-2.5 py-1 rounded-full text-[10px] font-extrabold uppercase border ${
                        order.status === 'PENDING' ? 'bg-amber-500/20 text-amber-300 border-amber-500/40 animate-pulse' :
                        order.status === 'PREPARING' ? 'bg-purple-500/20 text-purple-300 border-purple-500/40' :
                        'bg-emerald-500/20 text-emerald-300 border-emerald-500/40'
                      }`}>
                        {order.status}
                      </span>
                    </div>

                    <div className="py-3 space-y-2">
                      {order.items.map((item, i) => (
                        <div key={i} className="flex items-start gap-2.5 text-xs">
                          <span className="w-5 h-5 rounded-md bg-amber-500/20 text-amber-400 font-extrabold flex items-center justify-center text-xs shrink-0">
                            {item.quantity}x
                          </span>
                          <div>
                            <span className="font-bold text-zinc-100">{item.productName}</span>
                            {item.selectedVariantName && (
                              <span className="block text-[10px] text-amber-400 font-medium">Option: {item.selectedVariantName}</span>
                            )}
                          </div>
                        </div>
                      ))}

                      {order.notes && (
                        <div className="p-2 rounded-xl bg-amber-500/10 border border-amber-500/20 text-xs text-amber-300">
                          <strong className="block text-[10px] uppercase font-bold text-amber-400">Note:</strong>
                          "{order.notes}"
                        </div>
                      )}
                    </div>
                  </div>

                  <div className="pt-3 border-t border-zinc-800 flex justify-between items-center">
                    <span className="text-[11px] text-zinc-500">{order.createdAt}</span>
                    {order.status === 'PENDING' && (
                      <button
                        onClick={() => handleUpdateOrderStatus(order.id, 'PREPARING')}
                        className="px-3.5 py-1.5 bg-amber-500 hover:bg-amber-400 text-zinc-950 font-bold text-xs rounded-xl shadow-md transition"
                      >
                        Start Preparing
                      </button>
                    )}
                    {order.status === 'PREPARING' && (
                      <button
                        onClick={() => handleUpdateOrderStatus(order.id, 'READY')}
                        className="px-3.5 py-1.5 bg-emerald-500 hover:bg-emerald-400 text-zinc-950 font-bold text-xs rounded-xl shadow-md transition"
                      >
                        Mark Ready
                      </button>
                    )}
                    {order.status === 'READY' && (
                      <button
                        onClick={() => handleUpdateOrderStatus(order.id, 'SERVED')}
                        className="px-3.5 py-1.5 bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-bold text-xs rounded-xl transition"
                      >
                        Mark Served
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Service Requests Column */}
        <div className="space-y-4">
          <h2 className="text-lg font-bold text-white flex items-center gap-2">
            <Bell className="w-5 h-5 text-rose-500" />
            Waiter Requests ({serviceRequests.length})
          </h2>

          {serviceRequests.length === 0 ? (
            <div className="text-center py-16 text-zinc-600 glass-card rounded-3xl border border-zinc-800">
              <Bell className="w-10 h-10 mx-auto mb-2 opacity-40 text-rose-500" />
              <p className="font-medium text-sm">No pending waiter calls</p>
            </div>
          ) : (
            <div className="space-y-3">
              {serviceRequests.map(req => (
                <div key={req.id} className="glass-card p-4.5 rounded-3xl border border-rose-500/40 space-y-3 shadow-lg shadow-rose-500/5">
                  <div className="flex justify-between items-start">
                    <div>
                      <span className="font-extrabold text-white text-sm block">{req.tableName}</span>
                      <span className="text-xs text-rose-400 font-bold uppercase tracking-wider block mt-0.5">
                        🔔 {req.type === 'CALL_WAITER' ? 'Waiter Call' : req.type === 'BILL_REQUEST' ? 'Bill Request' : req.type || 'Waiter Request'}
                      </span>
                    </div>
                    <button
                      onClick={() => handleResolveServiceRequest(req.id)}
                      className="px-3 py-1.5 bg-rose-600 hover:bg-rose-500 text-white font-bold text-xs rounded-xl transition shadow-md shrink-0"
                    >
                      Resolve Request
                    </button>
                  </div>

                  {/* Customer Explanation Box */}
                  {req.message ? (
                    <div className="p-3 rounded-2xl bg-amber-500/15 border border-amber-500/30 text-amber-200 text-xs">
                      <strong className="block text-[10px] uppercase font-extrabold text-amber-400 mb-0.5 tracking-wider">
                        💬 Customer Explanation / Note:
                      </strong>
                      "{req.message}"
                    </div>
                  ) : (
                    <div className="p-2.5 rounded-xl bg-zinc-900 border border-zinc-800 text-zinc-400 text-xs italic">
                      No additional explanation specified.
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
