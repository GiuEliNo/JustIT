package it.dosti.justit.dao.booking;

import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.repairreport.RepairReportCompleted;
import it.dosti.justit.model.repairreport.RepairReportRejected;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookingDAODemo implements BookingDAO {

    private final List<Booking> bookings = new ArrayList<>();
    private int nextId = 1004;

    private static final String SHOP_NAME_DEMO = "Arindale Riparazione";
    private static final User user1 = new ClientUser("demo_client");
    private static final User user2 = new ClientUser("Valentino mastrota");
    private static final Shop shop = new Shop.Builder(SHOP_NAME_DEMO)
            .id(1)
            .address("Via di Tor Pignattara 38")
            .phone("+39 06 2456789")
            .email("arindale.riparazione@demo.justit.it")
            .description("Centro assistenza")
            .image(new byte[]{1})
            .openingHours("09:00 - 18:00")
            .homeAssistance(true)
            .coordinates(new Coordinates(41.87, 12.54))
            .build();

    static {
        shop.setTech(new TechnicianUser("Gulio Agricolo", "demo_tech", "demo.tech@mail.com", shop));
    }

    public BookingDAODemo() {

        Booking completed = new Booking.Builder(user1)
                .bookingId(1001)
                .shopEntity(shop)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(12))
                .timeSlot(TimeSlot.MORNING)
                .description("Sostituzione batteria Stonex One")
                .status(BookingStatus.COMPLETED)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(15))
                .build();

        completed.setRepairReport(
                new RepairReportCompleted(
                        "Batteria sostituita con componente originale. Dispositivo funzionante.",
                        1.5,
                        30.0,
                        25.0
                )
        );

        completed.issueInvoice();
        bookings.add(completed);


        bookings.add(new Booking.Builder(user1)
                .bookingId(1002)
                .shopEntity(shop)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(2))
                .timeSlot(TimeSlot.AFTERNOON)
                .description("Installazione sailfish os")
                .status(BookingStatus.CONFIRMED)
                .homeAssistance(true)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(10))
                .build());


        bookings.add(new Booking.Builder(user1)
                .bookingId(1003)
                .shopEntity(shop)
                .date(LocalDate.now(ZoneId.systemDefault()).plusDays(3))
                .timeSlot(TimeSlot.EVENING)
                .description("Pulizia steam controller")
                .status(BookingStatus.PENDING_CONFIRM)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                .build());


        Booking rejected = new Booking.Builder(user2)
                .bookingId(2001)
                .shopEntity(shop)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(5))
                .timeSlot(TimeSlot.MORNING)
                .description("Aggiornamento Gentoo")
                .status(BookingStatus.REJECTED)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(8))
                .build();

        rejected.setRepairReport(
                new RepairReportRejected(
                        "Impossibile completare l'aggiornamento: dipendenze incompatibili con il kernel attuale."
                )
        );

        bookings.add(rejected);
    }


    @Override
    public int addBooking(Booking booking) {

        int generatedId = nextId++;

        Booking newBooking = new Booking.Builder(booking.getUser())
                .bookingId(generatedId)
                .shopEntity(booking.getShop())
                .date(booking.getDate())
                .timeSlot(booking.getTimeSlot())
                .description(booking.getDescription())
                .status(booking.getStatus())
                .homeAssistance(booking.getHomeAssistance())
                .createdAt(booking.getCreatedAt())
                .reservationPaymentTransactionId(
                        booking.getReservationPaymentTransactionId()
                )
                .build();

        bookings.add(newBooking);

        return generatedId;
    }


    @Override
    public boolean existsBooking(Booking booking) {
        int shopId = booking.getShop().getId();
        LocalDate date = booking.getDate();
        TimeSlot timeSlot = booking.getTimeSlot();

        return bookings.stream()
                .anyMatch(
                        b -> b.getShop().getId().equals(shopId)
                                && b.getDate().equals(date)
                                && b.getTimeSlot() == timeSlot
                );
    }


    @Override
    public List<Booking> getBookingsByUser(String username) {

        List<Booking> userBookings = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getUser().getUsername().equals(username)) {
                userBookings.add(booking);
            }
        }

        return userBookings;
    }


    @Override
    public List<Booking> getBookingsByShop(Integer shopId) {

        List<Booking> shopBookings = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getShop().getId().equals(shopId)) {
                shopBookings.add(booking);
            }
        }

        return shopBookings;
    }


    @Override
    public void updateStatus(Booking booking) {

        for (int i = 0; i < bookings.size(); i++) {

            Booking current = bookings.get(i);

            if (current.getBookingId().equals(booking.getBookingId())) {

                Booking updated = new Booking.Builder(current.getUser())
                        .bookingId(current.getBookingId())
                        .shopEntity(current.getShop())
                        .date(current.getDate())
                        .timeSlot(current.getTimeSlot())
                        .description(current.getDescription())
                        .status(booking.getStatus())
                        .homeAssistance(current.getHomeAssistance())
                        .createdAt(current.getCreatedAt())
                        .repairReport(current.getRepairReport())
                        .invoice(current.getInvoice())
                        .reservationPaymentTransactionId(
                                current.getReservationPaymentTransactionId()
                        )
                        .build();

                bookings.set(i, updated);
                return;
            }
        }
    }


    @Override
    public List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date) {

        List<TimeSlot> occupiedSlots = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getShop().getId().equals(shopId) && booking.getDate().equals(date)) {
                occupiedSlots.add(booking.getTimeSlot());
            }
        }

        return occupiedSlots;
    }


    @Override
    public Booking getBookingById(Integer bookingId) {

        return bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getUser().getUsername().equals(username)
                    && booking.getShop().getId().equals(shopId)
                    && booking.getStatus() == BookingStatus.COMPLETED
                    && booking.getBookingId().equals(1001)) {

                result.add(booking);
            }
        }

        return result;
    }


    @Override
    public List<Booking> getCompletedBookingsWithoutReview(String username) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getUser().getUsername().equals(username)
                    && booking.getStatus() == BookingStatus.COMPLETED
                    && booking.getBookingId().equals(1001)) {

                result.add(booking);
            }
        }

        return result;
    }


    @Override
    public void saveRepairReport(Booking updatedBooking) {

        for (Booking booking : bookings) {

            if (booking.getBookingId().equals(updatedBooking.getBookingId())) {

                booking.setRepairReport(updatedBooking.getRepairReport());
                return;
            }
        }
    }


    @Override
    public void saveInvoice(Booking updatedBooking) {

        for (Booking booking : bookings) {

            if (booking.getBookingId().equals(updatedBooking.getBookingId())) {

                booking.setInvoice(updatedBooking.getInvoice());
                return;
            }
        }
    }


    @Override
    public void updateInvoice(Booking updatedBooking) {

        for (Booking booking : bookings) {

            if (booking.getBookingId().equals(updatedBooking.getBookingId())) {

                booking.setInvoice(updatedBooking.getInvoice());
                booking.setReservationPaymentTransactionId(
                        updatedBooking.getReservationPaymentTransactionId()
                );

                return;
            }
        }
    }


    @Override
    public boolean deleteReservedBookingSlot(Integer bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId().equals(bookingId)) {

                bookings.remove(booking);
                return true;
            }
        }

        return false;
    }
}
