# Readora: A Smart Library Management System for Academic Institutions

## Group Members
- Cabrera, Francis Andrei L.
- Dialimas, Nirhven Kyle C.
- Repe, Dave Laurence R.
- Supremo, Rolando P.
- Tabada, Arnold Michael P.

## Project Description
Readora: A Smart Library Management System for Academic Institutions is a Java desktop application developed in IntelliJ IDEA that helps make library management more organized, efficient, and easy to use in an academic institution. It is designed to manage book records, member information, and borrowing transactions in a more accurate and convenient way. The system aims to solve common problems in manual library operations, such as messy record-keeping, difficulty in tracking borrowed and returned books, and slow processing of library data. Through a simple and user-friendly interface, Readora offers a smarter and more reliable way of handling library services for the school community.

## Proposed Features
- User login for secure and authorized access
- Add, edit, and delete book records
- Register and manage library member information
- Borrow and return book transactions
- Search and filter available books
- View and monitor borrowing records
- Generate simple library reports

## Planned Technologies
- Java
- JavaFX
- JDBC
- SQLite or MySQL
- IntelliJ IDEA

## Evaluation Criteria Mapping (Initial)
- **Object-Oriented Programming (OOP):** The system will apply object-oriented programming principles by using classes such as Book, Member, Librarian, BorrowRecord, and DatabaseManager. These classes will help organize the structure of the system and support proper interaction between data and functions.

- **Java Generics:** The project will use Java generics through collections such as List<Book>, List<Member>, and Map<String, Book> to make the code more reusable, organized, and easier to manage.

- **Multithreading and Concurrency:** The system may use multithreading for background tasks such as loading records, retrieving database data, or generating reports without freezing the JavaFX interface.

- **Graphical User Interface (GUI):** The system will use JavaFX with FXML files to create a user-friendly and organized desktop interface. It will include forms, buttons, tables, and navigation for easier interaction.

- **Database Connectivity:** The project will use JDBC to connect the application to a database such as SQLite or MySQL. This will allow the system to store, retrieve, update, and delete records related to books, members, and borrowing transactions.

- **Unified Modeling Language (UML):** Draft Use Case Diagram and Class Diagram will be created to represent the system’s actors, functions, classes, and relationships. These diagrams will serve as the initial visual design of the project.

- **Design Patterns:** The project may use the Singleton design pattern for database connection management to make sure that only one database connection instance is maintained and reused when needed.

- **Code Quality and Documentation:** The project will follow proper naming conventions, clean code structure, and organized documentation. The README.md file, diagrams, and source files will be prepared clearly to make the project easier to understand and maintain.