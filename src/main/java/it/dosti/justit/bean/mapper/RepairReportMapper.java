package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.model.booking.repairReport.RepairReport;
import it.dosti.justit.model.booking.repairReport.RepairReportCompleted;

public class RepairReportMapper {

    private RepairReportMapper() {
    }

    public static RepairReportBean toBean(RepairReport report) {

        if (report == null) {
            return null;
        }

        RepairReportBean bean = new RepairReportBean();
        bean.setTechNotes(report.getTechNotes());


        if (report instanceof RepairReportCompleted) {

            RepairReportCompleted completed = (RepairReportCompleted) report;

            bean.setLaborHours(completed.getLaborHours());
            bean.setCostHours(completed.getCostHours());
            bean.setPartCosts(completed.getPartCosts());
        }


        return bean;
    }
}
