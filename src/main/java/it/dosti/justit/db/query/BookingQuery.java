package it.dosti.justit.db.query;

public class BookingQuery {

    private BookingQuery() {}

    public static final String INSERT_BOOKING = "INSERT INTO Booking(id, idShop, username, date, timeSlot, description, isHomeAssistance, createdAt, transaction_id)"+
            " VALUES (?,?,?,?,?,?,?,?,?)";

    public static final String UPDATE_STATUS = "UPDATE Booking SET state = ? WHERE id = ?";
    public static final String SELECT_BOOKING_USER = "SELECT B.idShop, B.id, S.name,B.date,B.timeSlot,B.description, B.state, B.isHomeAssistance, B.createdAt, B.transaction_id FROM Booking B join Shop S ON B.idShop = S.id WHERE B.username = ?";
    public static final String SELECT_COMPLETED_WITHOUT_REVIEW_USER = "SELECT B.idShop, B.id, S.name, B.date, B.timeSlot, " +
            "B.description, B.state, B.isHomeAssistance FROM Booking B join Shop S ON B.idShop = S.id " +
            "LEFT JOIN reviews R ON R.booking_id = B.id WHERE B.username = ? AND B.state IN ('COMPLETED') " +
            "AND R.booking_id IS NULL";
    public static final String SELECT_BOOKING_SHOP = "SELECT id, username, date, timeSlot, description, state, isHomeAssistance, createdAt, transaction_id FROM Booking WHERE idShop=?";
    public static final String SELECT_OCCUPIED_SLOTS_DATE = "SELECT timeSlot FROM Booking WHERE idShop = ? AND date = ? AND state IN ('PENDING_PAYMENT', 'PENDING_CONFIRM', 'CONFIRMED')";
    public static final String SELECT_BOOKING_ID = "SELECT idShop, username, date, timeSlot, description, state, isHomeAssistance, createdAt, transaction_id FROM Booking WHERE id = ?";
    public static final String SELECT_COMPLETED_WITHOUT_REVIEW_SHOP = "SELECT B.id, B.username, B.date, B.timeSlot, " +
            "B.description, B.state, B.isHomeAssistance, B.transaction_id FROM Booking B LEFT JOIN reviews R ON R.booking_id = B.id " +
            "WHERE B.username = ? AND B.idShop = ? AND B.state IN ('COMPLETED') AND R.booking_id IS NULL";

    public static final String EXIST_BOOKING = "SELECT 1 FROM Booking WHERE idShop = ? AND date = ? AND timeSlot = ? " + "AND state IN ('PENDING_CONFIRM', 'CONFIRMED', 'PENDING_PAYMENT') LIMIT 1";
    public static final String DELETE_BOOKING_RESERVATION= "DELETE FROM Booking WHERE id = ?";

    public static final String INSERT_REPAIR_REPORT =
            "INSERT INTO repair_report (booking_id, tech_notes, labor_hours, cost_hours, part_costs, refund) VALUES (?,?,?,?,?,?)";

    public static final String SELECT_REPAIR_REPORT =
            "SELECT tech_notes, labor_hours, cost_hours, part_costs FROM repair_report WHERE booking_id = ?";

    public static final String INSERT_INVOICE =
            "INSERT OR REPLACE INTO invoice (booking_id, total_cost, paid) VALUES (?,?,?)";

    public static final String SELECT_INVOICE =
            "SELECT total_cost, paid FROM invoice WHERE booking_id = ?";

    public static final String UPDATE_INVOICE_PAID =
            "UPDATE invoice SET paid = ? WHERE booking_id = ?";

    public static final String UPDATE_RESERVATION_PAYMENT_TRANSACTION_ID =
            "UPDATE Booking SET transaction_id = ? WHERE id = ?";
}
