'use client';

import { useEffect, useState } from 'react';
import { QRCodeSVG } from 'qrcode.react';
import {
  QrCode, Utensils, Users, ExternalLink, Plus, X,
  CheckCircle2, Clock, Flame, Trash2, DollarSign, TrendingUp, ShoppingBag,
  ArrowUpRight, Award, Shield, UserPlus, UserCheck, ChefHat, Lock, LogOut,
  ShieldCheck, FolderPlus, AlertCircle
} from 'lucide-react';
import { api, clearAllAuthStorage, clearLegacyLocalStorageAuth } from '@/lib/api';
import { MenuCategory, Product, RestaurantTable } from '@/lib/types';
import { motion } from 'framer-motion';

interface StaffMember {
  id: number;
  name: string;
  email: string;
  role: 'KITCHEN' | 'WAITER' | 'MANAGER';
  pin: string;
  isOnDuty: boolean;
  joinedDate: string;
}

interface SalesTimeframeData {
  grossRevenue: number;
  growth: string;
  orderCount: number;
  avgOrderValue: number;
  tips: number;
  turnoverRate: string;
  occupancy: string;
  hourlyDistribution: { label: string; amount: number; percent: number; color: string }[];
  paymentSplit: { cardPct: number; cardAmt: number; walletPct: number; walletAmt: number; cashPct: number; cashAmt: number };
  records: any[];
}

