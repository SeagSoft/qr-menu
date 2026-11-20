'use client';

import { useState } from 'react';
import Link from 'next/link';
import {
  Utensils,
  ChefHat,
  ShieldCheck,
  ArrowRight,
  Users,
  Lock,
  Monitor,
  ClipboardList,
  Receipt,
  LayoutGrid,
  CheckCircle2,
} from 'lucide-react';

type PortalTab = 'all' | 'staff' | 'kitchen' | 'admin';

export default function Home() {
  const [activeTab, setActiveTab] = useState<PortalTab>('all');

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 flex flex-col justify-between p-6 relative overflow-hidden">
      {/* Background Ambient Glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[520px] h-[520px] bg-purple-600/10 blur-[140px] rounded-full pointer-events-none" />
      <div className="absolute bottom-10 right-1/4 w-[380px] h-[380px] bg-emerald-600/10 blur-[140px] rounded-full pointer-events-none" />

      {/* Top Brand Header */}
      <header className="max-w-5xl w-full mx-auto pt-4 flex flex-col sm:flex-row items-center justify-between gap-4 border-b border-zinc-800/80 pb-5 relative z-10">
        <div className="flex items-center gap-3">
          <div className="w-11 h-11 rounded-2xl bg-purple-600/20 border border-purple-500/30 text-purple-400 flex items-center justify-center shadow-lg shadow-purple-600/10">
            <Utensils className="w-5 h-5" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="font-extrabold text-lg text-white tracking-tight">GourmetQR</span>
              <span className="text-[10px] font-bold uppercase tracking-wider px-2 py-0.5 rounded-full bg-purple-500/15 text-purple-300 border border-purple-500/30">
                Kurumsal SaaS
              </span>
            </div>
            <p className="text-xs text-zinc-400">Restoran İçi Operasyon &amp; Yönetim Merkezi</p>
          </div>
        </div>

        <div className="flex items-center gap-2 text-xs text-zinc-400 bg-zinc-900/90 px-3.5 py-2 rounded-xl border border-zinc-800">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
          <span>Sistem Aktif • Yalnızca Yetkili Personel Erişimi</span>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-5xl w-full mx-auto my-auto py-10 space-y-8 relative z-10">
        {/* Title & Subtitle */}
        <div className="text-center space-y-3 max-w-2xl mx-auto">
          <div className="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-zinc-900 border border-zinc-800 text-purple-400 text-xs font-semibold">
            <ShieldCheck className="w-3.5 h-3.5" /> Restoran Operasyon &amp; Giriş Merkezi
          </div>
          <h1 className="text-3xl sm:text-4xl font-black text-white tracking-tight">
            Görev Alanınızı Seçerek Giriş Yapın
          </h1>
          <p className="text-xs sm:text-sm text-zinc-400 leading-relaxed">
            Garson, kasiyer, mutfak şefi ve işletme yöneticileri için rol bazlı operasyon kapıları.
            Müşteri siparişleri yalnızca masalardaki fiziksel QR kodlar üzerinden alınmaktadır.
          </p>
        </div>

        {/* Filter Tabs */}
        <div className="flex justify-center">
          <div className="inline-flex flex-wrap items-center gap-1.5 bg-zinc-900/90 p-1.5 rounded-2xl border border-zinc-800">
            <button
              onClick={() => setActiveTab('all')}
              className={`px-4 py-2 rounded-xl text-xs font-bold transition flex items-center gap-1.5 ${
                activeTab === 'all'
                  ? 'bg-purple-600 text-white shadow-md shadow-purple-600/30'
                  : 'text-zinc-400 hover:text-white'
              }`}
            >
              <LayoutGrid className="w-3.5 h-3.5" /> Tüm Giriş Kapıları
            </button>
            <button
              onClick={() => setActiveTab('staff')}
              className={`px-4 py-2 rounded-xl text-xs font-bold transition flex items-center gap-1.5 ${
                activeTab === 'staff'
                  ? 'bg-purple-600 text-white shadow-md shadow-purple-600/30'
                  : 'text-zinc-400 hover:text-white'
              }`}
            >
              <Users className="w-3.5 h-3.5" /> Personel Giriş Kapısı
            </button>
            <button
              onClick={() => setActiveTab('kitchen')}
              className={`px-4 py-2 rounded-xl text-xs font-bold transition flex items-center gap-1.5 ${
                activeTab === 'kitchen'
                  ? 'bg-amber-600 text-white shadow-md shadow-amber-600/30'
                  : 'text-zinc-400 hover:text-white'
              }`}
            >
              <ChefHat className="w-3.5 h-3.5" /> Mutfak Ekranı (KDS)
            </button>
            <button
              onClick={() => setActiveTab('admin')}
              className={`px-4 py-2 rounded-xl text-xs font-bold transition flex items-center gap-1.5 ${
                activeTab === 'admin'
                  ? 'bg-emerald-600 text-white shadow-md shadow-emerald-600/30'
                  : 'text-zinc-400 hover:text-white'
              }`}
            >
              <ShieldCheck className="w-3.5 h-3.5" /> Yönetici Paneli
            </button>
          </div>
        </div>

        {/* 3 Core Portal Cards */}
        <div
          className={`grid gap-6 ${
            activeTab === 'all' ? 'grid-cols-1 md:grid-cols-3' : 'grid-cols-1 max-w-xl mx-auto'
          }`}
        >
          {/* 1. Personel Giriş Kapısı (/staff/login) */}
          {(activeTab === 'all' || activeTab === 'staff') && (
            <div className="glass-card p-6 rounded-3xl border border-zinc-800 hover:border-purple-500/50 transition flex flex-col justify-between bg-zinc-900/40">
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="w-12 h-12 rounded-2xl bg-purple-600/20 border border-purple-500/30 text-purple-400 flex items-center justify-center">
                    <Users className="w-6 h-6" />
                  </div>
                  <span className="px-2.5 py-1 rounded-full text-[10px] font-bold bg-purple-500/15 text-purple-300 border border-purple-500/30 flex items-center gap-1">
                    <Lock className="w-3 h-3" /> Rol Bazlı Giriş
                  </span>
                </div>

                <div>
                  <h2 className="text-lg font-extrabold text-white">Personel Giriş Kapısı</h2>
                  <p className="text-xs text-zinc-400 mt-1.5 leading-relaxed">
                    Garson ve Kasiyerlerin kullanıcı adı / PIN ile giriş yaptığı ana giriş alanı.
                  </p>
                </div>

                <div className="p-3.5 rounded-2xl bg-zinc-900/90 border border-zinc-800/90 space-y-2 text-[11px] text-zinc-300">
                  <div className="font-bold text-purple-300 flex items-center gap-1.5">
                    <CheckCircle2 className="w-3.5 h-3.5 text-purple-400 shrink-0" />
                    <span>Otomatik Rol Yönlendirmesi:</span>
                  </div>
                  <p className="text-zinc-400 leading-relaxed">
                    Garsonlar <strong>Sipariş &amp; Masa</strong> ekranına, Kasiyerler{' '}
                    <strong>Kasa paneline</strong> otomatik yönlendirilir.
                  </p>
                  <div className="pt-1 flex flex-wrap gap-1.5">
                    <span className="px-2 py-0.5 rounded-lg bg-zinc-950 border border-zinc-800 text-[10px] text-zinc-400 flex items-center gap-1">
                      <ClipboardList className="w-3 h-3 text-purple-400" /> Garson: Sipariş &amp; Masa
                    </span>
                    <span className="px-2 py-0.5 rounded-lg bg-zinc-950 border border-zinc-800 text-[10px] text-zinc-400 flex items-center gap-1">
                      <Receipt className="w-3 h-3 text-blue-400" /> Kasiyer: Kasa &amp; Adisyon
                    </span>
                  </div>
                </div>
              </div>

              <Link
                href="/staff/login"
                className="mt-6 w-full py-3.5 px-4 rounded-2xl bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs transition shadow-lg shadow-purple-600/25 flex items-center justify-center gap-2"
              >
                <span>Personel Girişi Yap</span>
                <ArrowRight className="w-4 h-4" />
              </Link>
            </div>
          )}

          {/* 2. Mutfak Ekranı - KDS (/kitchen) */}
          {(activeTab === 'all' || activeTab === 'kitchen') && (
            <div className="glass-card p-6 rounded-3xl border border-zinc-800 hover:border-amber-500/50 transition flex flex-col justify-between bg-zinc-900/40">
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="w-12 h-12 rounded-2xl bg-amber-500/20 border border-amber-500/30 text-amber-400 flex items-center justify-center">
                    <ChefHat className="w-6 h-6" />
                  </div>
                  <span className="px-2.5 py-1 rounded-full text-[10px] font-bold bg-amber-500/15 text-amber-300 border border-amber-500/30 flex items-center gap-1">
                    <Monitor className="w-3 h-3" /> Şifresiz Kiosk Modu
                  </span>
                </div>

                <div>
                  <h2 className="text-lg font-extrabold text-white">Mutfak Ekranı (KDS)</h2>
                  <p className="text-xs text-zinc-400 mt-1.5 leading-relaxed">
                    Şifresiz, sürekli açık kalan Kiosk &amp; Bump Bar destekli canlı mutfak sipariş
                    ekranı.
                  </p>
                </div>

                <div className="p-3.5 rounded-2xl bg-zinc-900/90 border border-zinc-800/90 space-y-2 text-[11px] text-zinc-300">
                  <div className="font-bold text-amber-300 flex items-center gap-1.5">
                    <CheckCircle2 className="w-3.5 h-3.5 text-amber-400 shrink-0" />
                    <span>Kiosk &amp; Bump Bar Özellikleri:</span>
                  </div>
                  <p className="text-zinc-400 leading-relaxed">
                    Masalardan gelen siparişler anlık olarak ekrana düşer; tek dokunuşla{' '}
                    <strong>Hazırlanıyor</strong> ve <strong>Hazır</strong> durumuna geçirilir.
                  </p>
                </div>
              </div>

              <Link
                href="/kitchen?token=GOURMET-KDS-2026"
                className="mt-6 w-full py-3.5 px-4 rounded-2xl bg-amber-500 hover:bg-amber-400 text-zinc-950 font-extrabold text-xs transition shadow-lg shadow-amber-500/20 flex items-center justify-center gap-2"
              >
                <span>Mutfak KDS Ekranını Aç</span>
                <ArrowRight className="w-4 h-4" />
              </Link>
            </div>
          )}

          {/* 3. Yönetici Paneli (/admin) */}
          {(activeTab === 'all' || activeTab === 'admin') && (
            <div className="glass-card p-6 rounded-3xl border border-zinc-800 hover:border-emerald-500/50 transition flex flex-col justify-between bg-zinc-900/40">
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="w-12 h-12 rounded-2xl bg-emerald-500/20 border border-emerald-500/30 text-emerald-400 flex items-center justify-center">
                    <ShieldCheck className="w-6 h-6" />
                  </div>
                  <span className="px-2.5 py-1 rounded-full text-[10px] font-bold bg-emerald-500/15 text-emerald-300 border border-emerald-500/30 flex items-center gap-1">
                    <Lock className="w-3 h-3" /> Admin PIN Korumalı
                  </span>
                </div>

                <div>
                  <h2 className="text-lg font-extrabold text-white">Yönetici Paneli</h2>
                  <p className="text-xs text-zinc-400 mt-1.5 leading-relaxed">
                    Menü yönetimi, masa QR üretimi, personel rol/şifre atama ve günlük satış
                    raporları.
                  </p>
                </div>

                <div className="p-3.5 rounded-2xl bg-zinc-900/90 border border-zinc-800/90 space-y-2 text-[11px] text-zinc-300">
                  <div className="font-bold text-emerald-300 flex items-center gap-1.5">
                    <CheckCircle2 className="w-3.5 h-3.5 text-emerald-400 shrink-0" />
                    <span>Tam Yetkili İşletme Kontrolü:</span>
                  </div>
                  <p className="text-zinc-400 leading-relaxed">
                    Kategori ve ürün ekleme, yazdırılabilir masa QR kodları, personel vardiya/PIN
                    yönetimi ve canlı ciro analitiği.
                  </p>
                </div>
              </div>

              <Link
                href="/admin/dashboard"
                className="mt-6 w-full py-3.5 px-4 rounded-2xl bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs transition shadow-lg shadow-emerald-600/25 flex items-center justify-center gap-2"
              >
                <span>Yönetici Paneline Giriş</span>
                <ArrowRight className="w-4 h-4" />
              </Link>
            </div>
          )}
        </div>
      </main>

      {/* Footer */}
      <footer className="max-w-5xl w-full mx-auto pt-4 border-t border-zinc-900 flex flex-col sm:flex-row items-center justify-between gap-2 text-[11px] text-zinc-500 relative z-10">
        <span>GourmetQR Restaurant Operations Platform</span>
        <span>JWT &amp; Rol Tabanlı Güvenli Oturum Altyapısı</span>
      </footer>
    </div>
  );
}
