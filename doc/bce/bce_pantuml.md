<!-- Colori Pantone
light-red: Boundary hex: #FF7F7F 
light-blue: Entity hex:708a93 --> 





```plantuml
@startuml
class   ClientCallIntervent             <<(B,#FF7F7F)boundary>>{
bookIntervent()
}
class   CallIntervent                   <<controller>>{
callIntervent()
notifyTechnician()
processPayment()
}

class   TechnicianPage                  <<(E,#708a93)entity>>
class   Payment                         <<controller>>{
callPaymentProviders()
}
class   ProcessPayment                  <<(B,#FF7F7F)boundary>>{
callProviderDAO()
}
class   ClientBrowseTechnicianPage      <<(B,#FF7F7F)boundary>>{
SearchTechnicianPage()
}
class   BrowseTechnicianPage            <<controller>>{
findTechnicians()
listTechnicians()
callFilterPosition()
}
class   FilterTechnicianPosition        <<controller>>{
callGpsService()
}
class   PositionService                 <<(B,#FF7F7F)boundary>>{
callGpsProvider()
}
class   ManageOwnPage                   <<controller>>{
editShopName()
editShopServiceFees()
editShopLocation()
editShopImage()
editInterventHours()
}
class   TechnicianManageOwnPage         <<(B,#FF7F7F)boundary>>{
ManageOwnPage()
}
class   CheckInterventionCalendar                  <<controller>>{
ReadInterventionDetails()
ApproveIntervention()
RefuseIntervention()
NotifyClient()
}
class   Intervent                       <<(E,#708a93)entity>>
class   ClientAddReview                 <<boundary>> { 
AddReview()
 }
class   ClientReadReview                <<(B,#FF7F7F)boundary>>{
ClientReadReview()
}
class   AddReview                       <<controller>>{
WriteReview()
NotifyTechnician()
}
class   Review                          <<(E,#708a93)entity>>
class   ReadReview                      <<controller>>{
ReadReview()
}

class TechnicianReadReview              <<(B,#FF7F7F) boundary>>{
TechnicianReadReview()
}
class TechnicianCheckInterventionCalendar <<(B,#FF7F7F) boundary>>{
TechnicianCheckInterventionCalendar()
}

class InterventionCalendar          <<(E,#708a93)entity>>{
}



Intervent --o InterventionCalendar
TechnicianCheckInterventionCalendar -u-> CheckInterventionCalendar
TechnicianReadReview -u-> ReadReview
ClientReadReview -l-> ReadReview
ReadReview -up-> Review
ClientAddReview -l-> AddReview
AddReview -l-> Review
Review -l-o TechnicianPage
ClientCallIntervent -d-> CallIntervent
CallIntervent -d-> Payment
Payment -> Intervent
Payment -u-> ProcessPayment
ClientBrowseTechnicianPage -d-> BrowseTechnicianPage
BrowseTechnicianPage -> TechnicianPage
BrowseTechnicianPage -> FilterTechnicianPosition
FilterTechnicianPosition -d-> TechnicianPage
FilterTechnicianPosition -> PositionService
TechnicianManageOwnPage -u-> ManageOwnPage
ManageOwnPage -u-> TechnicianPage
CheckInterventionCalendar -u-> InterventionCalendar
CallIntervent -d-> Intervent
InterventionCalendar  <-r-  TechnicianPage
CallIntervent -u-> TechnicianPage

@enduml
```
