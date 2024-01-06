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

## Cloud Deployment

### Cloud SQL Database
 **Open google console and set up the project**
  - Enable Cloud SQL API
  - Create a Cloud SQL Instance with MySQL as the database engine
  - Once the instance is created, create the database.
  - Modify the application.properties to point to the created database and connection. 
   - spring.cloud.gcp.sql.database-name=<DB_NAME>
   - spring.cloud.gcp.sql.instance-connection-name=<Connection URL. Eg : stock-advisor-408720:us-central1:sa-db>

### Cloud PubSub Set up

 **Access to Google Cloud Console and choose the project created to enable pubsub**
   - Enable the Pub/Sub API for that project.
   - Create a service account. 
   - Grant Role as Pub/Sub Admin, Pub/Sub Publisher and Pub/Sub Subscribers.
  
### Deploy the app to App Engine

1. **Enable App Engine in Google Cloud Console for the project created**
2. **Go to App Engine and Select Create Application**
3. **Once the App Engine application is created, deploy the application from local system suing following steps**
     - Install google cloud sdk on local system**
     - Open terminal and set up project to deploy**
     - Initialize using gcloud:** Choose the account and project: `gcloud init`.
     - Navigate to the project folder:** Include necessary plugins in `pom.xml`. Deploy the project to App Engine: `gcloud app deploy`.
     - Retrieve application URL:** Use `gcloud app browse` to get the application URL.
     - Use `gcloud app logs tail -s default` to see logs on the terminal.
