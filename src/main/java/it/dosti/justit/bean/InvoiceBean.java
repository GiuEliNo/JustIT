package it.dosti.justit.bean;

public class InvoiceBean {
    private double totalCost;
    private boolean paid;

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean canBePaid() {
        return !paid && totalCost > 0;
    }
}
