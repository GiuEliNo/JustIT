package it.dosti.justit.events.state;

public interface BookingStateMachine {
    void goNext(BookingEvent event);
    void changeToState(BookingState newState);
}
