export interface Business {
  id: number;
  name: string;
  slug: string;
  ownerEmail: string;
  phone?: string;
  isActive?: boolean;
}

export interface Branch {
  id: number;
  businessId: number;
  name: string;
  address?: string;
  phone?: string;
  isActive?: boolean;
}

export interface RestaurantTable {
  id: number;
  businessId?: number;
  branchId?: number;
  tableNumber?: string;
  name?: string;
  capacity?: number;
  status?: string;
  activeToken?: string;
  currentOrderCount?: number;
  qrToken?: string;
  qrUrl?: string;
  isActive?: boolean;
}

export interface MenuCategory {
  id: number;
  businessId?: number;
  name: string;
  description?: string;
  sortOrder?: number;
  isActive?: boolean;
  icon?: string;
  displayOrder?: number;
}

export interface ProductOption {
  id: number;
  name: string;
  priceModifier: number;
  isAvailable?: boolean;
}

export interface OptionGroup {
  id: number;
  name: string;
  minSelect?: number;
  maxSelect?: number;
  isRequired?: boolean;
  minSelections?: number;
  maxSelections?: number;
  options: ProductOption[];
}

export interface ProductVariant {
  id: number;
  name: string;
  price?: number;
  priceModifier?: number;
  isAvailable?: boolean;
}

export interface Product {
  id: number;
  categoryId: number;
  name: string;
  description?: string;
  price?: number;
  basePrice?: number;
  imageUrl?: string;
  sortOrder?: number;
  isActive?: boolean;
  isAvailable?: boolean;
  tags?: string[];
  calories?: number;
  prepTimeMinutes?: number;
  variants?: ProductVariant[];
  optionGroups?: OptionGroup[];
}

export interface OrderItem {
  id?: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  totalPrice?: number;
  selectedVariantName?: string;
  selectedOptionsText?: string;
  notes?: string;
}

export interface CartItem {
  cartItemId?: string | number;
  product: Product;
  selectedVariant?: ProductVariant;
  selectedOptions: ProductOption[];
  quantity: number;
  notes?: string;
  specialInstructions?: string;
  totalPrice: number;
}

export type OrderStatus = 'PENDING' | 'PREPARING' | 'READY' | 'SERVED' | 'CANCELLED';

export interface Order {
  id: number;
  orderNumber: string;
  tableId?: number;
  tableName: string;
  branchId?: number;
  branchName?: string;
  status: OrderStatus;
  totalAmount: number;
  paymentMethod?: string;
  tipAmount?: number;
  notes?: string;
  items: OrderItem[];
  createdAt: string;
  updatedAt?: string;
  estimatedMinutes?: number;
}

export interface ServiceRequest {
  id: number;
  tableName: string;
  branchName?: string;
  type: string;
  status: string;
  message?: string;
  resolvedByName?: string;
  createdAt?: string;
  requestedAt?: string;
  resolvedAt?: string;
}

export interface StaffUser {
  id: number;
  businessId: number;
  branchId?: number;
  email: string;
  firstName: string;
  lastName: string;
  role: 'SUPER_ADMIN' | 'ADMIN' | 'MANAGER' | 'WAITER' | 'CASHIER' | 'KITCHEN';
  isActive?: boolean;
  active?: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface SalesReport {
  branchId: number;
  grossRevenue: number;
  orderCount: number;
  avgOrderValue: number;
  activeOrderCount: number;
  orders: Order[];
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  user: StaffUser;
}
