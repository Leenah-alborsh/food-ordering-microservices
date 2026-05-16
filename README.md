# Food Ordering Microservices

Spring Boot microservices project for the Advanced Software Engineering running project. The system models a food ordering workflow across customer, menu, ordering, payment, kitchen, and delivery services using a public monorepo structure and MySQL.

## Overview

This project demonstrates a practical microservices-based food ordering application where each service owns its own responsibility and database. The main implemented workflow is:

1. A customer exists in `customer-service`
2. A menu item exists in `menu-service`
3. `ordering-service` validates both records
4. `payment-service` processes the payment
5. `kitchen-service` creates a preparation task
6. `delivery-service` completes the final delivery stage
7. `ordering-service` reflects the final lifecycle status

## Implemented Services

| Service | Port | Database | Main Responsibility |
|---|---:|---|---|
| Customer Service | `9001` | `customer_service_db` | Customer profiles and lookup |
| Ordering Service | `9002` | `ordering_service_db` | Order creation and workflow coordination |
| Menu Service | `9003` | `menu_service_db` | Menu items, prices, and availability |
| Payment Service | `9004` | `payment_service_db` | Payment processing simulation |
| Kitchen Service | `9005` | `kitchen_service_db` | Preparation task management |
| Delivery Service | `9006` | `delivery_service_db` | Delivery tracking and status updates |

## Communication

### Main implemented workflow

- `ordering-service` -> `customer-service` via REST
- `ordering-service` -> `menu-service` via REST
- `ordering-service` -> `payment-service` via REST
- `ordering-service` -> `kitchen-service` via REST
- `ordering-service` -> `delivery-service` via REST

### Additional implemented options

- GraphQL customer lookup between `ordering-service` and `customer-service`
- gRPC customer lookup between `ordering-service` and `customer-service`

## Key Endpoints

### Customer Service

- `GET /api/customers`
- `GET /api/customers/{id}`
- `POST /api/customers`
- `POST /graphql`

### Menu Service

- `GET /api/menu/items`
- `GET /api/menu/items/{id}`
- `POST /api/menu/items`

### Ordering Service

- `GET /api/orders`
- `GET /api/orders/{id}`
- `POST /api/orders`
- `POST /api/orders/graphql`
- `POST /api/orders/grpc`

### Payment Service

- `POST /api/payments/process`
- `GET /api/payments`
- `GET /api/payments/{id}`
- `GET /api/payments/order/{orderId}`

### Kitchen Service

- `POST /api/kitchen/orders`
- `GET /api/kitchen/orders`
- `GET /api/kitchen/orders/{id}`
- `GET /api/kitchen/orders/order/{orderId}`
- `PUT /api/kitchen/orders/{id}/status`

### Delivery Service

- `POST /api/deliveries`
- `GET /api/deliveries`
- `GET /api/deliveries/{id}`
- `GET /api/deliveries/order/{orderId}`
- `PUT /api/deliveries/{id}/status`

## Project Structure

```text
food-ordering-microservices/
|-- customer-service/
|-- ordering-service/
|-- menu-service/
|-- payment-service/
|-- kitchen-service/
|-- delivery-service/
|-- docker-compose.yml
`-- README.md
```

## Local Setup

### Requirements

- Java 17
- MySQL running on `localhost:3306`
- Docker Desktop for the Docker requirement

### Databases

Each service uses its own database:

- `customer_service_db`
- `ordering_service_db`
- `menu_service_db`
- `payment_service_db`
- `kitchen_service_db`
- `delivery_service_db`

### Run the services

Start each microservice from its own folder using `bootRun`. The services should become available on ports `9001` to `9006`.

Example:

```powershell
cd customer-service
.\gradlew.bat bootRun
```

Repeat the same pattern for:

- `menu-service`
- `payment-service`
- `kitchen-service`
- `delivery-service`
- `ordering-service`

## Verification

After startup, the following URLs should respond locally:

- `http://localhost:9001/api/customers`
- `http://localhost:9002/api/orders`
- `http://localhost:9003/api/menu/items`
- `http://localhost:9004/api/payments`
- `http://localhost:9005/api/kitchen/orders`
- `http://localhost:9006/api/deliveries`

### GraphQL

- Endpoint: `http://localhost:9001/graphql`
- GraphiQL UI: `http://localhost:9001/graphiql`

Sample query:

```json
{
  "query": "query { customerById(id: 1) { id name email address } }"
}
```

### Main tested order flow

The main tested workflow in this project is:

1. Verify existing customers
2. Verify existing menu items
3. Create an order through `POST /api/orders`
4. Update kitchen status to `READY`
5. Update delivery status to `OUT_FOR_DELIVERY`
6. Update delivery status to `DELIVERED`
7. Verify the final order status through `GET /api/orders/{id}`

## Docker

The Docker requirement is satisfied by containerizing `customer-service`.

### Build

```powershell
cd customer-service
docker build -t customer-service .
```

### Run

```powershell
docker run --name customer-service-docker -p 9001:9001 -e CUSTOMER_DB_URL="jdbc:mysql://host.docker.internal:3306/customer_service_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" -e CUSTOMER_DB_USERNAME="root" -e CUSTOMER_DB_PASSWORD="" customer-service
```

After the container starts successfully, verify:

- `http://localhost:9001/api/customers`

## Implemented Scope

- Six Spring Boot microservices in one monorepo
- MySQL persistence with separate databases per service
- REST-based core workflow
- GraphQL and gRPC as additional communication options
- Docker support for at least one microservice

## Future Work

- CI/CD with GitHub Actions and Docker Hub
- Kafka-based event-driven workflow
- API Gateway
- Service discovery
- Kubernetes deployment

## Academic Note

This repository represents the final running project implementation. The documented workflow and scope focus on the features that were implemented and verified locally.
