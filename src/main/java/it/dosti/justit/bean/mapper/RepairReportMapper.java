package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.model.repairreport.RepairReport;
import it.dosti.justit.model.repairreport.RepairReportCompleted;
import it.dosti.justit.model.repairreport.RepairReportRejected;

public class RepairReportMapper {

    private RepairReportMapper() {
    }

    public static RepairReportBean toBean(RepairReport report) {

        if (report == null) {
            return null;
        }

        RepairReportBean bean = new RepairReportBean();
        bean.setTechNotes(report.getTechNotes());


        if (report instanceof RepairReportCompleted completed) {

            bean.setLaborHours(completed.getLaborHours());
            bean.setCostHours(completed.getCostHours());
            bean.setPartCosts(completed.getPartCosts());
        }

        if (report instanceof RepairReportRejected rejected) {
            bean.setRefundAmount(rejected.getRefundAmount());
        }

        return bean;
    }
}
