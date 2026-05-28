package it.dosti.justit.dao;

import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAODemo implements BookingDAO {

    private final List<Booking> bookings = new ArrayList<>();
    private int nextId = 1004;

    private static final String SHOP_NAME_DEMO = "Arindale Riparazione";
    private static final String USER_DEMO = "demo_client";

    public BookingDAODemo() {
        bookings.add(new Booking.Builder(USER_DEMO)
                .bookingId(1001)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now().minusDays(12))
                .timeSlot(TimeSlot.MORNING)
                .description("Sostituzione batteria Stonex One")
                .status(BookingStatus.COMPLETED)
                .homeAssistance(false)
                .build());

        bookings.add(new Booking.Builder(USER_DEMO)
                .bookingId(1002)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now().minusDays(2))
                .timeSlot(TimeSlot.AFTERNOON)
                .description("Installazione sailfish os")
                .status(BookingStatus.CONFIRMED)
                .homeAssistance(true)
                .build());

        bookings.add(new Booking.Builder(USER_DEMO)
                .bookingId(1003)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now().plusDays(3))
                .timeSlot(TimeSlot.EVENING)
                .description("Pulizia steam controller")
                .status(BookingStatus.PENDING)
                .homeAssistance(false)
                .build());

        bookings.add(new Booking.Builder("demo_client_2")
                .bookingId(2001)
                .shopId(1)
                .shopName(SHOP_NAME_DEMO)
                .date(LocalDate.now().minusDays(5))
                .timeSlot(TimeSlot.MORNING)
                .description("Aggiornamento Gentoo")
                .status(BookingStatus.REJECTED)
                .homeAssistance(false)
                .build());
    }

    @Override
    public int addBooking(Booking booking) {
        int generatedId = nextId++;
        BookingStatus status = booking.getStatus() == null ? BookingStatus.PENDING : booking.getStatus();

        Booking newBooking = new Booking.Builder(booking.getUsername())
                .bookingId(generatedId)
                .shopId(booking.getShopId())
                .shopName(booking.getShopName())
                .date(booking.getDate())
                .timeSlot(booking.getTimeSlot())
                .description(booking.getDescription())
                .status(status)
                .homeAssistance(booking.getHomeAssistance())
                .build();

        bookings.add(newBooking);
        return generatedId;
    }

    @Override
    public boolean existsBooking(Integer shopId, LocalDate date, TimeSlot timeSlot) {
        return bookings.stream()
                .anyMatch(b -> b.getShopId().equals(shopId)
                        && b.getDate().equals(date)
                        && b.getTimeSlot() == timeSlot);
    }

    @Override
    public List<Booking> getBookingsByUser(String username) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUsername().equals(username)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }

    @Override
    public List<Booking> getBookingsByShop(Integer shopId) {
        List<Booking> shopBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getShopId().equals(shopId)) {
                shopBookings.add(booking);
            }
        }
        return shopBookings;
    }

    @Override
    public void updateStatus(Integer bookingId, BookingStatus status) {
        for (int i = 0; i < bookings.size(); i++) {
            Booking current = bookings.get(i);
            if (current.getBookingId().equals(bookingId)) {
                Booking updated = new Booking.Builder(current.getUsername())
                        .bookingId(current.getBookingId())
                        .shopId(current.getShopId())
                        .shopName(current.getShopName())
                        .date(current.getDate())
                        .timeSlot(current.getTimeSlot())
                        .description(current.getDescription())
                        .status(status)
                        .homeAssistance(current.getHomeAssistance())
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
            if (booking.getShopId().equals(shopId) && booking.getDate().equals(date)) {
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
        List<Booking> completedWithoutReview = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUsername().equals(username)
                    && booking.getShopId().equals(shopId)
                    && booking.getStatus() == BookingStatus.COMPLETED
                    && booking.getBookingId().equals(1001)) {
                completedWithoutReview.add(booking);
            }
        }
        return completedWithoutReview;
    }

    @Override
    public List<Booking> getCompletedBookingsWithoutReview(String username) {
        List<Booking> completedWithoutReview = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUsername().equals(username)
                    && booking.getStatus() == BookingStatus.COMPLETED
                    && booking.getBookingId().equals(1001)) {
                completedWithoutReview.add(booking);
            }
        }
        return completedWithoutReview;
    }
}