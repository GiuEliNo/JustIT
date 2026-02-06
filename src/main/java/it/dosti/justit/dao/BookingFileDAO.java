package it.dosti.justit.dao;

import it.dosti.justit.model.booking.Booking;

import java.util.List;

public interface BookingFileDAO {
    public void exportToFile(List<Booking> bookings);
}
