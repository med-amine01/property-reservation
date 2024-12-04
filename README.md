# Property Reservation Platform (Technical Test)

This is a full-stack application with a **Spring Boot backend** and an **Angular frontend**. The backend provides a REST API, and the frontend is served using Nginx.

## Prerequisites

- **Docker** and **Docker Compose** must be installed on your machine.
- If running **locally**, you'll need:
    - **Java 17** for the backend.
    - **Angular 17** and **Node.js 18** for the frontend.

## How to Run (Using Docker)

To launch both the backend and frontend, run the following command:

```bash
docker-compose up -d
```

This command will build and start both the backend and frontend containers.

### URLs

#### Backend (Swagger UI)
The Swagger UI for the backend is available at:

```
http://localhost:8099/swagger-ui/index.html
```

#### Frontend
The frontend is available at:

```
http://localhost:4200
```

## How to Run Locally (Without Docker)

1. **Clone the repositories**:

    - Backend: [https://github.com/med-amine01/property-reservation](https://github.com/med-amine01/property-reservation)
    - Frontend: [https://github.com/med-amine01/propease-platform](https://github.com/med-amine01/propease-platform)

2. **Backend Setup**:
    - Ensure you have **Java 17** installed.
    - Navigate to the backend directory:
      ```bash
      cd property-reservation
      ```
    - Run the application:
      ```bash
      ./mvnw spring-boot:run
      ```
    - The backend will be available at [http://localhost:8099](http://localhost:8099).

3. **Frontend Setup**:
    - Ensure you have **Node.js 18** and **Angular 17** installed.
    - Navigate to the frontend directory:
      ```bash
      cd propease-platform
      ```
    - Install dependencies:
      ```bash
      npm install
      ```
    - Run the development server:
      ```bash
      ng serve
      ```
    - The frontend will be available at [http://localhost:4200](http://localhost:4200).

## Stopping the Services (Docker)

To stop and remove the containers, run:

```bash
docker-compose down
```

---

For any questions or issues, feel free to reach out at **m.chebbi.tech@gmail.com**! 🚀

---
