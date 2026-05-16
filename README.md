# Smart Parking Management System

This is a Java-based Smart Parking Management System developed for managing vehicle parking efficiently.

## Project Description

The Smart Parking Management System is a desktop application that helps manage parking slots, vehicle entry, vehicle exit, parking fee calculation, reservations, and analytics.

The system is developed using Java Swing for the user interface and MySQL for database management.

## Features

- User login system
- Admin dashboard
- Analytics dashboard
- Vehicle entry management
- Vehicle exit management
- Automatic parking slot allocation
- Parking fee calculation
- Vehicle search
- Reservation management
- Exit record management
- MySQL database integration
- Waiting queue management
- VIP vehicle priority handling

## Technologies Used

- Java
- Java Swing
- MySQL
- XAMPP
- JDBC
- GitHub
- VS Code

## Data Structures Used

- Array
- Queue
- Priority Queue
- HashMap
- LinkedList
- Graph

## Algorithms Used

- Linear Search
- Binary Search
- Sorting Algorithm
- Dijkstra Algorithm
- Queue Processing Algorithm
- Fee Calculation Algorithm

## Project Structure

SmartParkingSystem/
├── src/
│ ├── models/
│ ├── services/
│ ├── ui/
│ ├── utils/
│ └── Main.java
├── lib/
├── database/
├── .gitignore
└── README.md

## How to Run

Step 1: Start XAMPP

        Start Apache and MySQL.

Step 2: Open Project Folder

        Open the project in VS Code.

Step 3: Compile the Project

        javac -cp "lib/*" -d out src/models/*.java src/services/*.java src/utils/*.java src/ui/*.java src/Main.java

Step 4: Run the Project

        java -cp "out;lib/*" Main

## Database

The system uses MySQL database tables for:

### users

parking_records
exit_records
reservations
parking_slots

### Before running the system, import the SQL file into phpMyAdmin and start MySQL from XAMPP.

## Important

MySQL must be started before running the system.
MySQL Connector JAR file must be inside the lib folder.
The out folder is not uploaded to GitHub because it contains compiled class files.
The out folder can be generated again by compiling the project.
