package it.dosti.justit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RepairReport {

    private String techNotes;
    private double laborHours;
    private double costHours;
    private double partCosts;




    @JsonCreator
    public RepairReport(
            @JsonProperty("techNotes") String techNotes,
            @JsonProperty("laborHours") double laborHours,
            @JsonProperty("costHours") double costHours,
            @JsonProperty("partCosts") double partCosts) {
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
