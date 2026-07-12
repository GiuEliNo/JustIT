package it.dosti.justit.dto;

public class ReportDTO {

    private Long bookingId;
    private String techNotes;
    private double laborHours;
    private double costHours;
    private double partCosts;

    private double refundAmount;


    public ReportDTO(){
        //Only needed for jackson databinding
    }

    public void setBookingId(Long bookingId) {
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

    public Long getBookingId() {
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

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

}
