package it.dosti.justit.dao.bookingexport;

import it.dosti.justit.bean.BookingCSVBean;

import java.io.File;
import java.util.List;

public interface BookingExportFileDAO {
    void exportToFile(List<BookingCSVBean> bookings, File file);
}
