**AlexAuto - Car Selling Platform**

**AlexAuto** is a comprehensive, full-stack platform for managing a car dealership's inventory. It features a robust RESTful API backend built with Spring Boot and a modern, interactive frontend powered by Next.js and React. The platform provides advanced features for searching, filtering, and managing car data, including image uploads, all powered by a containerized Microsoft SQL Server database.

**FEATURES**:
1. Full CRUD Operations: Create, Read, Update, and Delete car records.
2.Dynamic Search and Filtering: A powerful /car/search endpoint that allows for combining multiple filter criteria (make, model, year range, price, kilometers, etc.) to find specific vehicles.
3.Pagination and Sorting: All list-based endpoints are paginated and sortable to handle large datasets efficiently.
4.Image Uploads: Link car objects to images by uploading files directly to the server.
5.Input Validation: Ensures data integrity by validating all incoming data against predefined rules.
6.Centralized Error Handling: Provides clean, consistent JSON error responses for invalid requests.
7.Dual-Profile Configuration: Supports both a persistent database (db profile) and an in-memory (json profile) setup for flexible development and testing.

**TECH STACK**:
### **Backend**
-   **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot)
-   **Language:** Java 17+
-   **Core Libraries:** Spring Data JPA (Hibernate), Spring Web, Spring Boot Starter Validation
-   **Build Tool:** [Maven](https://maven.apache.org/)

### **Frontend**
-   **Framework:** [Next.js](https://nextjs.org/) (a React framework for production)
-   **Language:** JavaScript
-   **Styling:** [Tailwind CSS](https://tailwindcss.com/) (A utility-first CSS framework)

