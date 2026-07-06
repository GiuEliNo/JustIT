package it.dosti.justit.model.booking;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.TimeSlot;

public class BookingFactory {

    private BookingFactory() {}
    //TODO aggiungere differenziazione tra i due tipi di booking (a domicilio e in negozio)
    public static Booking createBooking(BookingBean bookingBean) {
        return new Booking.Builder(bookingBean.getUsername())
                .shopId(bookingBean.getShopId())
                .date(bookingBean.getDate())
                .timeSlot((TimeSlot.valueOf(bookingBean.getTimeSlot())))
                .description(bookingBean.getDescription())
                .status(BookingStatus.PENDING_PAYMENT)
                .homeAssistance(bookingBean.getHomeAssistance())
                .createdAt()
                .build();
    }
}
