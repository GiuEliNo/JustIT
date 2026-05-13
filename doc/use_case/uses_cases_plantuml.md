@startuml
left to right direction
actor "Client" as c
actor "Technician" as t
actor "Payment System" as p

package JustIT {
usecase "Add review*" as UC1
usecase "Book an appointment*" as UC2
usecase "Inbox notification*" as UC3
usecase "Manage shop page*" as UC4
usecase "View booking list*" as UC5
usecase "Export booking*" as UC6
usecase "Manage booking status*" as UC7

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
UC7 .> UC5: extend
UC2 --> p

@enduml