package it.dosti.justit.dao;

import it.dosti.justit.bean.BookingCSVBean;

import java.io.File;
import java.util.List;

public interface BookingFileDAO {
    public void exportToFile(List<BookingCSVBean> bookings, File file);
}
