package it.dosti.justit;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.DatabaseInitializationException;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.notification.NotificationDbObserver;
import it.dosti.justit.model.review.observer.ReviewCreatedPublisher;
import it.dosti.justit.utils.JustItLogger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseAppMode implements AppMode {
    protected ConnectionDB db;
    protected Path dataDir;
    protected Path dbPath;
    protected boolean isDemoMode;

    protected BaseAppMode(boolean isDemoMode) {
        this.isDemoMode = isDemoMode;
        this.db= ConnectionDB.getInstance();
        if(this.isDemoMode) {
            this.initDemoMode();
        }else{
            this.initRealMode();
        }

        this.testingConnectToDb();

        NotificationDbObserver notificationObserver = new NotificationDbObserver();
        BookingStatusPublisher.getInstance().registerObserver(notificationObserver);
        ReviewCreatedPublisher.getInstance().registerObserver(notificationObserver);
    }

    private void initRealMode() {
        try {
            Path baseDir = Paths.get(System.getProperty("user.dir"));

            dataDir = baseDir.resolve("data");
            dbPath = dataDir.resolve("justit.db");

            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

            if (!Files.exists(dbPath)) {
                try (InputStream input = getClass().getResourceAsStream("/DB/justit.db")) {

                    if (input == null) {
                        throw new DatabaseInitializationException("Database not found");
                    }

                    Files.copy(input, dbPath);
                }
            }

            db.setConfiguration(dbPath, false);


        } catch (DatabaseInitializationException | IOException e ) {
            JustItLogger.getInstance().error(e.getMessage(),e);
        }
    }

    private void initDemoMode() {
        try{
            JustItLogger.getInstance().info("Initializing Demo Mode");
            db.setConfiguration(null, true);

            populateDbData();
        }
        catch(DatabaseInitializationException e){
            JustItLogger.getInstance().error(e.getMessage(),e);
        }
    }


    private void populateDbData() throws DatabaseInitializationException {
        try(
                Connection conn = db.connectDB();
                Statement stmt = conn.createStatement()
                ){

            stmt.execute("CREATE TABLE IF NOT EXISTS Shop (" +
                    "id             INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    "name           TEXT     NOT NULL, " +
                    "address        TEXT, " +
                    "phone          TEXT, " +
                    "email          TEXT, " +
                    "description    TEXT, " +
                    "image          BLOB, " +
                    "openingHours   TEXT, " +
                    "homeAssistance BOOLEAN, " +
                    "latitude       REAL, " +
                    "longitude      REAL" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "id        INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name      TEXT    NOT NULL DEFAULT '', " +
                    "username  TEXT    NOT NULL UNIQUE, " +
                    "email     TEXT    NOT NULL DEFAULT '', " +
                    "password  TEXT    NOT NULL DEFAULT '', " +
                    "address   TEXT, " +
                    "latitude  REAL, " +
                    "longitude REAL" +
                    ");");

            stmt.execute( "CREATE TABLE IF NOT EXISTS Technician (" +
                    "id       INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name     TEXT    NOT NULL, " +
                            "username TEXT, " +
                            "password TEXT, " +
                            "email    TEXT, " +
                            "shop     INTEGER REFERENCES Shop (id) ON DELETE NO ACTION ON UPDATE CASCADE" +
                            ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS reviews (" +
                    "id       INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title    TEXT    NOT NULL, " +
                    "stars    INTEGER, " +
                    "review   TEXT, " +
                    "shop_id  INTEGER REFERENCES Shop, " +
                    "username TEXT" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Notification (" +
                    "id           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username     TEXT    NOT NULL, " +
                    "shop_id      INTEGER NOT NULL, " +
                    "booking_id   INTEGER, " +
                    "review_id    INTEGER, " +
                    "type         TEXT    NOT NULL, " +
                    "created_time TEXT    NOT NULL, " +
                    "read         INTEGER NOT NULL DEFAULT 0, " +
                    "FOREIGN KEY (booking_id) REFERENCES Booking (id), " +
                    "FOREIGN KEY (review_id) REFERENCES reviews (id)" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Booking (" +
                    "id               INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idShop           INTEGER REFERENCES Shop ON UPDATE CASCADE, " +
                    "username         TEXT    REFERENCES User ON UPDATE CASCADE, " +
                    "date             TEXT    NOT NULL, " +
                    "timeSlot         TEXT    NOT NULL, " +
                    "description      TEXT, " +
                    "state            TEXT    DEFAULT 'PENDING', " +
                    "isHomeAssistance BOOLEAN, " +
                    "CHECK (state IN ('PENDING', 'CONFIRMED', 'REJECTED', 'COMPLETED'))" +
                    ");");


        }catch(SQLException e){
            JustItLogger.getInstance().error(e.getMessage(),e);
            throw new DatabaseInitializationException("Error initializing DB",e);
        }

    }




    private void testingConnectToDb() {
        try(Connection conn =  db.connectDB()) {
            JustItLogger.getInstance().info("[DB TEST] Connected.");
        }catch(SQLException e){
            JustItLogger.getInstance().error("[DB TEST] not connected",e);
        }
    }
}
