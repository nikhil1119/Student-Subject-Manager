# Student and Subject Management System

## Description

This is a **Spring Boot application** designed to manage students and subjects with a **many-to-many relationship**. It showcases the use of **Spring MVC**, **Spring Security**, and **Spring Data JPA**. The project includes a REST API for creating students and subjects, viewing lists of students and subjects, and using **JWT** for authentication and authorization. The system supports **role-based access control**, with two roles: **student** and **admin**.

---

## Features

- **User Authentication**:
  - Signup and Login (JWT-based).
  - Secure logout.
- **Role-Based Access Control**:
  - **Student Role**: Can view the list of subjects.
  - **Admin Role**: Can create students and subjects, and view all students.
- **API Functionality**:
  - Add new students and subjects (Admin-only).
  - View all students and subjects.
- **Secure REST APIs** with JWT and role-based permissions.

---

## Technologies Used

- **Backend**: Java 17, Spring Boot 3.2.5
- **Frameworks**: Spring MVC, Spring Security 6, Spring Data JPA
- **Database**: H2 In-Memory Database
- **Authentication**: JWT (jjwt-api, jjwt-impl, jjwt-jackson)

---

## API Endpoints

### **User Endpoints**

1. **Signup** (`/signup`): Register a new user (default role: Student).
    - **Method**: POST  
    - **Request Body**:
      ```json
      {
        "username": "your_username",
        "password": "your_password",
        "role": "STUDENT" // optional, default is STUDENT
      }
      ```
    - **Response**:
      ```json
      {
        "message": "User registered successfully",
        "status": true
      }
      ```

2. **Login** (`/signin`): Obtain a JWT token for authorization.
    - **Method**: POST  
    - **Request Body**:
      ```json
      {
        "username": "your_username",
        "password": "your_password"
      }
      ```
    - **Response**:
      ```json
      {
        "jwtToken": "<JWT_TOKEN>",
        "username": "your_username",
        "roles": ["ROLE_STUDENT/ROLE_ADMIN"]
      }
      ```

3. **Logout** (`/signout`): Securely log out the user.
    - **Method**: POST
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`

---

### **Admin-Only Endpoints**

1. **Create Student** (`/api/students/create`): Add a new student with subjects.
    - **Method**: POST  
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`  
    - **Request Body**:
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
    - **Response**:
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

2. **View All Students** (`/api/students/get-all`): Get a list of all students.
    - **Method**: GET  
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`  
    - **Response**:
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

---

### **Shared Endpoints (Admin and Student)**

1. **View All Subjects** (`/api/subjects/get-all`): Get a list of all subjects.
    - **Method**: GET  
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`  
    - **Response**:
      ```json
      [
        {"id": 1, "name": "English"},
        {"id": 2, "name": "History"},
        ...
      ]
      ```

2. **Create Subject** (`/api/subjects/create`): Add a new subject (Admin-only).
    - **Method**: POST  
    - **Headers**: `Authorization: Bearer <JWT_TOKEN>`  
    - **Request Body**:
      ```json
      {
        "name": "Physics"
      }
      ```
    - **Response**:
      ```json
      {
        "id": 3,
        "name": "Physics"
      }
      ```

---

## Security

- **JWT Authentication**:  
  - Secures API endpoints by requiring a token in the `Authorization` header.
  - Example: `Authorization: Bearer <JWT_TOKEN>`
- **Roles**:
  - **Student**: Limited access (view subjects).
  - **Admin**: Full access (manage students and subjects).

---

## Database Configuration

- Uses **H2 In-Memory Database** for fast setup.
- The database schema is initialized using the `schema.sql` file.

---

## Setup and Running the Application

1. **Clone the Repository**:
   ```bash
   git clone <repository_url>
   cd <repository_directory>

## Default Users

- **Admin**:
    ```json
    {
        "username": "admin",
        "password": "adminPass"
    }
    ```

- **Student**:
    ```json
    {
        "username": "student1",
        "password": "password1"
    }
    ```

---

## Future Enhancements

- Add **Admin Registration** during signup.
- Add the UI for better user experience.
- Add more granular permissions for specific features.
- Assigning roles
