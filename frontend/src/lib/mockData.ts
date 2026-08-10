import { MenuCategory, Product, RestaurantTable, Order, ServiceRequest } from './types';

export const MOCK_CATEGORIES: MenuCategory[] = [
  { id: 1, name: "Popular & Chef's Special", description: "Our most ordered signature creations", icon: "Flame", displayOrder: 1 },
  { id: 2, name: "Gourmet Burgers & Steaks", description: "Handcrafted dry-aged meats with artisanal buns", icon: "Beef", displayOrder: 2 },
  { id: 3, name: "Artisanal Pizzas", description: "Stone-baked Wood-fired Neapolitan pizza", icon: "Pizza", displayOrder: 3 },
  { id: 4, name: "Fresh Pasta & Risotto", description: "Handmade daily pasta with rich Italian sauces", icon: "UtensilsCrossed", displayOrder: 4 },
  { id: 5, name: "Desserts & Delights", description: "Decadent sweet treats and pastries", icon: "Cake", displayOrder: 5 },
  { id: 6, name: "Craft Beverages & Cocktails", description: "Fresh juices, artisanal coffees & specialty drinks", icon: "Coffee", displayOrder: 6 }
];

export const MOCK_PRODUCTS: Product[] = [
  {
    id: 101,
    categoryId: 1,
    name: "Truffle Wagyu Ribeye Steak",
    description: "250g A5 Wagyu ribeye, black truffle butter, charred asparagus, microgreens, garlic mashed potato.",
    basePrice: 850,
    imageUrl: "https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Chef's Pick", "Gluten-Free", "Premium"],
    calories: 780,
    prepTimeMinutes: 20,
    variants: [
      { id: 1, name: "Medium Rare", priceModifier: 0, isAvailable: true },
      { id: 2, name: "Medium", priceModifier: 0, isAvailable: true },
      { id: 3, name: "Medium Well", priceModifier: 0, isAvailable: true }
    ],
    optionGroups: [
      {
        id: 1,
        name: "Side Sauce Choice",
        isRequired: true,
        minSelections: 1,
        maxSelections: 1,
        options: [
          { id: 11, name: "Red Wine Reduction", priceModifier: 0, isAvailable: true },
          { id: 12, name: "Creamy Peppercorn", priceModifier: 30, isAvailable: true },
          { id: 13, name: "Black Truffle Aioli", priceModifier: 45, isAvailable: true }
        ]
      }
    ]
  },
  {
    id: 102,
    categoryId: 1,
    name: "Smoked Salmon & Caviar Avocado Toast",
    description: "Poached organic eggs, Norwegian cured salmon, beluga caviar, whipped avocado on sourdough.",
    basePrice: 380,
    imageUrl: "https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Best Seller", "Fresh"],
    calories: 450,
    prepTimeMinutes: 12,
    variants: [],
    optionGroups: []
  },
  {
    id: 103,
    categoryId: 2,
    name: "Double Smoked Bacon & Cheddar Burger",
    description: "200g smashed Angus beef, aged red cheddar, caramelized onion jam, crispy bacon, brioche bun.",
    basePrice: 320,
    imageUrl: "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Juicy", "Popular"],
    calories: 920,
    prepTimeMinutes: 15,
    variants: [
      { id: 4, name: "Single Patty (120g)", priceModifier: -60, isAvailable: true },
      { id: 5, name: "Double Patty (240g)", priceModifier: 0, isAvailable: true },
      { id: 6, name: "Triple Monster (360g)", priceModifier: 110, isAvailable: true }
    ],
    optionGroups: [
      {
        id: 2,
        name: "Choose Fries",
        isRequired: true,
        minSelections: 1,
        maxSelections: 1,
        options: [
          { id: 21, name: "Crispy Salted Fries", priceModifier: 0, isAvailable: true },
          { id: 22, name: "Truffle Parmesan Fries", priceModifier: 50, isAvailable: true },
          { id: 23, name: "Sweet Potato Fries", priceModifier: 40, isAvailable: true }
        ]
      }
    ]
  },
  {
    id: 104,
    categoryId: 3,
    name: "Tartufo e Burrata Neapolitan Pizza",
    description: "Fresh Mozzarella di Bufala, black truffle sauce, creamy Burrata cheese, wild mushrooms, basil.",
    basePrice: 420,
    imageUrl: "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Vegetarian", "Wood-fired"],
    calories: 850,
    prepTimeMinutes: 14,
    optionGroups: [
      {
        id: 3,
        name: "Crust Style",
        isRequired: false,
        minSelections: 0,
        maxSelections: 1,
        options: [
          { id: 31, name: "Classic Neapolitan", priceModifier: 0, isAvailable: true },
          { id: 32, name: "Stuffed Mozzarella Crust", priceModifier: 60, isAvailable: true }
        ]
      }
    ]
  },
  {
    id: 105,
    categoryId: 4,
    name: "Wild Mushroom & Truffle Tagliatelle",
    description: "Handmade egg pasta, porcini, chanterelles, white truffle oil, 24-month aged Parmigiano Reggiano.",
    basePrice: 360,
    imageUrl: "https://images.unsplash.com/photo-1621996346565-e3d5d6281288?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Handmade Pasta"],
    calories: 620,
    prepTimeMinutes: 15
  },
  {
    id: 106,
    categoryId: 5,
    name: "Classic Italian Tiramisu",
    description: "Savoiardi ladyfingers soaked in dark espresso & Marsala wine, layered with light mascarpone cream.",
    basePrice: 180,
    imageUrl: "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Sweet", "House Special"],
    calories: 420,
    prepTimeMinutes: 5
  },
  {
    id: 107,
    categoryId: 6,
    name: "Iced Caramel Macchiato",
    description: "Double shot espresso, vanilla syrup, cold whole milk, drizzled with house caramel.",
    basePrice: 120,
    imageUrl: "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=800&q=80",
    isAvailable: true,
    tags: ["Refreshing"],
    calories: 210,
    prepTimeMinutes: 4,
    variants: [
      { id: 7, name: "Regular Size", priceModifier: 0, isAvailable: true },
      { id: 8, name: "Large Size", priceModifier: 25, isAvailable: true }
    ]
  }
];

