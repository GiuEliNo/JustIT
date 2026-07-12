package it.dosti.justit.dto;


public class InvoiceDTO {

    private Long bookingId;
    private double totalAmount;
    private boolean paid;

    public InvoiceDTO(){
        //Only needed for Jackson databinding
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getBookingId() {
        return bookingId;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public boolean isPaid() {
        return paid;
    }

}
