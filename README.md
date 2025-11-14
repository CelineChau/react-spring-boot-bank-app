# Fast & Reckless Bank

A simple in-memory banking application developed with **Spring Boot** (backend) and **React + Tailwind** (frontend).  
The app allows you to create accounts, deposit, withdraw, and transfer money. All data is stored **in-memory** for simplicity.

---

## Requirements

- macOS
- Homebrew
- Java JDK 17 or higher
- Maven
- Node.js + Yarn

---

## Backend Setup

1. Clone the project and navigate to the backend folder:

```bash
cd bankserver
```

2. Build and run the Spring Boot server:
```bash
mvn clean spring-boot:run
```
- Backend runs on: http://localhost:8081
- The frontend expects the backend API at: http://localhost:8081

## Frontend Setup

1. Navigate to the frontend folder:

```bash
cd frontend
```

2. Install dependencies:
```bash
yarn install
```

3. Start the app:
```bash
yarn start
```
- Open your browser at: http://localhost:5173


## What next ?

Possible impovements :
- [api] Error handling, with personnalized status and message
- [api] Improve the safetiness of the critical operation
- [front] Add possibility to click on each account and display the last 50 transactions in a modal for example
- [front] Use a react state management (redux, zustand..) to clean code architecture. I didn't do it because not needed for a simple app 


