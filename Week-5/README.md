SPRINGBOOT STUDENT MANAGEMENT SYSTEM

// Features:
- Add, view, update, delete student records
- Input validation using Spring Validation API
- Global exception handling
- MySQL database integration via Spring Data JPA
- Clean and modular code structure
  
// Technologies used:
Java 17
Springboot
Spring Data JPA
MYSQL
Spring validation
Postman

// Mysql setup:
Create a database in Mysql workbench and run below statement
- CREATE DATABASE student_db;

// Run the application
- Run the week5Application.java in eclipse
- Next open postman and give some requests
- add the below data in body part in postman using post request 
{
    "name": "Mahathi",
    "age": 18,
    "grade": "B",
    "address": "Vizag"

}
- add some more data like above and perfom below operations
  post , get , get student by id, put , delete 

