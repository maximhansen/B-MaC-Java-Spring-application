# The Bakery (B-MaC) - Software Architecture

## Project Overview

The Bakery (B-MaC) is a project aimed at helping a large bakery concern manage and automate its supply chain management and customer communication. The bakery delivers quality cakes, donuts, and cookies to other bakeries and retail customers through an online ordering platform.

## Problem Statement

B-MaC has ambitious goals and has requested a system that allows for scalable architecture to evolve over time. The project involves different user profiles, including loyal customers, bakery staff, and a new customer building a webshop.

## Users

1. **Dabbing Doughnut (Customer):**
    - Regular orders, loyalty points, and discounts.
    - Integration with the REST API, no user interface provided.

2. **Barry (Head Donut Baker):**
    - Manages donut recipes and oversees order cutoff times.
    - Creates outgoing orders to the warehouse.

3. **Willow (Warehouse Manager):**
    - Handles incoming orders for ingredients.
    - Manages warehouse stock, including varied sections for different ingredients.

4. **Fruit Cake Inc. (Webshop Customer):**
    - Specialized in fruit cakes, integrating with B-MaC's API.

## Systems

### Clients API (API endpoints)

Functionalities include user account management, order creation, confirmation, cancellation, loyalty points, and more.

### Warehouse API (API endpoints)

Manages warehouse operations, including ingredient stock, outgoing orders, and deliveries.

### Bakery (Web Application)

Web application for bakery staff to create and manage products, recipes, and baking processes.

## User Stories

A detailed list of user stories is provided in a CSV file. These stories cover various aspects of user interactions and system functionalities.\
[👉 Click here to see client user stores.](./user-stories/Product_Backlog-Clients.csv)\
[👉 Click here to see warehouse user stores.](./user-stories/Product_Backlog-Warehouse.csv)\
[👉 Click here to see bakery user stores.](./user-stories/Product_Backlog-Bakery.csv)

## Testing

Automated tests, including unit tests for price calculations and loyalty levels, are implemented. Targeting a code line coverage of at least 60% for the Clients application.\
[👉 Click here to see client tests - extensive.](./client/src/test)\
[👉 Click here to see warehouse tests.](./warehouse/src/test)\
[👉 Click here to see bakery tests.](./bakery/src/test)

## Configuration and Logging

Configurations include infrastructure connections, databases, queues, IAM, and IDP configurations. Logging statements are implemented for diagnosis and error handling.

## Technical Specifications

- Developed using Spring Boot (Java) and Gradle.
- Postgres database for each microservice with separate schemas (Clients, Bakery, Warehouse).
- CloudAMQP messaging for queues.
- Keycloak for IAM and IDP.

## Infrastructure (Docker-Compose)

A Docker-Compose file is provided for setting up the development environment, including Postgres, RabbitMQ, IAM, and IDP services.

[👉 Click here to see the docker compose-file.](./docker/docker-compose-file.yml)

## Unique loyalty system

I've designed the loyalty system for maximum flexibility. You can easily tweak the points calculator and loyalty levels in both the application code and the properties file. This way, adapting to changing business needs is a breeze, giving us the agility to adjust without extensive code changes.

[👉 Click here to see the client properties-file.](./client/src/main/resources/application.properties)