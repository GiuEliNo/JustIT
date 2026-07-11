package it.dosti.justit.model.booking.repairReport;

import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.model.booking.BookingStatus;

public class RepairReportFactory {

    public static RepairReport create(BookingStatus status, RepairReportBean bean) {

        return switch(status) {

            case REJECTED ->
                    new RepairReportRejected(bean.getTechNotes());

            case COMPLETED ->
                    new RepairReportCompleted(bean.getTechNotes(), bean.getLaborHours(), bean.getCostHours(), bean.getPartCosts());

            default ->
                    throw new IllegalArgumentException("Invalid status");
        };
    }
}
