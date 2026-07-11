package it.dosti.justit.model.repairreport;

public class RepairReportRejected implements RepairReport {

    private final String techNotes;

    public RepairReportRejected(String techNotes) {
        this.techNotes = techNotes;
    }

    @Override
    public String getTechNotes() {
        return techNotes;
    }
}