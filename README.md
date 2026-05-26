# BFHL Challenge REST API

An enterprise-grade, high-performance Spring Boot REST API built to handle tokens, classifications, arithmetic operations, and custom string mutations for the **BFHL challenge**.

---

## 🚀 Project Overview

This service parses mixed token arrays containing letters, integers, and special characters. It performs:
- **Odd/Even Parity Classification**: Efficient and overflow-safe numeric categorizations using mathematical algorithms.
- **Alphabetic Extraction & Case Standardization**: Converting pure letters into uppercase.
- **Special Character Isolation**: Separating punctuation/non-alphanumeric elements.
- **Large-Number Summation**: Safely summing numbers using arbitrary-precision arithmetic (`BigInteger`) to guarantee zero overflow.
- **Reversed Alternating-Caps Concatenation**: A custom string builder implementing character reversal and index-based alternating caps (e.g. `EoDdCbAa`).
- **Dynamic Config**: Fully configurable user profiles (`user_id`, `email`, `roll_number`) customizable at runtime.
- **Robust Exception Framework**: Custom handlers trapping validation, JSON parsing, and general server exceptions to ensure a uniform response interface.

---

## 🛠️ Tech Stack & Architecture

- **Java 17** & **Spring Boot 3.2.5**
- **Maven** (Dependency and build management)
- **Lombok** (Boilerplate reduction)
- **JUnit 5** & **Mockito** (Testing suite)
- **SLF4J** & **Logback** (Structured console logging)
- **JSR-380 validation** (Inputs safety)

### Design & Package Architecture
The codebase strictly adheres to **SOLID principles**, **Clean Architecture**, and the **DTO design pattern**. 

```
src/main/java/com/bajaj/bfhl/
 ├── config/
 │    └── CorsConfig.java           <-- CORS security mapping for frontend calls
 ├── controller/
 │    └── BfhlController.java       <-- RestController exposing POST /bfhl
 ├── dto/
 │    ├── BfhlRequest.java          <-- Input validation DTO mapping 'data'
 │    ├── BfhlResponse.java         <-- Success payload with snake_case JSON mappings
 │    └── BfhlErrorResponse.java    <-- Unified error response DTO mapping is_success: false
 ├── exception/
 │    ├── BfhlException.java        <-- Specialized application exception
 │    └── GlobalExceptionHandler.java<-- ControllerAdvice trapping and mapping errors
 ├── service/
 │    ├── BfhlService.java          <-- Business layer interface (resolves reserved keyword conflicts)
 │    └── impl/
 │         └── BfhlServiceImpl.java  <-- Injectable service logic using @Value config binders
 ├── util/
 │    └── BfhlUtility.java          <-- High-performance mathematical/string algorithms
 └── BfhlApplication.java           <-- Main bootstrapper class
```

---

## 📡 API Specifications

### 1. Process Mixed Tokens (POST `/bfhl`)

Processes and classifies token inputs.

- **URL**: `/bfhl`
- **Method**: `POST`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
  ```json
  {
    "data": ["a", "1", "334", "4", "R", "$"]
  }
  ```

- **Response Body (200 OK)**:
  ```json
  {
    "is_success": true,
    "user_id": "akshara_shukla_23072003",
    "email": "aksharashukla2307171@acropolis.in",
    "roll_number": "0827CS231025",
    "odd_numbers": ["1"],
    "even_numbers": ["334", "4"],
    "alphabets": ["A", "R"],
    "special_characters": ["$"],
    "sum": "339",
    "concat_string": "Ra"
  }
  ```

---

### 2. Error Contract Example (400 Bad Request)

Returned for invalid inputs, missing fields, or malformed JSON payloads.

- **URL**: `/bfhl`
- **Method**: `POST`
- **Request Body (Invalid)**: `{ "data": null }`
- **Response Body (400 Bad Request)**:
  ```json
  {
    "is_success": false,
    "error_message": "Validation failed: data: The 'data' field is required and cannot be null"
  }
  ```

---

## 💻 Local Setup & Execution

### Prerequisites
- **Java JDK 17** or higher
- **Apache Maven 3.6+**

### Step 1: Clone and Configure
Navigate to the directory and edit your profile parameters in `src/main/resources/application.yml` (optional):
```yaml
bfhl:
  user:
    full-name: your_full_name
    dob: "ddmmyyyy"
    email: your_email@example.com
    roll-number: YOUR_ROLL_NUMBER
```

### Step 2: Compile and Build Package
Run the following Maven clean-install command to verify tests and build the production-ready runnable fat-JAR:
```bash
mvn clean package
```

### Step 3: Run Locally
Start the Spring Boot dev-server:
```bash
mvn spring-boot:run
```
Alternatively, execute the compiled JAR directly:
```bash
java -jar target/bfhl-1.0.0.jar
```
The server will boot up locally at: **`http://localhost:8080`**

---

## 🧪 Testing Suite

Automated verification is built into the application. We maintain a high branch coverage of both controller mappings and core business utilities.

Execute the following command to run all service tests, mock controller integration tests, validation tests, and edge case assertions:
```bash
mvn test
```

Tested scenarios include:
1. Mixed valid inputs matching rules.
2. Large numbers (safe from integer boundary overflows).
3. Negative signed integers.
4. Purely alphabetic character sequences.
5. Punctuation and special character lists.
6. Empty arrays and null field validations.
7. Alternating caps algorithm validations (e.g. `["A", "ABCD", "DOE"]` producing `EoDdCbAa`).

---

## ☁️ Deployment Instructions

The application is cloud-native and pre-configured for **Railway** or **Render**.

### Key Deployment Configurations:
1. **Dynamic Port Allocation**: In `application.yml`, the server port is configured as `server.port: ${PORT:8080}`. Cloud environments inject a random port under the `PORT` environment variable which Spring Boot binds seamlessly.
2. **Procfile Verification**: The root directory contains a `Procfile` instructing hosting containers how to launch the web process.
   ```
   web: java -Dserver.port=$PORT -jar target/bfhl-1.0.0.jar
   ```

### Deploying to Render
1. Register/Login on [Render](https://render.com).
2. Connect your Git repository.
3. Select **Create Web Service**.
4. Configure standard settings:
   - **Environment**: `Docker` or `Java` (Select `Java` and choose Java 17).
   - **Build Command**: `mvn clean package -DskipTests` (or run tests by excluding `-DskipTests`).
   - **Start Command**: `java -Dserver.port=$PORT -jar target/bfhl-1.0.0.jar`
5. Click **Deploy Web Service**.

### Deploying to Railway
1. Register/Login on [Railway.app](https://railway.app).
2. Click **New Project** -> **Deploy from GitHub repo**.
3. Choose the repository.
4. Railway will automatically detect Maven, build it via Buildpacks, and bind the `Procfile` command securely.

---

## 🧪 Live Manual Verification

### 1. Curl Verification (Post Request)
```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```
