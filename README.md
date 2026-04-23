# Food Ordering Microservices

A Spring Boot microservices project for a food ordering system. The project demonstrates service collaboration through three communication approaches:

- REST
- GraphQL
- gRPC

The system currently includes two working microservices:

- `customer-service`
- `ordering-service`

## Features

- Customer data retrieval from MySQL
- Order creation and persistence in MySQL
- Inter-service communication using REST
- Inter-service communication using GraphQL
- Inter-service communication using gRPC
- Gradle-based setup using Groovy DSL

## Project Structure

```text
food-ordering-system/
|-- customer-service/
|-- ordering-service/
`-- README.md
```

## Services

### Customer Service

- Port: `9001`
- gRPC Port: `9091`
- Database: `customer_service_db`
- Endpoints:
  - `GET /api/customers/{id}`
  - `POST /graphql`

### Ordering Service

- Port: `9002`
- Database: `ordering_service_db`
- Endpoints:
  - `POST /api/orders`
  - `POST /api/orders/graphql`
  - `POST /api/orders/grpc`

## Requirements

- Java 17
- MySQL running on `localhost:3306`
- Gradle Wrapper (already included)

## Running the Project

Open two terminals.

### Run customer-service

```powershell
cd customer-service
$env:GRADLE_USER_HOME = Join-Path (Get-Location) '.gradle-user-home'
.\gradlew.bat bootRun --no-daemon --max-workers=2
```

### Run ordering-service

```powershell
cd ordering-service
$env:GRADLE_USER_HOME = Join-Path (Get-Location) '.gradle-user-home'
.\gradlew.bat bootRun --no-daemon --max-workers=2
```

## Example Requests

### Customer Service REST

```powershell
Invoke-RestMethod http://localhost:9001/api/customers/1
```

### Customer Service GraphQL

```powershell
$body = @{
  query = 'query($id: ID!) { customerById(id: $id) { id name email address } }'
  variables = @{ id = '1' }
} | ConvertTo-Json -Depth 5

Invoke-RestMethod http://localhost:9001/graphql -Method Post -ContentType 'application/json' -Body $body
```

### Ordering Service REST

```powershell
$body = @{ customerId = 1; productName = 'Burger'; quantity = 2 } | ConvertTo-Json
Invoke-RestMethod http://localhost:9002/api/orders -Method Post -ContentType 'application/json' -Body $body
```

### Ordering Service GraphQL-backed Flow

```powershell
$body = @{ customerId = 1; productName = 'Burger'; quantity = 2 } | ConvertTo-Json
Invoke-RestMethod http://localhost:9002/api/orders/graphql -Method Post -ContentType 'application/json' -Body $body
```

### Ordering Service gRPC-backed Flow

```powershell
$body = @{ customerId = 1; productName = 'Burger'; quantity = 2 } | ConvertTo-Json
Invoke-RestMethod http://localhost:9002/api/orders/grpc -Method Post -ContentType 'application/json' -Body $body
```

## Repository Description

Suggested GitHub description:

`Spring Boot food ordering microservices with REST, GraphQL, and gRPC communication.`
