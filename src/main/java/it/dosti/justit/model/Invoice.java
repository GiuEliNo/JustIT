package it.dosti.justit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.dosti.justit.exceptions.InvalidBookingStateException;

public class Invoice {

    private final double totalCost;
    private boolean paid;

    @JsonCreator
    public Invoice(
            @JsonProperty("totalCost") double totalCost,
            @JsonProperty("paid") boolean paid) {
        if (totalCost < 0) {
            throw new InvalidBookingStateException("Invoice total cost cannot be negative");
        }
        this.totalCost = totalCost;
        this.paid = paid;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        if (paid) {
            throw new InvalidBookingStateException("Invoice already paid");
        }
        this.paid = true;
    }
    }
