@startuml
left to right direction
actor "Client" as c
actor "Technician" as t
actor "Notification System" as n
actor "Payment System" as p


package JustIT {
usecase "Add review*" as UC1
usecase "Book an appointment*" as UC2
usecase "Inbox notification*" as UC3
usecase "Manage shop page*" as UC4
usecase "View booking list*" as UC5
usecase "Export booking*" as UC6
usecase "Manage booking status*" as UC7
usecase "Process Payment" as UC8


}

note bottom of JustIT
* includes login
  end note

c --> UC1
c --> UC2
c --> UC3
t --> UC3
t --> UC4
t --> UC5
t --> UC6
UC2 .> UC8: include
UC7 -l.> UC5: extend
UC8 --> p
UC2 --> n
UC7 --> n

@enduml