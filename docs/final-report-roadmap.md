# Final Report Roadmap

This file tracks the gap between the assignment requirements, the current Word report, and the current implementation in this workspace.

## 1. Assignment Requirements

- Submit one final PDF.
- Page 1 must be a cover page with course, project title, student name, student ID, supervisor, and date.
- Page 2 must contain:
  - Public GitHub repository link
  - Public video link
- Remaining pages must contain formal project documentation.
- The application must:
  - implement all microservices
  - run successfully
  - be pushed to a public GitHub monorepo
  - containerize at least one microservice in Docker Desktop
- The final video must cover:
  - introduction
  - report walkthrough
  - implementation walkthrough
  - Docker deployment
  - optional CI/CD
  - gained knowledge and skills

## 2. Current Report Status

### Already good

- The cover page layout is clean and suitable for submission.
- The document structure is formal and organized.
- The report content matches the implemented academic scope better than an over-claimed enterprise architecture report would.
- The report already distinguishes implemented scope from future work.

### Still incomplete

- The submission date is still blank.
- The GitHub repository link is still a placeholder.
- The public video link is still a placeholder.
- Figure areas still contain placeholder text such as "Insert a screenshot...".
- The document currently reads as a near-final template, not a finalized evidence-based report.
- The Docker section should reflect the exact command and credentials that succeed in practice.
- The report should not claim any public GitHub status unless the repository is actually published.
- The report should not claim CI/CD unless it is actually implemented and verified.

## 3. What The Codebase Currently Supports

### Confirmed from the workspace

- Six Spring Boot microservices exist:
  - `customer-service`
  - `ordering-service`
  - `menu-service`
  - `payment-service`
  - `kitchen-service`
  - `delivery-service`
- The configured ports are:
  - `9001` customer-service
  - `9002` ordering-service
  - `9003` menu-service
  - `9004` payment-service
  - `9005` kitchen-service
  - `9006` delivery-service
- `customer-service` exposes GraphQL and gRPC support.
- `ordering-service` has REST, GraphQL-backed, and gRPC-backed order creation endpoints.
- `customer-service` and `ordering-service` both have Dockerfiles.
- Root `docker-compose.yml` currently starts:
  - MySQL
  - customer-service

### Important reality checks

- This workspace is not currently a Git repository, so the public GitHub link cannot be filled from here yet.
- The current report mentions Docker support for at least one microservice, which is true from the files present, but must still be verified by successful execution before final submission.
- The report should describe GraphQL and gRPC as implemented extras, not as the main tested workflow unless those flows are also tested and captured.

## 4. Evidence We Need Before Finalizing The Report

- Screenshot of the repository structure showing the six services.
- Screenshot of the six MySQL databases or schemas.
- Screenshot of `GET /api/customers`.
- Screenshot of `GET /api/menu/items`.
- Screenshot of successful order creation from `POST /api/orders`.
- Screenshot of kitchen status update to `READY`.
- Screenshot of delivery status update.
- Screenshot of final order status from `GET /api/orders/{id}`.
- Screenshot of Docker Desktop showing a running container.
- Final public GitHub repository link.
- Final public video link.

## 5. Recommended Work Order

1. Verify runtime prerequisites:
   - Java
   - MySQL or Docker MySQL
   - Docker Desktop
2. Run the services and confirm the local workflow.
3. Capture the required screenshots as we verify each step.
4. Replace placeholders inside the Word report with real links, dates, and screenshots.
5. Re-render and visually inspect the updated DOCX.
6. Export the final PDF only after all evidence is real and all links are public.
7. After practical verification is complete, prepare the video speaking script.

## 6. Current Guidance For The Report Text

- Keep the implemented workflow centered on REST because it is the clearest verified path.
- Keep GraphQL and gRPC as additional implemented communication options.
- Keep Kafka, API Gateway, Consul, Kubernetes, and CI/CD under future work unless they are later implemented and tested.
- Avoid writing any sentence that implies something is deployed publicly unless we verify it.
