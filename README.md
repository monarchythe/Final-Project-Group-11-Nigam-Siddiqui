# Final-Project-Group-11-Nigam-Siddiqui
# 🎭 SMU Theater Ticket Booking System — Client–Server Architecture (Unselected)

## 📌 Overview
This is the **Client–Server Architecture** implementation of the SMU Educational Movie & Podcast Ticket Booking System.  
It uses **one Spring Boot server** to handle all logic and also serves the static frontend pages.

The microservices version exists in the `Selected/` folder.  
This version lives in the `Unselected/smu-theater-cs/` folder.

---

## 🚀 How to Run the Application

Open a terminal inside:

```
Unselected/smu-theater-cs
```

Run the server using:

```
./gradlew bootRun
```

Once running, open the system in your browser:

```
http://localhost:8090/login.html
```

---

## 🎯 Features
- 🔐 User Authentication (Register + Login)
- 🎬 Events Listing & Details
- 🎟 Seat Selection (A–E rows)
- 🧾 Booking System
- 💳 Mock Payment System
- 🖥 Static Website (HTML/CSS/JS served by Spring Boot)
- 🌐 REST Endpoints in Java + JSON communication

---

## 📂 Project Structure
```
smu-theater-cs/
│
├── src/main/java/com/smu/cs/
│   ├── Application.java
│   ├── controllers/
│   │   ├── AuthController.java
│   │   ├── EventsController.java
│   │   ├── BookingController.java
│   │   └── PaymentController.java
│   ├── models/
│   ├── storage/
│   └── ...
│
└── src/main/resources/static/
    ├── login.html
    ├── dashboard.html
    ├── seat-selection.html
    ├── payment.html
    ├── style.css
    └── images/
```

---

## 🧪 API Endpoints

### Authentication
```
POST /auth/register
POST /auth/login
```

### Events
```
GET /events
GET /events/{id}
```

### Booking
```
POST /booking/book
GET  /booking/{id}
```

### Payment
```
POST /payment/pay
```

---

## 🧬 Running Git Commands to Push This Version

From inside the root repo:

```
git add .
git commit -m "Add Client-Server Architecture Implementation"
git push origin main
```

---

## 📝 Notes
- This version uses **in-memory storage only** (no DB file required).
- All frontend files are served from `src/main/resources/static`.
- The architecture is intentionally monolithic to contrast with the microservices version.

---

## 🎓 Academic Purpose
This implementation is used for:
- Comparison with microservices architecture  
- Risk analysis  
- Performance differences  
- Architectural trade-offs  
- Final project submission for CS5/7319 Software Architecture

---