export const MOCK_TABLES: RestaurantTable[] = [
  { id: 1, name: "Table 01 (Garden Terrace)", capacity: 4, status: "OCCUPIED", activeToken: "tbl-001", currentOrderCount: 2 },
  { id: 2, name: "Table 02 (VIP Booth)", capacity: 6, status: "BILL_REQUESTED", activeToken: "tbl-002", currentOrderCount: 1 },
  { id: 3, name: "Table 03 (Window Seat)", capacity: 2, status: "AVAILABLE", activeToken: "tbl-003", currentOrderCount: 0 },
  { id: 4, name: "Table 04 (Main Hall)", capacity: 4, status: "WAITER_CALLED", activeToken: "tbl-004", currentOrderCount: 1 },
  { id: 5, name: "Table 05 (Bar High-Top)", capacity: 2, status: "OCCUPIED", activeToken: "tbl-005", currentOrderCount: 3 }
];

export const MOCK_ORDERS: Order[] = [
  {
    id: 1001,
    orderNumber: "ORD-9841",
    tableName: "Table 01 (Garden Terrace)",
    branchName: "Bosphorus Main Branch",
    items: [
      { id: 1, productName: "Truffle Wagyu Ribeye Steak", quantity: 1, unitPrice: 850, totalPrice: 850, selectedVariantName: "Medium Rare", selectedOptionsText: "Black Truffle Aioli" },
      { id: 2, productName: "Iced Caramel Macchiato", quantity: 2, unitPrice: 120, totalPrice: 240, selectedVariantName: "Large Size" }
    ],
    totalAmount: 1090,
    status: "PREPARING",
    createdAt: "10 mins ago",
    estimatedMinutes: 12,
    notes: "No onions in steak garnishing please."
  },
  {
    id: 1002,
    orderNumber: "ORD-9842",
    tableName: "Table 04 (Main Hall)",
    branchName: "Bosphorus Main Branch",
    items: [
      { id: 3, productName: "Double Smoked Bacon & Cheddar Burger", quantity: 2, unitPrice: 320, totalPrice: 640, selectedOptionsText: "Truffle Parmesan Fries" },
      { id: 4, productName: "Tartufo e Burrata Neapolitan Pizza", quantity: 1, unitPrice: 420, totalPrice: 420 }
    ],
    totalAmount: 1060,
    status: "PENDING",
    createdAt: "2 mins ago",
    estimatedMinutes: 18
  },
  {
    id: 1003,
    orderNumber: "ORD-9839",
    tableName: "Table 02 (VIP Booth)",
    branchName: "Bosphorus Main Branch",
    items: [
      { id: 5, productName: "Wild Mushroom & Truffle Tagliatelle", quantity: 2, unitPrice: 360, totalPrice: 720 },
      { id: 6, productName: "Classic Italian Tiramisu", quantity: 2, unitPrice: 180, totalPrice: 360 }
    ],
    totalAmount: 1080,
    status: "READY",
    createdAt: "25 mins ago"
  }
];

export const MOCK_SERVICE_REQUESTS: ServiceRequest[] = [
  { id: 1, tableName: "Table 04 (Main Hall)", type: "CALL_WAITER", status: "PENDING", message: "Customer requested extra cutlery, napkins, and ice water refill", requestedAt: "1 min ago" },
  { id: 2, tableName: "Table 02 (VIP Booth)", type: "BILL_REQUEST", status: "PENDING", message: "Customer requested credit card terminal to pay bill", requestedAt: "4 mins ago" },
  { id: 3, tableName: "Table 01 (Garden Terrace)", type: "WATER_REFILL", status: "ATTENDED", message: "Fresh sparkling water bottle requested", requestedAt: "15 mins ago" }
];
