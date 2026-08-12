# Online Reservation System — OIBSIP Java Development Task 1

## Objective
A GUI-based train/transport reservation system where users log in, book
tickets (with an auto-generated PNR), and cancel bookings by looking up
that PNR.

## Tech Stack
- Java (Swing for GUI)
- JDBC
- MySQL / MariaDB (via XAMPP)
- Maven (dependency management — `mariadb-java-client`)

## Architecture
```
com.matuma.oibsip.reservation
├── Main.java                 → application entry point
├── DatabaseConnection.java   → JDBC connection helper
├── Reservation.java          → reservation data model
├── ReservationDAO.java       → booking, lookup, and cancellation logic
├── UserDAO.java              → login credential validation
├── LoginFrame.java           → login screen
├── DashboardFrame.java       → main menu (book / cancel)
├── BookingFrame.java         → reservation form + PNR generation
└── CancellationFrame.java    → PNR lookup + cancellation
```

## Database Schema

**`reservations` table**
| Column               | Type      |
|----------------------|-----------|
| pnr                  | VARCHAR(10) — Primary Key |
| passenger_name       | VARCHAR(100) |
| train_number         | VARCHAR(10) |
| train_name           | VARCHAR(100) |
| class_type           | VARCHAR(20) |
| journey_date         | DATE |
| source_station       | VARCHAR(100) |
| destination_station  | VARCHAR(100) |
| booking_timestamp    | TIMESTAMP (auto) |

**`users` table**
| Column   | Type |
|----------|------|
| username | VARCHAR(50) — Primary Key |
| password | VARCHAR(100) |

## Features
- Login form with invalid credential handling (access denied on mismatch)
- Reservation form: passenger name, train number/name, class type, journey
  date, source/destination station
- Auto-generated unique 8-character alphanumeric PNR on booking
- Confirmation dialog showing full booking details after a successful reservation
- Cancellation form: PNR lookup + full booking detail display
- "Are you sure?" confirmation dialog before cancellation is processed
- Input validation: no empty required fields, numeric train number check,
  strict `yyyy-mm-dd` date format validation

## Setup Instructions

1. **Start MySQL/MariaDB** (via XAMPP Control Panel — start the MySQL Database service)

2. **Create the database and tables:**
   ```sql
   CREATE DATABASE reservation_system;
   USE reservation_system;

   CREATE TABLE reservations (
       pnr VARCHAR(10) PRIMARY KEY,
       passenger_name VARCHAR(100) NOT NULL,
       train_number VARCHAR(10) NOT NULL,
       train_name VARCHAR(100) NOT NULL,
       class_type VARCHAR(20) NOT NULL,
       journey_date DATE NOT NULL,
       source_station VARCHAR(100) NOT NULL,
       destination_station VARCHAR(100) NOT NULL,
       booking_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   CREATE TABLE users (
       username VARCHAR(50) PRIMARY KEY,
       password VARCHAR(100) NOT NULL
   );

   INSERT INTO users (username, password) VALUES ('matuma', 'Oibsip2026!');
   ```

3. **Create a dedicated database user (optional but recommended):**
   ```sql
   CREATE USER 'reservation_app'@'localhost' IDENTIFIED BY 'OibsipTask1!2026';
   GRANT ALL PRIVILEGES ON reservation_system.* TO 'reservation_app'@'localhost';
   FLUSH PRIVILEGES;
   ```

4. **Run the app** — open the project in IntelliJ IDEA (Maven auto-imports
   the `mariadb-java-client` dependency), then run `Main.java`.

## Login Credentials (Demo)
- Username: `matuma`
- Password: `Oibsip2026!`

## Sample Flow
1. Log in with valid credentials
2. From the dashboard, choose "Book a Reservation"
3. Fill in passenger and journey details → click "Book Reservation"
4. Receive a confirmation dialog with the generated PNR
5. From the dashboard, choose "Cancel a Reservation"
6. Enter the PNR → "Fetch Booking" → review details → "Confirm Cancellation"

## Author
Matuma Malapile — OIBSIP Java Development Track