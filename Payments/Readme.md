#  Payments Service

The backend service for the **Payments Portal Application**, built using **Spring Boot**.  
It manages payment creation, retrieval, updates, and deletion while ensuring **idempotency**, **daily sequence-number generation**, and **concurrency-safe reference assignment**.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA (H2 in-memory DB)
- Lombok
- Resilience4j (Circuit Breaker)
- Swagger / OpenAPI 3
- JUnit 5 + Mockito
- Maven

---

## Features

- CRUD APIs for managing payments
- Prevents duplicates using `clientRequestId` (idempotent behavior)
- Daily sequential reference generation:
  ```
  PAY-YYYYMMDD-####
  ```
- Concurrency-safe increment using **PESSIMISTIC_WRITE** lock
- Circuit breaker on payment creation
- Centralized global exception handling
- Basic caching on listing endpoint
- Swagger documentation enabled

---

## API Endpoints

### **POST** `/api/payments`
Create a payment.  
If `clientRequestId` already used → returns existing payment.

### **GET** `/api/payments`
List all payments.

### **GET** `/api/payments/{id}`
Fetch a single payment.

### **PUT** `/api/payments/{id}`
Update amount & currency.

### **DELETE** `/api/payments/{id}`
Delete a payment.

---

##  Sample JSON Response

```json
{
  "id": 1,
  "reference": "PAY-20250214-0001",
  "amount": 150.0,
  "currency": "USD",
  "createdAt": "2025-02-14T09:41:33.810Z",
  "clientRequestId": "b27d4019-b0ad-42d0-9b6e-afc22e23d93a"
}
```

---

##  URLs

| Resource | URL |
|---------|-----|
| Backend Root | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI Docs | http://localhost:8080/v3/api-docs |
| H2 Console | http://localhost:8080/h2-console |

---

## Docker

```bash
docker build -t payments-backend .
docker run -p 8080:8080 payments-backend
```

---

## Notes & Assumptions

- `clientRequestId` enforces idempotency and avoids duplicates.
- Database sequence value stored per date for predictability.
- `PESSIMISTIC_WRITE` chosen to demonstrate concurrency-safe increments.
---
