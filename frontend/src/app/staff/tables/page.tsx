'use client';

import React, { useEffect, useState } from 'react';
import { Navbar } from '@/components/Navbar';
import { RestaurantTable } from '@/lib/types';
import { api, clearAllAuthStorage, clearLegacyLocalStorageAuth } from '@/lib/api';
import { QrCode, Plus, RefreshCw, Users, CheckCircle2, Lock, LogOut, ShieldCheck } from 'lucide-react';
import { motion } from 'framer-motion';

export default function StaffTablesPage() {
  const [isStaffAuthenticated, setIsStaffAuthenticated] = useState(false);
  const [staffPin, setStaffPin] = useState('');
  const [staffPinError, setStaffPinError] = useState<string | null>(null);

  const [tables, setTables] = useState<RestaurantTable[]>([]);
  const [loading, setLoading] = useState(true);
  const [newTableNumber, setNewTableNumber] = useState('');
  const [addingTable, setAddingTable] = useState(false);

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

  useEffect(() => {
    if (!isStaffAuthenticated) return;

    async function loadTables() {
      try {
        setLoading(true);
        const data = await api.getBranchTables(1);
        setTables(data);
      } catch (err) {
        console.error('Failed to load branch tables', err);
      } finally {
        setLoading(false);
      }
    }
    loadTables();
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

  const toggleTableStatus = (tableId: number) => {
    setTables(prev =>
      prev.map(t => {
        if (t.id === tableId) {
          const currentStatus = (t as any).status || 'AVAILABLE';
          const nextStatus = currentStatus === 'AVAILABLE' ? 'OCCUPIED' : 'AVAILABLE';
          return { ...t, status: nextStatus };
        }
        return t;
      })
    );
  };

  const handleRotateToken = (tableId: number) => {
    const newToken = Math.random().toString(36).substring(2, 14);
    setTables(prev =>
      prev.map(t => (t.id === tableId ? { ...t, activeToken: newToken, qrToken: newToken, qrUrl: `http://localhost:3000/t/${newToken}` } : t))
    );
  };

  const handleAddTable = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTableNumber.trim() || addingTable) return;
    try {
      setAddingTable(true);
      const newTbl = await api.createTable({ branchId: 1, tableNumber: newTableNumber.trim() });
      setTables(prev => [newTbl, ...prev]);
      setNewTableNumber('');
    } catch (err: any) {
      alert(`Failed to add table: ${err.message}`);
    } finally {
      setAddingTable(false);
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
            <h1 className="text-2xl font-bold text-white">Staff Floor & Table Control Access</h1>
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
              <ShieldCheck className="w-5 h-5" /> Authenticate & Access Floor Tables
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
              <Users className="w-4 h-4" />
              <span>Staff Floor & Table Control</span>
            </div>
            <h1 className="text-2xl sm:text-3xl font-extrabold text-white">Live Restaurant Tables</h1>
          </div>

          <div className="flex flex-wrap items-center gap-3">
            <form onSubmit={handleAddTable} className="flex gap-2">
              <input
                type="text"
                required
                value={newTableNumber}
                onChange={e => setNewTableNumber(e.target.value)}
                placeholder="Table # (e.g. Table 05)"
                className="bg-slate-900 border border-slate-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-orange-500"
              />
              <button
                type="submit"
                disabled={addingTable}
                className="px-4 py-2 rounded-xl gradient-btn text-white font-bold text-xs flex items-center gap-1.5 shadow-md"
              >
                <Plus className="w-4 h-4" /> {addingTable ? 'Adding...' : 'Add Table'}
              </button>
            </form>

            <button
              onClick={handleStaffLogout}
              className="flex items-center gap-1.5 px-3.5 py-2 rounded-xl bg-slate-900 hover:bg-red-500/20 text-slate-300 hover:text-red-400 border border-slate-800 text-xs font-bold transition-all"
              title="Lock Floor Controls"
            >
              <LogOut className="w-4 h-4" />
              Exit Staff
            </button>
          </div>
        </div>

        {loading ? (
          <div className="text-center py-12 text-slate-500 text-sm">Loading restaurant floor tables...</div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {tables.map((table) => {
              const token = table.qrToken || table.activeToken || `tbl-00${table.id}`;
              const tableName = (table as any).name || table.tableNumber || `Table 0${table.id}`;
              const status = (table as any).status || 'AVAILABLE';

              return (
                <div key={table.id} className="glass-card rounded-3xl p-6 border border-slate-800 flex flex-col justify-between hover:border-orange-500/30 transition">
                  <div>
                    <div className="flex items-center justify-between mb-3">
                      <h3 className="text-base font-extrabold text-white">{tableName}</h3>
                      <span className={`px-2.5 py-1 rounded-full text-[10px] font-bold ${
                        status === 'OCCUPIED' ? 'bg-rose-500/20 text-rose-400 border border-rose-500/30' :
                        status === 'BILL_REQUESTED' ? 'bg-amber-500/20 text-amber-400 border border-amber-500/30' :
                        status === 'WAITER_CALLED' ? 'bg-purple-500/20 text-purple-400 border border-purple-500/30' :
                        'bg-emerald-500/20 text-emerald-400 border border-emerald-500/30'
                      }`}>
                        {status}
                      </span>
                    </div>
                    <p className="text-xs text-slate-400">Capacity: {(table as any).capacity || 4} Guests</p>
                    <p className="text-[11px] font-mono text-slate-400 mt-1 truncate">QR Token: {token}</p>
                    <a
                      href={`/t/${token}`}
                      target="_blank"
                      rel="noreferrer"
                      className="mt-2 inline-block text-[11px] font-semibold text-orange-400 hover:text-orange-300"
                    >
                      Open Customer Menu →
                    </a>
                  </div>

                  <div className="mt-6 pt-4 border-t border-slate-800/80 flex items-center gap-2">
                    <button
                      onClick={() => toggleTableStatus(table.id)}
                      className="flex-1 py-2 rounded-xl bg-slate-800 hover:bg-slate-700 text-xs font-bold text-slate-200 transition-colors"
                    >
                      Toggle Status
                    </button>
                    <button
                      onClick={() => handleRotateToken(table.id)}
                      className="p-2 rounded-xl bg-slate-800 hover:bg-slate-700 text-amber-400 transition-colors"
                      title="Rotate Security Token"
                    >
                      <RefreshCw className="w-4 h-4" />
                    </button>
                  </div>
                </div>
              );
            })}
          </div>
        )}
      </main>
    </div>
  );
}
