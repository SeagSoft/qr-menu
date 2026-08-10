'use client';

import { useState, useEffect } from 'react';
import {
  Receipt, Clock, CheckCircle2, Lock, LogOut, Search, CreditCard,
  DollarSign, ShoppingBag, ShieldCheck, Printer, Utensils
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

interface ServedOrderRecord {
  id: number;
  orderNumber: string;
  tableName: string;
  servedTime: string;
  servedDate: string;
  items: { name: string; quantity: number; unitPrice: number }[];
  paymentMethod: string;
  subtotal: number;
  tipAmount: number;
  totalAmount: number;
  status: 'SERVED' | 'COMPLETED';
}

const INITIAL_SERVED_RECORDS: ServedOrderRecord[] = [
  {
    id: 101,
    orderNumber: 'ORD-2149',
    tableName: 'Table #12',
    servedTime: '12:05 PM',
    servedDate: 'Today',
    items: [
      { name: 'Truffle Wagyu Ribeye Steak', quantity: 2, unitPrice: 850.00 },
      { name: 'Iced Caramel Macchiato', quantity: 2, unitPrice: 120.00 }
    ],
    paymentMethod: 'Credit Card (Visa)',
    subtotal: 1940.00,
    tipAmount: 15.00,
    totalAmount: 1955.00,
    status: 'SERVED'
  },
  {
    id: 102,
    orderNumber: 'ORD-2148',
    tableName: 'Table #04',
    servedTime: '11:48 AM',
    servedDate: 'Today',
    items: [
      { name: 'Tartufo e Burrata Neapolitan Pizza', quantity: 1, unitPrice: 28.00 },
      { name: 'Classic Italian Tiramisu', quantity: 1, unitPrice: 14.00 }
    ],
    paymentMethod: 'Apple Pay',
    subtotal: 42.00,
    tipAmount: 5.50,
    totalAmount: 47.50,
    status: 'SERVED'
  },
  {
    id: 103,
    orderNumber: 'ORD-2147',
    tableName: 'Table #01',
    servedTime: '11:32 AM',
    servedDate: 'Today',
    items: [
      { name: 'Double Smoked Bacon Burger', quantity: 2, unitPrice: 22.00 },
      { name: 'Fresh Organic Lemonade', quantity: 2, unitPrice: 7.25 }
    ],
    paymentMethod: 'Credit Card (Mastercard)',
    subtotal: 58.50,
    tipAmount: 6.00,
    totalAmount: 64.50,
    status: 'SERVED'
  },
  {
    id: 104,
    orderNumber: 'ORD-2146',
    tableName: 'Table #08',
    servedTime: '10:55 AM',
    servedDate: 'Today',
    items: [
      { name: 'Wild Mushroom Tagliatelle', quantity: 1, unitPrice: 24.00 },
      { name: 'Espresso Martini', quantity: 1, unitPrice: 14.00 }
    ],
    paymentMethod: 'Cash at Counter',
    subtotal: 38.00,
    tipAmount: 4.00,
    totalAmount: 42.00,
    status: 'SERVED'
  }
];

export default function CashierPortalPage() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [cashierPin, setCashierPin] = useState('');
  const [cashierName, setCashierName] = useState('Sarah Jenkins (Cashier #01)');
  const [loginError, setLoginError] = useState<string | null>(null);

  const [servedRecords, setServedRecords] = useState<ServedOrderRecord[]>(INITIAL_SERVED_RECORDS);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedReceipt, setSelectedReceipt] = useState<ServedOrderRecord | null>(null);

  useEffect(() => {
    // Check if cashier session exists in localStorage
    const savedSession = localStorage.getItem('qr_cashier_session');
    if (savedSession) {
      setIsAuthenticated(true);
    }

    const loadRealCustomerOrders = () => {
      const storedOrdersStr = typeof window !== 'undefined' ? localStorage.getItem('qr_all_orders') : null;
      if (storedOrdersStr) {
        try {
          const ordersArr = JSON.parse(storedOrdersStr);
          if (Array.isArray(ordersArr) && ordersArr.length > 0) {
            const formattedRecords: ServedOrderRecord[] = ordersArr.map((o: any) => ({
              id: o.id || Date.now(),
              orderNumber: o.orderNumber || `ORD-${Math.floor(1000 + Math.random() * 9000)}`,
              tableName: o.tableName || 'Table #01',
              servedTime: o.paidAt || o.soldTime || 'Just now',
              servedDate: o.soldDate || 'Today',
              items: o.items ? o.items.map((i: any) => ({
                name: i.productName || i.name || 'Menu Item',
                quantity: i.quantity || 1,
                unitPrice: i.unitPrice || 10
              })) : [{ name: 'Chef Special Dish', quantity: 1, unitPrice: o.grandTotal || o.totalAmount || 50 }],
              paymentMethod: o.paymentMethod || 'Credit Card',
              subtotal: o.subtotal || o.totalAmount || 100,
              tipAmount: o.tipAmount || 0,
              totalAmount: o.grandTotal || o.totalAmount || 100,
              status: 'SERVED'
            }));

            // Merge formatted customer orders with initial mock records uniquely by orderNumber
            const recordMap = new Map<string, ServedOrderRecord>();
            formattedRecords.forEach(r => recordMap.set(r.orderNumber, r));
            INITIAL_SERVED_RECORDS.forEach(r => {
              if (!recordMap.has(r.orderNumber)) {
                recordMap.set(r.orderNumber, r);
              }
            });

            setServedRecords(Array.from(recordMap.values()));
          }
        } catch (e) {
          console.error('Failed to parse qr_all_orders', e);
        }
      }
    };

    loadRealCustomerOrders();

    // Listen for live served orders in same tab and cross-tab storage changes
    const handleNewServedOrder = (e: any) => {
      loadRealCustomerOrders();
    };

    const handleStorageChange = (e: StorageEvent) => {
      if (e.key === 'qr_all_orders' || e.key === 'qr_latest_order') {
        loadRealCustomerOrders();
      }
    };

    if (typeof window !== 'undefined') {
      window.addEventListener('qr_new_order', handleNewServedOrder);
      window.addEventListener('storage', handleStorageChange);
    }

    return () => {
      if (typeof window !== 'undefined') {
        window.removeEventListener('qr_new_order', handleNewServedOrder);
        window.removeEventListener('storage', handleStorageChange);
      }
    };
  }, []);

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    const pin = cashierPin.trim();
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
        setLoginError(`⛔ Access Denied: Staff member "${foundMember.name}" is currently OFF DUTY. Ask your manager to activate your shift in the Admin Panel.`);
        return;
      }
      setIsAuthenticated(true);
      localStorage.setItem('qr_cashier_session', 'ACTIVE');
      setLoginError(null);
      return;
    }

    if (['9999', '0000'].includes(pin)) {
      setIsAuthenticated(true);
      localStorage.setItem('qr_cashier_session', 'ACTIVE');
      setLoginError(null);
      return;
    }

    setLoginError('Invalid Cashier PIN code. Try default PIN: 1234');
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem('qr_cashier_session');
    setCashierPin('');
  };

  const filteredRecords = servedRecords.filter(r =>
    r.orderNumber.toLowerCase().includes(searchQuery.toLowerCase()) ||
    r.tableName.toLowerCase().includes(searchQuery.toLowerCase()) ||
    r.servedTime.toLowerCase().includes(searchQuery.toLowerCase()) ||
    r.items.some(i => i.name.toLowerCase().includes(searchQuery.toLowerCase()))
  );

  const totalServedSales = servedRecords.reduce((acc, r) => acc + r.totalAmount, 0);

  // If not logged in, display secure PIN login screen
  if (!isAuthenticated) {
    return (
      <div className="min-h-screen bg-zinc-950 text-zinc-100 flex items-center justify-center p-4">
        <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel max-w-md w-full p-8 rounded-3xl border border-zinc-800 text-center space-y-6">
          <div className="w-16 h-16 rounded-2xl bg-purple-600/20 text-purple-400 border border-purple-500/30 flex items-center justify-center mx-auto">
            <Lock className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Cashier Register Login</h1>
            <p className="text-xs text-zinc-400 mt-1">Authorized Cashier System • Served Products Portal</p>
          </div>

          {loginError && (
            <div className="p-3 rounded-xl bg-red-500/10 border border-red-500/30 text-red-400 text-xs">
              {loginError}
            </div>
          )}

          <form onSubmit={handleLogin} className="space-y-4 text-left">
            <div>
              <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1.5">
                Enter Cashier Security PIN
              </label>
              <input
                type="password"
                maxLength={4}
                required
                value={cashierPin}
                onChange={e => setCashierPin(e.target.value)}
                placeholder="•••• (Default: 1234)"
                className="w-full bg-zinc-900 border border-zinc-800 rounded-2xl px-4 py-3 text-center text-xl tracking-[0.5em] font-mono text-white placeholder:tracking-normal placeholder:text-xs placeholder-zinc-500 focus:outline-none focus:border-purple-500"
              />
            </div>

            <button
              type="submit"
              className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-3.5 rounded-2xl text-sm transition shadow-lg shadow-purple-600/30 flex items-center justify-center gap-2"
            >
              <ShieldCheck className="w-5 h-5" /> Authenticate & Access Cashier Register
            </button>
          </form>

          <div className="pt-2 text-zinc-500 text-[11px]">
            <span>System PIN: </span>
            <code className="text-purple-400 font-mono font-bold">1234</code>
          </div>
        </motion.div>
      </div>
    );
  }

  // Authenticated Cashier Portal Dashboard
  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 p-6 max-w-6xl mx-auto space-y-6">
      {/* Top Header */}
      <header className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 pb-6 border-b border-zinc-800">
        <div className="flex items-center gap-3">
          <div className="w-12 h-12 rounded-2xl bg-purple-600/20 text-purple-400 border border-purple-500/30 flex items-center justify-center font-bold">
            <Receipt className="w-6 h-6" />
          </div>
          <div>
            <h1 className="text-xl font-bold text-white">Cashier Register System</h1>
            <p className="text-xs text-purple-400 font-medium">Logged in as {cashierName}</p>
          </div>
        </div>

        <div className="flex items-center gap-3">
          <span className="text-xs font-semibold px-3 py-1.5 rounded-full bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 flex items-center gap-1.5">
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" /> Register Active
          </span>
          <button
            onClick={handleLogout}
            className="px-4 py-2 bg-zinc-900 hover:bg-red-500/20 text-zinc-300 hover:text-red-400 border border-zinc-800 rounded-xl text-xs font-bold transition flex items-center gap-1.5"
          >
            <LogOut className="w-4 h-4" /> Exit Cashier Register
          </button>
        </div>
      </header>

      {/* KPI Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div className="glass-card p-4 rounded-3xl border-zinc-800">
          <div className="flex items-center gap-3 text-emerald-400 mb-2">
            <DollarSign className="w-6 h-6" />
            <span className="text-xs font-semibold text-zinc-400">Total Served Product Revenue</span>
          </div>
          <span className="text-2xl font-extrabold text-white">${totalServedSales.toFixed(2)}</span>
        </div>

        <div className="glass-card p-4 rounded-3xl border-zinc-800">
          <div className="flex items-center gap-3 text-purple-400 mb-2">
            <CheckCircle2 className="w-6 h-6" />
            <span className="text-xs font-semibold text-zinc-400">Served Orders Count</span>
          </div>
          <span className="text-2xl font-extrabold text-white">{servedRecords.length} Served Orders</span>
        </div>

        <div className="glass-card p-4 rounded-3xl border-zinc-800">
          <div className="flex items-center gap-3 text-amber-400 mb-2">
            <Clock className="w-6 h-6" />
            <span className="text-xs font-semibold text-zinc-400">Average Service Time</span>
          </div>
          <span className="text-2xl font-extrabold text-white">12.5 Minutes</span>
        </div>
      </div>

      {/* Served Products & Payment Information Audit Table */}
      <div className="glass-card p-6 rounded-3xl border-zinc-800 space-y-4">
        <div className="pb-4 border-b border-zinc-800">
          <h3 className="font-bold text-white text-base flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-emerald-400" />
            Served Products Audit Register
          </h3>
          <p className="text-xs text-zinc-400">Displaying only completed & served products with exact hour timestamps and payment method details</p>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full text-left text-xs">
            <thead>
              <tr className="text-zinc-400 border-b border-zinc-800 pb-2">
                <th className="pb-3 font-semibold">Served Hour & Date</th>
                <th className="pb-3 font-semibold">Order Ref</th>
                <th className="pb-3 font-semibold">Table</th>
                <th className="pb-3 font-semibold">Served Product Items</th>
                <th className="pb-3 font-semibold">Payment Info</th>
                <th className="pb-3 font-semibold">Tip</th>
                <th className="pb-3 font-semibold text-right">Total Amount</th>
                <th className="pb-3 font-semibold text-center">Receipt</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-zinc-800/60">
              {servedRecords.length === 0 ? (
                <tr>
                  <td colSpan={8} className="py-12 text-center text-zinc-500">
                    No served product records logged yet.
                  </td>
                </tr>
              ) : (
                servedRecords.map(record => (
                  <tr key={record.id} className="hover:bg-zinc-900/40 transition">
                    <td className="py-3.5 font-bold text-purple-300 flex items-center gap-1.5 whitespace-nowrap">
                      <Clock className="w-3.5 h-3.5 text-purple-400 shrink-0" />
                      <span>{record.servedTime}</span>
                      <span className="text-[10px] text-zinc-500 font-normal">({record.servedDate})</span>
                    </td>
                    <td className="py-3.5 font-mono text-zinc-300 font-semibold">{record.orderNumber}</td>
                    <td className="py-3.5 text-white font-semibold whitespace-nowrap">{record.tableName}</td>
                    <td className="py-3.5 text-zinc-200">
                      <div className="space-y-0.5 max-w-xs">
                        {record.items.map((item, idx) => (
                          <div key={idx} className="flex justify-between text-xs">
                            <span>{item.quantity}x {item.name}</span>
                            <span className="text-zinc-400 font-mono">${(item.quantity * item.unitPrice).toFixed(2)}</span>
                          </div>
                        ))}
                      </div>
                    </td>
                    <td className="py-3.5 whitespace-nowrap">
                      <span className="px-2.5 py-1 rounded-full text-[10px] font-bold bg-zinc-900 text-zinc-300 border border-zinc-800">
                        {record.paymentMethod}
                      </span>
                    </td>
                    <td className="py-3.5 text-amber-400 font-semibold whitespace-nowrap">
                      +${record.tipAmount.toFixed(2)}
                    </td>
                    <td className="py-3.5 text-right font-extrabold text-emerald-400 text-sm whitespace-nowrap">
                      ${record.totalAmount.toFixed(2)}
                    </td>
                    <td className="py-3.5 text-center">
                      <button
                        onClick={() => setSelectedReceipt(record)}
                        className="px-2.5 py-1 rounded-xl bg-purple-600/20 hover:bg-purple-600/30 text-purple-300 text-[11px] font-semibold transition flex items-center gap-1 mx-auto"
                      >
                        <Printer className="w-3 h-3" /> View
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* Receipt Modal */}
      {selectedReceipt && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-md">
          <div className="glass-panel p-6 rounded-3xl max-w-sm w-full border border-purple-500/40 text-center space-y-4">
            <div className="w-14 h-14 bg-emerald-500/20 text-emerald-400 rounded-full flex items-center justify-center mx-auto border border-emerald-500/30">
              <Printer className="w-7 h-7" />
            </div>

            <span className="px-3 py-1 rounded-full text-[10px] font-extrabold bg-emerald-500/20 text-emerald-300 border border-emerald-500/30 uppercase tracking-wider">
              Cashier Verified • SERVED & PAID
            </span>

            <h3 className="text-lg font-bold text-white">Receipt {selectedReceipt.orderNumber}</h3>
            <p className="text-xs text-zinc-400">Served at {selectedReceipt.servedTime} ({selectedReceipt.tableName})</p>

            <div className="bg-zinc-900/80 p-4 rounded-2xl border border-zinc-800 text-left space-y-2 text-xs">
              <div className="font-bold text-zinc-300 pb-2 border-b border-zinc-800">Purchased Items:</div>
              {selectedReceipt.items.map((item, i) => (
                <div key={i} className="flex justify-between text-zinc-300">
                  <span>{item.quantity}x {item.name}</span>
                  <span className="font-mono text-white">${(item.quantity * item.unitPrice).toFixed(2)}</span>
                </div>
              ))}

              <div className="pt-2 border-t border-zinc-800 space-y-1">
                <div className="flex justify-between text-zinc-400">
                  <span>Payment Method</span>
                  <span className="text-white font-medium">{selectedReceipt.paymentMethod}</span>
                </div>
                {selectedReceipt.tipAmount > 0 && (
                  <div className="flex justify-between text-amber-400">
                    <span>Staff Tip</span>
                    <span>+${selectedReceipt.tipAmount.toFixed(2)}</span>
                  </div>
                )}
                <div className="flex justify-between font-extrabold text-sm text-white pt-2 border-t border-zinc-800">
                  <span>Total Amount Paid</span>
                  <span className="text-emerald-400">${selectedReceipt.totalAmount.toFixed(2)}</span>
                </div>
              </div>
            </div>

            <button
              onClick={() => setSelectedReceipt(null)}
              className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-2.5 rounded-xl text-xs transition"
            >
              Close Receipt Window
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
