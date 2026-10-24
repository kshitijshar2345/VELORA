# VELORA — Premium Fashion Store

A full-stack starter for a premium fashion ecommerce store: React + Vite frontend, Spring Boot REST API, and MySQL. Includes product/category administration, search/filtering, wishlist, cart, orders, coupons, editable homepage content, and Razorpay order creation/verification integration.

## Important deployment notes
- The source is provided as a deployable starter, not a claim that your Render account/database/payment credentials have already been configured or that a live deployment has been tested.
- Do not commit secrets. Configure the environment variables shown in `.env.example` and `backend/.env.example` in your hosting dashboard.
- Razorpay requires valid test/live keys. Start with test keys and verify payment flows before accepting real payments.
- The first admin is bootstrapped from `ADMIN_EMAIL` and `ADMIN_PASSWORD` at backend startup if no account with that email exists. Use a strong password and keep the credentials private.
- For production, use HTTPS, rotate JWT secrets, configure backups, email delivery, shipping/tax rules, and test order/payment idempotency.

## Local prerequisites
- Java 17+
- Maven 3.9+
- Node.js 20+
- MySQL 8+

## 1. Create database
```sql
CREATE DATABASE velora_store CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 2. Run backend
Set environment variables from `backend/.env.example` in your shell or IDE, then:
```bash
cd backend
mvn spring-boot:run
```
API: `http://localhost:8080/api`

## 3. Run frontend
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```
Frontend: `http://localhost:5173`
Set `VITE_API_URL=http://localhost:8080/api` in `frontend/.env`.

## Admin access
The backend creates the configured admin account on startup if it does not exist. Sign in through `/login` with `ADMIN_EMAIL` / `ADMIN_PASSWORD`. The UI exposes the admin dashboard only for an account with the ADMIN role; backend endpoints independently enforce authorization.

## Razorpay
Set `RAZORPAY_KEY_ID` and `RAZORPAY_KEY_SECRET` on the backend. The frontend receives only the public key ID from `/api/payments/key`. Checkout creates a server-side Razorpay order and verifies the signature before marking the order paid. Test with Razorpay test credentials first.

## Render deployment overview
1. Create a managed MySQL database (or use an external MySQL provider) and note host, port, database, username, password.
2. Deploy `backend/` as a Render Web Service using Docker or a Java build. A `Dockerfile` is included. Set environment variables from `backend/.env.example`; `PORT` is supplied by Render.
3. Deploy `frontend/` as a Render Static Site with root directory `frontend`, build command `npm install && npm run build`, publish directory `dist`. Set `VITE_API_URL` to `https://YOUR-BACKEND.onrender.com/api` before building.
4. Set backend `APP_CORS_ALLOWED_ORIGINS` to the exact frontend URL (no trailing slash). Redeploy backend and frontend.
5. Test product listing, admin CRUD, checkout, Razorpay signature verification, and order history in a staging environment.

## Project structure
- `frontend/` React application
- `backend/` Spring Boot REST API
- `database/schema.sql` database creation helper
- `.env.example` deployment checklist
