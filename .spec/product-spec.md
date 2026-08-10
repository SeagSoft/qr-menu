# QR Menu Platform — Product Specification (MVP)

## Overview
A SaaS platform where restaurants create digital menus accessible via QR codes on tables. Customers scan, browse, order, and request services — all from their mobile browser.

## User Roles
| Role | Description |
|------|-------------|
| SUPER_ADMIN | Platform-level administrator |
| BUSINESS_OWNER | Owns one business, manages all branches |
| BRANCH_MANAGER | Manages one branch |
| WAITER | Handles orders and service requests |
| KITCHEN | Prepares orders |
| CASHIER | Handles payments |
| CUSTOMER | Anonymous, accesses via QR scan |

## MVP Modules
1. Business & Branch Management
2. Table Management (with secure QR)
3. Menu Management (categories, products, variants, options)
4. Customer QR Menu (mobile-first)
5. Ordering (status workflow)
6. Staff Panel
7. Kitchen Display
8. Service Requests
9. Payments (offline + provider interface)
10. Basic Reporting

## Milestone 1 Scope
- Business, Branch, Staff Auth
- Table + QR token generation
- Menu (categories, products, variants, options)
- Public QR → menu resolution
- Customer mobile menu UI
- Staff login + basic admin (menu & table CRUD)

## Out of Milestone 1
- Order submission & workflow
- Kitchen display
- Service requests
- Payments
- Reporting
- WebSocket real-time
- PWA support
