# PowerliftingApp Microservices

## Project Overview

**PowerliftingApp** is a microservices-based application for Powerlifting enthusiasts. It consists of three services:

* **Userservice** – manages user registration, authentication, and user data
* **Profileservice** – manages user profiles (avatar, bio, Wilks score)
* **Wilksservice** – calculates Wilks scores for powerlifting performance

The services communicate through **REST API**. The application uses Spring Boot, PostgreSQL, Maven, and Docker for easy deployment.

---

## Technologies Used

* Java 17
* Spring Boot 3.3
* Maven
* Spring Security
* Spring Data JPA
* PostgreSQL
* Docker & Docker Compose

---

## Prerequisites

Before running the project, make sure you have:

* Java 17 installed
* Docker installed
* Maven installed
* Postman or any REST client for testing API endpoints

---

## Running the Project

1. **Clone the repository**

```bash
git clone <repository-url>
cd App
```
2. **Build all services with Maven**
```bash
cd App
mvn clean package -DskipTests
```

3. **Build and start services using Docker Compose**

```bash
docker-compose up -d --build
```

4. **Check service logs**

```bash
docker logs -f userservice
docker logs -f profileservice
docker logs -f wilksservice
```

5. **Access service endpoints**

* Userservice: `http://localhost:8080`
* Profileservice: `http://localhost:8081`
* Wilksservice: `http://localhost:8082`

---

## API Endpoints

### Userservice

| Method | Endpoint           | Description                 |
| ------ | ------------------ | --------------------------- |
| POST   | `/auth/register`   | Register a new user         |
| POST   | `/auth/login`      | User login                  |
| POST   | `/auth/logout`     | Logout user                 |
| GET    | `/auth/me`         | Get current user info       |
| PUT    | `/auth/me`         | Update current user profile |
| GET    | `/auth/users`           | Get all users               |
| GET    | `/auth/users/{id}`      | Get user by ID              |
| PUT    | `/auth/users/{id}/role` | Update user role            |
| DELETE | `/auth/users/{id}`      | Delete user by ID           |


**Sample Registration Request:**

```json
{
  "name": "user_1",
  "password": "password",
  "email": "user_1@example.com"
}
```
```json
{
  "id": 1,
  "role": "USER"
  "name": "user_1",
  "email": "user_1@example.com",
}
```

### Profileservice

| Method | Endpoint             | Description            |
| ------ | -------------------- | ---------------------- |
| GET    | `/profiles/{profileId}` | Get profile by profile ID |
| GET    | `/profiles`          | Get all profiles |
| POST   | `/profiles`          | Create a profile       |
| POST   | `/profiles/update-wilks`| Update Wilks Score in current logged profile|
| DELETE | `/profiles/{profileId}` | Delete a profile by profile ID       |

**Sample Profile Creation Request:**

```json
{
  "avatarUrl": "https://example.com/avatar1.png",
  "bio": "This is a sample bio"
}
```
**Sample Response:**

```json
{
    "id": 1,
    "userId": 1,
    "bio": "This is a sample bio",
    "avatarUrl": "https://example.com/avatar1.png",
    "wilksScore": null
}
```

---

### Wilksservice

| Method | Endpoint           | Description           |
| ------ | ------------------ | --------------------- |
| POST   | `/wilks/calculate` | Calculate Wilks score |

**Sample Request:**

```json
{
  "bodyWeight": 80.0,
  "totalLifted": 250.0,
  "gender": "MALE"
}
```

**Sample Response:**

```json
{
  "wilksScore": 312.45
}
```

---

## Features

* User registration and authentication with Spring Security
* Profile management (CRUD)
* Wilks score calculation microservice
* PostgreSQL database integration
* Dockerized services for easy deployment
* REST API communication between services

---

## Project Status

* Fully functional microservices-based application
* REST APIs tested via Postman
* Ready for deployment on any system with Docker and Maven installed
