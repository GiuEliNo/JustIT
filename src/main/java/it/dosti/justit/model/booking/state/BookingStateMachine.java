package it.dosti.justit.model.booking.state;

public interface BookingStateMachine {
    void goNext(BookingEvent event);
    void changeToState(BookingState newState);
}
