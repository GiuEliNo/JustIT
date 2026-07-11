package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.BookingBean;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.user.ClientUser;

import java.util.List;

public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingBean toBean(Booking booking) {

        if (booking == null) {
            return null;
        }

        BookingBean bean = new BookingBean();

        bean.setUsername(booking.getUser().getUsername());
        bean.setBookingID(booking.getBookingId());
        bean.setDate(booking.getDate());
        bean.setTimeSlot(booking.getTimeSlot().toString());
        bean.setDescription(booking.getDescription());
        bean.setStatus(booking.getStatus().toString());
        bean.setShopName(booking.getShop().getName());
        bean.setHomeAssistance(booking.getHomeAssistance());

        if (booking.getHomeAssistance() && booking.getUser() instanceof ClientUser) {

            ClientUser client = (ClientUser) booking.getUser();
            bean.setUserAddress(client.getAddress());
        }

        bean.setRepairReport(RepairReportMapper.toBean(booking.getRepairReport()));
        bean.setInvoice(InvoiceMapper.toBean(booking.getInvoice()));

        return bean;
    }


    public static List<BookingBean> toBeans(List<Booking> bookings) {

        return bookings.stream()
                .map(BookingMapper::toBean)
                .toList();
    }
}