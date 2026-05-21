@startuml


title Manage Booking

actor Technician

participant BookingPageGUI <<View>>

Technician -> BookingPageGUI: Select ManageBooking
activate BookingPageGUI

BookingPageGUI -> BookingController ++: <<create>>
BookingController -> BookingList ++: <<create>>
deactivate BookingList
BookingController -> Booking++: <<create>>

deactivate Booking

database DB
BookingController -> DB ++: load

DB -->> BookingController --: return Bookings data

BookingController -> BookingList++: populate Bookings
deactivate BookingList

BookingController -->> BookingPageGUI--: return bookings data


deactivate BookingPageGUI

Technician -> BookingPageGUI: select booking
activate BookingPageGUI

BookingPageGUI -> BookingController ++:

Booking -> DB ++: load booking details
DB -->> Booking--: return booking data

Booking-->> BookingPageGUI --: shows booking data

deactivate BookingPageGUI


Technician -> BookingPageGUI: change booking status
activate BookingPageGUI

BookingPageGUI -> Booking: update Booking information
activate Booking
deactivate Booking

deactivate BookingPageGUI

Technician -> BookingPageGUI ++: select confirm

BookingPageGUI ->> Booking++: prova

Booking -> DB ++
DB -->> Booking --: prova

Booking -->> BookingPageGUI--

deactivate BookingPageGUI




@enduml