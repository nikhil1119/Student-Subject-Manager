# Student and Subject Management System

## Description

This project is a basic Spring Boot application that demonstrates the use of Spring MVC, Spring Security, and Spring Data JPA. The application manages students and subjects with a many-to-many relationship between them. The project includes a REST API for creating students, getting a list of all students, and getting a list of all subjects. It also uses JWT for authentication and authorization with two roles: student and admin.

## Technologies Used

- Java version 17
- Spring Boot 3.2.5
- Spring MVC
- Spring Security 6
- Spring Data JPA
- JWT (jjwt-api, jjwt-impl, jjwt-jackson)
- H2 In-Memory Database

## Features

- Register user(admin is coming soon)
- Create a student(for admin only)
- Create subject(for admin only)
- Get a list of all students
- Get a list of all subjects
- JWT-based authentication and authorization
- Role-based access control for student and admin

## API Endpoints

### User Endpoints()
#### (First create user and sign in before accessing any api endpoint as they require authorization)

- **Signup**
    - **URL:** `/signup`
    - **Method:** POST
    - **Request Body:**
      ```json
      {
        "username": "your_username",
        "password": "your_password",
        "role": "STUDENT" //optional (default)
      }
      ```

    - **Response:**
      ```json
      {
        "message": "User registered successfully",
        "status": true
      }
      ```

  - As of now User can only register for student role. Admin role registration is coming soon.


- **Login (Obtain JWT Token)**
    - **URL:** `/signin`
    - **Method:** POST
    - **Request Body:**
      ```json
      {
        "username": "your_username",
        "password": "your_password"
      }
      ```
      
      - for admin
      ```json
      {
      "username": "admin",
      "password": "adminPass"
      }
      ```

      - preregistered student (if you don't want to create one)
      ```json
      {
      "username": "student1",
      "password": "password1"
      }
      ```
      
    - **Response:**
      ```json
      {
        "jwtToken": "<JWT_TOKEN>",
        "username": "your_username",
        "roles": ["ROLE_STUDENT/ROLE_ADMIN"] //default role
      }
      ```

- **Logout**
    - **URL:** `/signout`
    - **Method:** POST
    - **Request Body:**
      Authorization token: Bearer <JWT_TOKEN>
      

### Student Endpoints

- **Create a Student (Admin only)**
    - **URL:** `/api/students/create`
    - **Method:** POST
    - **Headers:**
        - `Authorization: Bearer <JWT_TOKEN>`
    - **Request Body:**
      ```json
      {
        "name": "Jane Doe",
        "address": "456 Elm St",
        "subjects": [
          {"name": "English"},
          {"name": "History"}
        ]
      }
      ```
    - **Response:**
      ```json
      {
        "id": 1,
        "name": "Jane Doe",
        "address": "456 Elm St",
        "subjects": [
          {"id": 1, "name": "English"},
          {"id": 2, "name": "History"}
        ]
      }
      ```

- **Get List of All Students (Admin only)**
    - **URL:** `/api/students/get-all`
    - **Method:** GET
    - **Headers:**
        - `Authorization: Bearer <JWT_TOKEN>`
    - **Response:**
      ```json
      [
        {
          "id": 1,
          "name": "Jane Doe",
          "address": "456 Elm St",
          "subjects": [
            {"id": 1, "name": "English"},
            {"id": 2, "name": "History"}
          ]
        },
        ...
      ]
      ```

### Subject Endpoints

- **Get List of All Subjects (Student and Admin)**
    - **URL:** `/api/subjects/get-all`
    - **Method:** GET
    - **Headers:**
        - `Authorization: Bearer <JWT_TOKEN>`
    - **Response:**
      ```json
      [
        {"id": 1, "name": "English"},
        {"id": 2, "name": "History"},
        ...
      ]
      ```

- **Create a Subject (Admin only)**
    - **URL:** `/api/subjects/create`
    - **Method:** POST
    - **Headers:**
        - `Authorization: Bearer <JWT_TOKEN>`
    - **Request Body:**
      ```json
      {
        "name": "Physics"
      }
      ```
    - **Response:**
      ```json
      {
        "id": 3,
        "name": "Physics"
      }
      ```

## Security

### Roles and Permissions

- **Student Role**
    - Can view the list of subjects

- **Admin Role**
    - Can create a student
    - Can view the list of subjects
    - Can view the list of students

### JWT Authentication

- JWT is used for securing the API endpoints.
- Users can log in with their credentials to obtain a JWT token, which should be included in the `Authorization` header of subsequent requests.
- Select `Bearer Token` in `Auth Type`

## Database Configuration

- The project uses an H2 in-memory database for simplicity.
- The `schema.sql` file is used to initialize the database schema.

## Setup and Running the Application

1. **Clone the repository:**
   ```js
   git clone <repository_url>
   cd <repository_directory>
   ```

2. **Build Command**
    ```js
    ./mvnw clean install
   ```

3. **Run Command**
    ```js
    ./mvnw spring-boot:run
   ```
   
- You can use IDE (preferably IntelliJ IDEA) to run the application if you have one installed already (easy to run) 
   
