@startuml


title Manage Booking

actor Technician

participant HomePageGUI <<boundary>>
participant BookingPageGUI <<boundary>>
participant BookingController <<controller>>
participant Booking <<entity>>
participant Client <<entity>>
database DB
participant NotificationService <<boundary>>

Technician -> HomePageGUI: select ManageBooking
activate HomePageGUI
HomePageGUI -> BookingPageGUI: initialize BookingPageGUI
activate BookingPageGUI

BookingPageGUI -> BookingController ++: <<create>>
BookingController -> Booking ++: <<create>>
deactivate Booking
BookingController ->> DB ++: getAllBookingsDB
DB -->> BookingController --: return Bookings data


BookingController -->> BookingPageGUI --: return bookings data
deactivate HomePageGUI


deactivate BookingPageGUI

Technician -> BookingPageGUI: select show booking info
activate BookingPageGUI

BookingPageGUI -> BookingController ++: get selected booking
BookingController ->> Booking : load booking details
activate Booking
BookingController ->> Client : load user info
activate Client
deactivate Booking
deactivate Client

BookingController-->> BookingPageGUI --: shows data

deactivate BookingPageGUI


Technician -> BookingPageGUI: change booking status
activate BookingPageGUI

BookingPageGUI -> BookingController ++: update Booking
BookingController ->> DB ++: save on db
deactivate DB
BookingController ->> NotificationService ++: send notification to client
deactivate NotificationService
deactivate BookingController
deactivate BookingPageGUI

@enduml
