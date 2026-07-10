package it.dosti.justit.model;

import it.dosti.justit.exceptions.InvalidBookingStateException;

public class Invoice {

    private final double totalCost;
    private boolean paid;


    public Invoice( double totalCost, boolean paid) {
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