const TIMEFRAME_DATASETS: Record<'today' | 'yesterday' | 'week' | 'month', SalesTimeframeData> = {
  today: {
    grossRevenue: 3420.50,
    growth: '+18.4%',
    orderCount: 42,
    avgOrderValue: 81.44,
    tips: 382.00,
    turnoverRate: '1.8 Turns / Table',
    occupancy: '85% Occupancy',
    hourlyDistribution: [
      { label: '08:00 - 11:00 (Breakfast)', amount: 420.00, percent: 35, color: 'bg-amber-500' },
      { label: '11:00 - 15:00 (Lunch Peak)', amount: 1280.00, percent: 85, color: 'bg-emerald-500' },
      { label: '15:00 - 18:00 (Afternoon Tea)', amount: 380.00, percent: 30, color: 'bg-blue-500' },
      { label: '18:00 - 22:00 (Dinner Peak)', amount: 1340.50, percent: 95, color: 'bg-purple-500' },
    ],
    paymentSplit: { cardPct: 68, cardAmt: 2325.90, walletPct: 24, walletAmt: 820.90, cashPct: 8, cashAmt: 273.70 },
    records: [
      { id: 2149, orderNumber: 'ORD-2149', tableName: 'Table #12', soldTime: '12:05 PM', soldDate: 'Today', itemsText: '2x Truffle Wagyu Ribeye Steak, 2x Iced Caramel Macchiato', paymentMethod: 'Credit Card', tipAmount: 15.00, totalAmount: 1940.00, status: 'PAID' },
      { id: 2148, orderNumber: 'ORD-2148', tableName: 'Table #04', soldTime: '11:48 AM', soldDate: 'Today', itemsText: '1x Tartufo e Burrata Pizza, 1x Classic Tiramisu', paymentMethod: 'Apple Pay', tipAmount: 5.50, totalAmount: 42.00, status: 'PAID' },
      { id: 2147, orderNumber: 'ORD-2147', tableName: 'Table #01', soldTime: '11:32 AM', soldDate: 'Today', itemsText: '2x Double Smoked Bacon Burger, 2x Fresh Lemonade', paymentMethod: 'Credit Card', tipAmount: 6.00, totalAmount: 58.50, status: 'PAID' },
      { id: 2146, orderNumber: 'ORD-2146', tableName: 'Table #08', soldTime: '10:55 AM', soldDate: 'Today', itemsText: '1x Wild Mushroom Tagliatelle, 1x Espresso Martini', paymentMethod: 'Cash', tipAmount: 4.00, totalAmount: 38.00, status: 'PAID' },
      { id: 2145, orderNumber: 'ORD-2145', tableName: 'Table #02', soldTime: '10:15 AM', soldDate: 'Today', itemsText: '3x Avocado Toast & Poached Eggs, 3x Iced Latte', paymentMethod: 'Apple Pay', tipAmount: 10.00, totalAmount: 84.00, status: 'PAID' },
    ]
  },
  yesterday: {
    grossRevenue: 2890.00,
    growth: '+5.2%',
    orderCount: 36,
    avgOrderValue: 80.27,
    tips: 295.00,
    turnoverRate: '1.5 Turns / Table',
    occupancy: '78% Occupancy',
    hourlyDistribution: [
      { label: '08:00 - 11:00 (Breakfast)', amount: 350.00, percent: 30, color: 'bg-amber-500' },
      { label: '11:00 - 15:00 (Lunch Peak)', amount: 1050.00, percent: 75, color: 'bg-emerald-500' },
      { label: '15:00 - 18:00 (Afternoon Tea)', amount: 290.00, percent: 25, color: 'bg-blue-500' },
      { label: '18:00 - 22:00 (Dinner Peak)', amount: 1200.00, percent: 85, color: 'bg-purple-500' },
    ],
    paymentSplit: { cardPct: 62, cardAmt: 1791.80, walletPct: 30, walletAmt: 867.00, cashPct: 8, cashAmt: 231.20 },
    records: [
      { id: 2139, orderNumber: 'ORD-2139', tableName: 'Table #06', soldTime: '09:14 PM', soldDate: 'Yesterday', itemsText: '2x Truffle Wagyu Ribeye, 1x Red Wine Bottle', paymentMethod: 'Credit Card', tipAmount: 25.00, totalAmount: 320.00, status: 'PAID' },
      { id: 2138, orderNumber: 'ORD-2138', tableName: 'Table #03', soldTime: '08:30 PM', soldDate: 'Yesterday', itemsText: '2x Tartufo Pizza, 2x Tiramisu', paymentMethod: 'Apple Pay', tipAmount: 12.00, totalAmount: 78.00, status: 'PAID' },
      { id: 2137, orderNumber: 'ORD-2137', tableName: 'Table #09', soldTime: '07:15 PM', soldDate: 'Yesterday', itemsText: '4x Gourmet Bacon Burgers, 4x Craft Beers', paymentMethod: 'Credit Card', tipAmount: 18.00, totalAmount: 142.00, status: 'PAID' },
      { id: 2136, orderNumber: 'ORD-2136', tableName: 'Table #02', soldTime: '01:20 PM', soldDate: 'Yesterday', itemsText: '2x Wild Mushroom Tagliatelle, 2x Iced Tea', paymentMethod: 'Cash', tipAmount: 5.00, totalAmount: 52.00, status: 'PAID' },
    ]
  },
  week: {
    grossRevenue: 21450.00,
    growth: '+12.8%',
    orderCount: 274,
    avgOrderValue: 78.28,
    tips: 2410.00,
    turnoverRate: '1.7 Turns / Table',
    occupancy: '82% Avg Occupancy',
    hourlyDistribution: [
      { label: 'Mon - Tue (Weekday Early)', amount: 4200.00, percent: 45, color: 'bg-blue-500' },
      { label: 'Wed - Thu (Midweek Dining)', amount: 5800.00, percent: 65, color: 'bg-emerald-500' },
      { label: 'Fri - Sat (Weekend Peak)', amount: 8950.00, percent: 100, color: 'bg-purple-500' },
      { label: 'Sun (Sunday Brunch & Evening)', amount: 2500.00, percent: 35, color: 'bg-amber-500' },
    ],
    paymentSplit: { cardPct: 65, cardAmt: 13942.50, walletPct: 27, walletAmt: 5791.50, cashPct: 8, cashAmt: 1716.00 },
    records: [
      { id: 2125, orderNumber: 'ORD-2125', tableName: 'Table #12', soldTime: '09:30 PM', soldDate: 'Sun, 04 Aug', itemsText: '2x Truffle Wagyu Ribeye Steak, 2x Pinot Noir Fine Wine', paymentMethod: 'Credit Card', tipAmount: 35.00, totalAmount: 350.00, status: 'PAID' },
      { id: 2124, orderNumber: 'ORD-2124', tableName: 'Table #08', soldTime: '08:45 PM', soldDate: 'Sun, 04 Aug', itemsText: '3x Tartufo e Burrata Pizza, 3x Aperol Spritz Cocktail', paymentMethod: 'Apple Pay', tipAmount: 22.00, totalAmount: 132.00, status: 'PAID' },
      { id: 2123, orderNumber: 'ORD-2123', tableName: 'Table #04', soldTime: '07:20 PM', soldDate: 'Sat, 03 Aug', itemsText: '4x Double Smoked Bacon Burger, 4x Craft Beer Flight', paymentMethod: 'Credit Card', tipAmount: 20.00, totalAmount: 160.00, status: 'PAID' },
      { id: 2122, orderNumber: 'ORD-2122', tableName: 'Table #02', soldTime: '06:10 PM', soldDate: 'Sat, 03 Aug', itemsText: '2x Saffron & Lobster Risotto, 2x Espresso Martini', paymentMethod: 'Credit Card', tipAmount: 18.00, totalAmount: 100.00, status: 'PAID' },
      { id: 2121, orderNumber: 'ORD-2121', tableName: 'Table #06', soldTime: '02:15 PM', soldDate: 'Fri, 02 Aug', itemsText: '3x Wild Mushroom Tagliatelle, 3x Fresh Organic Lemonade', paymentMethod: 'Apple Pay', tipAmount: 12.00, totalAmount: 93.75, status: 'PAID' },
      { id: 2120, orderNumber: 'ORD-2120', tableName: 'Table #05', soldTime: '01:45 PM', soldDate: 'Fri, 02 Aug', itemsText: '2x Artisanal Margherita Pizza, 2x Classic Italian Tiramisu', paymentMethod: 'Cash', tipAmount: 8.00, totalAmount: 72.00, status: 'PAID' },
      { id: 2119, orderNumber: 'ORD-2119', tableName: 'Table #10', soldTime: '12:30 PM', soldDate: 'Thu, 01 Aug', itemsText: '4x Avocado Toast & Poached Eggs, 4x Iced Caramel Macchiato', paymentMethod: 'Credit Card', tipAmount: 15.00, totalAmount: 120.00, status: 'PAID' },
      { id: 2118, orderNumber: 'ORD-2118', tableName: 'Table #11', soldTime: '07:30 PM', soldDate: 'Thu, 01 Aug', itemsText: '3x Tartufo Pizza, 3x Aperol Spritz', paymentMethod: 'Apple Pay', tipAmount: 20.00, totalAmount: 135.00, status: 'PAID' },
      { id: 2117, orderNumber: 'ORD-2117', tableName: 'Table #03', soldTime: '06:40 PM', soldDate: 'Wed, 31 Jul', itemsText: '2x Chef\'s Banquet Tasting Menu, 2x Fine Wine Pairing', paymentMethod: 'Credit Card', tipAmount: 40.00, totalAmount: 380.00, status: 'PAID' },
      { id: 2116, orderNumber: 'ORD-2116', tableName: 'Table #09', soldTime: '02:00 PM', soldDate: 'Wed, 31 Jul', itemsText: '2x Double Smoked Bacon Burger, 2x Iced Latte', paymentMethod: 'Cash', tipAmount: 6.00, totalAmount: 56.00, status: 'PAID' },
      { id: 2115, orderNumber: 'ORD-2115', tableName: 'Table #07', soldTime: '01:15 PM', soldDate: 'Tue, 30 Jul', itemsText: '2x Wild Mushroom Tagliatelle, 2x Fresh Juices', paymentMethod: 'Credit Card', tipAmount: 8.00, totalAmount: 64.00, status: 'PAID' },
      { id: 2114, orderNumber: 'ORD-2114', tableName: 'Table #01', soldTime: '12:10 PM', soldDate: 'Mon, 29 Jul', itemsText: '2x Bacon Burgers, 2x Craft Sodas', paymentMethod: 'Cash', tipAmount: 5.00, totalAmount: 46.00, status: 'PAID' },
    ]
  },
  month: {
    grossRevenue: 94820.00,
    growth: '+22.1%',
    orderCount: 1180,
    avgOrderValue: 80.35,
    tips: 10350.00,
    turnoverRate: '1.9 Turns / Table',
    occupancy: '88% Avg Occupancy',
    hourlyDistribution: [
      { label: 'Week 1 (01 Aug - 07 Aug)', amount: 22400.00, percent: 75, color: 'bg-emerald-500' },
      { label: 'Week 2 (08 Aug - 14 Aug)', amount: 24100.00, percent: 85, color: 'bg-purple-500' },
      { label: 'Week 3 (15 Aug - 21 Aug)', amount: 23800.00, percent: 80, color: 'bg-blue-500' },
      { label: 'Week 4 (22 Aug - 31 Aug)', amount: 24520.00, percent: 90, color: 'bg-amber-500' },
    ],
    paymentSplit: { cardPct: 67, cardAmt: 63529.40, walletPct: 25, walletAmt: 23705.00, cashPct: 8, cashAmt: 7585.60 },
    records: [
      { id: 2095, orderNumber: 'ORD-2095', tableName: 'Table #12', soldTime: '09:40 PM', soldDate: '04 Aug', itemsText: '6x Truffle Wagyu Ribeye Steak, 6x Pinot Noir Fine Wine', paymentMethod: 'Credit Card', tipAmount: 140.00, totalAmount: 1420.00, status: 'PAID' },
      { id: 2094, orderNumber: 'ORD-2094', tableName: 'Table #08', soldTime: '08:50 PM', soldDate: '04 Aug', itemsText: '4x Tartufo e Burrata Pizza, 4x Classic Italian Tiramisu', paymentMethod: 'Apple Pay', tipAmount: 25.00, totalAmount: 168.00, status: 'PAID' },
      { id: 2093, orderNumber: 'ORD-2093', tableName: 'Table #05', soldTime: '07:30 PM', soldDate: '03 Aug', itemsText: '5x Double Smoked Bacon Burger, 5x Espresso Martini', paymentMethod: 'Credit Card', tipAmount: 30.00, totalAmount: 180.00, status: 'PAID' },
      { id: 2092, orderNumber: 'ORD-2092', tableName: 'Table #02', soldTime: '06:15 PM', soldDate: '03 Aug', itemsText: '3x Saffron & Lobster Risotto, 3x Aperol Spritz', paymentMethod: 'Credit Card', tipAmount: 22.00, totalAmount: 156.00, status: 'PAID' },
      { id: 2091, orderNumber: 'ORD-2091', tableName: 'Table #10', soldTime: '02:10 PM', soldDate: '02 Aug', itemsText: '4x Wild Mushroom Tagliatelle, 4x Fresh Organic Lemonade', paymentMethod: 'Apple Pay', tipAmount: 16.00, totalAmount: 125.00, status: 'PAID' },
      { id: 2090, orderNumber: 'ORD-2090', tableName: 'Table #12', soldTime: '09:10 PM', soldDate: '02 Aug', itemsText: '6x Banquet Chef Tasting Menu, Fine Wine Pairing', paymentMethod: 'Credit Card', tipAmount: 120.00, totalAmount: 1250.00, status: 'PAID' },
      { id: 2089, orderNumber: 'ORD-2089', tableName: 'Table #01', soldTime: '01:15 PM', soldDate: '01 Aug', itemsText: '3x Artisanal Margherita Pizza, 3x Craft Beer Flight', paymentMethod: 'Cash', tipAmount: 12.00, totalAmount: 120.00, status: 'PAID' },
      { id: 2088, orderNumber: 'ORD-2088', tableName: 'Table #06', soldTime: '12:00 PM', soldDate: '01 Aug', itemsText: '4x Avocado Toast & Poached Eggs, 4x Iced Caramel Macchiato', paymentMethod: 'Apple Pay', tipAmount: 14.00, totalAmount: 120.00, status: 'PAID' },
      { id: 2085, orderNumber: 'ORD-2085', tableName: 'Table #04', soldTime: '08:15 PM', soldDate: '31 Jul', itemsText: '4x Truffle Ribeye Steaks, 4x Tiramisu', paymentMethod: 'Apple Pay', tipAmount: 50.00, totalAmount: 410.00, status: 'PAID' },
      { id: 2080, orderNumber: 'ORD-2080', tableName: 'Table #11', soldTime: '07:45 PM', soldDate: '30 Jul', itemsText: '3x Double Smoked Bacon Burger, 3x Craft Sodas', paymentMethod: 'Credit Card', tipAmount: 15.00, totalAmount: 85.00, status: 'PAID' },
      { id: 2075, orderNumber: 'ORD-2075', tableName: 'Table #03', soldTime: '02:30 PM', soldDate: '29 Jul', itemsText: '2x Saffron & Lobster Risotto, 2x Fresh Juices', paymentMethod: 'Credit Card', tipAmount: 14.00, totalAmount: 86.00, status: 'PAID' },
      { id: 2070, orderNumber: 'ORD-2070', tableName: 'Table #08', soldTime: '01:45 PM', soldDate: '28 Jul', itemsText: '3x Artisanal Pizzas, 3x Italian Sodas', paymentMethod: 'Credit Card', tipAmount: 15.00, totalAmount: 98.00, status: 'PAID' },
      { id: 2060, orderNumber: 'ORD-2060', tableName: 'Table #07', soldTime: '08:00 PM', soldDate: '27 Jul', itemsText: '2x Truffle Wagyu Ribeye, 2x Pinot Noir Wine', paymentMethod: 'Apple Pay', tipAmount: 40.00, totalAmount: 370.00, status: 'PAID' },
      { id: 2055, orderNumber: 'ORD-2055', tableName: 'Table #09', soldTime: '01:10 PM', soldDate: '26 Jul', itemsText: '3x Wild Mushroom Tagliatelle, 3x Tiramisu', paymentMethod: 'Credit Card', tipAmount: 16.00, totalAmount: 114.00, status: 'PAID' },
      { id: 2050, orderNumber: 'ORD-2050', tableName: 'Table #02', soldTime: '12:30 PM', soldDate: '25 Jul', itemsText: '2x Tagliatelle Pasta, 2x Espresso Coffee', paymentMethod: 'Cash', tipAmount: 7.00, totalAmount: 54.00, status: 'PAID' },
    ]
  }
};

