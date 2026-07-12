package it.dosti.justit.bean;

public class PaymentQuoteBean {
    private Long bookingId;
    private double quote;


    public void setQuote(double quote) {
        this.quote = quote;
    }

    public double getQuote() {
        return quote;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public Long getBookingId() {
        return bookingId;
    }

    public PaymentQuoteBean(Long bookingId, double quote) {
        this.bookingId = bookingId;
        this.quote = quote;
    }
}
