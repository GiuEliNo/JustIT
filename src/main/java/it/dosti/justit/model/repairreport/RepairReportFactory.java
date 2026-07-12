package it.dosti.justit.model.repairreport;

import it.dosti.justit.bean.RepairReportBean;
import it.dosti.justit.model.booking.BookingStatus;

public final class RepairReportFactory {

    private static RepairReportFactory instance = null;

    private RepairReportFactory() {
        // Sonar
    }

    public static synchronized RepairReportFactory getInstance() {
        if (instance == null) {
            instance = new RepairReportFactory();
        }
        return instance;
    }


    public RepairReport create(BookingStatus status, RepairReportBean bean) {

        if (status == null || bean == null) {
            throw new IllegalArgumentException("Status or report data cannot be null");
        }

        return switch (status) {

            case REJECTED -> createRejected(bean);
            case COMPLETED -> createCompleted(bean);
            default -> throw new IllegalArgumentException("Cannot create repair report for status: " + status);
        };
    }


    private RepairReportRejected createRejected(RepairReportBean bean) {
        return new RepairReportRejected(bean.getTechNotes());
    }


    private RepairReportCompleted createCompleted(RepairReportBean bean) {
        return new RepairReportCompleted(bean.getTechNotes(), bean.getLaborHours(), bean.getCostHours(), bean.getPartCosts());
    }
}