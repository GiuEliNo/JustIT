package it.dosti.justit.model.booking;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.user.User;

public class BookingFactory {

    private BookingFactory() {}

    public static Booking createBookingFromBean(BookingBean bookingBean, Shop shop, User user) {
        return new Booking.Builder(user)
                .shopEntity(shop)
                .date(bookingBean.getDate())
                .timeSlot((TimeSlot.valueOf(bookingBean.getTimeSlot())))
                .description(bookingBean.getDescription())
                .status(BookingStatus.PENDING_PAYMENT)
                .homeAssistance(bookingBean.getHomeAssistance())
                .createdAt()
                .build();
    }
}
