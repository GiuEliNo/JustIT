package it.dosti.justit.dao.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.dto.InvoiceDTO;
import it.dosti.justit.dto.ReportDTO;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.model.*;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.model.booking.BookingStatus;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;
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
    private static final String FILENAME_REPORTS = "reports";
    private static final String FILENAME_USERS = "users";
    private static final String FILENAME_INVOICES = "invoices";

    @Override
    public int addBooking(Booking booking) throws RegisterOnBackEndException {
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
            throw new RegisterOnBackEndException(e.getMessage());
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
                    if(booking.getShop().getId().compareTo(shopId)==0 && booking.getDate().isEqual(date) && booking.getTimeSlot().compareTo(timeSlot)==0){
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
                    if(booking.getUser().getUsername().equals(username)){
                        booking.setUser(retrieveUser(booking));
                        booking.setShop(retrieveShop(booking));
                        booking.setRepairReport(retrieveReport(booking.getBookingId()));
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
                    if(booking.getShop().getId().compareTo(shopId)==0){
                        booking.setUser(retrieveUser(booking));
                        booking.setShop(retrieveShop(booking));
                        booking.setRepairReport(retrieveReport(booking.getBookingId()));
                        booking.setInvoice(retrieveInvoice(booking));
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
    public void updateStatus(Booking updatedBooking){
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    if(booking.getBookingId().compareTo(updatedBooking.getBookingId())==0){
                        booking.changeStatus(updatedBooking.getStatus());
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
                    booking.setShop(retrieveShop(booking));
                    if(booking.getShop().getId().compareTo(shopId)==0 && booking.getDate().isEqual(date)){
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
                        booking.setUser(retrieveUser(booking));
                        booking.setShop(retrieveShop(booking));
                        booking.setRepairReport(retrieveReport(booking.getBookingId()));
                        booking.setInvoice(retrieveInvoice(booking));
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
            for(Booking booking : bookings){
                booking.setUser(retrieveUser(booking));
                booking.setShop(retrieveShop(booking));
                booking.setRepairReport(retrieveReport(booking.getBookingId()));
                booking.setInvoice(retrieveInvoice(booking));
            }
            List<Booking> filteredBookings;
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {});
                filteredBookings = bookings.stream()
                        .filter(booking -> shopId.equals(booking.getShop().getId()) && booking.getStatus()== BookingStatus.COMPLETED)
                        .filter(a -> reviews.stream()
                                .noneMatch(b -> Objects.equals(b.getBooking().getBookingId(), a.getBookingId())))
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
            for(Booking booking : bookings){
                booking.setUser(retrieveUser(booking));
                booking.setShop(retrieveShop(booking));
                booking.setRepairReport(retrieveReport(booking.getBookingId()));
                booking.setInvoice(retrieveInvoice(booking));
            }
            List<Booking> filteredBookings;
            List<Review> reviews = JsonHandler.readCollectionOnJsonFile(FILENAME_REVIEWS, new TypeReference<>() {});
            filteredBookings = bookings.stream()
                    .filter(booking -> username.equals(booking.getUser().getUsername()) && booking.getStatus()== BookingStatus.COMPLETED)
                    .filter(a -> reviews.stream()
                            .noneMatch(b -> Objects.equals(b.getBooking().getBookingId(), a.getBookingId())))
                    .collect(Collectors.toList());

            return filteredBookings;

        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }


    @Override
    public void saveRepairReport(Booking updatedBooking) {
        try { List<ReportDTO> reports = JsonHandler.readCollectionOnJsonFile(FILENAME_REPORTS, new TypeReference<>() {});

            RepairReport repairReport = updatedBooking.getRepairReport();

            if (repairReport == null) {
                return;
            }

            ReportDTO report = new ReportDTO();

            report.setBookingId(updatedBooking.getBookingId());
            report.setTechNotes(repairReport.getTechNotes());

            if (repairReport instanceof RepairReportCompleted) {
                RepairReportCompleted completedReport =
                        (RepairReportCompleted) repairReport;

                report.setCostHours(completedReport.getCostHours());
                report.setLaborHours(completedReport.getLaborHours());
                report.setPartCosts(completedReport.getPartCosts());
            }

            reports.add(report);

            JsonHandler.writeJsonFile(reports, FILENAME_REPORTS);

        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }


    @Override
    public void saveInvoice(Booking updatedBooking) {
        try {
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            for (Booking booking : bookings) {
                if (booking.getBookingId().equals(updatedBooking.getBookingId())) {
                    booking.setInvoice(updatedBooking.getInvoice());
                    break;
                }
            }
            JsonHandler.writeJsonFile(bookings, FILENAME_BOOKINGS);
        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteReservedBookingSlot(Integer bookingId) {
        try{
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});
            if(!bookings.isEmpty()){
                for(Booking booking : bookings){
                    if(booking.getBookingId().equals(bookingId)){
                        bookings.remove(booking);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    private Invoice retrieveInvoice(Booking booking){
        try{
            List<InvoiceDTO> invoices = JsonHandler.readCollectionOnJsonFile(FILENAME_INVOICES, new TypeReference<>() {});
            if(!invoices.isEmpty()){
                for(InvoiceDTO invoice : invoices){
                    if(invoice.getBookingId().equals(booking.getBookingId())){
                        return new Invoice(invoice.getTotalAmount(), invoice.isPaid());
                    }
                }
            }

        }
        catch (Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }


    private RepairReportCompleted retrieveReport(Integer bookingId) {
        try{
            List<ReportDTO> reports = JsonHandler.readCollectionOnJsonFile(FILENAME_REPORTS, new TypeReference<>() {});
            if (!reports.isEmpty()) {
                for(ReportDTO report : reports){
                    if (report.getBookingId().equals(bookingId)) {
                        return new RepairReportCompleted(report.getTechNotes(), report.getLaborHours(), report.getCostHours(), report.getBookingId());
                    }
                }
            }
        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void updateInvoice(Booking updatedBooking) {
        try {
            List<Booking> bookings = JsonHandler.readCollectionOnJsonFile(FILENAME_BOOKINGS, new TypeReference<>() {});

            for (Booking booking : bookings) {
                if (booking.getBookingId().equals(updatedBooking.getBookingId())) {
                    if (booking.getInvoice() != null) {
                        booking.getInvoice().markPaid();
                    }
                    break;
                }
            }

            JsonHandler.writeJsonFile(bookings, FILENAME_BOOKINGS);

        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }


    private Shop retrieveShop(Booking booking){
        try{
            List<Shop> shops = JsonHandler.readCollectionOnJsonFile(FILENAME_SHOPS, new TypeReference<>() {
            });
            if(!shops.isEmpty()){
                for(Shop shop : shops){
                    if (shop.getId().equals(booking.getShop().getId())) {
                        return shop;
                    }
                }
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    private User retrieveUser(Booking booking){
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USERS, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(ClientUser user : users){
                    if(user.getUsername().equals(booking.getUser().getUsername())){
                        return user;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }
}
