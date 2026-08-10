import { MenuCategory, Product, RestaurantTable, Order, ServiceRequest, AuthResponse } from './types';
import { MOCK_CATEGORIES, MOCK_PRODUCTS, MOCK_TABLES, MOCK_ORDERS, MOCK_SERVICE_REQUESTS } from './mockData';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

async function fetcher<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({ message: 'An unknown error occurred' }));
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
    try {
      const table = await fetcher<RestaurantTable>(`/public/tables/token/${qrToken}`);
      if (table) return table;
    } catch (err) {
      console.warn(`Backend table lookup for token ${qrToken} failed, using fallback mock table.`, err);
    }
    const matched = MOCK_TABLES.find(t => t.activeToken === qrToken || t.qrToken === qrToken);
    if (matched) return matched;
    return {
      id: 1,
      businessId: 1,
      branchId: 1,
      name: `Table (${qrToken.substring(0, 6)})`,
      tableNumber: '01',
      activeToken: qrToken,
      qrToken: qrToken,
      capacity: 4,
      status: 'AVAILABLE'
    };
  },

  getCategories: async (businessId: number): Promise<MenuCategory[]> => {
    try {
      const cats = await fetcher<MenuCategory[]>(`/public/menu/business/${businessId}/categories`);
      if (cats && Array.isArray(cats) && cats.length > 0) return cats;
    } catch (err) {
      console.warn('Backend getCategories failed, using local categories.', err);
    }

    const storedCatsStr = typeof window !== 'undefined' ? localStorage.getItem('qr_custom_categories') : null;
    const storedCats: MenuCategory[] = storedCatsStr ? JSON.parse(storedCatsStr) : [];

    const catMap = new Map<number, MenuCategory>();
    MOCK_CATEGORIES.forEach(c => catMap.set(c.id, c));
    storedCats.forEach(c => catMap.set(c.id, c));

    return Array.from(catMap.values());
  },

  getProducts: async (categoryId: number): Promise<Product[]> => {
    try {
      const prods = await fetcher<Product[]>(`/public/menu/category/${categoryId}/products`);
      if (prods && Array.isArray(prods) && prods.length > 0) {
        return prods;
      }
    } catch (err) {
      console.warn('Backend getProducts failed for category', categoryId, err);
    }

    const storedProdsStr = typeof window !== 'undefined' ? localStorage.getItem('qr_custom_products') : null;
    const storedProds: Product[] = storedProdsStr ? JSON.parse(storedProdsStr) : [];

    const mockCategoryProds = MOCK_PRODUCTS.filter(p => p.categoryId === categoryId);
    const customCategoryProds = storedProds.filter(p => p.categoryId === categoryId);

    const combinedSet = [...customCategoryProds, ...mockCategoryProds];
    
    const uniqueMap = new Map<string, Product>();
    combinedSet.forEach(p => {
      if (!uniqueMap.has(p.name)) {
        uniqueMap.set(p.name, p);
      }
    });

    return Array.from(uniqueMap.values());
  },

  createCategory: async (data: { businessId: number; name: string; description?: string }): Promise<MenuCategory> => {
    let createdCat: MenuCategory;
    try {
      createdCat = await fetcher<MenuCategory>('/admin/categories', {
        method: 'POST',
        body: JSON.stringify(data),
      });
    } catch (err) {
      console.warn('Backend createCategory failed, returning local category DTO.', err);
      const storedCatsStr = typeof window !== 'undefined' ? localStorage.getItem('qr_custom_categories') : null;
      const storedCats: MenuCategory[] = storedCatsStr ? JSON.parse(storedCatsStr) : [];
      
      const maxMockId = Math.max(0, ...MOCK_CATEGORIES.map(c => c.id));
      const maxStoredId = Math.max(0, ...storedCats.map(c => c.id));
      const nextId = Math.max(10, maxMockId, maxStoredId) + 1;

      createdCat = {
        id: nextId,
        businessId: data.businessId,
        name: data.name,
        description: data.description,
        displayOrder: MOCK_CATEGORIES.length + storedCats.length + 1,
        isActive: true
      };
    }

    if (typeof window !== 'undefined') {
      const storedCatsStr = localStorage.getItem('qr_custom_categories');
      const storedCats: MenuCategory[] = storedCatsStr ? JSON.parse(storedCatsStr) : [];
      const updated = [...storedCats.filter(c => c.id !== createdCat.id), createdCat];
      localStorage.setItem('qr_custom_categories', JSON.stringify(updated));
      window.dispatchEvent(new CustomEvent('qr_category_created', { detail: createdCat }));
    }

    if (!MOCK_CATEGORIES.some(c => c.id === createdCat.id)) {
      MOCK_CATEGORIES.push(createdCat);
    }

    return createdCat;
  },

  createProduct: async (data: { categoryId: number; name: string; description?: string; price: number; imageUrl?: string; calories?: number; prepTimeMinutes?: number; tags?: string[] }): Promise<Product> => {
    let createdProd: Product;
    try {
      createdProd = await fetcher<Product>('/admin/products', {
        method: 'POST',
        body: JSON.stringify(data),
      });
    } catch (err) {
      console.warn('Backend createProduct failed, creating local product DTO.', err);
      createdProd = {
        id: Math.floor(1000 + Math.random() * 9000),
        categoryId: data.categoryId,
        name: data.name,
        description: data.description || '',
        price: data.price,
        basePrice: data.price,
        imageUrl: data.imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=80',
        isAvailable: true,
        tags: data.tags || ['New'],
        calories: data.calories || 500,
        prepTimeMinutes: data.prepTimeMinutes || 15
      };
    }

    MOCK_PRODUCTS.unshift(createdProd);
    if (typeof window !== 'undefined') {
      const storedProdsStr = localStorage.getItem('qr_custom_products');
      const storedProds: Product[] = storedProdsStr ? JSON.parse(storedProdsStr) : [];
      localStorage.setItem('qr_custom_products', JSON.stringify([createdProd, ...storedProds]));
      window.dispatchEvent(new CustomEvent('qr_product_created', { detail: createdProd }));
    }

    return createdProd;
  },

  deleteProduct: async (productId: number): Promise<void> => {
    try {
      await fetcher<void>(`/admin/products/${productId}`, {
        method: 'DELETE',
      });
    } catch (err) {
      console.warn(`Backend deleteProduct for ID ${productId} failed, removing locally.`, err);
    }

    const idx = MOCK_PRODUCTS.findIndex(p => p.id === productId);
    if (idx !== -1) {
      MOCK_PRODUCTS.splice(idx, 1);
    }

    if (typeof window !== 'undefined') {
      const storedProdsStr = localStorage.getItem('qr_custom_products');
      if (storedProdsStr) {
        const storedProds: Product[] = JSON.parse(storedProdsStr);
        const updated = storedProds.filter(p => p.id !== productId);
        localStorage.setItem('qr_custom_products', JSON.stringify(updated));
      }
      window.dispatchEvent(new CustomEvent('qr_product_deleted', { detail: productId }));
    }
  },

  submitOrder: async (qrToken: string, data: { items: any[]; notes?: string }): Promise<Order> => {
    try {
      return await fetcher<Order>(`/public/tables/${qrToken}/orders`, {
        method: 'POST',
        body: JSON.stringify(data),
      });
    } catch (err) {
      console.warn('Backend submitOrder failed, producing simulated order response.', err);
      return {
        id: Math.floor(1000 + Math.random() * 9000),
        orderNumber: `ORD-${Math.floor(1000 + Math.random() * 9000)}`,
        tableName: 'Table 01',
        status: 'PENDING',
        totalAmount: data.items.reduce((acc, i) => acc + (i.unitPrice * i.quantity), 0),
        items: data.items,
        notes: data.notes,
        createdAt: 'Just now'
      };
    }
  },

  submitServiceRequest: async (qrToken: string, type: string, message?: string): Promise<ServiceRequest> => {
    try {
      return await fetcher<ServiceRequest>(`/public/tables/${qrToken}/service-requests`, {
        method: 'POST',
        body: JSON.stringify({ type, message }),
      });
    } catch (err) {
      console.warn('Backend submitServiceRequest failed, producing simulated request response.', err);
      return {
        id: Math.floor(1 + Math.random() * 1000),
        tableName: 'Table 01',
        type,
        status: 'PENDING',
        message: message || 'Service requested',
        requestedAt: 'Just now'
      };
    }
  },

  // Admin Tables & Management
  getBranchTables: async (branchId: number): Promise<RestaurantTable[]> => {
    try {
      const tables = await fetcher<RestaurantTable[]>(`/admin/tables/branch/${branchId}`);
      if (tables && tables.length > 0) return tables;
    } catch (err) {
      console.warn('Backend getBranchTables failed, returning mock tables.', err);
    }
    return MOCK_TABLES;
  },

  createTable: async (data: { branchId: number; tableNumber: string }): Promise<RestaurantTable> => {
    try {
      return await fetcher<RestaurantTable>('/admin/tables', {
        method: 'POST',
        body: JSON.stringify(data),
      });
    } catch (err) {
      console.warn('Backend createTable failed, returning generated table DTO.', err);
      const randomToken = Math.random().toString(36).substring(2, 14);
      return {
        id: Math.floor(10 + Math.random() * 90),
        branchId: data.branchId,
        tableNumber: data.tableNumber,
        qrToken: randomToken,
        activeToken: randomToken,
        qrUrl: `http://localhost:3000/t/${randomToken}`,
        status: 'AVAILABLE',
        capacity: 4
      };
    }
  },

  // Auth
  login: (credentials: { email: string; password: string }) =>
    fetcher<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    }),

  // Staff Operations
  getBranchActiveOrders: async (branchId: number): Promise<Order[]> => {
    try {
      const orders = await fetcher<Order[]>(`/staff/branches/${branchId}/orders`);
      if (orders && orders.length > 0) return orders;
    } catch (err) {
      console.warn('Backend getBranchActiveOrders failed, using mock orders.', err);
    }
    return MOCK_ORDERS;
  },

  updateOrderStatus: async (branchId: number, orderId: number, status: string): Promise<Order> => {
    try {
      return await fetcher<Order>(`/staff/branches/${branchId}/orders/${orderId}/status?status=${status}`, {
        method: 'PATCH',
      });
    } catch (err) {
      console.warn('Backend updateOrderStatus failed, updating local mock state.', err);
      const found = MOCK_ORDERS.find(o => o.id === orderId);
      if (found) {
        found.status = status as any;
        return found;
      }
      return {
        id: orderId,
        orderNumber: `ORD-${orderId}`,
        tableName: 'Table 01',
        status: status as any,
        totalAmount: 100,
        items: [],
        createdAt: 'Just now'
      };
    }
  },

  getBranchServiceRequests: async (branchId: number): Promise<ServiceRequest[]> => {
    try {
      const requests = await fetcher<ServiceRequest[]>(`/staff/branches/${branchId}/service-requests`);
      if (requests && requests.length > 0) return requests;
    } catch (err) {
      console.warn('Backend getBranchServiceRequests failed, returning mock service requests.', err);
    }
    return MOCK_SERVICE_REQUESTS;
  },

  updateServiceRequestStatus: async (branchId: number, requestId: number, status: string): Promise<ServiceRequest> => {
    try {
      return await fetcher<ServiceRequest>(`/staff/branches/${branchId}/service-requests/${requestId}/status?status=${status}`, {
        method: 'PATCH',
      });
    } catch (err) {
      console.warn('Backend updateServiceRequestStatus failed, returning resolved mock request.', err);
      return {
        id: requestId,
        tableName: 'Table 01',
        type: 'CALL_WAITER',
        status,
        requestedAt: 'Just now'
      };
    }
  },
};

export const fetchActiveKitchenOrders = (branchId: number = 1) => api.getBranchActiveOrders(branchId);
export const updateOrderStatusInBackend = (branchId: number = 1, orderId: number, status: string) => api.updateOrderStatus(branchId, orderId, status);
