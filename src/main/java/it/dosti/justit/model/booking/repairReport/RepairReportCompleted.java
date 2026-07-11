package it.dosti.justit.model.booking.repairReport;

public class RepairReportCompleted implements RepairReport {

    private final String techNotes;
    private final double laborHours;
    private double costHours;
    private final double partCosts;

    public RepairReportCompleted(String techNotes, double laborHours, Double costHours, double partCosts) {
        this.techNotes = techNotes;
        this.laborHours = laborHours;
        this.partCosts = partCosts;

        if (costHours == 0) {
            this.costHours = 10;
        } // tariffa minima
    }

    @Override
    public String getTechNotes() {
        return techNotes;
    }

    public double getLaborHours() {
        return laborHours;
    }

    public double getCostHours() {
        return costHours;
    }

    public double getPartCosts() {
        return partCosts;
    }

    public double calculateTotalCost() {
        return partCosts + laborHours * costHours;
    }
}