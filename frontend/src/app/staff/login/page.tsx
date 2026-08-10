'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { ShieldCheck, Eye, EyeOff, LogIn, Utensils, Sparkles, AlertCircle, CheckCircle2, ChefHat, LayoutDashboard } from 'lucide-react';

export default function StaffLoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [loginSuccess, setLoginSuccess] = useState(false);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      const res = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      if (!res.ok) {
        const errData = await res.json().catch(() => null);
        throw new Error(errData?.message || 'Invalid email or password');
      }

      const data = await res.json();

      // Store JWT token
      if (typeof window !== 'undefined') {
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('staffUser', JSON.stringify(data.user || data));
      }

      setLoginSuccess(true);

      // Redirect based on role
      setTimeout(() => {
        const role = data.user?.role || data.role || '';
        if (role === 'KITCHEN_STAFF') {
          router.push('/kitchen');
        } else {
          router.push('/admin');
        }
      }, 1200);

    } catch (err: any) {
      setError(err.message || 'Login failed. Please check your credentials.');
    } finally {
      setIsLoading(false);
    }
  };

  // Demo login helper
  const fillDemoCredentials = () => {
    setEmail('admin@bosphorus.com');
    setPassword('password123');
  };

  return (
    <div className="min-h-screen bg-[#0b0f19] text-slate-100 flex items-center justify-center relative overflow-hidden">
      
      {/* Background Glows */}
      <div className="fixed inset-0 pointer-events-none">
        <div className="absolute top-[-20%] left-[-15%] w-[500px] h-[500px] bg-orange-500/8 rounded-full blur-[140px]" />
        <div className="absolute bottom-[-10%] right-[-10%] w-[600px] h-[600px] bg-rose-500/8 rounded-full blur-[160px]" />
        <div className="absolute top-[40%] left-[50%] translate-x-[-50%] w-[300px] h-[300px] bg-purple-500/5 rounded-full blur-[120px]" />
      </div>

      {/* Login Card */}
      <div className="relative z-10 w-full max-w-md mx-4">
        
        {/* Brand Header */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-16 h-16 rounded-3xl gradient-btn text-white shadow-2xl shadow-orange-500/30 mb-5">
            <Utensils className="w-8 h-8" />
          </div>
          <h1 className="text-3xl font-extrabold tracking-tight">
            <span className="gradient-text">GourmetQR</span>
          </h1>
          <p className="text-sm text-slate-400 mt-2">Staff Authentication Portal</p>
        </div>

        {/* Login Form Card */}
        <div className="glass-panel p-8 rounded-3xl border border-slate-800 shadow-2xl">
          
          {/* Success State */}
          {loginSuccess && (
            <div className="text-center py-8">
              <div className="w-16 h-16 rounded-full bg-emerald-500/20 text-emerald-400 flex items-center justify-center mx-auto mb-4">
                <CheckCircle2 className="w-8 h-8" />
              </div>
              <h2 className="text-lg font-bold text-emerald-400">Login Successful!</h2>
              <p className="text-xs text-slate-400 mt-2">Redirecting you to the operations panel...</p>
            </div>
          )}

          {/* Login Form */}
          {!loginSuccess && (
            <>
              <div className="flex items-center gap-2 mb-6">
                <ShieldCheck className="w-5 h-5 text-orange-400" />
                <h2 className="text-lg font-bold text-white">Staff Sign In</h2>
              </div>

              {/* Error Alert */}
              {error && (
                <div className="flex items-center gap-2 p-3 mb-5 rounded-xl bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs font-medium">
                  <AlertCircle className="w-4 h-4 flex-shrink-0" />
                  <span>{error}</span>
                </div>
              )}

              <form onSubmit={handleLogin} className="space-y-5">
                {/* Email */}
                <div>
                  <label className="block text-xs font-bold text-slate-400 uppercase tracking-wider mb-2">
                    Email Address
                  </label>
                  <input
                    id="login-email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="staff@restaurant.com"
                    required
                    className="w-full bg-slate-900/90 border border-slate-700 rounded-xl px-4 py-3 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-orange-500 transition-colors"
                  />
                </div>

                {/* Password */}
                <div>
                  <label className="block text-xs font-bold text-slate-400 uppercase tracking-wider mb-2">
                    Password
                  </label>
                  <div className="relative">
                    <input
                      id="login-password"
                      type={showPassword ? 'text' : 'password'}
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      placeholder="Enter your password"
                      required
                      className="w-full bg-slate-900/90 border border-slate-700 rounded-xl px-4 py-3 pr-12 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-orange-500 transition-colors"
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-3.5 top-1/2 -translate-y-1/2 text-slate-400 hover:text-white transition-colors"
                    >
                      {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                    </button>
                  </div>
                </div>

                {/* Submit Button */}
                <button
                  type="submit"
                  disabled={isLoading}
                  className="w-full flex items-center justify-center gap-2.5 py-3.5 rounded-xl gradient-btn text-white font-bold text-sm shadow-xl disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isLoading ? (
                    <div className="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                  ) : (
                    <LogIn className="w-4 h-4" />
                  )}
                  <span>{isLoading ? 'Authenticating...' : 'Sign In'}</span>
                </button>
              </form>

              {/* Divider */}
              <div className="my-6 flex items-center gap-3">
                <div className="flex-1 h-px bg-slate-800"></div>
                <span className="text-[10px] uppercase tracking-widest text-slate-500 font-bold">Quick Access</span>
                <div className="flex-1 h-px bg-slate-800"></div>
              </div>

              {/* Demo Credentials Helper */}
              <button
                type="button"
                onClick={fillDemoCredentials}
                className="w-full flex items-center justify-center gap-2 py-2.5 rounded-xl bg-slate-900 border border-slate-800 text-slate-300 hover:text-white hover:border-slate-700 text-xs font-medium transition-all"
              >
                <Sparkles className="w-3.5 h-3.5 text-amber-400" />
                Fill Demo Credentials
              </button>

              {/* Quick Role Shortcuts */}
              <div className="grid grid-cols-2 gap-3 mt-4">
                <button
                  type="button"
                  onClick={() => router.push('/kitchen')}
                  className="flex items-center justify-center gap-2 p-3 rounded-xl bg-slate-900/80 border border-slate-800 text-slate-400 hover:text-orange-400 hover:border-orange-500/30 text-xs font-bold transition-all"
                >
                  <ChefHat className="w-4 h-4" />
                  Kitchen View
                </button>
                <button
                  type="button"
                  onClick={() => router.push('/admin')}
                  className="flex items-center justify-center gap-2 p-3 rounded-xl bg-slate-900/80 border border-slate-800 text-slate-400 hover:text-amber-400 hover:border-amber-500/30 text-xs font-bold transition-all"
                >
                  <LayoutDashboard className="w-4 h-4" />
                  Admin Panel
                </button>
              </div>
            </>
          )}
        </div>

        {/* Footer */}
        <p className="text-center text-[11px] text-slate-600 mt-6">
          GourmetQR Platform • JWT Secured Staff Portal
        </p>
      </div>
    </div>
  );
}
