package it.dosti.justit.bean;

public class PaymentQuoteBean {
    private Integer bookingId;
    private double quote;


    public void setQuote(double quote) {
        this.quote = quote;
    }

    public double getQuote() {
        return quote;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    public Integer getBookingId() {
        return bookingId;
    }

    public PaymentQuoteBean(Integer bookingId, double quote) {
        this.bookingId = bookingId;
        this.quote = quote;
    }
}
