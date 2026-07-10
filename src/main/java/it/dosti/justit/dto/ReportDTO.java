package it.dosti.justit.dto;

public class ReportDTO {

    private Integer bookingId;
    private String techNotes;
    private double laborHours;
    private double costHours;
    private double partCosts;


    public ReportDTO(){
        //Only needed for jackson databinding
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
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

    public Integer getBookingId() {
        return bookingId;
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
