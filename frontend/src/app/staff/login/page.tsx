'use client';

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import {
  ShieldCheck,
  Eye,
  EyeOff,
  LogIn,
  Utensils,
  AlertCircle,
  CheckCircle2,
  Users,
  Receipt,
  ArrowLeft,
  Lock,
} from 'lucide-react';
import { api, clearLegacyLocalStorageAuth } from '@/lib/api';

export default function StaffLoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [loginSuccess, setLoginSuccess] = useState(false);
  const [targetRouteLabel, setTargetRouteLabel] = useState('');

  useEffect(() => {
    clearLegacyLocalStorageAuth();
  }, []);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      const data = await api.login({ email: email.trim(), password: password.trim() });
      const role = data.user?.role || 'WAITER';

      if (typeof window !== 'undefined') {
        sessionStorage.setItem('token', data.accessToken);
        sessionStorage.setItem('accessToken', data.accessToken);
        sessionStorage.setItem('staffUser', JSON.stringify(data.user || data));
        sessionStorage.setItem('qr_staff_session', 'ACTIVE');

        if (role === 'CASHIER') {
          sessionStorage.setItem('qr_cashier_session', 'ACTIVE');
        } else if (role === 'ADMIN' || role === 'SUPER_ADMIN' || role === 'MANAGER') {
          sessionStorage.setItem('qr_admin_session', 'ACTIVE');
        }
      }

      let targetPath = '/staff/orders';
      let label = 'Garson Sipariş & Masa Ekranı (/staff/orders)';

      if (role === 'WAITER') {
        targetPath = '/staff/orders';
        label = 'Garson Sipariş & Masa Ekranı (/staff/orders)';
      } else if (role === 'CASHIER') {
        targetPath = '/cashier';
        label = 'Kasiyer Kasa Paneli (/cashier)';
      } else if (role === 'ADMIN' || role === 'SUPER_ADMIN' || role === 'MANAGER') {
        targetPath = '/admin/dashboard';
        label = 'Yönetici Paneli (/admin/dashboard)';
      } else if (role === 'KITCHEN') {
        targetPath = '/kitchen?token=GOURMET-KDS-2026';
        label = 'Mutfak Ekranı (/kitchen)';
      }

      setTargetRouteLabel(label);
      setLoginSuccess(true);

      setTimeout(() => {
        router.push(targetPath);
      }, 900);
    } catch (err: any) {
      setError(err.message || 'Giriş başarısız. Lütfen e-posta ve şifre/PIN bilgilerinizi kontrol edin.');
    } finally {
      setIsLoading(false);
    }
  };

  const fillRoleCredentials = (selectedRole: 'WAITER' | 'CASHIER' | 'ADMIN') => {
    setError('');
    if (selectedRole === 'WAITER') {
      setEmail('waiter@gourmet.com');
      setPassword('password123');
    } else if (selectedRole === 'CASHIER') {
      setEmail('cashier@gourmet.com');
      setPassword('password123');
    } else {
      setEmail('admin@gourmet.com');
      setPassword('password123');
    }
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 flex items-center justify-center p-4 relative overflow-hidden">
      {/* Ambient Background */}
      <div className="fixed inset-0 pointer-events-none">
        <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[480px] h-[480px] bg-purple-600/10 rounded-full blur-[140px]" />
      </div>

      <div className="relative z-10 w-full max-w-md">
        {/* Back to Operations Hub */}
        <div className="mb-4">
          <Link
            href="/"
            className="inline-flex items-center gap-1.5 text-xs font-semibold text-zinc-400 hover:text-white transition"
          >
            <ArrowLeft className="w-4 h-4" /> Operasyon Merkezine Dön
          </Link>
        </div>

        {/* Brand Header */}
        <div className="text-center mb-6">
          <div className="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-purple-600/20 border border-purple-500/30 text-purple-400 shadow-lg mb-3">
            <Utensils className="w-7 h-7" />
          </div>
          <h1 className="text-2xl font-extrabold text-white tracking-tight">
            Personel Giriş Kapısı
          </h1>
          <p className="text-xs text-zinc-400 mt-1">
            Garsonlar Sipariş &amp; Masa ekranına, Kasiyerler Kasa paneline otomatik yönlendirilir
          </p>
        </div>

        {/* Login Card */}
        <div className="glass-panel p-7 rounded-3xl border border-zinc-800 bg-zinc-900/50 shadow-2xl">
          {loginSuccess ? (
            <div className="text-center py-8 space-y-3">
              <div className="w-14 h-14 rounded-full bg-emerald-500/20 border border-emerald-500/30 text-emerald-400 flex items-center justify-center mx-auto">
                <CheckCircle2 className="w-7 h-7" />
              </div>
              <h2 className="text-base font-bold text-emerald-400">Giriş Başarılı!</h2>
              <p className="text-xs text-zinc-300">
                Yönlendiriliyorsunuz: <span className="font-bold text-white">{targetRouteLabel}</span>
              </p>
            </div>
          ) : (
            <>
              <div className="flex items-center justify-between mb-5 pb-3 border-b border-zinc-800">
                <div className="flex items-center gap-2">
                  <Lock className="w-4 h-4 text-purple-400" />
                  <h2 className="text-sm font-bold text-white">Kimlik Doğrulama</h2>
                </div>
                <span className="text-[10px] font-bold px-2 py-0.5 rounded-full bg-purple-500/15 text-purple-300 border border-purple-500/30">
                  JWT Oturum
                </span>
              </div>

              {error && (
                <div className="flex items-center gap-2 p-3 mb-5 rounded-xl bg-red-500/15 border border-red-500/30 text-red-300 text-xs font-medium">
                  <AlertCircle className="w-4 h-4 shrink-0 text-red-400" />
                  <span>{error}</span>
                </div>
              )}

              <form onSubmit={handleLogin} className="space-y-4">
                <div>
                  <label className="block text-xs font-bold text-zinc-400 uppercase tracking-wider mb-1.5">
                    Personel E-Posta / Kullanıcı Adı
                  </label>
                  <input
                    id="login-email"
                    type="email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    placeholder="ornek: waiter@gourmet.com"
                    required
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-4 py-3 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500 transition"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-zinc-400 uppercase tracking-wider mb-1.5">
                    Şifre veya Güvenlik PIN Kodu
                  </label>
                  <div className="relative">
                    <input
                      id="login-password"
                      type={showPassword ? 'text' : 'password'}
                      value={password}
                      onChange={e => setPassword(e.target.value)}
                      placeholder="Şifre veya 4 haneli PIN (örn: 1234)"
                      required
                      className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-4 py-3 pr-11 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500 transition"
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-3.5 top-1/2 -translate-y-1/2 text-zinc-400 hover:text-white transition"
                    >
                      {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                    </button>
                  </div>
                </div>

                <button
                  type="submit"
                  disabled={isLoading}
                  className="w-full flex items-center justify-center gap-2 py-3.5 rounded-xl bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs shadow-lg shadow-purple-600/25 transition disabled:opacity-50"
                >
                  <LogIn className="w-4 h-4" />
                  <span>{isLoading ? 'Doğrulanıyor...' : 'Giriş Yap ve Panele Geç'}</span>
                </button>
              </form>

              {/* Quick Demo Role Selector */}
              <div className="mt-6 pt-5 border-t border-zinc-800 space-y-2.5">
                <span className="block text-[11px] font-bold text-zinc-400 text-center">
                  Hızlı Test Girişi İçin Rol Seçin:
                </span>
                <div className="grid grid-cols-3 gap-2">
                  <button
                    type="button"
                    onClick={() => fillRoleCredentials('WAITER')}
                    className="p-2.5 rounded-xl bg-zinc-900 hover:bg-zinc-800 border border-zinc-800 hover:border-purple-500/40 text-center transition"
                  >
                    <Users className="w-4 h-4 text-purple-400 mx-auto mb-1" />
                    <span className="block text-[11px] font-bold text-white">Garson</span>
                    <span className="block text-[9px] text-zinc-500">Sipariş &amp; Masa</span>
                  </button>

                  <button
                    type="button"
                    onClick={() => fillRoleCredentials('CASHIER')}
                    className="p-2.5 rounded-xl bg-zinc-900 hover:bg-zinc-800 border border-zinc-800 hover:border-blue-500/40 text-center transition"
                  >
                    <Receipt className="w-4 h-4 text-blue-400 mx-auto mb-1" />
                    <span className="block text-[11px] font-bold text-white">Kasiyer</span>
                    <span className="block text-[9px] text-zinc-500">Kasa Paneli</span>
                  </button>

                  <button
                    type="button"
                    onClick={() => fillRoleCredentials('ADMIN')}
                    className="p-2.5 rounded-xl bg-zinc-900 hover:bg-zinc-800 border border-zinc-800 hover:border-emerald-500/40 text-center transition"
                  >
                    <ShieldCheck className="w-4 h-4 text-emerald-400 mx-auto mb-1" />
                    <span className="block text-[11px] font-bold text-white">Yönetici</span>
                    <span className="block text-[9px] text-zinc-500">Admin Panel</span>
                  </button>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
