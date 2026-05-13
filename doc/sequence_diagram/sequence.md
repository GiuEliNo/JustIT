@startuml
actor User


User -> LoginGUI: 1:Login
activate LoginGUI

LoginGUI -> BookingGUI: 2: Search Shop GUI
activate BookingGUI
deactivate LoginGUI

BookingGUI -> BookingController: 3: Initialize a booking controller

activate BookingController

BookingController -->> Booking: 4: Initialize a booking
activate Booking

Booking -->> BookingController
deactivate Booking

User -> BookingGUI: 5 Select Add Booking

BookingGUI -> BookingController: 6 Add Booking

BookingController ->> Booking: 7 Add booking data
activate Booking

Booking ->> BookingController
deactivate Booking

BookingController -->> Notification: 8 Send Notification
activate Notification

Notification -->> BookingController
deactivate Notification

deactivate BookingController

BookingGUI -> User: Done

deactivate BookingGUI

@enduml
