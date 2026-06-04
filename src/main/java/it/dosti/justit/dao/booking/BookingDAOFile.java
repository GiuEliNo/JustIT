package it.dosti.justit.dao.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.TimeSlot;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.Review;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingDAOFile implements BookingDAO {
    private static final String FILENAME_BOOKINGS = "bookings";
    private static final String FILENAME_REVIEWS = "reviews";
    private static final String FILENAME_SHOPS = "shops";

    @Override
    public int addBooking(Booking booking) {
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {
            });
            if(!bookings.isEmpty()){
                booking.setBookingId(bookings.stream().mapToInt(Booking::getBookingId).max().getAsInt() +1);
            }
            else{
                booking.setBookingId(1);
            }
            bookings.add(booking);
            JsonHandler.writeJsonFile(bookings, FILENAME_BOOKINGS);
            return 1;
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public boolean existsBooking(Integer shopId, LocalDate date, TimeSlot timeSlot){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {
            });
            if (!bookings.isEmpty()){
                boolean found = false;
                for (Booking booking : bookings) {
                    if(booking.getShopId().compareTo(shopId)==0 && booking.getDate().isEqual(date) && booking.getTimeSlot().compareTo(timeSlot)==0){
                        found = true;
                        break;
                    }
                }
                return found;
            }

        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByUser(String username){
        try {
            List<Booking> bookingsGeneral = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookingsGeneral.isEmpty()){
                List<Booking> bookingsUser = new ArrayList<>();
                for(Booking booking : bookingsGeneral){
                    if(booking.getUsername().equals(username)){
                        String shopName = retrieveShopName(booking.getShopId());
                        booking.setShopName(shopName);
                        bookingsUser.add(booking);
                    }
                }
                return bookingsUser;
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Booking> getBookingsByShop(Integer shopId){
        try {
            List<Booking> bookingsGeneral = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookingsGeneral.isEmpty()){
                List<Booking> bookingsShop = new ArrayList<>();
                for(Booking booking : bookingsGeneral){
                    if(booking.getShopId().compareTo(shopId)==0){
                        bookingsShop.add(booking);
                    }
                }
                return bookingsShop;
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void updateStatus(Integer bookingId, BookingStatus status){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    if(booking.getBookingId().compareTo(bookingId)==0){
                        booking.changeStatus( status);
                        }
                    }
            }
            JsonHandler.writeJsonFile(bookings, FILENAME_BOOKINGS);
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public List<TimeSlot> getOccupiedSlots(Integer shopId, LocalDate date){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>(){});
            if(!bookings.isEmpty()){
                List<TimeSlot> timeSlots = new ArrayList<>();
                for(Booking booking : bookings){
                    if(booking.getShopId().compareTo(shopId)==0 && booking.getDate().isEqual(date)){
                        timeSlots.add(booking.getTimeSlot());
                    }
                }
                return timeSlots;
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Booking getBookingById(Integer bookingId){
        try{

            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    if(booking.getBookingId().compareTo(bookingId)==0){
                        return booking;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Booking> getCompletedBookingsWithoutReviewPerShop(String username, Integer shopId){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            List<Booking> filteredBookings;
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {});
                filteredBookings = bookings.stream()
                        .filter(booking -> shopId.equals(booking.getShopId()) && booking.getStatus()== BookingStatus.COMPLETED)
                        .filter(a -> reviews.stream()
                                .noneMatch(b -> Objects.equals(b.getBookingId(), a.getBookingId())))
                        .collect(Collectors.toList());

                return filteredBookings;

        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Booking> getCompletedBookingsWithoutReview(String username){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            List<Booking> filteredBookings;
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {});
            filteredBookings = bookings.stream()
                    .filter(booking -> username.equals(booking.getUsername()) && booking.getStatus()== BookingStatus.COMPLETED)
                    .filter(a -> reviews.stream()
                            .noneMatch(b -> Objects.equals(b.getBookingId(), a.getBookingId())))
                    .collect(Collectors.toList());

            return filteredBookings;

        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }


    public String retrieveShopName(Integer shopId){
        try{
            List<Shop> shops = JsonHandler.readCollectionOnJsonFile(FILENAME_SHOPS, new TypeReference<>() {
            });
            if(!shops.isEmpty()){
                for(Shop shop : shops){
                    if (shop.getId().equals(shopId)) {
                        return shop.getName();
                    }
                }
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return "";
    }

}
