# QR Menu Platform — Project Constitution

## Project Identity
- **Name**: QR Menu Platform
- **Type**: Multi-tenant SaaS restaurant ordering platform
- **Architecture**: Modular monolith

## Core Principles
1. **Tenant isolation is non-negotiable** — data belonging to one business must never be accessible by another.
2. **Backend is the source of truth** — all prices calculated server-side, frontend displays only.
3. **Spec-driven development** — specifications are written before implementation.
4. **Incremental delivery** — one task group at a time, tested before moving on.
5. **No premature optimization** — build what's needed now, not what might be needed later.

## Technology Decisions
- **Backend**: Java 22 + Spring Boot 3.3+ (modular monolith)
- **Frontend**: Next.js + TypeScript + Tailwind CSS + shadcn/ui
- **Database**: PostgreSQL 16 (H2 for local dev fallback)
- **Auth**: JWT (stateless, with refresh tokens)
- **Build**: Maven (wrapper), npm
- **Migrations**: Flyway (SQL-based)
- **API**: REST with OpenAPI documentation

## Architectural Constraints
- No microservices, Kubernetes, Kafka, event sourcing, CQRS, GraphQL, or Elasticsearch.
- No Redis unless an immediate, documented need arises.
- Package-by-feature organization on both backend and frontend.
- DTOs for all API boundaries — never expose entities directly.
- Monetary values use DECIMAL(10,2) — never floating-point.
- All timestamps in UTC.
- Soft deletes for business data.

## Security Rules
- QR tokens must be cryptographically secure (32-byte SecureRandom, Base64URL).
- Sequential IDs never exposed in public URLs.
- Tenant context resolved from JWT claims — never trusted from client request body.
- Every staff query validates business/branch membership.
- Passwords hashed with BCrypt.

## Development Rules
- Inspect before editing.
- Small, reviewable changes.
- Tests after every task group.
- Never hide or disable failing tests.
- Database changes only via Flyway migrations.
- No secrets in code or version control.
