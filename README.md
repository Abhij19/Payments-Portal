# Payments Portal Application

A complete **Payments Management Portal** built with a **Spring Boot backend** and an **Angular frontend**.  
Users can **create, list, update, and delete payments**, and every payment gets a **unique daily sequence reference**.  
Idempotency is implemented using `clientRequestId`.

---

## Tech Stack

### **Backend (Spring Boot)**
- Java 17  
- Spring Boot 3  
- Spring Data JPA (H2 In-memory)  
- Hibernate  
- Lombok  
- Resilience4j (Circuit Breaker)  
- Swagger / OpenAPI 3  
- JUnit 5 + Mockito  
- Maven  

### **Frontend (Angular)**
- Angular 17   
- TypeScript  
- Bootstrap  

---

## Features

### **Backend Features**
- Create / List / Update / Delete payments  
- Idempotency via `clientRequestId`  
- Daily sequential reference numbers:  

```
PAY-YYYYMMDD-0001
```

- Concurrency-safe sequence using **PESSIMISTIC_WRITE** locking  
- Circuit breaker protection  
- Basic caching for GET endpoints  
- H2 console enabled for debugging  
- Global exception handling  
- Full Swagger UI documentation  

### **Frontend Features**
- Clean and responsive UI  
- Table listing of payments  
- Add new payment  
- Edit and delete payments  
- Auto-generate UUID for idempotency  
- Bootstrap-based layout  

---

## API Endpoints

### **POST** `/api/payments`
Creates a new payment.  
If duplicate `clientRequestId` is sent → returns the *existing* payment.

### **GET** `/api/payments`
Fetch all payments.

### **GET** `/api/payments/{id}`
Fetch a payment by ID.

### **PUT** `/api/payments/{id}`
Update amount/currency.

### **DELETE** `/api/payments/{id}`
Delete a payment.

---

## Sample JSON Response

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

## Run Backend

### Build
```
mvn clean install
```

### Run
```
mvn spring-boot:run
```
OR
```
java -jar target/payments-backend-0.0.1-SNAPSHOT.jar
```

### URLs

| Resource           | URL |
|-------------------|-----|
| Backend Root      | http://localhost:8080 |
| Swagger UI        | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON       | http://localhost:8080/v3/api-docs |
| H2 Console        | http://localhost:8080/h2-console |

---

## Run Frontend

### Install dependencies
```
npm install
```

### Start Angular
```
ng serve
```

### Frontend URL  
http://localhost:4200

Backend must run at:
```
http://localhost:8080
```

---

## Docker (Backend Only)

```
docker build -t payments-backend .
docker run -p 8080:8080 payments-backend
```

---

## Screenshots

Screenshots for:
- Swagger UI  
- H2 Console login  
- Database schema  

Stored in:

```
/screenshots/
```

---

## Notes

- `clientRequestId` ensures no duplicate payments.  
- PESSIMISTIC locks prevent race conditions on sequence generation.  
- H2 DB is used.

---
