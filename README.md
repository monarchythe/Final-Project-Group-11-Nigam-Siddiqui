# SMU Educational Theater Booking System  
_Group 11 – Nigam & Siddiqui_  

This repository contains the final project for the Software Architecture course.  
We implemented the **same SMU Theater Movie/Podcast booking system** using two
candidate architecture styles:

- **Selected**: Microservices + API Gateway  
- **Unselected**: Client–Server (Spring Boot monolith)

Both versions share the same user-facing functionality (login/register, event
list, seat selection, payment), but differ in how responsibilities are split
across components and how they communicate.

---

## 1. Repository Structure

```text
Final-Project-Group-11-Nigam-Siddiqui/
├── Selected/           # Selected architecture: Microservices + API Gateway
│   └── smu-theater-ms/
│       ├── api-gateway/
│       ├── auth-service/
│       ├── event-service/
│       ├── booking-service/
│       ├── payment-service/
│       └── frontend/   # Static HTML/CSS/JS UI
│
├── Unselected/         # Unselected architecture: Client–Server monolith
│   └── smu-theater-cs/
│       ├── src/main/java/com/smu/cs/
│       │   ├── auth/       # AuthController, User model
│       │   ├── events/     # EventsController, Event model
│       │   ├── booking/    # BookingController, Booking model
│       │   ├── payment/    # PaymentController, Payment model
│       │   └── models/     # Shared domain classes
│       └── src/main/resources/static/
│           ├── login.html
│           ├── dashboard.html
│           ├── seat-selection.html
│           ├── payment.html
│           └── style.css
│
├── LICENSE
└── README.md           # This document

---

2. Platforms, Tools, and Installation

Both implementations use Java + Gradle, and were developed primarily in
IntelliJ IDEA.

2.1 Required Software

JDK 17 (or compatible 17+)

Download from:

Oracle JDK: https://www.oracle.com/java/technologies/downloads/

Or OpenJDK: https://adoptium.net/

After installation, verify:

java -version


It should report version 17.x.

Gradle Wrapper (recommended)

You do not need to install Gradle manually.

Each sub-project includes gradlew / gradlew.bat and the .gradle
wrapper, so commands like ./gradlew bootRun or ./gradlew :api-gateway:run
will download the correct Gradle (8.x) automatically.

IntelliJ IDEA (optional but recommended)

Download Community Edition: https://www.jetbrains.com/idea/

Open each subproject (smu-theater-ms, smu-theater-cs) as a Gradle project;
IntelliJ will import all modules.

Python 3 (only needed for the Selected frontend)

Used to serve static HTML for the microservices version.

Verify:

python3 --version


Ports used (by default):

Microservices:

API Gateway: 8080

Event Service: 8081

Auth Service: 8082

Booking Service: 8083

Payment Service: 8084

Frontend static server: 5500

Client–Server monolith:

Spring Boot app (with embedded UI): 8090

Make sure these ports are free before running.

3. How to Compile and Run – Selected Architecture (Microservices)

Project path: Selected/smu-theater-ms

3.1 Build / Compile

From the root of this sub-project:

cd Selected/smu-theater-ms
./gradlew clean build


This compiles all microservices and downloads dependencies (Javalin, Jetty,
Jackson, etc.).

3.2 Run the Microservices

Open five terminals (or run via IntelliJ configurations), from
Selected/smu-theater-ms:

API Gateway (port 8080)

./gradlew :api-gateway:run


Event Service (port 8081)

./gradlew :event-service:run


Auth Service (port 8082)

./gradlew :auth-service:run


Booking Service (port 8083)

./gradlew :booking-service:run


Payment Service (port 8084)

./gradlew :payment-service:run


You should see logs from each service indicating they are listening on their
respective ports and responding to /health (for the gateway) and JSON APIs
for events, bookings, etc.

3.3 Run the Frontend (Microservices)

In a sixth terminal:

cd Selected/smu-theater-ms/frontend
python3 -m http.server 5500


Then open:

http://localhost:5500/login.html


Happy-path flow (Selected architecture):

Register a user on the login page

Frontend → POST http://localhost:8080/auth/register → Auth Service

Login

Frontend → POST /auth/login via gateway

Token + username stored in localStorage

Dashboard

UI calls /events on the gateway, which forwards to the Event Service

User clicks Book on a specific event → seat-selection.html

Seat Selection

User clicks a seat (A1–E5) → POST /booking/book via gateway

Booking ID stored client side

Payment

UI shows event title + seat from localStorage

POST /payment/pay via gateway → Payment Service returns transaction ID

On success, user is redirected back to the dashboard.

Note: Microservices version uses in-memory maps only.
Restarting any service clears users, events, bookings, and payments.

4. How to Compile and Run – Unselected Architecture (Client–Server)

Project path: Unselected/smu-theater-cs

This implementation uses Spring Boot 3.x as a single monolithic server with
embedded static HTML pages.

4.1 Build / Compile
cd Unselected/smu-theater-cs
./gradlew clean build


Gradle will download Spring Boot and other dependencies and compile all
controllers/models.

4.2 Run the Monolithic Server

From the same directory:

./gradlew bootRun


You should see Spring Boot startup logs ending with something like:

Tomcat started on port 8090 (http) with context path '/'
Started Application in X.XXX seconds

4.3 Access the System (Client–Server)

Open the browser at:

http://localhost:8090/login.html


The same UI is used, but all JavaScript calls use relative paths (e.g.,
/auth/login, /events, /booking/book, /payment/pay) that are served by
the same Spring Boot app.

Happy-path flow (Client–Server):

Register user → POST /auth/register handled by AuthController

Login → POST /auth/login returns a simple token + username

Dashboard → GET /events from EventsController

Seat selection → POST /booking/book via BookingController

Payment → POST /payment/pay via PaymentController

HTML files (login.html, dashboard.html, seat-selection.html,
payment.html) are served from src/main/resources/static.

Again, this implementation uses an in-memory Database class (no persistent
DB): all data resets on restart.

5. Architectural Differences and Rationale
5.1 High-Level Architecture

Selected – Microservices + Gateway

Separate deployable services:

api-gateway (Javalin)

auth-service

event-service

booking-service

payment-service

Services communicate via HTTP JSON calls.

Frontend talks only to the gateway.

Each service has its own Gradle module and can be scaled independently.

Unselected – Client–Server (Monolith)

Single Spring Boot application:

Controllers:

AuthController

EventsController

BookingController

PaymentController

Shared models:

User, Event, Booking, Payment

Simple Database class holding in-memory maps.

Static HTML/CSS/JS served directly from src/main/resources/static.

All logic runs in the same process and shares memory.

5.2 Components & Connectors (Code-Level)
Concern	Microservices (Selected)	Client–Server (Unselected)
Auth	auth-service (Javalin, port 8082)	AuthController in Spring Boot
Events	event-service (Javalin, port 8081)	EventsController
Booking	booking-service (Javalin, port 8083)	BookingController
Payment	payment-service (Javalin, port 8084)	PaymentController
Coordination	api-gateway forwards /auth, /events, etc.	Direct controller methods inside single application
Data Storage	Per-service in-memory maps	Shared Database class with in-memory maps
Frontend	Separate frontend/ static server (Python)	Embedded static/ folder in Spring Boot

Connectors:

Microservices:

HTTP between gateway and services (HttpClient + JSON)

Browser → gateway on port 8080

Client–Server:

Browser → Spring MVC controllers (no internal HTTP calls)

Internal communication via method calls and shared Java objects.

5.3 Deployment & Scaling

Microservices

Each service can be deployed to a separate container/VM and scaled
independently (e.g., more instances of booking-service only).

Fault isolation: if payment is down, event listing may still work.

Overhead: multiple processes, more DevOps/scripts, service discovery if
taken to production.

Client–Server

Single deployment artifact (jar or war).

Easier to configure, run, and debug; only one process to monitor.

Scaling is coarse-grained: must scale the entire application, not individual
functions.

A failure (e.g., memory leak) can bring down the entire system.

5.4 Data and State Management

Both implementations intentionally use in-memory maps for users,
events, bookings, and payments (no persistent DB).

Microservices version keeps separate maps per service; client-server keeps one
Database component with multiple maps.

This separation allows us to discuss evolutionary paths:

Microservices: each service can later adopt its own database / schema.

Monolith: can start with a single relational database schema, later split if
needed but with higher refactoring cost.

5.5 Development, Testing, and Maintainability

Microservices – Pros

Clear boundaries around business capabilities (Auth, Events, Booking,
Payment).

Easier to assign teams per service.

Javalin services are small and easy to reason about.

API Gateway provides a single entry point to enforce cross-cutting concerns
(logging, auth, rate limiting, etc.).

Microservices – Cons

Local development: requires running 4–5 processes plus the frontend.

Harder debugging across process boundaries.

Requires careful versioning of service contracts.

Client–Server – Pros

Very fast local setup: ./gradlew bootRun and go.

End-to-end debugging in a single IDE process.

Fewer moving parts; simpler for small teams and early prototypes.

Client–Server – Cons

Tighter coupling between concerns; codebase grows as a single unit.

Harder to scale individual parts.

Modifications to one area risk side effects in others.

5.6 Empirical Observations (Prototyping)

From small-scale experiments (documented in more detail in the risk-analysis
slides):

Performance / Latency

For light load (single user flows), both architectures had similar
latencies (all responses < 100 ms).

Under a simulated burst of booking requests, microservices introduced
small overhead (extra HTTP hops through the gateway), but remained within
acceptable latency for an interactive system.

Resource Usage

Client–Server used fewer OS processes and slightly less memory.

Microservices used more total memory (separate JVMs) but allowed us to
imagine independent scaling (e.g., extra instances of booking-service
only).

Developer Effort

Microservices required more configuration (five Gradle modules, ports,
gateway wiring).

Once the base skeleton was established, adding a new route to a specific
service was straightforward and local.

5.7 Rationale for Final Selection

We chose Microservices + API Gateway as the selected architecture because:

It matches the domain: a theater booking system with clear bounded
contexts (authentication, events catalog, bookings, payments).

It supports future scalability and evolution:

We could imagine heavy load on bookings around show-time while events and
auth remain light.

It provides modifiability and better fault isolation:

A change in payment logic or integration with a real payment provider can
be done inside payment-service with minimal impact on others.

Even though the Client–Server version is easier to deploy and may be
perfectly adequate for a small campus theater, the microservices version
demonstrates more advanced architectural tactics (API Gateway, service
decomposition) aligned with the course goals.

The Client–Server implementation remains valuable as:

A baseline to compare complexity, performance, and operational effort.

An example of a simpler architecture that might be chosen under different
constraints (small team, limited operations expertise, single-node
deployment).

6. Implementation Differences (Code, Components, Connectors)
6.1 Source Code Layout

Microservices

Multiple Gradle sub-projects (api-gateway, auth-service, etc.).

Each has its own build.gradle.kts, src/main/java, and tests.

Services communicate via HTTP clients (java.net.http.HttpClient).

Client–Server

Single Spring Boot app.

Controllers are simple annotated classes using @RestController and
@RequestMapping.

Communication is in-process method calls; controllers delegate to helper
classes and shared models.

6.2 Reusable Components

Shared concepts (User, Event, Booking, Payment) appear in both
implementations, but:

In microservices, they are duplicated per service where needed.

In the monolith, they live in a shared models package and are reused
across controllers.

Connectors

Microservices: JSON over HTTP + API gateway routing.

Client–Server: Spring MVC method dispatch + JSON serialization.

7. Notes for Grader

Both systems are intentionally simplified:

No persistence (no database) – data is stored in memory and cleared on restart.

No real authentication tokens or encryption – tokens are simple placeholders.

No real payment integration – payment is simulated with randomly generated
transaction IDs.

To test both implementations back-to-back:

Run the microservices version (Section 3) and confirm:

/login → events → seat selection → payment works via gateway.

Then stop all services and run the client–server version (Section 4) and
follow the same flow at http://localhost:8090/login.html.

The Selected and Unselected sub-directories each contain their own
detailed README that goes deeper into architecture diagrams, module structure,
and sequence flows for that style.

If any step is unclear, please refer to the per-architecture READMEs or the
slides in the project report, which include sequence diagrams and ATAM-style
risk analysis comparing both styles.

