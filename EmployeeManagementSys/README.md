Employee Management System 

A simple microservice-based Employee Management System using Spring Boot 3.x, Java 17+, JWT authentication, Spring Security, and JDBC for database operations.

Features
- User Registration & Login (JWT Token-based)
- Role-based Authorization (`ADMIN`, `USER`)
- Secure REST APIs using Spring Security
- JDBC and MySQL database integration

  Technologies Used
  - Java 17+
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Token)
- JDBC
- MySQL
- Lombok
- Postman (for API testing)

  How to run:
CREATE DATABASE ems_db;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(100) NOT NULL
)
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    email VARCHAR(100) UNIQUE

);

Test with Postman
Register : post http://localhost:8080/api/auth/resgiter
login: post http://localhost:8080/api/auth/login
{
  "username": "vasavi",
  "password": "vasavi123"
}
employees : /api/employees/**
