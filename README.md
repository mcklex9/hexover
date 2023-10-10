# Hexagon Architecture: An Example Implementation
This repository demonstrates an example implementation of the Hexagonal (Ports and Adapters) Architecture. The application showcases the creation of an account along with a two-step asynchronous verification. It employs MongoDB, capitalizing on its optimistic locking feature, given the expected low operation volume.

### Architectural Layers
The application is structured into the following layers:
* Domain Layer: This layer embodies business logic and rules. It's where the tactical Domain-Driven Design (DDD) objects reside.
* Application Layer (Use Cases): Orchestration of the Domain layer happens here. This layer defines ports that determine its inputs and outputs. A facade, acting as a single point of entry for all incoming requests, is also present here.
* Adapters Layer: Here you'll find implementations of the ports defined in the application layer.
### Testing Strategy
Efforts have been made to ensure that tests run swiftly:
* Domain: Unit tests primarily focusing on covering the business logic.
* Application: Tests use Gherkin scenarios, ensuring all use cases are addressed. The facade serves as the entry point for each test case. In-memory implementations of repositories and services make these tests fast.
* Adapters: This is where integration tests reside. Test containers validate the real database integration. The tests have been optimized to load only essential contexts, enhancing their speed.
* Architecture: ArchUnit tests ensure adherence to layer visibility rules.
* Pact tests (pending): The exposed API should undergo contract tests, ideally using Pact tests.

### What's Missing?
Some features and components not covered in this example include:
* Kafka: For asynchronous verification. Integration tests for Kafka would leverage Testcontainers.
* Pact Tests: Yet to be implemented.
* Security: Security measures haven't been addressed.
* DDD Objects: The current implementation includes a basic Aggregate. There's potential to introduce more Entities, Value Objects, Policies, and more.
* Domain Events & Notifications: Each operation should trigger an event. To ensure proper event handling, operations should be idempotent. Additionally, event management should utilize Transactional Inbox and Outbox patterns.

### Getting Started
To run the application, you need Docker:
1. Navigate to the `/docker` directory.
2. Execute the command `docker-compose -d mongo`.