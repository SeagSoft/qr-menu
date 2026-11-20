import {
  MenuCategory,
  Product,
  RestaurantTable,
  Order,
  ServiceRequest,
  AuthResponse,
  StaffUser,
  SalesReport,
} from './types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

export function clearAllAuthStorage() {
  if (typeof window === 'undefined') return;
  const authKeys = [
    'token',
    'accessToken',
    'staffUser',
    'qr_admin_session',
    'qr_cashier_session',
    'qr_staff_session',
    'kioskToken',
  ];
  authKeys.forEach(key => {
    sessionStorage.removeItem(key);
    localStorage.removeItem(key);
  });
}

export function clearLegacyLocalStorageAuth() {
  if (typeof window === 'undefined') return;
  const legacyKeys = [
    'token',
    'accessToken',
    'staffUser',
    'qr_admin_session',
    'qr_cashier_session',
    'qr_staff_session',
  ];
  legacyKeys.forEach(key => localStorage.removeItem(key));
}

async function fetcher<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const token =
    typeof window !== 'undefined'
      ? sessionStorage.getItem('token') || sessionStorage.getItem('accessToken')
      : null;
  const kioskToken =
    typeof window !== 'undefined' ? sessionStorage.getItem('kioskToken') : null;

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  if (kioskToken) {
    headers['X-Kiosk-Token'] = kioskToken;
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const errorData = await response
      .json()
      .catch(() => ({ message: `HTTP error! status: ${response.status}` }));

    if (
      Array.isArray(errorData.validationErrors) &&
      errorData.validationErrors.length > 0
    ) {
      const details = errorData.validationErrors
        .map((e: { field?: string; message?: string }) =>
          e.field ? `${e.field}: ${e.message}` : e.message
        )
        .join(', ');
      throw new Error(`${errorData.message || 'Validation error'} (${details})`);
    }

    throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
  }

  if (response.status === 204) {
    return {} as T;
  }

  return response.json();
}

export const api = {
  // Public Customer Endpoints
  getTableByToken: async (qrToken: string): Promise<RestaurantTable> => {
    return fetcher<RestaurantTable>(`/public/tables/token/${qrToken}`);
  },

  getCategories: async (businessId: number): Promise<MenuCategory[]> => {
    return fetcher<MenuCategory[]>(`/public/menu/business/${businessId}/categories`);
  },

  getProducts: async (categoryId: number): Promise<Product[]> => {
    return fetcher<Product[]>(`/public/menu/category/${categoryId}/products`);
  },

  // Admin Menu Management
  createCategory: async (data: {
    businessId: number;
    name: string;
    description?: string;
    sortOrder?: number;
  }): Promise<MenuCategory> => {
    return fetcher<MenuCategory>('/admin/categories', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  deleteCategory: async (categoryId: number): Promise<void> => {
    return fetcher<void>(`/admin/categories/${categoryId}`, {
      method: 'DELETE',
    });
  },

  createProduct: async (data: {
    categoryId: number;
    name: string;
    description?: string;
    price: number;
    imageUrl?: string;
    calories?: number;
    prepTimeMinutes?: number;
    tags?: string[];
  }): Promise<Product> => {
    return fetcher<Product>('/admin/products', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  deleteProduct: async (productId: number): Promise<void> => {
    return fetcher<void>(`/admin/products/${productId}`, {
      method: 'DELETE',
    });
  },

  // Public Order & Service Request Submission
  submitOrder: async (
    qrToken: string,
    data: { items: any[]; paymentMethod?: string; tipAmount?: number; notes?: string }
  ): Promise<Order> => {
    return fetcher<Order>(`/public/tables/${qrToken}/orders`, {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  submitServiceRequest: async (
    qrToken: string,
    type: string,
    message?: string
  ): Promise<ServiceRequest> => {
    return fetcher<ServiceRequest>(`/public/tables/${qrToken}/service-requests`, {
      method: 'POST',
      body: JSON.stringify({ type, message }),
    });
  },

  // Admin Tables & Management
  getBranchTables: async (branchId: number): Promise<RestaurantTable[]> => {
    return fetcher<RestaurantTable[]>(`/admin/tables/branch/${branchId}`);
  },

  createTable: async (data: {
    branchId: number;
    tableNumber: string;
  }): Promise<RestaurantTable> => {
    return fetcher<RestaurantTable>('/admin/tables', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  // Admin Staff Management
  getStaff: async (businessId: number = 1): Promise<StaffUser[]> => {
    return fetcher<StaffUser[]>(`/admin/staff/business/${businessId}`);
  },

  createStaff: async (data: {
    businessId: number;
    branchId?: number;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    role: 'ADMIN' | 'MANAGER' | 'WAITER' | 'CASHIER' | 'KITCHEN';
  }): Promise<StaffUser> => {
    return fetcher<StaffUser>('/admin/staff', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  toggleStaffStatus: async (staffId: number): Promise<StaffUser> => {
    return fetcher<StaffUser>(`/admin/staff/${staffId}/toggle`, {
      method: 'PATCH',
    });
  },

  deleteStaff: async (staffId: number): Promise<void> => {
    return fetcher<void>(`/admin/staff/${staffId}`, {
      method: 'DELETE',
    });
  },

  // Admin Orders & Revenue Reports
  getBranchOrders: async (branchId: number = 1): Promise<Order[]> => {
    return fetcher<Order[]>(`/admin/orders/branch/${branchId}`);
  },

  getSalesReport: async (branchId: number = 1): Promise<SalesReport> => {
    return fetcher<SalesReport>(`/admin/orders/branch/${branchId}/report`);
  },

  // Auth
  login: (credentials: { email: string; password: string }) =>
    fetcher<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    }),

  // Staff Operations
  getBranchActiveOrders: async (branchId: number): Promise<Order[]> => {
    return fetcher<Order[]>(`/staff/branches/${branchId}/orders`);
  },

  getBranchOrderHistory: async (branchId: number = 1): Promise<Order[]> => {
    return fetcher<Order[]>(`/staff/branches/${branchId}/orders/history`);
  },

  updateOrderStatus: async (
    branchId: number,
    orderId: number,
    status: string
  ): Promise<Order> => {
    return fetcher<Order>(
      `/staff/branches/${branchId}/orders/${orderId}/status?status=${status}`,
      {
        method: 'PATCH',
      }
    );
  },

  getBranchServiceRequests: async (branchId: number): Promise<ServiceRequest[]> => {
    return fetcher<ServiceRequest[]>(`/staff/branches/${branchId}/service-requests`);
  },

  updateServiceRequestStatus: async (
    branchId: number,
    requestId: number,
    status: string
  ): Promise<ServiceRequest> => {
    return fetcher<ServiceRequest>(
      `/staff/branches/${branchId}/service-requests/${requestId}/status?status=${status}`,
      {
        method: 'PATCH',
      }
    );
  },
};

export const fetchActiveKitchenOrders = (branchId: number = 1) =>
  api.getBranchActiveOrders(branchId);
export const updateOrderStatusInBackend = (
  branchId: number = 1,
  orderId: number,
  status: string
) => api.updateOrderStatus(branchId, orderId, status);
