package it.dosti.justit.dao;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import it.dosti.justit.model.booking.Booking;
import it.dosti.justit.utils.JustItLogger;

import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class BookingFileDAOCSV implements BookingFileDAO {
    @Override
    public void exportToFile(List<Booking> bookings) {
        try (Writer writer = new FileWriter("booking_export.csv")) {

            StatefulBeanToCsv<Booking> beanToCsv =
                    new StatefulBeanToCsvBuilder<Booking>(writer).build();

            beanToCsv.write(bookings);

        } catch (Exception e) {
            JustItLogger.getInstance().error("Error exporting bookings to CSV", e);
        }
    }
}
