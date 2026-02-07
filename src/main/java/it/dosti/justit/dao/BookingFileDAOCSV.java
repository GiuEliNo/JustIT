package it.dosti.justit.dao;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import it.dosti.justit.bean.BookingCSVBean;
import it.dosti.justit.utils.JustItLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class BookingFileDAOCSV implements BookingFileDAO {
    @Override
    public void exportToFile(List<BookingCSVBean> bookings, File file) {
        try (Writer writer = new FileWriter(file)) {

            writer.write("BOOKING ID, USER, DATE, TIME SLOT, STATUS, DESCRIPTION\n");
            StatefulBeanToCsv<BookingCSVBean> beanToCsv = new StatefulBeanToCsvBuilder<BookingCSVBean>(writer).build();
            beanToCsv.write(bookings);

        } catch (Exception e) {
            JustItLogger.getInstance().error("Error exporting bookings to CSV", e);
        }
    }
}
