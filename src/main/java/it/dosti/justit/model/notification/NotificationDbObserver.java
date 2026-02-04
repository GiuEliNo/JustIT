package it.dosti.justit.model.notification;

import it.dosti.justit.dao.NotificationDAO;
import it.dosti.justit.dao.NotificationDAOJDBC;
import it.dosti.justit.model.booking.observer.BookingStatusChange;
import it.dosti.justit.model.booking.observer.BookingStatusObserver;

public class NotificationDbObserver implements BookingStatusObserver {
    private final NotificationDAO notificationDAO = new NotificationDAOJDBC();

    @Override
    public void onStatusChanged(BookingStatusChange change) {
        notificationDAO.insertNotification(
                change.getUsername(),
                change.getBookingId(),
                change.getOldStatus(),
                change.getNewStatus(),
                change.getOccurredAt()
        );
    }
}
