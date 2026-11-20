'use client';

import { useEffect, useState, use } from 'react';
import { api } from '@/lib/api';
import { MenuCategory, Product, RestaurantTable, ProductVariant, ProductOption, OptionGroup } from '@/lib/types';
import {
  ShoppingBag, Bell, Utensils, CheckCircle2, ChevronRight, Plus,
  Minus, X, AlertCircle, CreditCard, MessageSquare, ShieldCheck,
  Smartphone, Wallet, Receipt
} from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';

interface CartItem {
  product: Product;
  selectedVariant?: ProductVariant;
  selectedOptions: ProductOption[];
  quantity: number;
  notes?: string;
  totalPrice: number;
}

export default function CustomerTablePage({ params }: { params: Promise<{ token: string }> }) {
  const resolvedParams = use(params);
  const qrToken = resolvedParams.token;

  const [table, setTable] = useState<RestaurantTable | null>(null);
  const [categories, setCategories] = useState<MenuCategory[]>([]);
  const [activeCategoryId, setActiveCategoryId] = useState<number>(1);
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  // Modal & Flow states
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [selectedVariant, setSelectedVariant] = useState<ProductVariant | undefined>(undefined);
  const [selectedOptions, setSelectedOptions] = useState<ProductOption[]>([]);
  const [itemQuantity, setItemQuantity] = useState(1);
  const [itemNotes, setItemNotes] = useState('');

  // Cart & Payment states
  const [cart, setCart] = useState<CartItem[]>([]);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [checkoutStep, setCheckoutStep] = useState<'CART' | 'PAYMENT' | 'PROCESSING'>('CART');
  const [paymentMethod, setPaymentMethod] = useState<'CARD' | 'DIGITAL_WALLET' | 'COUNTER'>('CARD');
  const [tipPercentage, setTipPercentage] = useState<number>(10);
  const [cardHolder, setCardHolder] = useState('Alex Morgan');
  const [cardNumber, setCardNumber] = useState('•••• •••• •••• 4242');
  const [cardExpiry, setCardExpiry] = useState('12/28');
  const [cardCvc, setCardCvc] = useState('888');

  // Service / Message Waiter states
  const [isMessageModalOpen, setIsMessageModalOpen] = useState(false);
  const [customMessage, setCustomMessage] = useState('');
  const [sendingMessage, setSendingMessage] = useState(false);
  const [serviceSuccess, setServiceSuccess] = useState<string | null>(null);

  // Completed Order Receipt state
  const [completedOrder, setCompletedOrder] = useState<any | null>(null);

  useEffect(() => {
    async function loadInitialTableAndCategories() {
      try {
        setLoading(true);
        const tableData = await api.getTableByToken(qrToken);
        setTable(tableData);

        const cats = await api.getCategories(tableData.businessId || 1);
        setCategories(cats);

        if (cats.length > 0) {
          setActiveCategoryId(cats[0].id);
        }
      } catch (err: any) {
        console.error('Failed to load table and menu', err);
      } finally {
        setLoading(false);
      }
    }
    loadInitialTableAndCategories();
  }, [qrToken]);

  useEffect(() => {
    async function loadProductsForCategory() {
      if (!activeCategoryId) return;
      try {
        const fetchedProds = await api.getProducts(activeCategoryId);
        setProducts(fetchedProds);
      } catch (err) {
        console.error('Failed to load products for category', err);
      }
    }
    loadProductsForCategory();

    const handleProductChange = () => {
      loadProductsForCategory();
    };

    if (typeof window !== 'undefined') {
      window.addEventListener('qr_product_created', handleProductChange);
      window.addEventListener('qr_product_deleted', handleProductChange);
      window.addEventListener('storage', handleProductChange);
    }

    return () => {
      if (typeof window !== 'undefined') {
        window.removeEventListener('qr_product_created', handleProductChange);
        window.removeEventListener('qr_product_deleted', handleProductChange);
        window.removeEventListener('storage', handleProductChange);
      }
    };
  }, [activeCategoryId]);

  const handleCategorySelect = (catId: number) => {
    setActiveCategoryId(catId);
  };

  const getProductPrice = (p: Product): number => Number(p.price ?? p.basePrice ?? 0);
  const getVariantPrice = (v: ProductVariant, base: number): number => {
    if (v.price !== undefined && v.price !== null) return Number(v.price);
    if (v.priceModifier !== undefined && v.priceModifier !== null) return base + Number(v.priceModifier);
    return base;
  };

  const openProductModal = (product: Product) => {
    setSelectedProduct(product);
    setSelectedVariant(product.variants && product.variants.length > 0 ? product.variants[0] : undefined);
    setSelectedOptions([]);
    setItemQuantity(1);
    setItemNotes('');
  };

  const toggleOption = (option: ProductOption, group: OptionGroup) => {
    const exists = selectedOptions.some(o => o.id === option.id);
    if (exists) {
      setSelectedOptions(selectedOptions.filter(o => o.id !== option.id));
    } else {
      const groupOptionIds = group.options.map(o => o.id);
      if (group.maxSelect === 1) {
        const otherGroupOptionsRemoved = selectedOptions.filter(o => !groupOptionIds.includes(o.id));
        setSelectedOptions([...otherGroupOptionsRemoved, option]);
      } else if (selectedOptions.filter(o => groupOptionIds.includes(o.id)).length < (group.maxSelect || 1)) {
        setSelectedOptions([...selectedOptions, option]);
      }
    }
  };

  const calculateItemPrice = (): number => {
    if (!selectedProduct) return 0;
    const base = getProductPrice(selectedProduct);
    const variantPrice = selectedVariant ? getVariantPrice(selectedVariant, base) : base;
    const optionsTotal = selectedOptions.reduce((acc, opt) => acc + Number(opt.priceModifier || 0), 0);
    return (variantPrice + optionsTotal) * itemQuantity;
  };

  const addToCart = () => {
    if (!selectedProduct) return;

    const newItem: CartItem = {
      product: selectedProduct,
      selectedVariant,
      selectedOptions: [...selectedOptions],
      quantity: itemQuantity,
      notes: itemNotes,
      totalPrice: calculateItemPrice(),
    };

    setCart([...cart, newItem]);
    setSelectedProduct(null);
  };

  const cartSubtotal = cart.reduce((acc, item) => acc + item.totalPrice, 0);
  const tipAmount = (cartSubtotal * tipPercentage) / 100;
  const grandTotal = cartSubtotal + tipAmount;

  const handleProcessPaymentAndOrder = async () => {
    if (cart.length === 0) return;
    try {
      setCheckoutStep('PROCESSING');

      // Simulate payment network roundtrip
      await new Promise(resolve => setTimeout(resolve, 1500));

      const itemsPayload = cart.map(item => ({
        productName: item.product.name,
        quantity: item.quantity,
        unitPrice: item.selectedVariant ? getVariantPrice(item.selectedVariant, getProductPrice(item.product)) : getProductPrice(item.product),
        selectedVariantName: item.selectedVariant?.name,
        selectedOptionsText: item.selectedOptions.map(o => o.name).join(', '),
        notes: item.notes,
      }));

      const orderResult = await api.submitOrder(qrToken, {
        items: itemsPayload,
        paymentMethod,
        tipAmount: Number(tipAmount.toFixed(2)),
        notes: `Paid via ${paymentMethod} (Tip: ${tipPercentage}%)`,
      });

      const newOrderObject = {
        ...orderResult,
        tableName: table?.tableNumber ? `Table ${table.tableNumber}` : (table?.name || 'Table 01'),
        paymentMethod,
        subtotal: cartSubtotal,
        tipAmount,
        grandTotal,
        paidAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      };

      // Broadcast order locally for instant Kitchen KDS & Cashier & Admin synchronization
      if (typeof window !== 'undefined') {
        const storedStr = localStorage.getItem('qr_all_orders');
        const storedOrders = storedStr ? JSON.parse(storedStr) : [];
        const updatedOrders = [newOrderObject, ...storedOrders];
        localStorage.setItem('qr_all_orders', JSON.stringify(updatedOrders));
        localStorage.setItem('qr_latest_order', JSON.stringify(newOrderObject));
        window.dispatchEvent(new CustomEvent('qr_new_order', { detail: newOrderObject }));
      }

      setCompletedOrder(newOrderObject);

      setCart([]);
      setIsCartOpen(false);
      setCheckoutStep('CART');
    } catch (err: any) {
      alert(`Payment Failed: ${err.message}`);
      setCheckoutStep('PAYMENT');
    }
  };

  const handleSendMessageToWaiter = async (presetText?: string) => {
    const textToSend = presetText || customMessage;
    if (!textToSend.trim() || sendingMessage) return;

    try {
      setSendingMessage(true);
      const reqRes = await api.submitServiceRequest(qrToken, 'CALL_WAITER', textToSend);

      const reqObj = {
        id: reqRes.id || Date.now(),
        tableName: table?.tableNumber ? `Table ${table.tableNumber}` : 'Table 01',
        type: 'CALL_WAITER',
        message: textToSend,
        status: 'PENDING',
        requestedAt: 'Just now'
      };

      if (typeof window !== 'undefined') {
        window.dispatchEvent(new CustomEvent('qr_new_service_request', { detail: reqObj }));
        localStorage.setItem('qr_latest_request', JSON.stringify(reqObj));
      }

      setServiceSuccess(`Message sent to waiter: "${textToSend}"`);
      setIsMessageModalOpen(false);
      setCustomMessage('');
      setTimeout(() => setServiceSuccess(null), 5000);
    } catch (err: any) {
      alert(`Failed to send message: ${err.message}`);
    } finally {
      setSendingMessage(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-zinc-950 flex flex-col items-center justify-center text-zinc-400">
        <div className="w-10 h-10 border-4 border-purple-500 border-t-transparent rounded-full animate-spin mb-4" />
        <p className="font-medium text-sm">Opening digital table menu...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 pb-32">
      {/* Header Banner */}
      <header className="sticky top-0 z-30 glass-nav px-4 py-3.5 flex items-center justify-between border-b border-zinc-800">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-xl bg-purple-600/20 border border-purple-500/30 flex items-center justify-center text-purple-400">
            <Utensils className="w-5 h-5" />
          </div>
          <div>
            <h1 className="font-bold text-base leading-tight text-white">{table?.name || 'Digital Menu'}</h1>
            <p className="text-xs text-purple-400 font-medium">Table #{table?.tableNumber || '01'}</p>
          </div>
        </div>

        {/* Message Waiter Action */}
        <button
          onClick={() => setIsMessageModalOpen(true)}
          className="flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-amber-500/10 border border-amber-500/30 text-amber-300 text-xs font-semibold hover:bg-amber-500/20 transition"
        >
          <MessageSquare className="w-3.5 h-3.5" />
          Message Waiter
        </button>
      </header>

      {/* Service Request Success Alert */}
      {serviceSuccess && (
        <motion.div initial={{ opacity: 0, y: -10 }} animate={{ opacity: 1, y: 0 }} className="mx-4 mt-3 p-3 rounded-xl bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-xs flex items-center gap-2">
          <CheckCircle2 className="w-4 h-4 shrink-0" />
          {serviceSuccess}
        </motion.div>
      )}

      {/* Category Tabs Navigation */}
      <nav className="sticky top-[61px] z-20 bg-zinc-950/95 backdrop-blur-md px-4 py-3 overflow-x-auto flex gap-2 no-scrollbar border-b border-zinc-800/80">
        {categories.map(cat => {
          const isActive = activeCategoryId === cat.id;
          return (
            <button
              key={cat.id}
              onClick={() => handleCategorySelect(cat.id)}
              className={`px-4 py-2 rounded-2xl text-xs font-bold whitespace-nowrap transition ${
                isActive
                  ? 'bg-purple-600 text-white shadow-lg shadow-purple-600/30 border border-purple-500/40'
                  : 'bg-zinc-900 text-zinc-400 hover:text-white border border-zinc-800'
              }`}
            >
              {cat.name}
            </button>
          );
        })}
      </nav>

      {/* Category Food Items */}
      <main className="px-4 py-4 max-w-lg mx-auto space-y-3">
        {products.length === 0 ? (
          <div className="text-center py-12 text-zinc-500 text-sm">No items in this category.</div>
        ) : (
          products.map(product => (
            <motion.div
              key={product.id}
              onClick={() => openProductModal(product)}
              whileTap={{ scale: 0.98 }}
              className="glass-card p-3.5 rounded-2xl flex gap-3.5 items-center cursor-pointer hover:border-purple-500/40 transition group"
            >
              {product.imageUrl ? (
                <img src={product.imageUrl} alt={product.name} className="w-20 h-20 rounded-xl object-cover bg-zinc-800 shrink-0" />
              ) : (
                <div className="w-20 h-20 rounded-xl bg-zinc-900 border border-zinc-800 flex items-center justify-center text-zinc-600 shrink-0">
                  <Utensils className="w-7 h-7" />
                </div>
              )}
              <div className="flex-1 min-w-0">
                <h3 className="font-semibold text-sm text-zinc-100 group-hover:text-purple-300 transition">{product.name}</h3>
                {product.description && <p className="text-xs text-zinc-400 line-clamp-2 mt-0.5">{product.description}</p>}
                <div className="mt-2 flex items-center justify-between">
                  <span className="font-bold text-sm text-white">${getProductPrice(product).toFixed(2)}</span>
                  <span className="text-[11px] font-semibold text-purple-400 flex items-center gap-0.5">
                    Customize <ChevronRight className="w-3 h-3" />
                  </span>
                </div>
              </div>
            </motion.div>
          ))
        )}
      </main>

      {/* Product Customization Modal */}
      <AnimatePresence>
        {selectedProduct && (
          <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/70 backdrop-blur-sm">
            <motion.div
              initial={{ y: '100%' }}
              animate={{ y: 0 }}
              exit={{ y: '100%' }}
              transition={{ type: 'spring', damping: 25, stiffness: 200 }}
              className="glass-panel w-full max-w-lg rounded-t-3xl p-5 max-h-[85vh] flex flex-col"
            >
              <div className="flex items-start justify-between pb-3 border-b border-zinc-800">
                <div>
                  <h2 className="text-lg font-bold text-white">{selectedProduct.name}</h2>
                  <p className="text-xs text-zinc-400">${getProductPrice(selectedProduct).toFixed(2)}</p>
                </div>
                <button onClick={() => setSelectedProduct(null)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                  <X className="w-5 h-5" />
                </button>
              </div>

              <div className="overflow-y-auto py-4 space-y-5 flex-1 pr-1">
                {/* Variants */}
                {selectedProduct.variants && selectedProduct.variants.length > 0 && (
                  <div>
                    <h4 className="text-xs font-bold uppercase tracking-wider text-zinc-400 mb-2">Choose Size / Variant</h4>
                    <div className="grid grid-cols-2 gap-2">
                      {selectedProduct.variants.map(v => (
                        <button
                          key={v.id}
                          onClick={() => setSelectedVariant(v)}
                          className={`p-2.5 rounded-xl border text-xs font-semibold flex justify-between items-center ${
                            selectedVariant?.id === v.id
                              ? 'bg-purple-600/20 border-purple-500 text-purple-300'
                              : 'bg-zinc-900 border-zinc-800 text-zinc-400'
                          }`}
                        >
                          <span>{v.name}</span>
                          <span>${getVariantPrice(v, getProductPrice(selectedProduct)).toFixed(2)}</span>
                        </button>
                      ))}
                    </div>
                  </div>
                )}

                {/* Option Groups */}
                {selectedProduct.optionGroups && selectedProduct.optionGroups.map(og => (
                  <div key={og.id}>
                    <h4 className="text-xs font-bold uppercase tracking-wider text-zinc-400 mb-2">
                      {og.name} {og.maxSelect === 1 ? '(Choose 1)' : `(Optional, max ${og.maxSelect})`}
                    </h4>
                    <div className="space-y-1.5">
                      {og.options.map(opt => {
                        const isSelected = selectedOptions.some(o => o.id === opt.id);
                        return (
                          <button
                            key={opt.id}
                            onClick={() => toggleOption(opt, og)}
                            className={`w-full p-2.5 rounded-xl border text-xs font-medium flex justify-between items-center transition ${
                              isSelected
                                ? 'bg-purple-600/20 border-purple-500 text-purple-300'
                                : 'bg-zinc-900 border-zinc-800 text-zinc-400 hover:text-zinc-200'
                            }`}
                          >
                            <span>{opt.name}</span>
                            <span>+{Number(opt.priceModifier) > 0 ? `$${Number(opt.priceModifier).toFixed(2)}` : 'Free'}</span>
                          </button>
                        );
                      })}
                    </div>
                  </div>
                ))}

                {/* Special Requests */}
                <div>
                  <h4 className="text-xs font-bold uppercase tracking-wider text-zinc-400 mb-2">Special Instructions</h4>
                  <input
                    type="text"
                    value={itemNotes}
                    onChange={e => setItemNotes(e.target.value)}
                    placeholder="e.g. Extra sauce, no onions"
                    className="w-full bg-zinc-900 border border-zinc-800 rounded-xl px-3 py-2 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-purple-500"
                  />
                </div>
              </div>

              {/* Modal Footer */}
              <div className="pt-4 border-t border-zinc-800 flex items-center justify-between gap-4">
                <div className="flex items-center gap-3 bg-zinc-900 p-1.5 rounded-xl border border-zinc-800">
                  <button onClick={() => setItemQuantity(Math.max(1, itemQuantity - 1))} className="p-1 rounded-lg bg-zinc-800 text-zinc-300 hover:text-white">
                    <Minus className="w-4 h-4" />
                  </button>
                  <span className="font-bold text-sm min-w-[20px] text-center">{itemQuantity}</span>
                  <button onClick={() => setItemQuantity(itemQuantity + 1)} className="p-1 rounded-lg bg-zinc-800 text-zinc-300 hover:text-white">
                    <Plus className="w-4 h-4" />
                  </button>
                </div>

                <button
                  onClick={addToCart}
                  className="flex-1 bg-purple-600 hover:bg-purple-500 text-white font-bold py-3 px-4 rounded-xl text-sm transition shadow-lg shadow-purple-600/30"
                >
                  Add to Order • ${calculateItemPrice().toFixed(2)}
                </button>
              </div>
            </motion.div>
          </div>
        )}
      </AnimatePresence>

      {/* Floating Cart & Pay Bar */}
      {cart.length > 0 && (
        <div className="fixed bottom-4 left-4 right-4 z-40 max-w-lg mx-auto">
          <motion.button
            initial={{ scale: 0.9, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            onClick={() => { setCheckoutStep('CART'); setIsCartOpen(true); }}
            className="w-full glass-panel border border-purple-500/50 p-4 rounded-2xl flex items-center justify-between text-white glow-purple shadow-2xl"
          >
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 rounded-xl bg-purple-600 flex items-center justify-center font-bold text-sm shadow-md">
                {cart.reduce((a, b) => a + b.quantity, 0)}
              </div>
              <div className="text-left">
                <span className="font-bold text-sm block">Review Order & Pay</span>
                <span className="text-[11px] text-purple-300">Tap to choose payment method</span>
              </div>
            </div>
            <span className="font-extrabold text-lg text-purple-300">${cartSubtotal.toFixed(2)}</span>
          </motion.button>
        </div>
      )}

      {/* Cart & Integrated Payment Modal */}
      <AnimatePresence>
        {isCartOpen && (
          <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/80 backdrop-blur-md">
            <motion.div
              initial={{ y: '100%' }}
              animate={{ y: 0 }}
              exit={{ y: '100%' }}
              className="glass-panel w-full max-w-lg rounded-t-3xl p-5 max-h-[90vh] flex flex-col"
            >
              {/* Modal Header */}
              <div className="flex items-center justify-between pb-3 border-b border-zinc-800">
                <div className="flex items-center gap-2">
                  {checkoutStep === 'CART' ? <ShoppingBag className="w-5 h-5 text-purple-400" /> : <CreditCard className="w-5 h-5 text-purple-400" />}
                  <h2 className="text-base font-bold text-white">
                    {checkoutStep === 'CART' ? 'Your Table Order' : 'Checkout & Payment'}
                  </h2>
                </div>
                <button onClick={() => setIsCartOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                  <X className="w-5 h-5" />
                </button>
              </div>

              {/* STEP 1: CART ITEMS */}
              {checkoutStep === 'CART' && (
                <>
                  <div className="overflow-y-auto py-3 space-y-3 flex-1">
                    {cart.map((item, idx) => (
                      <div key={idx} className="bg-zinc-900/80 p-3 rounded-2xl border border-zinc-800 flex justify-between items-start">
                        <div>
                          <h4 className="font-semibold text-sm text-white">
                            {item.quantity}x {item.product.name}
                          </h4>
                          {item.selectedVariant && <p className="text-xs text-purple-400">Size: {item.selectedVariant.name}</p>}
                          {item.selectedOptions.length > 0 && (
                            <p className="text-xs text-zinc-400">{item.selectedOptions.map(o => o.name).join(', ')}</p>
                          )}
                          {item.notes && <p className="text-[11px] text-zinc-500 italic">"{item.notes}"</p>}
                        </div>
                        <span className="font-bold text-sm text-white">${item.totalPrice.toFixed(2)}</span>
                      </div>
                    ))}
                  </div>

                  <div className="pt-3 border-t border-zinc-800 space-y-3">
                    <div className="flex justify-between items-center text-sm font-bold">
                      <span className="text-zinc-400">Subtotal</span>
                      <span className="text-lg text-white">${cartSubtotal.toFixed(2)}</span>
                    </div>

                    <button
                      onClick={() => setCheckoutStep('PAYMENT')}
                      className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-3.5 rounded-xl text-sm transition shadow-lg shadow-purple-600/30 flex items-center justify-center gap-2"
                    >
                      <CreditCard className="w-4 h-4" />
                      Proceed to Payment Gateway
                    </button>
                  </div>
                </>
              )}

              {/* STEP 2: PAYMENT GATEWAY */}
              {checkoutStep === 'PAYMENT' && (
                <div className="overflow-y-auto py-3 space-y-4 flex-1">
                  {/* Payment Method Selector */}
                  <div>
                    <label className="text-xs font-bold text-zinc-400 uppercase tracking-wider block mb-2">Select Payment Method</label>
                    <div className="grid grid-cols-3 gap-2">
                      <button
                        onClick={() => setPaymentMethod('CARD')}
                        className={`p-3 rounded-2xl border text-xs font-bold flex flex-col items-center gap-1.5 transition ${
                          paymentMethod === 'CARD' ? 'bg-purple-600/20 border-purple-500 text-purple-300' : 'bg-zinc-900 border-zinc-800 text-zinc-400'
                        }`}
                      >
                        <CreditCard className="w-5 h-5" />
                        <span>Card</span>
                      </button>

                      <button
                        onClick={() => setPaymentMethod('DIGITAL_WALLET')}
                        className={`p-3 rounded-2xl border text-xs font-bold flex flex-col items-center gap-1.5 transition ${
                          paymentMethod === 'DIGITAL_WALLET' ? 'bg-purple-600/20 border-purple-500 text-purple-300' : 'bg-zinc-900 border-zinc-800 text-zinc-400'
                        }`}
                      >
                        <Smartphone className="w-5 h-5" />
                        <span>Apple Pay</span>
                      </button>

                      <button
                        onClick={() => setPaymentMethod('COUNTER')}
                        className={`p-3 rounded-2xl border text-xs font-bold flex flex-col items-center gap-1.5 transition ${
                          paymentMethod === 'COUNTER' ? 'bg-purple-600/20 border-purple-500 text-purple-300' : 'bg-zinc-900 border-zinc-800 text-zinc-400'
                        }`}
                      >
                        <Wallet className="w-5 h-5" />
                        <span>Cash / Counter</span>
                      </button>
                    </div>
                  </div>

                  {/* Card Form */}
                  {paymentMethod === 'CARD' && (
                    <div className="bg-zinc-900/80 p-3.5 rounded-2xl border border-zinc-800 space-y-3">
                      <div>
                        <label className="text-[11px] font-semibold text-zinc-400 block mb-1">Cardholder Name</label>
                        <input
                          type="text"
                          value={cardHolder}
                          onChange={e => setCardHolder(e.target.value)}
                          className="w-full bg-zinc-950 border border-zinc-800 rounded-xl px-3 py-2 text-xs text-white"
                        />
                      </div>
                      <div>
                        <label className="text-[11px] font-semibold text-zinc-400 block mb-1">Card Number</label>
                        <input
                          type="text"
                          value={cardNumber}
                          onChange={e => setCardNumber(e.target.value)}
                          className="w-full bg-zinc-950 border border-zinc-800 rounded-xl px-3 py-2 text-xs text-white font-mono"
                        />
                      </div>
                      <div className="grid grid-cols-2 gap-2">
                        <div>
                          <label className="text-[11px] font-semibold text-zinc-400 block mb-1">Expiry Date</label>
                          <input
                            type="text"
                            value={cardExpiry}
                            onChange={e => setCardExpiry(e.target.value)}
                            className="w-full bg-zinc-950 border border-zinc-800 rounded-xl px-3 py-2 text-xs text-white font-mono"
                          />
                        </div>
                        <div>
                          <label className="text-[11px] font-semibold text-zinc-400 block mb-1">CVC / CVV</label>
                          <input
                            type="text"
                            value={cardCvc}
                            onChange={e => setCardCvc(e.target.value)}
                            className="w-full bg-zinc-950 border border-zinc-800 rounded-xl px-3 py-2 text-xs text-white font-mono"
                          />
                        </div>
                      </div>
                    </div>
                  )}

                  {/* Tip Selector */}
                  <div>
                    <label className="text-xs font-bold text-zinc-400 uppercase tracking-wider block mb-2">Add Tip for Staff</label>
                    <div className="grid grid-cols-4 gap-2">
                      {[0, 10, 15, 20].map(tip => (
                        <button
                          key={tip}
                          onClick={() => setTipPercentage(tip)}
                          className={`py-2 rounded-xl text-xs font-bold border transition ${
                            tipPercentage === tip ? 'bg-amber-500/20 border-amber-500 text-amber-300' : 'bg-zinc-900 border-zinc-800 text-zinc-400'
                          }`}
                        >
                          {tip === 0 ? 'No Tip' : `${tip}%`}
                        </button>
                      ))}
                    </div>
                  </div>

                  {/* Price Breakdown */}
                  <div className="bg-zinc-900/60 p-3 rounded-2xl border border-zinc-800 space-y-1.5 text-xs">
                    <div className="flex justify-between text-zinc-400">
                      <span>Order Subtotal</span>
                      <span>${cartSubtotal.toFixed(2)}</span>
                    </div>
                    {tipAmount > 0 && (
                      <div className="flex justify-between text-amber-400">
                        <span>Staff Tip ({tipPercentage}%)</span>
                        <span>+${tipAmount.toFixed(2)}</span>
                      </div>
                    )}
                    <div className="flex justify-between font-extrabold text-sm text-white pt-2 border-t border-zinc-800">
                      <span>Total Amount Due</span>
                      <span className="text-purple-400">${grandTotal.toFixed(2)}</span>
                    </div>
                  </div>

                  {/* Pay & Confirm Button */}
                  <button
                    onClick={handleProcessPaymentAndOrder}
                    className="w-full bg-purple-600 hover:bg-purple-500 text-white font-extrabold py-3.5 rounded-xl text-sm transition shadow-lg shadow-purple-600/30 flex items-center justify-center gap-2"
                  >
                    <ShieldCheck className="w-5 h-5" />
                    Pay & Complete Order (${grandTotal.toFixed(2)})
                  </button>
                </div>
              )}

              {/* STEP 3: PAYMENT PROCESSING SPINNER */}
              {checkoutStep === 'PROCESSING' && (
                <div className="py-16 text-center space-y-4">
                  <div className="w-12 h-12 border-4 border-purple-500 border-t-transparent rounded-full animate-spin mx-auto" />
                  <h3 className="text-base font-bold text-white">Processing Secure Payment...</h3>
                  <p className="text-xs text-zinc-400">Communicating with payment gateway & kitchen WebSockets</p>
                </div>
              )}
            </motion.div>
          </div>
        )}
      </AnimatePresence>

      {/* Completed Payment Receipt & Ticket Modal */}
      <AnimatePresence>
        {completedOrder && (
          <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/85 backdrop-blur-md">
            <motion.div initial={{ scale: 0.9, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className="glass-panel p-6 rounded-3xl max-w-sm w-full text-center border border-purple-500/40">
              <div className="w-16 h-16 bg-emerald-500/20 text-emerald-400 rounded-full flex items-center justify-center mx-auto mb-4 border border-emerald-500/30">
                <Receipt className="w-8 h-8" />
              </div>

              <span className="px-3 py-1 rounded-full text-[10px] font-extrabold bg-emerald-500/20 text-emerald-300 border border-emerald-500/30 uppercase tracking-wider">
                Payment Successful • PAID
              </span>

              <h2 className="text-xl font-bold text-white mt-3 mb-1">Order #{completedOrder.orderNumber}</h2>
              <p className="text-xs text-zinc-400 mb-4">Paid at {completedOrder.paidAt} via {completedOrder.paymentMethod}</p>

              <div className="bg-zinc-900/80 p-4 rounded-2xl border border-zinc-800 text-left space-y-2 text-xs mb-5">
                <div className="flex justify-between font-semibold text-zinc-300 pb-2 border-b border-zinc-800">
                  <span>Table Number</span>
                  <span className="text-white">Table #{table?.tableNumber || '01'}</span>
                </div>
                <div className="flex justify-between text-zinc-400">
                  <span>Subtotal</span>
                  <span>${completedOrder.subtotal?.toFixed(2)}</span>
                </div>
                {completedOrder.tipAmount > 0 && (
                  <div className="flex justify-between text-amber-400">
                    <span>Tip</span>
                    <span>+${completedOrder.tipAmount?.toFixed(2)}</span>
                  </div>
                )}
                <div className="flex justify-between font-bold text-sm text-white pt-2 border-t border-zinc-800">
                  <span>Total Paid</span>
                  <span className="text-emerald-400">${completedOrder.grandTotal?.toFixed(2)}</span>
                </div>
              </div>

              <button
                onClick={() => setCompletedOrder(null)}
                className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-3 rounded-xl text-xs transition shadow-lg shadow-purple-600/30"
              >
                Back to Digital Menu
              </button>
            </motion.div>
          </div>
        )}
      </AnimatePresence>

      {/* Message Waiter Quick Modal */}
      <AnimatePresence>
        {isMessageModalOpen && (
          <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/80 backdrop-blur-sm">
            <motion.div initial={{ y: '100%' }} animate={{ y: 0 }} exit={{ y: '100%' }} className="glass-panel w-full max-w-lg rounded-t-3xl p-5 space-y-4">
              <div className="flex items-center justify-between pb-3 border-b border-zinc-800">
                <div className="flex items-center gap-2 text-amber-400">
                  <MessageSquare className="w-5 h-5" />
                  <h2 className="text-base font-bold text-white">Message Waiter</h2>
                </div>
                <button onClick={() => setIsMessageModalOpen(false)} className="p-1 rounded-full bg-zinc-800 text-zinc-400 hover:text-white">
                  <X className="w-5 h-5" />
                </button>
              </div>

              {/* Quick Preset Buttons */}
              <div>
                <label className="text-xs font-bold text-zinc-400 uppercase tracking-wider block mb-2.5">Quick Assistance Messages</label>
                <div className="grid grid-cols-2 gap-2">
                  <button
                    onClick={() => handleSendMessageToWaiter('💧 Water refill needed')}
                    className="p-3 rounded-2xl bg-zinc-900 hover:bg-amber-500/10 border border-zinc-800 hover:border-amber-500/30 text-xs font-semibold text-zinc-200 text-left transition flex items-center gap-2"
                  >
                    💧 Water Refill Needed
                  </button>
                  <button
                    onClick={() => handleSendMessageToWaiter('🍴 Extra cutlery & napkins')}
                    className="p-3 rounded-2xl bg-zinc-900 hover:bg-amber-500/10 border border-zinc-800 hover:border-amber-500/30 text-xs font-semibold text-zinc-200 text-left transition flex items-center gap-2"
                  >
                    🍴 Extra Cutlery
                  </button>
                  <button
                    onClick={() => handleSendMessageToWaiter('💳 Bring bill to table')}
                    className="p-3 rounded-2xl bg-zinc-900 hover:bg-amber-500/10 border border-zinc-800 hover:border-amber-500/30 text-xs font-semibold text-zinc-200 text-left transition flex items-center gap-2"
                  >
                    💳 Bring Bill to Table
                  </button>
                  <button
                    onClick={() => handleSendMessageToWaiter('🙋 Waiter needed at table')}
                    className="p-3 rounded-2xl bg-zinc-900 hover:bg-amber-500/10 border border-zinc-800 hover:border-amber-500/30 text-xs font-semibold text-zinc-200 text-left transition flex items-center gap-2"
                  >
                    🙋 Waiter Needed
                  </button>
                </div>
              </div>

              {/* Custom Message Input */}
              <div className="pt-2">
                <label className="text-xs font-bold text-zinc-400 uppercase tracking-wider block mb-1.5">Or Type Custom Request</label>
                <textarea
                  value={customMessage}
                  onChange={e => setCustomMessage(e.target.value)}
                  placeholder="e.g. Please bring extra high chair for baby..."
                  rows={2}
                  className="w-full bg-zinc-900 border border-zinc-800 rounded-xl p-3 text-xs text-white placeholder-zinc-500 focus:outline-none focus:border-amber-500 transition resize-none"
                />
                <button
                  onClick={() => handleSendMessageToWaiter()}
                  disabled={sendingMessage || !customMessage.trim()}
                  className="mt-3 w-full bg-amber-500 hover:bg-amber-400 disabled:opacity-50 text-zinc-950 font-bold py-3 rounded-xl text-xs transition shadow-lg shadow-amber-500/20"
                >
                  {sendingMessage ? 'Sending Request...' : 'Send Message to Waiter'}
                </button>
              </div>
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </div>
  );
}
