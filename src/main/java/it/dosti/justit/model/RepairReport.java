package it.dosti.justit.model;

public class RepairReport {

    private String techNotes;
    private double laborHours;
    private double costHours;
    private double partCosts;




    public RepairReport(String techNotes, double laborHours, double costHours, double partCosts) {
        this.techNotes = techNotes;
        this.laborHours = laborHours;
        this.costHours = costHours;
        this.partCosts = partCosts;
    }

    public String getTechNotes() { return techNotes; }
    public double getLaborHours() { return laborHours; }
    public double getCostHours() { return costHours; }
    public double getPartCosts() { return partCosts; }

    public double calculateTotalCost() {
        return partCosts + laborHours * costHours;
    }

}
