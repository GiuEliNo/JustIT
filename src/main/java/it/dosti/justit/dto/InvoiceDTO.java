package it.dosti.justit.dto;


public class InvoiceDTO {

    private Integer bookingId;
    private double totalAmount;
    private boolean paid;

    public InvoiceDTO(){
        //Only needed for Jackson databinding
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Integer getBookingId() {
        return bookingId;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public boolean isPaid() {
        return paid;
    }

}
