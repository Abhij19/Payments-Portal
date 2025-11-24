# Payments Portal

This is the frontend UI for the **Payments Portal Application**, built using **Angular 17** with **standalone components**.  
It interacts with the Spring Boot backend to allow users to **view, add, edit, and delete** payments.

---

## Tech Stack

- Angular 17   
- TypeScript  
- Bootstrap  
- Forms API (ngModel)  

---

## Features

- Payments list page with table view  
- Add new payment  
- Edit existing payment  
- Delete payment  
- Client-side UUID generation for idempotent backend requests  
- Simple and clean UI using Bootstrap  
- Angular standalone architecture to match best practices  

---

## Backend Integration

The frontend communicates with the backend:

```
http://localhost:8080/api/payments
```

Endpoints consumed:
- `GET /api/payments`
- `POST /api/payments`
- `PUT /api/payments/{id}`
- `DELETE /api/payments/{id}`

---

### Frontend URL:
```
http://localhost:4200
```

---

## Run Locally
ng serve --proxy-config proxy.conf.json
```

---

## UI Overview

### **Payments List**
- Displays reference, amount, currency, created date  
- Edit/Delete actions  
- "Add Payment" button  

### **Create/Edit Payment**
- Input: amount  
- Dropdown: currency  
- Auto-generated clientRequestId on create  
- Reuses same form for editing  

---

## Notes & Assumptions

- UUID generated in frontend for guaranteeing idempotency.   
- No authentication required as per assignment.  

---
