package it.dosti.justit.bean;

public class RepairReportBean {
    private String techNotes;
    private double laborHours;
    private double costHours;
    private double partCosts;

    public void setTechNotes(String techNotes) {
        this.techNotes = techNotes;
    }
    public void setLaborHours(double laborHours) {
        this.laborHours = laborHours;
    }

    public void setCostHours(double costHours) {
        this.costHours = costHours;
    }

    public void setPartCosts(double partCosts) {
        this.partCosts = partCosts;
    }
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
}
