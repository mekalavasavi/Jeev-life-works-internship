STUDENT MANAGEMENT SYSTEM

This is a simple and scalable "Student Management System " built using java , JDBC and MYSQL.

Features:
1) add , update , delete , search , display the students.
2) data stored and updated in MYSQL.
3) input validation and error handling
4) Console-based UI
5) junit test cases

Technologies used:
1)java 8
2)JDBC
3) MYSQL Server and workbench
4)eclipse IDE

Database setup:
1)open mysql workbench
2)create database studentdb and table students
3)Upload and run the code 
CREATE DATABASE studentdb;
USE studentdb;
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    grade VARCHAR(10),
    address VARCHAR(255)
);

How to run the Project:
1)clone the repository week-4.
2)open eclipse and in files open the project.
3)download MYSQLConnector/J and add it from external jars as .jar file.
4)add junit5 from the libraries and finish.
5)Right click on main.java and run as "java application".
