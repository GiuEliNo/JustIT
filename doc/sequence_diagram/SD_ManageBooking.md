@startuml

actor Technician

Technician -> BookingPageGUI
activate BookingPageGUI

BookingPageGUI -> BookingList: init BookingList
activate BookingList
BookingList -->> Booking: init data
activate Booking
deactivate Booking

BookingList -> DB: load data from DB


activate DB
DB -->> BookingList
deactivate DB

BookingList -->> BookingPageGUI
deactivate BookingList

deactivate BookingPageGUI

Technician -> BookingPageGUI: select booking
activate BookingPageGUI

BookingPageGUI -> Booking
activate Booking

Booking -> DB: load booking details
activate DB
deactivate DB

deactivate Booking

deactivate BookingPageGUI

Technician -> BookingPageGUI: change booking status
activate BookingPageGUI

BookingPageGUI -> Booking: update Booking information
activate Booking
deactivate Booking

deactivate BookingPageGUI

Technician -> BookingPageGUI: select confirm
activate BookingPageGUI

BookingPageGUI ->> Booking
activate Booking

Booking ->> DB
activate DB
DB -->> Booking
deactivate DB








@enduml