export default function AdminDashboardPage() {
  // Admin PIN Authentication Security State
  const [isAdminAuthenticated, setIsAdminAuthenticated] = useState(false);
  const [adminPin, setAdminPin] = useState('');
  const [adminPinError, setAdminPinError] = useState<string | null>(null);

  const [activeTab, setActiveTab] = useState<'sales' | 'tables' | 'menu' | 'staff'>('sales');
  
  // Table states
  const [tables, setTables] = useState<RestaurantTable[]>([]);
  const [loadingTables, setLoadingTables] = useState(true);
  const [isCreateTableModalOpen, setIsCreateTableModalOpen] = useState(false);
  const [newTableNumber, setNewTableNumber] = useState('');
  const [creatingTable, setCreatingTable] = useState(false);
  
  // Menu states
  const [categories, setCategories] = useState<MenuCategory[]>([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState<number>(1);
  const [products, setProducts] = useState<Product[]>([]);
  const [loadingMenu, setLoadingMenu] = useState(false);
  const [isCreateProductModalOpen, setIsCreateProductModalOpen] = useState(false);
  const [creatingProduct, setCreatingProduct] = useState(false);

  // Category Creation states
  const [isCreateCategoryModalOpen, setIsCreateCategoryModalOpen] = useState(false);
  const [newCategoryName, setNewCategoryName] = useState('');
  const [newCategoryDesc, setNewCategoryDesc] = useState('');
  const [creatingCategory, setCreatingCategory] = useState(false);

  // Staff states
  const [staffList, setStaffList] = useState<StaffMember[]>([
    { id: 1, name: 'Gordon Ramsay', email: 'gordon@bosphorus.com', role: 'KITCHEN', pin: '1234', isOnDuty: true, joinedDate: 'Jan 2026' },
    { id: 2, name: 'Alex Morgan', email: 'alex@bosphorus.com', role: 'WAITER', pin: '2345', isOnDuty: true, joinedDate: 'Feb 2026' },
    { id: 3, name: 'Marco Pierre', email: 'marco@bosphorus.com', role: 'KITCHEN', pin: '3456', isOnDuty: false, joinedDate: 'Mar 2026' },
    { id: 4, name: 'Sarah Jenkins', email: 'sarah@bosphorus.com', role: 'WAITER', pin: '4567', isOnDuty: true, joinedDate: 'Apr 2026' }
  ]);
  const [isAddStaffModalOpen, setIsAddStaffModalOpen] = useState(false);
  const [staffName, setStaffName] = useState('');
  const [staffEmail, setStaffEmail] = useState('');
  const [staffRole, setStaffRole] = useState<'KITCHEN' | 'WAITER' | 'MANAGER'>('WAITER');
  const [staffPin, setStaffPin] = useState('1122');

  // Sales Analytics states
  const [salesTimeframe, setSalesTimeframe] = useState<'today' | 'yesterday' | 'week' | 'month'>('today');
  const [liveOrdersList, setLiveOrdersList] = useState<any[]>([]);

  // Product Form states
  const [prodName, setProdName] = useState('');
  const [prodDesc, setProdDesc] = useState('');
  const [prodPrice, setProdPrice] = useState('');
  const [prodCategoryId, setProdCategoryId] = useState<number>(1);
  const [prodImageUrl, setProdImageUrl] = useState('');
  const [prodPrepTime, setProdPrepTime] = useState('15');
  const [prodCalories, setProdCalories] = useState('500');
  const [prodTags, setProdTags] = useState('Chef Pick, Fresh');

  const [notification, setNotification] = useState<string | null>(null);
  const [errorNotification, setErrorNotification] = useState<string | null>(null);

  const showSuccess = (msg: string) => {
    setErrorNotification(null);
    setNotification(msg);
    setTimeout(() => setNotification(null), 5000);
  };

  const showError = (msg: string) => {
    setNotification(null);
    setErrorNotification(msg);
    setTimeout(() => setErrorNotification(null), 7000);
  };

  useEffect(() => {
    clearLegacyLocalStorageAuth();
    const savedAdminSession = sessionStorage.getItem('qr_admin_session');
    const savedToken = sessionStorage.getItem('token');
    if (savedAdminSession === 'ACTIVE' && savedToken) {
      setIsAdminAuthenticated(true);
    } else {
      setIsAdminAuthenticated(false);
    }
  }, []);

  useEffect(() => {
    if (!isAdminAuthenticated) return;

    const loadOrderHistoryFromBackend = async () => {
      try {
        const orders = await api.getBranchOrderHistory(1);
        if (Array.isArray(orders)) {
          const records = orders
            .filter(o => o.status !== 'CANCELLED')
            .map(o => {
              const upperMethod = (o.paymentMethod || 'CARD').toUpperCase();
              const paymentLabel =
                upperMethod === 'COUNTER' || upperMethod === 'CASH'
                  ? 'Cash / Counter'
                  : upperMethod === 'DIGITAL_WALLET' || upperMethod === 'WALLET'
                  ? 'Digital Wallet'
                  : 'Credit Card';

              return {
                id: o.id,
                orderNumber: o.orderNumber,
                tableName: o.tableName || 'Table #01',
                soldTime: o.createdAt
                  ? new Date(o.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
                  : 'Today',
                soldDate: o.createdAt
                  ? new Date(o.createdAt).toLocaleDateString()
                  : 'Today',
                itemsText: o.items
                  ? o.items.map(i => `${i.quantity || 1}x ${i.productName}`).join(', ')
                  : 'Order Items',
                paymentMethod: paymentLabel,
                tipAmount: Number(o.tipAmount || 0),
                totalAmount: Number(o.totalAmount || 0),
                status: o.status,
              };
            });
          setLiveOrdersList(records);
        }
      } catch (reportErr) {
        console.error('Failed to load order history from backend', reportErr);
      }
    };

    async function loadInitialData() {
      try {
        setLoadingTables(true);
        const data = await api.getBranchTables(1);
        setTables(data);

        const cats = await api.getCategories(1);
        setCategories(cats);
        if (cats.length > 0) {
          setSelectedCategoryId(cats[0].id);
          setProdCategoryId(cats[0].id);
          const prods = await api.getProducts(cats[0].id);
          setProducts(prods);
        } else {
          setSelectedCategoryId(0);
          setProdCategoryId(0);
          setProducts([]);
        }

        try {
          const staffUsers = await api.getStaff(1);
          if (Array.isArray(staffUsers) && staffUsers.length > 0) {
            setStaffList(
              staffUsers.map(u => ({
                id: u.id,
                name: `${u.firstName || ''} ${u.lastName || ''}`.trim() || u.email,
                email: u.email,
                role: (u.role === 'KITCHEN' || u.role === 'WAITER' || u.role === 'MANAGER') ? u.role : 'MANAGER',
                pin: '1234',
                isOnDuty: u.isActive ?? u.active ?? true,
                joinedDate: u.createdAt ? new Date(u.createdAt).toLocaleDateString() : 'Active',
              }))
            );
          }
        } catch (staffErr) {
          console.error('Failed to load staff from backend', staffErr);
        }

        await loadOrderHistoryFromBackend();
      } catch (err: any) {
        console.error('Failed to load initial admin data', err);
        showError(`Veriler yüklenirken hata oluştu: ${err.message}`);
      } finally {
        setLoadingTables(false);
      }
    }
    loadInitialData();

    const handleLiveOrder = () => {
      loadOrderHistoryFromBackend();
    };

    const handleStorageChange = (e: StorageEvent) => {
      if (e.key === 'qr_all_orders' || e.key === 'qr_latest_order') {
        loadOrderHistoryFromBackend();
      }
    };

    if (typeof window !== 'undefined') {
      window.addEventListener('qr_new_order', handleLiveOrder);
      window.addEventListener('storage', handleStorageChange);
    }

    return () => {
      if (typeof window !== 'undefined') {
        window.removeEventListener('qr_new_order', handleLiveOrder);
        window.removeEventListener('storage', handleStorageChange);
      }
    };
  }, [isAdminAuthenticated]);

  const handleAdminLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setAdminPinError(null);
    try {
      const authRes = await api.login({
        email: 'admin@gourmet.com',
        password: adminPin.trim(),
      });
      sessionStorage.setItem('token', authRes.accessToken);
      sessionStorage.setItem('qr_admin_session', 'ACTIVE');
      sessionStorage.setItem('staffUser', JSON.stringify(authRes.user));
      setIsAdminAuthenticated(true);
    } catch (err: any) {
      setAdminPinError(err.message || 'Invalid Admin Security PIN code. Default Admin PIN: 9999 or 1234');
    }
  };

  const handleAdminLogout = () => {
    clearAllAuthStorage();
    setIsAdminAuthenticated(false);
    setAdminPin('');
  };

  const handleCategorySelect = async (catId: number) => {
    setSelectedCategoryId(catId);
    setProdCategoryId(catId);
    try {
      setLoadingMenu(true);
      const prods = await api.getProducts(catId);
      setProducts(prods);
    } catch (err: any) {
      console.error('Failed to load products for category', err);
      showError(`Kategori ürünleri yüklenemedi: ${err.message}`);
    } finally {
      setLoadingMenu(false);
    }
  };

  const handleCreateCategory = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newCategoryName.trim() || creatingCategory) return;

    try {
      setCreatingCategory(true);
      const created = await api.createCategory({
        businessId: 1,
        name: newCategoryName.trim(),
        description: newCategoryDesc.trim(),
        sortOrder: categories.length + 1
      });

      const refreshedCats = await api.getCategories(1);
      setCategories(refreshedCats);

      const newCatId = created.id;
      setSelectedCategoryId(newCatId);
      setProdCategoryId(newCatId);

      const refreshedProds = await api.getProducts(newCatId);
      setProducts(refreshedProds);

      setNewCategoryName('');
      setNewCategoryDesc('');
      setIsCreateCategoryModalOpen(false);
      showSuccess(`Successfully created new menu category "${created.name}" (ID: #${newCatId})!`);
    } catch (err: any) {
      showError(`Failed to create category: ${err.message}`);
    } finally {
      setCreatingCategory(false);
    }
  };

  const handleCreateTable = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newTableNumber.trim() || creatingTable) return;

    try {
      setCreatingTable(true);
      const created = await api.createTable({ branchId: 1, tableNumber: newTableNumber.trim() });
      const refreshedTables = await api.getBranchTables(1);
      setTables(refreshedTables);
      setNewTableNumber('');
      setIsCreateTableModalOpen(false);
      showSuccess(`Successfully created Table ${created.tableNumber || newTableNumber} with unique QR token!`);
    } catch (err: any) {
      showError(`Failed to create table: ${err.message}`);
    } finally {
      setCreatingTable(false);
    }
  };

  const handleCreateProduct = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!prodName.trim() || !prodPrice || creatingProduct) return;

    const targetCategoryId = prodCategoryId || selectedCategoryId;
    if (!targetCategoryId || categories.length === 0) {
      showError('Please create a menu category first before adding a product.');
      return;
    }

    try {
      setCreatingProduct(true);
      const priceNum = parseFloat(prodPrice);
      const tagsArray = prodTags.split(',').map(t => t.trim()).filter(Boolean);

      const created = await api.createProduct({
        categoryId: targetCategoryId,
        name: prodName.trim(),
        description: prodDesc.trim(),
        price: priceNum,
        imageUrl: prodImageUrl.trim() || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80',
        prepTimeMinutes: parseInt(prodPrepTime) || 15,
        calories: parseInt(prodCalories) || 500,
        tags: tagsArray.length > 0 ? tagsArray : ['Chef Special']
      });

      setSelectedCategoryId(targetCategoryId);
      const refreshedProducts = await api.getProducts(targetCategoryId);
      setProducts(refreshedProducts);

      setProdName('');
      setProdDesc('');
      setProdPrice('');
      setProdImageUrl('');
      setIsCreateProductModalOpen(false);
      showSuccess(`Successfully added new product "${created.name}" ($${priceNum.toFixed(2)}) to menu!`);
    } catch (err: any) {
      showError(`Failed to create product: ${err.message}`);
    } finally {
      setCreatingProduct(false);
    }
  };

  const handleDeleteProduct = async (productId: number, productName: string) => {
    if (!confirm(`Are you sure you want to delete "${productName}" from the menu?`)) return;

    try {
      await api.deleteProduct(productId);
      const refreshedProducts = await api.getProducts(selectedCategoryId);
      setProducts(refreshedProducts);
      showSuccess(`Successfully deleted "${productName}" from the menu.`);
    } catch (err: any) {
      showError(`Failed to delete product: ${err.message}`);
    }
  };

  const handleAddStaffMember = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!staffName.trim() || !staffEmail.trim()) return;

    const nameParts = staffName.trim().split(/\s+/);
    const firstName = nameParts[0] || 'Staff';
    const lastName = nameParts.slice(1).join(' ') || 'Member';

    try {
      const created = await api.createStaff({
        businessId: 1,
        branchId: 1,
        email: staffEmail.trim(),
        password: staffPin.trim() || '123456',
        firstName,
        lastName,
        role: staffRole,
      });

      const newStaff: StaffMember = {
        id: created.id,
        name: `${created.firstName} ${created.lastName}`.trim(),
        email: created.email,
        role: staffRole,
        pin: staffPin.trim() || '1234',
        isOnDuty: created.isActive ?? true,
        joinedDate: 'Just now'
      };

      setStaffList(prev => [newStaff, ...prev]);
      setStaffName('');
      setStaffEmail('');
      setIsAddStaffModalOpen(false);
      showSuccess(`Successfully added new staff member "${newStaff.name}" (${newStaff.role})!`);
    } catch (err: any) {
      showError(`Failed to add staff member: ${err.message}`);
    }
  };

  const handleToggleStaffDuty = async (id: number) => {
    try {
      const updatedUser = await api.toggleStaffStatus(id);
      setStaffList(prev =>
        prev.map(s =>
          s.id === id
            ? { ...s, isOnDuty: updatedUser.isActive ?? updatedUser.active ?? !s.isOnDuty }
            : s
        )
      );
    } catch (err: any) {
      showError(`Failed to toggle staff duty status: ${err.message}`);
    }
  };

  const handleDeleteStaff = async (id: number, name: string) => {
    if (!confirm(`Are you sure you want to remove staff member "${name}"?`)) return;
    try {
      await api.deleteStaff(id);
      setStaffList(prev => prev.filter(s => s.id !== id));
      showSuccess(`Removed staff member "${name}".`);
    } catch (err: any) {
      showError(`Failed to remove staff member: ${err.message}`);
    }
  };

  const baseDataset = TIMEFRAME_DATASETS[salesTimeframe];
  const combinedRecords = liveOrdersList;
  const dynamicGrossRevenue = combinedRecords.reduce((acc, r) => acc + Number(r.totalAmount || 0), 0);
  const dynamicOrderCount = combinedRecords.length;
  const dynamicAvgOrderValue = dynamicOrderCount > 0 ? dynamicGrossRevenue / dynamicOrderCount : 0;
  const dynamicTips = combinedRecords.reduce((acc, r) => acc + Number(r.tipAmount || 0), 0);

  const currentDataset: SalesTimeframeData = {
    ...baseDataset,
    grossRevenue: dynamicGrossRevenue,
    orderCount: dynamicOrderCount,
    avgOrderValue: dynamicAvgOrderValue,
    tips: dynamicTips,
    records: combinedRecords,
  };

  // Render Admin PIN Security Lock Screen if not authenticated
  if (!isAdminAuthenticated) {
    return (
      <div className="min-h-screen bg-zinc-950 text-zinc-100 flex items-center justify-center p-4">
        <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel max-w-md w-full p-8 rounded-3xl border border-zinc-800 text-center space-y-6">
          <div className="w-16 h-16 rounded-2xl bg-purple-600/20 text-purple-400 border border-purple-500/30 flex items-center justify-center mx-auto">
            <Lock className="w-8 h-8" />
          </div>

          <div>
            <h1 className="text-2xl font-bold text-white">Admin Control Panel Security Access</h1>
            <p className="text-xs text-zinc-400 mt-1">Multi-Tenant Management Portal • Enter Admin PIN</p>
          </div>

          {adminPinError && (
            <div className="p-3 rounded-xl bg-red-500/10 border border-red-500/30 text-red-400 text-xs">
              {adminPinError}
            </div>
          )}

          <form onSubmit={handleAdminLogin} className="space-y-4 text-left">
            <div>
              <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1.5">
                Admin Security PIN Code
              </label>
              <input
                type="password"
                maxLength={4}
                required
                value={adminPin}
                onChange={e => setAdminPin(e.target.value)}
                placeholder="•••• (Default: 9999 or 1234)"
                className="w-full bg-zinc-900 border border-zinc-800 rounded-2xl px-4 py-3 text-center text-xl tracking-[0.5em] font-mono text-white placeholder:tracking-normal placeholder:text-xs placeholder-zinc-500 focus:outline-none focus:border-purple-500"
              />
            </div>

            <button
              type="submit"
              className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-3.5 rounded-2xl text-sm transition shadow-lg shadow-purple-600/30 flex items-center justify-center gap-2"
            >
              <ShieldCheck className="w-5 h-5" /> Authenticate & Unlock Dashboard
            </button>
          </form>

          <div className="pt-2 text-zinc-500 text-[11px]">
            <span>System Default PIN: </span>
            <code className="text-purple-400 font-mono font-bold">9999</code> or <code className="text-purple-400 font-mono font-bold">1234</code>
          </div>
        </motion.div>
      </div>
    );
  }

  // Render Authenticated Admin Dashboard
  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 p-6 max-w-6xl mx-auto">
      {/* Header */}
      <header className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 pb-6 border-b border-zinc-800">
        <div>
          <h1 className="text-2xl font-bold text-white">Platform Admin Control Panel</h1>
          <p className="text-xs text-zinc-400">Multi-tenant Restaurant & Branch Management</p>
        </div>

        <div className="flex items-center gap-2">
          {activeTab === 'tables' && (
            <button
              onClick={() => setIsCreateTableModalOpen(true)}
              className="px-4 py-2 bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs rounded-xl transition flex items-center gap-1.5 shadow-lg shadow-purple-600/30"
            >
              <Plus className="w-4 h-4" /> Add Table & Generate QR
            </button>
          )}

          {activeTab === 'menu' && (
            <div className="flex gap-2">
              <button
                onClick={() => setIsCreateCategoryModalOpen(true)}
                className="px-3.5 py-2 bg-zinc-900 hover:bg-zinc-800 text-purple-300 border border-purple-500/30 font-bold text-xs rounded-xl transition flex items-center gap-1.5 shadow-md"
              >
                <FolderPlus className="w-4 h-4" /> + Add Category
              </button>
              <button
                disabled={categories.length === 0}
                title={categories.length === 0 ? 'Önce bir menü kategorisi oluşturmalısınız' : 'Add New Menu Product'}
                onClick={() => {
                  if (categories.length === 0) return;
                  setProdCategoryId(selectedCategoryId || categories[0].id);
                  setIsCreateProductModalOpen(true);
                }}
                className="px-4 py-2 bg-purple-600 hover:bg-purple-500 disabled:bg-zinc-800 disabled:text-zinc-500 disabled:cursor-not-allowed disabled:shadow-none text-white font-bold text-xs rounded-xl transition flex items-center gap-1.5 shadow-lg shadow-purple-600/30"
              >
                <Plus className="w-4 h-4" /> Add New Menu Product
              </button>
            </div>
          )}

          {activeTab === 'staff' && (
            <button
              onClick={() => setIsAddStaffModalOpen(true)}
              className="px-4 py-2 bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs rounded-xl transition flex items-center gap-1.5 shadow-lg shadow-purple-600/30"
            >
              <UserPlus className="w-4 h-4" /> Add New Staff Member
            </button>
          )}

          <a
            href="/kitchen?token=GOURMET-KDS-2026"
            target="_blank"
            rel="noreferrer"
            className="px-3.5 py-2 bg-amber-500/15 hover:bg-amber-500/25 text-amber-300 border border-amber-500/30 rounded-xl text-xs font-bold transition flex items-center gap-1.5"
            title="Open Kitchen Display Kiosk with Kiosk Token"
          >
            <ChefHat className="w-4 h-4" /> Kitchen Kiosk
          </a>

          <button
            onClick={handleAdminLogout}
            className="px-3.5 py-2 bg-zinc-900 hover:bg-red-500/20 text-zinc-300 hover:text-red-400 border border-zinc-800 rounded-xl text-xs font-bold transition flex items-center gap-1.5"
            title="Lock Admin Dashboard"
          >
            <LogOut className="w-4 h-4" /> Lock Panel
          </button>
        </div>
      </header>

      {/* Success Notification Banner */}
      {notification && (
        <div className="my-4 p-3.5 rounded-xl bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs flex items-center justify-between gap-2 shadow-lg">
          <div className="flex items-center gap-2">
            <CheckCircle2 className="w-4 h-4 shrink-0" />
            <span>{notification}</span>
          </div>
          <button onClick={() => setNotification(null)} className="text-emerald-400/70 hover:text-emerald-300">
            <X className="w-4 h-4" />
          </button>
        </div>
      )}

      {/* Red Error Notification Banner / Toast */}
      {errorNotification && (
        <div className="my-4 p-3.5 rounded-xl bg-red-500/15 border border-red-500/40 text-red-400 text-xs flex items-center justify-between gap-2 shadow-lg shadow-red-950/30">
          <div className="flex items-center gap-2">
            <AlertCircle className="w-4 h-4 shrink-0 text-red-400" />
            <span className="font-semibold">{errorNotification}</span>
          </div>
          <button onClick={() => setErrorNotification(null)} className="text-red-400/70 hover:text-red-300">
            <X className="w-4 h-4" />
          </button>
        </div>
      )}

      {/* Tabs */}
      <div className="flex border-b border-zinc-800 my-6 overflow-x-auto">
        <button
          onClick={() => setActiveTab('sales')}
          className={`px-5 py-2.5 font-semibold text-xs transition border-b-2 flex items-center gap-1.5 whitespace-nowrap ${
            activeTab === 'sales' ? 'border-purple-500 text-purple-400' : 'border-transparent text-zinc-400 hover:text-zinc-200'
          }`}
        >
          <TrendingUp className="w-4 h-4" /> Daily Sales & Business Analytics
        </button>
        <button
          onClick={() => setActiveTab('tables')}
          className={`px-5 py-2.5 font-semibold text-xs transition border-b-2 flex items-center gap-1.5 whitespace-nowrap ${
            activeTab === 'tables' ? 'border-purple-500 text-purple-400' : 'border-transparent text-zinc-400 hover:text-zinc-200'
          }`}
        >
          <QrCode className="w-4 h-4" /> Table QR Codes ({tables.length})
        </button>
        <button
          onClick={() => setActiveTab('menu')}
          className={`px-5 py-2.5 font-semibold text-xs transition border-b-2 flex items-center gap-1.5 whitespace-nowrap ${
            activeTab === 'menu' ? 'border-purple-500 text-purple-400' : 'border-transparent text-zinc-400 hover:text-zinc-200'
          }`}
        >
          <Utensils className="w-4 h-4" /> Menu & Product Manager
        </button>
        <button
          onClick={() => setActiveTab('staff')}
          className={`px-5 py-2.5 font-semibold text-xs transition border-b-2 flex items-center gap-1.5 whitespace-nowrap ${
            activeTab === 'staff' ? 'border-purple-500 text-purple-400' : 'border-transparent text-zinc-400 hover:text-zinc-200'
          }`}
        >
          <Users className="w-4 h-4" /> Staff Management ({staffList.length})
        </button>
      </div>

      {/* Sales Analytics Tab */}
      {activeTab === 'sales' && (
        <section className="space-y-6">
          {/* Timeframe Filter Bar */}
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-3 glass-card p-4 rounded-2xl border-zinc-800">
            <div>
              <h3 className="font-bold text-white text-base">Sales Performance Dashboard</h3>
              <p className="text-xs text-zinc-400">Viewing real-time metrics for: <span className="text-purple-300 uppercase font-semibold">{salesTimeframe}</span></p>
            </div>
            <div className="flex gap-1.5 bg-zinc-900 p-1 rounded-xl border border-zinc-800">
              {(['today', 'yesterday', 'week', 'month'] as const).map(tf => (
                <button
                  key={tf}
                  onClick={() => setSalesTimeframe(tf)}
                  className={`px-3 py-1.5 rounded-lg text-xs font-bold capitalize transition ${
                    salesTimeframe === tf ? 'bg-purple-600 text-white shadow-md' : 'text-zinc-400 hover:text-white'
                  }`}
                >
                  {tf === 'week' ? 'This Week' : tf === 'month' ? 'This Month' : tf}
                </button>
              ))}
            </div>
          </div>

          {/* Sales Metric KPI Cards */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <div className="glass-card p-4 rounded-3xl border-zinc-800 relative overflow-hidden">
              <div className="flex justify-between items-start mb-3">
                <div className="p-2.5 rounded-2xl bg-emerald-500/10 text-emerald-400 border border-emerald-500/20">
                  <DollarSign className="w-6 h-6" />
                </div>
                <span className="text-[11px] font-bold text-emerald-400 bg-emerald-500/10 px-2 py-0.5 rounded-full border border-emerald-500/20 flex items-center gap-0.5">
                  {currentDataset.growth} <ArrowUpRight className="w-3 h-3" />
                </span>
              </div>
              <span className="text-xs font-semibold text-zinc-400 block">Total Gross Revenue</span>
              <span className="text-2xl font-extrabold text-white">${currentDataset.grossRevenue.toLocaleString('en-US', { minimumFractionDigits: 2 })}</span>
              <span className="text-[11px] text-zinc-500 mt-1 block">From {currentDataset.orderCount} QR orders</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex justify-between items-start mb-3">
                <div className="p-2.5 rounded-2xl bg-purple-500/10 text-purple-400 border border-purple-500/20">
                  <ShoppingBag className="w-6 h-6" />
                </div>
                <span className="text-[11px] font-bold text-purple-400 bg-purple-500/10 px-2 py-0.5 rounded-full border border-purple-500/20">
                  Avg ${currentDataset.avgOrderValue.toFixed(2)}
                </span>
              </div>
              <span className="text-xs font-semibold text-zinc-400 block">Total Paid Orders</span>
              <span className="text-2xl font-extrabold text-white">{currentDataset.orderCount} Orders</span>
              <span className="text-[11px] text-zinc-500 mt-1 block">100% Paid via QR Gateway</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex justify-between items-start mb-3">
                <div className="p-2.5 rounded-2xl bg-amber-500/10 text-amber-400 border border-amber-500/20">
                  <Award className="w-6 h-6" />
                </div>
                <span className="text-[11px] font-bold text-amber-400 bg-amber-500/10 px-2 py-0.5 rounded-full border border-amber-500/20">
                  12.5% Avg Tip
                </span>
              </div>
              <span className="text-xs font-semibold text-zinc-400 block">Staff Tips Collected</span>
              <span className="text-2xl font-extrabold text-amber-300">${currentDataset.tips.toLocaleString('en-US', { minimumFractionDigits: 2 })}</span>
              <span className="text-[11px] text-zinc-500 mt-1 block">Distributed to floor staff</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex justify-between items-start mb-3">
                <div className="p-2.5 rounded-2xl bg-blue-500/10 text-blue-400 border border-blue-500/20">
                  <QrCode className="w-6 h-6" />
                </div>
                <span className="text-[11px] font-bold text-blue-400 bg-blue-500/10 px-2 py-0.5 rounded-full border border-blue-500/20">
                  {currentDataset.occupancy}
                </span>
              </div>
              <span className="text-xs font-semibold text-zinc-400 block">Table Turnover Rate</span>
              <span className="text-2xl font-extrabold text-white">{currentDataset.turnoverRate}</span>
              <span className="text-[11px] text-zinc-500 mt-1 block">Avg dining duration: 42 mins</span>
            </div>
          </div>

          {/* Detailed Hourly Sales Log & Transactions Audit Feed */}
          <div className="glass-card p-5 rounded-3xl border-zinc-800 space-y-4">
            <div className="pb-3 border-b border-zinc-800">
              <h4 className="font-bold text-white text-base flex items-center gap-2">
                <Clock className="w-4 h-4 text-purple-400" />
                Detailed Sales Transaction Log ({salesTimeframe.toUpperCase()})
              </h4>
              <p className="text-xs text-zinc-400">Complete itemized sales history logged with exact transaction timestamps</p>
            </div>

            {/* Sales Table */}
            <div className="overflow-x-auto">
              <table className="w-full text-left text-xs">
                <thead>
                  <tr className="text-zinc-400 border-b border-zinc-800 pb-2">
                    <th className="pb-2.5 font-semibold">Sold Hour & Date</th>
                    <th className="pb-2.5 font-semibold">Order Ref</th>
                    <th className="pb-2.5 font-semibold">Table</th>
                    <th className="pb-2.5 font-semibold">Purchased Product Items</th>
                    <th className="pb-2.5 font-semibold">Payment</th>
                    <th className="pb-2.5 font-semibold">Tip</th>
                    <th className="pb-2.5 font-semibold text-right">Amount Paid</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-zinc-800/60">
                  {combinedRecords.map((record) => (
                    <tr key={record.id} className="hover:bg-zinc-900/40 transition group">
                      <td className="py-3 font-bold text-purple-300 flex items-center gap-1.5 whitespace-nowrap">
                        <Clock className="w-3.5 h-3.5 text-purple-400 shrink-0" />
                        <span>{record.soldTime}</span>
                        <span className="text-[10px] text-zinc-500 font-normal">({record.soldDate})</span>
                      </td>
                      <td className="py-3 font-mono text-zinc-300 font-semibold">{record.orderNumber}</td>
                      <td className="py-3 text-white font-semibold whitespace-nowrap">{record.tableName}</td>
                      <td className="py-3 text-zinc-300 max-w-xs truncate" title={record.itemsText}>
                        {record.itemsText}
                      </td>
                      <td className="py-3 whitespace-nowrap">
                        <span className="px-2 py-0.5 rounded-full text-[10px] font-semibold bg-zinc-900 text-zinc-300 border border-zinc-800">
                          {record.paymentMethod}
                        </span>
                      </td>
                      <td className="py-3 text-amber-400 font-semibold whitespace-nowrap">
                        +${Number(record.tipAmount).toFixed(2)}
                      </td>
                      <td className="py-3 text-right font-extrabold text-emerald-400 whitespace-nowrap">
                        ${Number(record.totalAmount).toFixed(2)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </section>
      )}

      {/* Staff Management Tab */}
      {activeTab === 'staff' && (
        <section className="space-y-6">
          {/* Staff KPI Summary Cards */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex items-center gap-3 text-purple-400 mb-2">
                <Users className="w-6 h-6" />
                <span className="text-xs font-semibold text-zinc-400">Total Staff</span>
              </div>
              <span className="text-2xl font-extrabold text-white">{staffList.length} Members</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex items-center gap-3 text-emerald-400 mb-2">
                <ChefHat className="w-6 h-6" />
                <span className="text-xs font-semibold text-zinc-400">Kitchen Staff</span>
              </div>
              <span className="text-2xl font-extrabold text-white">{staffList.filter(s => s.role === 'KITCHEN').length} Chefs</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex items-center gap-3 text-amber-400 mb-2">
                <UserCheck className="w-6 h-6" />
                <span className="text-xs font-semibold text-zinc-400">Floor Waiters</span>
              </div>
              <span className="text-2xl font-extrabold text-white">{staffList.filter(s => s.role === 'WAITER').length} Waiters</span>
            </div>

            <div className="glass-card p-4 rounded-3xl border-zinc-800">
              <div className="flex items-center gap-3 text-blue-400 mb-2">
                <Shield className="w-6 h-6" />
                <span className="text-xs font-semibold text-zinc-400">Currently On-Duty</span>
              </div>
              <span className="text-2xl font-extrabold text-emerald-400">{staffList.filter(s => s.isOnDuty).length} Active</span>
            </div>
          </div>

          {/* Staff Roster Table */}
          <div className="glass-card p-5 rounded-3xl border-zinc-800 space-y-4">
            <div className="pb-3 border-b border-zinc-800">
              <h4 className="font-bold text-white text-base">Active Restaurant Staff Members</h4>
              <p className="text-xs text-zinc-400">Manage shift status, roles, and PIN credentials for POS/KDS access</p>
            </div>

            <div className="overflow-x-auto">
              <table className="w-full text-left text-xs">
                <thead>
                  <tr className="text-zinc-400 border-b border-zinc-800 pb-2">
                    <th className="pb-2.5 font-semibold">Staff Member</th>
                    <th className="pb-2.5 font-semibold">Role</th>
                    <th className="pb-2.5 font-semibold">Branch Access</th>
                    <th className="pb-2.5 font-semibold">Login PIN Code</th>
                    <th className="pb-2.5 font-semibold">Shift Duty Status</th>
                    <th className="pb-2.5 font-semibold text-right">Actions</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-zinc-800/60">
                  {staffList.map(member => (
                    <tr key={member.id} className="hover:bg-zinc-900/40 transition">
                      <td className="py-3 font-bold text-white flex items-center gap-3">
                        <div className="w-8 h-8 rounded-full bg-purple-600/20 border border-purple-500/30 flex items-center justify-center text-purple-400 font-bold text-xs">
                          {member.name.substring(0, 2).toUpperCase()}
                        </div>
                        <div>
                          <div className="font-bold text-white text-xs">{member.name}</div>
                          <div className="text-[11px] text-zinc-500">{member.email}</div>
                        </div>
                      </td>

                      <td className="py-3">
                        <span className={`px-2.5 py-1 rounded-full text-[10px] font-extrabold uppercase border ${
                          member.role === 'KITCHEN' ? 'bg-purple-500/10 text-purple-300 border-purple-500/30' :
                          member.role === 'WAITER' ? 'bg-amber-500/10 text-amber-300 border-amber-500/30' :
                          'bg-emerald-500/10 text-emerald-300 border-emerald-500/30'
                        }`}>
                          {member.role === 'KITCHEN' ? 'Kitchen Chef' : member.role === 'WAITER' ? 'Floor Waiter' : 'Branch Manager'}
                        </span>
                      </td>

                      <td className="py-3 text-zinc-300">Main Branch #1</td>

                      <td className="py-3">
                        <span className="font-mono text-xs text-amber-300 font-bold bg-zinc-900 px-2 py-1 rounded-lg border border-zinc-800">
                          PIN: {member.pin}
                        </span>
                      </td>

                      <td className="py-3">
                        <button
                          onClick={() => handleToggleStaffDuty(member.id)}
                          className={`px-3 py-1 rounded-xl text-[11px] font-bold border transition flex items-center gap-1.5 ${
                            member.isOnDuty
                              ? 'bg-emerald-500/15 text-emerald-400 border-emerald-500/30 hover:bg-emerald-500/25'
                              : 'bg-zinc-900 text-zinc-500 border-zinc-800 hover:text-zinc-300'
                          }`}
                        >
                          <span className={`w-2 h-2 rounded-full ${member.isOnDuty ? 'bg-emerald-400 animate-pulse' : 'bg-zinc-600'}`} />
                          {member.isOnDuty ? 'ON DUTY' : 'OFF DUTY'}
                        </button>
                      </td>

                      <td className="py-3 text-right">
                        <button
                          onClick={() => handleDeleteStaff(member.id, member.name)}
                          className="p-1.5 rounded-xl bg-red-600/20 hover:bg-red-600 text-red-400 hover:text-white transition"
                          title="Remove Staff Member"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </section>
      )}

      {/* Tables & QR Code Generator Tab */}
      {activeTab === 'tables' && (
        <section className="space-y-4">
          <div className="flex justify-between items-center">
            <h3 className="text-sm font-bold text-white">Live Table QR Codes (Print Ready)</h3>
          </div>

          {loadingTables ? (
            <div className="text-center py-12 text-zinc-500 text-sm">Loading restaurant tables...</div>
          ) : tables.length === 0 ? (
            <div className="text-center py-16 text-zinc-600 glass-card rounded-3xl border border-zinc-800">
              <QrCode className="w-10 h-10 mx-auto mb-2 opacity-40" />
              <p className="font-medium text-sm">No tables created yet. Click "Add Table & Generate QR" above to create your first table!</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
              {tables.map(tbl => {
                const tokenStr = tbl.qrToken || tbl.activeToken || `tbl-00${tbl.id}`;
                const targetUrl = tbl.qrUrl || `http://localhost:3000/t/${tokenStr}`;
                const tableName = tbl.name || tbl.tableNumber || `Table ${tbl.id}`;
                return (
                  <div key={tbl.id} className="glass-card p-5 rounded-3xl border-zinc-800 flex flex-col items-center text-center hover:border-purple-500/40 transition">
                    <div className="bg-white p-3 rounded-2xl shadow-xl mb-3">
                      <QRCodeSVG value={targetUrl} size={130} />
                    </div>
                    <h4 className="font-bold text-base text-white">{tableName}</h4>
                    <p className="text-[11px] text-zinc-400 font-mono mt-0.5 max-w-[200px] truncate">{tokenStr}</p>
                    <a
                      href={`/t/${tokenStr}`}
                      target="_blank"
                      rel="noreferrer"
                      className="mt-3 px-3 py-1.5 rounded-xl bg-purple-600/20 hover:bg-purple-600/30 text-purple-300 text-xs font-semibold flex items-center gap-1.5 transition"
                    >
                      Test QR Scan Link <ExternalLink className="w-3.5 h-3.5" />
                    </a>
                  </div>
                );
              })}
            </div>
          )}
        </section>
      )}

      {/* Menu & Product Manager Tab */}
      {activeTab === 'menu' && (
        <section className="space-y-6">
          <div className="flex justify-between items-center">
            <h3 className="text-sm font-bold text-white">Select Category to Manage Products</h3>
            <button
              onClick={() => setIsCreateCategoryModalOpen(true)}
              className="px-3 py-1.5 bg-purple-600/20 hover:bg-purple-600/30 text-purple-300 font-bold text-xs rounded-xl transition border border-purple-500/30 flex items-center gap-1.5"
            >
              <FolderPlus className="w-3.5 h-3.5" /> + Add New Category
            </button>
          </div>

          {categories.length === 0 ? (
            <div className="p-8 rounded-3xl bg-amber-500/10 border border-amber-500/30 text-center space-y-3">
              <AlertCircle className="w-10 h-10 text-amber-400 mx-auto" />
              <div>
                <h4 className="font-bold text-white text-sm">Henüz Menü Kategorisi Bulunmuyor (0 Kategori)</h4>
                <p className="text-xs text-zinc-400 mt-1">
                  Yeni ürün ekleyebilmek için önce en az bir menü kategorisi oluşturmanız gerekmektedir.
                </p>
              </div>
              <button
                onClick={() => setIsCreateCategoryModalOpen(true)}
                className="px-4 py-2 bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs rounded-xl transition inline-flex items-center gap-1.5 shadow-lg shadow-purple-600/30"
              >
                <FolderPlus className="w-4 h-4" /> İlk Kategoriyi Oluştur
              </button>
            </div>
          ) : (
            <>
              {/* Category Tabs */}
              <div className="flex gap-2 overflow-x-auto pb-2 border-b border-zinc-800">
                {categories.map(cat => (
                  <button
                    key={cat.id}
                    onClick={() => handleCategorySelect(cat.id)}
                    className={`px-4 py-2 rounded-2xl text-xs font-bold whitespace-nowrap transition ${
                      selectedCategoryId === cat.id
                        ? 'bg-purple-600 text-white shadow-md border border-purple-500/40'
                        : 'bg-zinc-900 border border-zinc-800 text-zinc-400 hover:text-white'
                    }`}
                  >
                    {cat.name}
                  </button>
                ))}
              </div>

              {/* Products Grid */}
              {loadingMenu ? (
                <div className="text-center py-12 text-zinc-500 text-sm">Loading products for this category...</div>
              ) : products.length === 0 ? (
                <div className="text-center py-12 text-zinc-500 text-sm glass-card rounded-3xl border border-zinc-800">
                  Bu kategoride henüz ürün bulunmuyor. Üstteki &quot;Add New Menu Product&quot; butonuyla yeni ürün ekleyebilirsiniz.
                </div>
              ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                  {products.map(prod => (
                    <div key={prod.id} className="glass-card p-4 rounded-3xl border border-zinc-800 flex flex-col justify-between hover:border-purple-500/30 transition group">
                      <div>
                        <div className="relative">
                          {prod.imageUrl ? (
                            <img src={prod.imageUrl} alt={prod.name} className="w-full h-36 object-cover rounded-2xl mb-3 bg-zinc-900" />
                          ) : (
                            <div className="w-full h-36 rounded-2xl bg-zinc-900 border border-zinc-800 flex items-center justify-center text-zinc-600 mb-3">
                              <Utensils className="w-8 h-8" />
                            </div>
                          )}
                          <button
                            onClick={() => handleDeleteProduct(prod.id, prod.name)}
                            className="absolute top-2 right-2 p-2 rounded-xl bg-red-600/80 hover:bg-red-600 text-white shadow-lg backdrop-blur-md transition flex items-center gap-1 text-[11px] font-bold"
                            title="Delete Product"
                          >
                            <Trash2 className="w-3.5 h-3.5" />
                            <span>Delete</span>
                          </button>
                        </div>

                        <div className="flex justify-between items-start">
                          <h4 className="font-bold text-base text-white">{prod.name}</h4>
                          <span className="font-extrabold text-sm text-purple-400">${Number(prod.price || prod.basePrice || 0).toFixed(2)}</span>
                        </div>
                        {prod.description && <p className="text-xs text-zinc-400 mt-1 line-clamp-2">{prod.description}</p>}
                      </div>

                      <div className="mt-4 pt-3 border-t border-zinc-800 flex items-center justify-between text-[11px] text-zinc-400">
                        <span className="flex items-center gap-1">
                          <Clock className="w-3.5 h-3.5 text-amber-400" /> {prod.prepTimeMinutes || 15} mins
                        </span>
                        <span className="flex items-center gap-1">
                          <Flame className="w-3.5 h-3.5 text-rose-400" /> {prod.calories || 500} kcal
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </>
          )}
        </section>
      )}

      {/* Create Category Modal */}
      {isCreateCategoryModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
          <div className="glass-panel p-6 rounded-3xl max-w-md w-full border border-zinc-800">
            <div className="flex items-center justify-between pb-3 border-b border-zinc-800 mb-4">
              <h3 className="font-bold text-white text-base">Add New Menu Category</h3>
              <button onClick={() => setIsCreateCategoryModalOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleCreateCategory} className="space-y-4">
              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Category Name *
                </label>
                <input
                  type="text"
                  required
                  value={newCategoryName}
                  onChange={e => setNewCategoryName(e.target.value)}
                  placeholder="e.g. Artisanal Soups & Salads"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Category Description
                </label>
                <textarea
                  value={newCategoryDesc}
                  onChange={e => setNewCategoryDesc(e.target.value)}
                  placeholder="e.g. Handcrafted daily soups and organic salads..."
                  rows={2}
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl p-3 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500 resize-none"
                />
              </div>

              <div className="pt-3 border-t border-zinc-800 flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsCreateCategoryModalOpen(false)}
                  className="px-4 py-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-bold text-xs rounded-xl"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={creatingCategory}
                  className="px-5 py-2 bg-purple-600 hover:bg-purple-500 disabled:opacity-50 text-white font-bold text-xs rounded-xl shadow-lg shadow-purple-600/30"
                >
                  {creatingCategory ? 'Creating Category...' : 'Create Category'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Create Table Modal */}
      {isCreateTableModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-sm">
          <div className="glass-panel p-6 rounded-3xl max-w-md w-full border border-zinc-800">
            <div className="flex items-center justify-between pb-3 border-b border-zinc-800 mb-4">
              <h3 className="font-bold text-white text-base">Add New Restaurant Table</h3>
              <button onClick={() => setIsCreateTableModalOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleCreateTable} className="space-y-4">
              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1.5">
                  Table Number / Name
                </label>
                <input
                  type="text"
                  required
                  value={newTableNumber}
                  onChange={e => setNewTableNumber(e.target.value)}
                  placeholder="e.g. Table 06 or VIP Booth 2"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2.5 text-sm text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div className="pt-2 flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsCreateTableModalOpen(false)}
                  className="px-4 py-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-bold text-xs rounded-xl"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={creatingTable}
                  className="px-5 py-2 bg-purple-600 hover:bg-purple-500 disabled:opacity-50 text-white font-bold text-xs rounded-xl shadow-lg shadow-purple-600/30"
                >
                  {creatingTable ? 'Generating QR Token...' : 'Create & Generate QR'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Create Product Modal */}
      {isCreateProductModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
          <div className="glass-panel p-6 rounded-3xl max-w-lg w-full border border-zinc-800 max-h-[90vh] overflow-y-auto">
            <div className="flex items-center justify-between pb-3 border-b border-zinc-800 mb-4">
              <h3 className="font-bold text-white text-base">Add New Menu Product</h3>
              <button onClick={() => setIsCreateProductModalOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleCreateProduct} className="space-y-4">
              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Product Name *
                </label>
                <input
                  type="text"
                  required
                  value={prodName}
                  onChange={e => setProdName(e.target.value)}
                  placeholder="e.g. Signature Truffle Pizza"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Price ($) *
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    required
                    value={prodPrice}
                    onChange={e => setProdPrice(e.target.value)}
                    placeholder="e.g. 24.50"
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500 font-mono"
                  />
                </div>

                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Category *
                  </label>
                  <select
                    value={prodCategoryId}
                    onChange={e => setProdCategoryId(Number(e.target.value))}
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white focus:outline-none focus:border-purple-500"
                  >
                    {categories.map(cat => (
                      <option key={cat.id} value={cat.id}>
                        {cat.name}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Description
                </label>
                <textarea
                  value={prodDesc}
                  onChange={e => setProdDesc(e.target.value)}
                  placeholder="Describe ingredients, taste profile..."
                  rows={2}
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl p-3 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500 resize-none"
                />
              </div>

              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Image URL
                </label>
                <input
                  type="text"
                  value={prodImageUrl}
                  onChange={e => setProdImageUrl(e.target.value)}
                  placeholder="https://images.unsplash.com/..."
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Prep Time (Mins)
                  </label>
                  <input
                    type="number"
                    value={prodPrepTime}
                    onChange={e => setProdPrepTime(e.target.value)}
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white"
                  />
                </div>

                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Calories (Kcal)
                  </label>
                  <input
                    type="number"
                    value={prodCalories}
                    onChange={e => setProdCalories(e.target.value)}
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white"
                  />
                </div>
              </div>

              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Tags (Comma separated)
                </label>
                <input
                  type="text"
                  value={prodTags}
                  onChange={e => setProdTags(e.target.value)}
                  placeholder="Chef Pick, Spicy, Gluten-Free"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white"
                />
              </div>

              <div className="pt-3 border-t border-zinc-800 flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsCreateProductModalOpen(false)}
                  className="px-4 py-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-bold text-xs rounded-xl"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={creatingProduct}
                  className="px-5 py-2 bg-purple-600 hover:bg-purple-500 disabled:opacity-50 text-white font-bold text-xs rounded-xl shadow-lg shadow-purple-600/30"
                >
                  {creatingProduct ? 'Saving Product...' : 'Save Product to Menu'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Add Staff Modal */}
      {isAddStaffModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
          <div className="glass-panel p-6 rounded-3xl max-w-md w-full border border-zinc-800">
            <div className="flex items-center justify-between pb-3 border-b border-zinc-800 mb-4">
              <h3 className="font-bold text-white text-base">Add New Staff Member</h3>
              <button onClick={() => setIsAddStaffModalOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                <X className="w-5 h-5" />
              </button>
            </div>

            <form onSubmit={handleAddStaffMember} className="space-y-4">
              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Full Name *
                </label>
                <input
                  type="text"
                  required
                  value={staffName}
                  onChange={e => setStaffName(e.target.value)}
                  placeholder="e.g. Elena Rostova"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div>
                <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                  Email Address *
                </label>
                <input
                  type="email"
                  required
                  value={staffEmail}
                  onChange={e => setStaffEmail(e.target.value)}
                  placeholder="e.g. elena@bosphorus.com"
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                />
              </div>

              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Role *
                  </label>
                  <select
                    value={staffRole}
                    onChange={e => setStaffRole(e.target.value as any)}
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white focus:outline-none focus:border-purple-500"
                  >
                    <option value="WAITER">Floor Waiter</option>
                    <option value="KITCHEN">Kitchen Chef (KDS)</option>
                    <option value="MANAGER">Branch Manager</option>
                  </select>
                </div>

                <div>
                  <label className="text-xs font-bold uppercase tracking-wider text-zinc-400 block mb-1">
                    Login PIN Code *
                  </label>
                  <input
                    type="text"
                    required
                    value={staffPin}
                    onChange={e => setStaffPin(e.target.value)}
                    placeholder="e.g. 5678"
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3.5 py-2 text-xs text-white font-mono"
                  />
                </div>
              </div>

              <div className="pt-3 border-t border-zinc-800 flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setIsAddStaffModalOpen(false)}
                  className="px-4 py-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-300 font-bold text-xs rounded-xl"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-5 py-2 bg-purple-600 hover:bg-purple-500 text-white font-bold text-xs rounded-xl shadow-lg shadow-purple-600/30"
                >
                  Save Staff Member
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
