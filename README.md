# 🍔 Food Delivery & Reservation API

A robust Spring Boot REST API for managing food deliveries and reservations. This project handles customer registration, menu management, and order processing with strict validation and error handling.

## 🚀 Technologies Used

* **Java 21**
* **Spring Boot 3.4.2** (Web, Data JPA, Validation)
* **PostgreSQL** (Database)
* **Liquibase** (Database Migration)
* **Docker & Docker Compose** (Containerization)
* **MapStruct** (DTO Mapping)
* **Lombok** (Boilerplate reduction)
* **JUnit 5 & Mockito** (Testing)

---

## 🛠️ Project Architecture

The project follows a clean architecture with separation of concerns:
* **Controller Layer**: Handles HTTP requests.
* **Service Layer**: Contains business logic.
* **Repository Layer**: Interacts with the database.
* **Domain**: JPA Entities.
* **DTO**: Data Transfer Objects for API communication.
* **Exception Handling**: Global handler for validation and business errors.

---

## ⚙️ Prerequisites

* Java 21 SDK
* Docker & Docker Compose (Recommended)
* Maven

---

## 🏃‍♂️ How to Run

### 1. Database Setup (using Docker)
The project includes a `docker-compose.yml` file to spin up a PostgreSQL database and the application.

```bash
# Start PostgreSQL and the Application
docker-compose up -d --build
#
# 🍔 Food Delivery & Reservation API

A robust Spring Boot REST API for managing food deliveries and reservations. This project handles customer registration, menu management, and order processing with strict validation and error handling.

## 🚀 Technologies Used

* **Java 21**
* **Spring Boot 3.4.2** (Web, Data JPA, Validation)
* **PostgreSQL** (Database)
* **Liquibase** (Database Migration)
* **Docker & Docker Compose** (Containerization)
* **MapStruct** (DTO Mapping)
* **Lombok** (Boilerplate reduction)
* **JUnit 5 & Mockito** (Testing)

---

## 🛠️ Project Architecture

The project follows a clean architecture with separation of concerns:
* **Controller Layer**: Handles HTTP requests.
* **Service Layer**: Contains business logic.
* **Repository Layer**: Interacts with the database.
* **Domain**: JPA Entities.
* **DTO**: Data Transfer Objects for API communication.
* **Exception Handling**: Global handler for validation and business errors.

---

## ⚙️ Prerequisites

* Java 21 SDK
* Docker & Docker Compose (Recommended)
* Maven

---

## 🏃‍♂️ How to Run

### 1. Database Setup (using Docker)
The project includes a `docker-compose.yml` file to spin up a PostgreSQL database and the application.

```bash
# Start PostgreSQL and the Application
docker-compose up -d --build

🔌 API Endpoints
1. 👤 Customers (/api/customers)
Register a new customer

POST /api/customers

Body:

JSON
{
  "firstName": "Ilyas",
  "lastName": "Faquih",
  "email": "ilyas@example.com",
  "phone": "+212 600 000 000"
}
Response: Returns the created customer with a unique code (e.g., uiid-09-xxxx).

2. 🍕 Menu (/api/menu)
Add a Menu Item

POST /api/menu

Body:

JSON
{
  "name": "Pizza Royale",
  "price": 85.00,
  "available": true
}
Get Menu Items

GET /api/menu (Get all)

GET /api/menu?q=pizza (Search by name)

3. 📦 Orders (/api/orders)
Create an Order

POST /api/orders

Body:

JSON
{
  "customerCode": "uiid-09-4821",
  "deliveryTime": "13:30",
  "deliveryMode": "LIVRAISON",
  "menuItemIds": [1, 2]
}
Validation Rules:

customerCode: Must exist.

deliveryTime: Must be between 08:00 and 00:00 (midnight).

menuItemIds: Cannot be empty.

🧪 Testing
The project includes Unit Tests (Service Layer) and Integration Tests (Controller Layer).

To run the tests:

Bash
./mvnw test
Key Tests Implemented:
ReservationServiceTest: Verifies business logic (e.g., throws exception if customer not found).

OrderControllerTest: Verifies input validation (e.g., returns 400 if delivery time is invalid).

🗄️ Database Schema
The database schema is managed via Liquibase:

customer: Stores customer details and unique codes.

menu_item: Stores available food items.

food_order: Stores order details (time, delivery mode).

order_items: Join table for the Many-to-Many relationship between Orders and Menu Items.

🛡️ Exception Handling
The API returns structured error responses:

Validation Error (400 Bad Request):

JSON
{
  "timestamp": "...",
  "status": 400,
  "error": "Validation Failed",
  "messages": {
    "deliveryTime": "Hors horaires de travail (08:00 - 00:00)"
  }
}
Business Error (500 Internal Server Error):

JSON
{
  "timestamp": "...",
  "status": 500,
  "error": "Business Error",
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Makaynch chi client b had l-code..."
}
