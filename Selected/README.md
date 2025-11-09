# Selected Architecture (Microservices)
# 🎭 SMU Theater Movie & Podcast Ticket Booking System  

A full-stack **Microservices Architecture** project developed for the **Software Architecture course at SMU**.  
This system allows users to register, log in, browse theater events (movies, podcasts), select seats, and make payments — all built using a distributed microservices setup with Javalin (Java), Gradle, and a lightweight HTML/CSS/JS frontend.

---

## 🏗️ System Architecture Overview

The system is composed of **four core microservices** connected via an **API Gateway** and accessed by a simple **HTML frontend**.

### 🧩 Microservices
| Service | Port | Description |
|----------|------|-------------|
| 🎟️ **Event Service** | `8081` | Provides event data (titles, times, seats available) |
| 🔐 **Auth Service** | `8082` | Handles user registration and login |
| 💺 **Booking Service** | `8083` | Manages seat booking and confirmation |
| 💳 **Payment Service** | `8084` | Handles payment processing |
| 🌐 **API Gateway** | `8080` | Central entry point that routes frontend requests to the correct service |

### ⚙️ Architecture Diagram
```
Frontend (HTML/CSS/JS)
        │
        ▼
   API Gateway (8080)
   ├── /events  → Event Service (8081)
   ├── /auth    → Auth Service (8082)
   ├── /booking → Booking Service (8083)
   └── /payment → Payment Service (8084)
```

Each service runs independently using **Javalin** and communicates via HTTP REST calls through the **API Gateway**.

---

## 🚀 Features

✅ **User Authentication**
- Register and login with username/password  
- Token-based login (simulated for demo)

✅ **Event Management**
- View available SMU theater events and timings  
- Each event shows remaining seats  

✅ **Seat Booking**
- Interactive seat selection grid  
- Seat confirmation flow integrated with backend  

✅ **Payment Flow**
- Simulated credit card or digital wallet payment  
- Transaction ID and status displayed  

✅ **Frontend Integration**
- HTML/CSS static frontend served via Python HTTP server  
- Fully connected to backend via API Gateway  
- CORS enabled for localhost development  

---

## 🗂️ Project Structure

```
smu-theater-ms/
│
├── api-gateway/         
│   └── src/main/java/com/smu/gateway/App.java
│
├── auth-service/        
│   └── src/main/java/com/smu/auth/AuthApp.java
│
├── booking-service/     
│   └── src/main/java/com/smu/booking/BookingApp.java
│
├── event-service/       
│   └── src/main/java/com/smu/event/EventApp.java
│
├── payment-service/     
│   └── src/main/java/com/smu/payment/PaymentApp.java
│
├── frontend/            
│   ├── login.html
│   ├── dashboard.html
│   ├── seat-selection.html
│   ├── payment.html
│   ├── style.css
│   └── assets/smucampus.jpg
│
├── build.gradle.kts     
└── settings.gradle.kts
```

---

## 🧰 Tech Stack

**Backend:**
- Java 17+
- Javalin 5.x
- Gradle build system
- Java HTTP Client (for inter-service calls)

**Frontend:**
- HTML5, CSS3, Vanilla JavaScript  
- Served via `python3 -m http.server 5500`

**Architecture:**
- Microservices + API Gateway pattern  
- CORS-enabled REST communication  

---

## ⚡️ How to Run the Project

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/smu-theater-ms.git
cd smu-theater-ms
```

### 2️⃣ Run Backend Microservices
Open **five terminals**, one for each service:

```bash
# Terminal 1: Event Service
./gradlew :event-service:run

# Terminal 2: Auth Service
./gradlew :auth-service:run

# Terminal 3: Booking Service
./gradlew :booking-service:run

# Terminal 4: Payment Service
./gradlew :payment-service:run

# Terminal 5: API Gateway
./gradlew :api-gateway:run
```

All services will start locally on ports `8081–8084`, and the gateway on `8080`.

---

### 3️⃣ Run Frontend
In a **separate terminal**:
```bash
cd frontend
python3 -m http.server 5500
```

Then open:
👉 [http://localhost:5500/login.html](http://localhost:5500/login.html)

---

## 💡 Demo Flow

1️⃣ **Login/Register** → Registers or logs in user via Auth Service  
2️⃣ **Dashboard** → Lists events from Event Service  
3️⃣ **Seat Selection** → Selects seat and books via Booking Service  
4️⃣ **Payment Page** → Makes mock payment via Payment Service  
5️⃣ Confirmation & success pop-ups appear  

---

## 🧪 API Testing (Optional)

You can test services individually using `curl`:

```bash
# Register
curl -X POST http://localhost:8080/auth/register   -H "Content-Type: application/json"   -d '{"username":"bobby","password":"smu123"}'

# Login
curl -X POST http://localhost:8080/auth/login   -H "Content-Type: application/json"   -d '{"username":"bobby","password":"smu123"}'

# Get Events
curl http://localhost:8080/events

# Book Seat
curl -X POST http://localhost:8080/booking/book   -H "Content-Type: application/json"   -d '{"eventId":"e1","username":"bobby","seat":"A5"}'

# Make Payment
curl -X POST http://localhost:8080/payment/pay   -H "Content-Type: application/json"   -d '{"bookingId":"xyz-123","amount":20.00,"method":"credit_card"}'
```

---

## 🧱 Key Design Decisions

- Used **Javalin** for lightweight, fast HTTP handling  
- Each service fully independent (can be containerized)  
- Clear **API Gateway routing** pattern for scalability  
- Enabled **CORS** for cross-origin browser access  
- Frontend uses **modular HTML pages** instead of frameworks for clarity  

---

## 📸 Frontend Screenshots

1. **Login / Register Page**  
   ![Login](docs/screenshots/login.png)

2. **Event Dashboard**  
   ![Dashboard](docs/screenshots/dashboard.png)

3. **Seat Selection Page**  
   ![Seat Selection](docs/screenshots/seat.png)

4. **Payment Page**  
   ![Payment](docs/screenshots/payment.png)

> *(You can add your actual screenshots to `frontend/docs/screenshots/` and update these image links.)*

---

## 🧑‍💻 Contributors

| Name | Role |
|------|------|
| Monarch Nigam | Backend Developer / Architect |
| [Teammate Name] | Frontend Developer |
| [Teammate Name] | QA / Tester |

---

## 🏁 Future Enhancements
- Integrate a database (MySQL/PostgreSQL) for persistent user & booking data  
- Add logout & user session tracking  
- Implement seat availability sync between users  
- Deploy each service via Docker and Kubernetes  

---

## 🏫 Course Info
**Course:** Software Architecture  
**Institution:** Southern Methodist University (SMU), Dallas, TX  
**Semester:** Fall 2025  

---

**© 2025 SMU Theater Microservices Project — Educational Use Only**

