package it.dosti.justit.bean;

import java.time.LocalDate;

public class BookingBean {
    private Long bookingID;
    private String username;
    private LocalDate date;
    private String timeSlot;
    private String description;
    private String status;
    private String shopName;
    private Boolean homeAssistance;
    private String userAddress;
    private RepairReportBean repairReport;
    private InvoiceBean invoice;
    private String reservationPaymentTransactionId;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getBookingID() {
        return this.bookingID;
    }
    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public void setHomeAssistance(Boolean homeAssistance) {
        this.homeAssistance = homeAssistance;
    }
    public Boolean getHomeAssistance() {
        return homeAssistance;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserAddress() {
        return userAddress;
    }
    public void setRepairReport(RepairReportBean repairReport) {
        this.repairReport = repairReport;
    }
    public RepairReportBean getRepairReport() {
        return repairReport;
    }
    public void setInvoice(InvoiceBean invoice) {
        this.invoice = invoice;
    }
    public InvoiceBean getInvoice() {
        return invoice;
    }

    public String getHomeAssistanceLabel() {
        if (homeAssistance == null) {
            return "N/A";
        }
        return homeAssistance.booleanValue() ? "Yes" : "No";
    }

    public String getReservationPaymentTransactionId() {
        return reservationPaymentTransactionId;
    }

    public void setReservationPaymentTransactionId(String reservationPaymentTransactionId) {
        this.reservationPaymentTransactionId = reservationPaymentTransactionId;
    }

    public boolean hasRepairReport() {
        return repairReport != null;
    }

    public boolean canPayInvoice() {
        return "COMPLETED".equals(status) && invoice != null && invoice.canBePaid();
    }

    public String getInvoiceTotalLabel() {
        if (invoice == null) {
            return "";
        }
        return String.format("€ %.2f", invoice.getTotalCost());
    }
}
