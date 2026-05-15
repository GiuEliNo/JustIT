@startuml
actor User


User -> LoginGUI: 1:Login
activate LoginGUI

LoginGUI -> ShopSearchGUI **: 2: create ShopSearchGUI

deactivate LoginGUI
destroy LoginGUI

deactivate ShopSearchGUI


User -> ShopSearchGUI: 3 select Shop
activate ShopSearchGUI

ShopSearchGUI -> ShopPageGUI **:4: Init Shop Page View
activate ShopPageGUI
deactivate ShopSearchGUI
destroy ShopSearchGUI

ShopPageGUI -> BookingGUI **: 5: Init booking View

activate BookingGUI
deactivate ShopPageGUI




BookingGUI -> BookingController **: 6: Initialize a booking controller

activate BookingController


BookingController -->> Booking **: 7:Initialize a booking
activate Booking

Booking -->> BookingController
deactivate Booking

BookingController -->>BookingGUI


deactivate BookingController
deactivate BookingGUI


User -> BookingGUI: 8: Select Add Booking
activate BookingGUI

BookingGUI -> BookingController: 9: Add Booking
activate BookingController

BookingController ->> Booking: 10: Add booking data
activate Booking

Booking -->> BookingController
deactivate Booking

BookingController -> DB: 11: Commit data to DB
activate DB
DB -->> BookingController: return
deactivate DB

destroy Booking


BookingController ->> Notification **: 12: Send Notification
activate Notification

Notification -->> BookingController
deactivate Notification

BookingController -->> BookingGUI: Done

deactivate BookingController

BookingGUI -->> User: Done

deactivate BookingGUI

@enduml
