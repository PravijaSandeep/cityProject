# Share Market Advisor Java Project

Share Market Advisor Project is a web-based application designed to provide users with share market advice. Leveraging Google's Cloud technology, this application allows users to access expert share market advice securely and efficiently.

## Overview

Upon successful login, users can specify their preferences by selecting the types of shares they are interested in monitoring and investing in. Users have access to comprehensive advice related to their chosen shares and can modify their preferences at any time. Removing a preferred share stops advice for that share, while adding a new share begins receiving relevant advice. This dynamic approach tailors the user experience to their evolving interests and investment strategies.

## Setup

### Tools and Platform

- **Spring Tool Suite (STS):** Integrated Development Environment (IDE) for Spring-based Java applications.
- **MySQL Server and MySQL Workbench:** Database and visual design tool for managing MySQL databases.
- **Spring Boot:** Framework simplifying the creation of Spring-based applications.
- **Java 17:** Programming language and platform for various applications.
- **CSS (Cascading Style Sheets):** Style sheet language for web document presentation.
- **HTML (HyperText Markup Language):** Markup language for creating web page structure.
- **Thymeleaf:** Server-side Java template engine for web and standalone environments.
- **JavaScript:** Programming language for interactive and dynamic web page content.

### Cloud Services

- **Google Authenticator:** OAuth 2.0-based authentication for secure user login.
- **Google Cloud App Engine:** Deployment and scalability with PaaS capabilities.
- **Google Cloud Pub/Sub:** Messaging service for asynchronous messaging and real-time communication.
- **Google Cloud SQL:** Relational Database Management using MySQL for storing user details, share information, and subscriptions.

### Dependencies

The sample `pom.xml` file includes:

- Spring Boot Starter Web
- Spring Boot Starter OAuth2 Client
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Data JPA
- MySQL Connector Java
- Lombok
- Spring Cloud GCP Pub/Sub Starter
- Spring Cloud GCP Dependencies
- Spring Cloud GCP Starter SQL MySQL

## Deployment

### Deploy the app to App Engine

1. **Initialize using gcloud:** Choose the account and project: `gcloud init`.
2. **Navigate to the project folder:** Include necessary plugins in `pom.xml`. Deploy the project to App Engine: `gcloud app deploy`.
3. **Retrieve application URL:** Use `gcloud app browse` to get the application URL.
4. **View logs:** Use `gcloud app logs tail -s default` to see logs on the terminal.

### Cloud SQL Database

- Open Cloud Shell and choose the project using `gcloud init`.
- Connect to the database instance using `gcloud sql connect <db-instance-name> --user=root`.
- Use commands such as `show databases`, `use <preferedb>`, `show tables`, `show columns from <table>` to manage and view databases and tables.
