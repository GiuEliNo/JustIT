package it.dosti.justit.db.query;

public class BookingQuery {

    private BookingQuery() {}

    public static final String INSERT_BOOKING = "INSERT INTO Booking(idShop, username, date, timeSlot, description)"+
            " VALUES (?,?,?,?,?)";

    public static final String UPDATE_STATUS = "UPDATE Booking SET state = ? WHERE id = ?";
    public static final String SELECT_BOOKING_USER = "SELECT B.id, S.name,B.date,B.timeSlot,B.description, B.state FROM Booking B join Shop S ON B.idShop = S.id WHERE B.username = ?";
    public static final String SELECT_BOOKING_SHOP = "SELECT id, username, date, timeSlot, description, state FROM Booking WHERE idShop=?";
    public static final String CHECK_BOOKING = "SELECT 1 FROM Booking WHERE username = ? AND idShop = ? AND state IN ('COMPLETED') LIMIT 1";
    public static final String SELECT_OCCUPIED_SLOTS_DATE = "SELECT timeSlot FROM Booking WHERE idShop = ? AND date = ? AND state IN ('PENDING', 'CONFIRMED')";
    public static final String SELECT_BOOKING_ID = "SELECT idShop, username, date, timeSlot, description, state FROM Booking WHERE id = ?";
}
