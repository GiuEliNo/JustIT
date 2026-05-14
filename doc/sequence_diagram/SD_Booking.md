@startuml
actor User


User -> LoginGUI: 1:Login
activate LoginGUI

LoginGUI -> BookingGUI: 2: Search Shop GUI
activate BookingGUI
deactivate LoginGUI
destroy LoginGUI


BookingGUI -> BookingController: 3: Initialize a booking controller

activate BookingController


BookingController -->> Booking **: 4:Initialize a booking
activate Booking

Booking -->> BookingController
deactivate Booking

BookingController -->>BookingGUI


deactivate BookingController

deactivate BookingGUI


User -> BookingGUI: 5: Select Add Booking
activate BookingGUI

BookingGUI -> BookingController: 6: Add Booking
activate BookingController

BookingController ->> Booking: 7: Add booking data
activate Booking

Booking -->> BookingController
deactivate Booking

BookingController -> DB: 8: Commit data to DB
activate DB
DB -->> BookingController: return
deactivate DB

destroy Booking


BookingController ->> Notification **: 9: Send Notification
activate Notification

Notification -->> BookingController
deactivate Notification

BookingController -->> BookingGUI: Done

deactivate BookingController

BookingGUI -->> User: Done

deactivate BookingGUI

@enduml
