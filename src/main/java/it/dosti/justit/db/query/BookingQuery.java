package it.dosti.justit.db.query;

public class BookingQuery {

    private BookingQuery() {}

    public static final String INSERT_BOOKING = "INSERT INTO Booking(idShop, username, date, timeSlot, description, isHomeAssistance)"+
            " VALUES (?,?,?,?,?,?)";

    public static final String UPDATE_STATUS = "UPDATE Booking SET state = ? WHERE id = ?";
    public static final String SELECT_BOOKING_USER = "SELECT B.idShop, B.id, S.name,B.date,B.timeSlot,B.description, B.state, B.isHomeAssistance FROM Booking B join Shop S ON B.idShop = S.id WHERE B.username = ?";
    public static final String SELECT_COMPLETED_WITHOUT_REVIEW_USER = "SELECT B.idShop, B.id, S.name, B.date, B.timeSlot, " +
            "B.description, B.state, B.isHomeAssistance FROM Booking B join Shop S ON B.idShop = S.id " +
            "LEFT JOIN reviews R ON R.booking_id = B.id WHERE B.username = ? AND B.state IN ('COMPLETED') " +
            "AND R.booking_id IS NULL";
    public static final String SELECT_BOOKING_SHOP = "SELECT id, username, date, timeSlot, description, state, isHomeAssistance FROM Booking WHERE idShop=?";
    public static final String SELECT_OCCUPIED_SLOTS_DATE = "SELECT timeSlot FROM Booking WHERE idShop = ? AND date = ? AND state IN ('PENDING', 'CONFIRMED')";
    public static final String SELECT_BOOKING_ID = "SELECT idShop, username, date, timeSlot, description, state, isHomeAssistance FROM Booking WHERE id = ?";
    public static final String SELECT_COMPLETED_WITHOUT_REVIEW_SHOP = "SELECT B.id, B.username, B.date, B.timeSlot, " +
            "B.description, B.state, B.isHomeAssistance FROM Booking B LEFT JOIN reviews R ON R.booking_id = B.id " +
            "WHERE B.username = ? AND B.idShop = ? AND B.state IN ('COMPLETED') AND R.booking_id IS NULL";

    public static final String EXIST_BOOKING = "SELECT 1 FROM Booking WHERE idShop = ? AND date = ? AND timeSlot = ? " + "AND state IN ('PENDING', 'CONFIRMED') LIMIT 1";
}
