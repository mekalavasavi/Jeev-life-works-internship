 **Angular Task**
 This is a simple Angular application.it includes:
 -**Signup Page**
 -**Login Page**
 -**To-Do List Page**

 This app uses:
 - Reactive Forms with validation
- Local storage for authentication simulation
- Routing and navigation
- Basic form error handling and styling

  Features:
 1) Signup Page
-Fields: Full Name, Email, Password
-Validates required fields
-Stores data in localStorage

2)Login Page
-Validates credentials against saved data
-Shows errors on invalid input
-Navigates to To-Do page on success

3)To-Do List Page
-Accessible only after login (route is protected)
-Add tasks with a form
-Display task list
-Delete tasks individually
-Stores tasks in localStorage

## Prerequisites
- [Node.js](https://nodejs.org/) (v18+ or v22 recommended)
- Angular CLI (npm install -g @angular/cli)

How to Run This Project:
1. Clone the Project

If you’ve cloned from GitHub:
git clone https://github.com/your-username/jeev-life-works-intership.git
cd jeev-life-works-internship/angular-task

2.Open in VS Code
Go to File > Open Folder...
Choose the angular-task folder
Open the integrated terminal 

Run this in the terminal:
**npm install
To start the server:
**ng serve
