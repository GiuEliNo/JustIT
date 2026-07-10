package it.dosti.justit.dao.booking;

import it.dosti.justit.model.RepairReport;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookingDAODemo implements BookingDAO {

    private final List<Booking> bookings = new ArrayList<>();
    private int nextId = 1004;

    private static final String SHOP_NAME_DEMO = "Arindale Riparazione";
    private static final String USER_DEMO = "demo_client";

    public BookingDAODemo() {

        Booking completed = new Booking.Builder(USER_DEMO)
                .bookingId(1001)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(12))
                .timeSlot(TimeSlot.MORNING)
                .description("Sostituzione batteria Stonex One")
                .status(BookingStatus.COMPLETED)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(15))
                .build();

        completed.setRepairReport(
                new RepairReport(
                        "Batteria sostituita con componente originale. Dispositivo funzionante.",
                        1.5,
                        30.0,
                        25.0
                )
        );

        completed.issueInvoice();
        bookings.add(completed);


        bookings.add(new Booking.Builder(USER_DEMO)
                .bookingId(1002)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(2))
                .timeSlot(TimeSlot.AFTERNOON)
                .description("Installazione sailfish os")
                .status(BookingStatus.CONFIRMED)
                .homeAssistance(true)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(10))
                .build());


        bookings.add(new Booking.Builder(USER_DEMO)
                .bookingId(1003)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now(ZoneId.systemDefault()).plusDays(3))
                .timeSlot(TimeSlot.EVENING)
                .description("Pulizia steam controller")
                .status(BookingStatus.PENDING_CONFIRM)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                .build());


        Booking rejected = new Booking.Builder("demo_client_2")
                .bookingId(2001)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now(ZoneId.systemDefault()).minusDays(5))
                .timeSlot(TimeSlot.MORNING)
                .description("Aggiornamento Gentoo")
                .status(BookingStatus.REJECTED)
                .homeAssistance(false)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(8))
                .build();

        rejected.setRepairReport(
                new RepairReport(
                        "Impossibile completare l'aggiornamento: dipendenze incompatibili con il kernel attuale.",
                        0.5,
                        30.0,
                        0.0
                )
        );

        bookings.add(rejected);
    }


    @Override
    public int addBooking(Booking booking) {

        int generatedId = nextId++;

        Booking newBooking = new Booking.Builder(booking.getUsername())
                .bookingId(generatedId)
                .shopId(booking.getShopId())
                .shopName(booking.getShopName())
                .date(booking.getDate())
                .timeSlot(booking.getTimeSlot())
                .description(booking.getDescription())
                .status(booking.getStatus())
                .homeAssistance(booking.getHomeAssistance())
                .createdAt(booking.getCreatedAt())
                .repairReport(booking.getRepairReport())
                .invoice(booking.getInvoice())
                .reservationPaymentTransactionId(
                        booking.getReservationPaymentTransactionId()
                )
                .build();

        bookings.add(newBooking);

        return generatedId;
    }


    @Override
    public boolean existsBooking(Integer shopId, LocalDate date, TimeSlot timeSlot) {
        return bookings.stream()
                .anyMatch(b ->
                        b.getShopId().equals(shopId)
                                && b.getDate().equals(date)
                                && b.getTimeSlot() == timeSlot
                );
    }


    @Override
    public List<Booking> getBookingsByUser(String username) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getUsername().equals(username)) {
                result.add(booking);
            }
        }

        return result;
    }


    @Override
    public List<Booking> getBookingsByShop(Integer shopId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getShopId().equals(shopId)) {
                result.add(booking);
            }
        }

        return result;
    }


    @Override
    public void updateStatus(Booking booking) {

        for (int i = 0; i < bookings.size(); i++) {

            Booking current = bookings.get(i);

            if (current.getBookingId().equals(booking.getBookingId())) {

                Booking updated = new Booking.Builder(current.getUsername())
                        .bookingId(current.getBookingId())
                        .shopId(current.getShopId())
                        .shopName(current.getShopName())
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

        List<TimeSlot> result = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getShopId().equals(shopId)
                    && booking.getDate().equals(date)) {
                result.add(booking.getTimeSlot());
            }
        }

        return result;
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
            if (booking.getUsername().equals(username)
                    && booking.getShopId().equals(shopId)
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
            if (booking.getUsername().equals(username)
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