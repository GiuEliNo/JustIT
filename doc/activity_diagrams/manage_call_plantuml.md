```plantuml
@startuml
start

note right
manage call of intervention
end note

:receive service request notification;
:view request details;

if (request acceptable?) then (yes)

    if (requested date available?) then (yes)
        :accept request;
    else (no)
        :propose alternative date;
    endif

    :update request status;
    :update technician schedule;
    :notify customer;

else (no)

    :reject request;
    :update request status;
    :notify client;

endif

end
@enduml
```
