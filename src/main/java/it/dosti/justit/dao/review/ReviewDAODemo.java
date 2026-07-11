package it.dosti.justit.dao.review;

import it.dosti.justit.exceptions.DaoException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Review;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAODemo implements ReviewDAO {

    private static final String SHOP_NAME_DEMO = "Arindale Riparazione";
    private final List<Review> reviews = new ArrayList<>();
    private static final User user1 = new ClientUser("Francesco pandini");

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

    private static final Booking booking = new Booking.Builder(user1)
            .bookingId(1001)
            .shopEntity(shop)
            .date(LocalDate.now(ZoneId.systemDefault()).minusDays(12))
            .timeSlot(TimeSlot.MORNING)
            .description("Sostituzione batteria Stonex One")
            .status(BookingStatus.COMPLETED)
            .homeAssistance(false)
            .createdAt(LocalDateTime.now(ZoneId.systemDefault()).minusDays(15))
            .build();

    public ReviewDAODemo() {
        Review review = new Review.Builder("Riparazione ottima")
                .star(5)
                .review("Consegna veloce e tecnico super disponibile.")
                .shop(shop)
                .booking(booking)
                .build();
        reviews.add(review);
    }

    @Override
    public List<Review> retrieveReviewsByShop(Integer shopId) {
        List<Review> shopReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getShop().getId().equals(shopId)) {
                shopReviews.add(review);
            }
        }
        return shopReviews;
    }

    @Override
    public void addReviewToShop(Review instance) throws DaoException {
        if (instance.getBooking().getBookingId() == null) {
            throw new DaoException("Review must be linked to a booking");
        }else{
            reviews.add(instance);
        }
    }
}
