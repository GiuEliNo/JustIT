package it.dosti.justit.model.repairreport;

public class RepairReportRejected implements RepairReport {

    private final String techNotes;

    private double refundAmount;

    public RepairReportRejected(String techNotes, Double refundAmount) {
        this.techNotes = techNotes;
        this.refundAmount = refundAmount;
    }

    @Override
    public String getTechNotes() {
        return techNotes;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

}