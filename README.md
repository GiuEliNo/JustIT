# <img title="" src="https://raw.githubusercontent.com/GiuEliNo/JustIT/refs/heads/main/src/main/resources/iconJustIT.png" alt="" width="80" data-align="inline" data-align="bottom"> JustIT
[![License: WTFPL](https://img.shields.io/badge/License-WTFPL-brightgreen.svg)](http://www.wtfpl.net/about/)
[![Java](https://img.shields.io/badge/Java-orange)](...)
[![JavaFX](https://img.shields.io/badge/JavaFX-yellow)](...)
[![Maven](https://img.shields.io/badge/Maven-red)](...)
[![SQLite](https://img.shields.io/badge/SQLite-blue)](...)

JustIT is a Java desktop application developed for the *Ingegneria del Software e Programmazione Web _(Software Engineering and Web Programming)_* course at the *University of Rome Tor Vergata*.

## Overview
JustIT provides a platform for managing technical shop appointments, allowing customers and technicians to interact through booking workflows.

The system supports two different user roles:

- **Customers**
- **Technicians**

The system follows the **MVC architectural pattern**, is developed with **Maven**, and provides two different user interfaces:

- JavaFX and FXML files.
- Standard Command Line Interface.

The application can run in two different modes:

- **Demo Mode**: execution using in-memory persistence with volatile data.
- **Full Mode**: execution using persistent storage through SQLite or JSON files.

## Features 

### Customer Features

- Account management.
- Browse available technical shops.
- Search shops based on location.
- Book technical appointments.
- View and manage personal bookings.
- Review completed services.
- Simulated payment operations.


### Technician Features

- Manage technical shop information.
- View incoming bookings.
- Manage appointment status.
- Complete repair reports.
- Export booking calendar to CSV.


## Technologies
The project is developed using:

- Java
- JavaFX and FXML
- Maven
- SQLite
- JDBC
- JSON persistence

### Persistence Layer

JustIT supports multiple persistence backends.

Available persistence modes:

- **SQLite database**
- **JSON file system**
- **In-memory storage for Demo Mode**

### External Integrations

- **OpenStreetMap Nominatim API** for geocoding addresses and location-based shop search.
- **Mock Visa and Mastercard payment gateways** used to simulate payment and refund operations.

### Design Patterns
- Observer
- Factory Method
- State pattern
- Singleton

## Project Structure

```
justit/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── it/dosti/justit/
│   │   │       ├── api/                  # API
│   │   │       ├── bean/                 # Data transfer beans
│   │   │       │   └── mapper/             # Bean/Domain mapping utilities
│   │   │       ├── controller/
│   │   │       │   ├── app/              # Application controllers
│   │   │       │   └── graphical/        # Graphical controllers
│   │   │       │       ├── cli/          
│   │   │       │       └── gui/          
│   │   │       ├── dao/                  # Data Access Objects
│   │   │       ├── db/                   # Database layer
│   │   │       │   └── query/              # SQL queries
│   │   │       ├── dto/                  # Data Transfer Objects
│   │   │       ├── events/               
│   │   │       │   └── publisher/        # Publisher-subscriber pattern
│   │   │       ├── exceptions/           # Custom exceptions
│   │   │       ├── model/                # Business model
│   │   │       └── ui/                     # UI utilities
│   │   │           └── navigation/       
│   │   └── resources/
│   │       ├── DB/                      # Database files
│   │       │   └── justit.db             # SQLite database
│   │       ├── *.fxml                   # JavaFX files
│   └── test/                            
│       └── java/it/dosti/justit/
├── doc/                                 # Project documentation
│   ├── activity_diagram/
│   ├── class_diagram/
│   ├── project_document/
│   │   └── typst/                      # Typst source
│   ├── sequence_diagram/
│   ├── state_diagram/
│   └── use_case_diagram/                            
├── pom.xml                              # Maven configuration
└── README.md
```

## Running

The application supports different execution options through command-line arguments:

| Argument  | Description |
|-----------|-------------|
| *default* | Starts the JavaFX graphical interface using SQLite persistence |
| `--cli`   | Starts the Command Line Interface |
| `--demo`  | Uses in-memory persistence with demo data |
| `--fs`    | Uses JSON file-system persistence |

### Demo Accounts
The following accounts can be used to test the application.

#### Demo Mode

| Role | Username    | Password |
|------|-------------|----------|
| Customer | demo_client | password |
| Technician | demo_tech      | password |

#### Full Mode (SQLite)

| Role | Username | Password |
|------|----------|----------|
| Customer | demo     | password |
| Technician | tec.demo | password |



## Screenshot
<p float="left">
  <img title="Login" src="doc/project_document/typst/sb_login.png" alt="Login" width="500" data-align="inline" data-align="center">  
  <img title="Manage Booking" src="doc/project_document/typst/sb_manage_bookings.png" alt="Manage Booking" width="500" data-align="inline" data-align="center">
  <img title="Search Page Shop" src="doc/project_document/typst/sb_search_page_shop.png" alt="Search Page Shop" width="500" data-align="inline" data-align="center">
  <img title="Book an appointment" src="doc/project_document/typst/sb_book_page.png" alt="Book an appointment" width="500" data-align="inline" data-align="center">
  <img title="Client Page" src="doc/project_document/typst/sb_account_page_client.png" alt="Client Page" width="500" data-align="inline" data-align="center">
  <img title="Booking List Client" src="doc/project_document/typst/sb_bookings_list_client.png" alt="Booking List Client" width="500" data-align="inline" data-align="center">
</p>

## License

```
DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2026 DOSTI
 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
```
<img title="Login Page" src="https://upload.wikimedia.org/wikipedia/commons/0/05/WTFPL_logo.svg" alt="Login Page" width="100" data-align="inline" data-align="bottom">

## Authors

- Giulio Rustia
- Valerio Mazza

**Year:** 2026