package it.dosti.justit;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.DatabaseInitializationException;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.notification.NotificationDbObserver;
import it.dosti.justit.model.review.observer.ReviewCreatedPublisher;
import it.dosti.justit.utils.JustItLogger;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

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

        this.testingConnectToDb(isDemoMode);

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

            db.setDbPath(dbPath);


        } catch (DatabaseInitializationException | IOException e ) {
            JustItLogger.getInstance().error(e.getMessage(),e);
        }
    }

    @SuppressWarnings("java:S5443")
    private void initDemoMode() {

        try {
            JustItLogger.getInstance().info("Initializing Demo Mode");

            Path tempDbPath;
            if(SystemUtils.IS_OS_UNIX) {
                Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
                tempDbPath = Files.createTempFile("justit_demo_", ".db", attr);
            }
            else {
                tempDbPath = Files.createTempFile("justit_demo_", ".db");
                File f = tempDbPath.toFile();

                boolean r = f.setReadable(true, true);
                boolean w = f.setWritable(true, true);
                if (!r || !w) {
                    JustItLogger.getInstance().warn("[DEMO MODE] Warning: temp file permissive not restricted.");
                }
            }

                tempDbPath.toFile().deleteOnExit();
                db.setDbPath(tempDbPath);
                populateDbData();
                } catch (IOException | DatabaseInitializationException e) {
            JustItLogger.getInstance().error("Critic Error Demo Mode", e);
        }
    }


    private void populateDbData() throws DatabaseInitializationException {
        try(
                Connection conn = db.connectDB();
                Statement stmt = conn.createStatement()
                ){


            String createShop = "CREATE TABLE IF NOT EXISTS Shop (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "address TEXT, " +
                    "phone TEXT, " +
                    "email TEXT, " +
                    "description TEXT, " +
                    "image BLOB, " +
                    "openingHours TEXT, " +
                    "homeAssistance BOOLEAN, " +
                    "latitude REAL, " +
                    "longitude REAL" +
                    ");";

            stmt.execute(createShop);

            String createUser = "CREATE TABLE IF NOT EXISTS User (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL DEFAULT '', " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "email TEXT NOT NULL DEFAULT '', " +
                    "password TEXT NOT NULL DEFAULT '', " +
                    "address TEXT, " +
                    "latitude REAL, " +
                    "longitude REAL" +
                    ");";

            stmt.execute(createUser);

            String createTech = "CREATE TABLE IF NOT EXISTS Technician (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "username TEXT, " +
                    "password TEXT, " +
                    "email TEXT, " +
                    "shop INTEGER REFERENCES Shop (id) ON DELETE NO ACTION ON UPDATE CASCADE" +
                    ");";

            stmt.execute(createTech);

            String createBooking = "CREATE TABLE IF NOT EXISTS Booking (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idShop INTEGER REFERENCES Shop ON UPDATE CASCADE, " +
                    "username TEXT REFERENCES User ON UPDATE CASCADE, " +
                    "date TEXT NOT NULL, " +
                    "timeSlot TEXT NOT NULL, " +
                    "description TEXT, " +
                    "state TEXT DEFAULT 'PENDING', " +
                    "isHomeAssistance BOOLEAN, " +
                    "CHECK (state IN ('PENDING', 'CONFIRMED', 'REJECTED', 'COMPLETED'))" +
                    ");";

            stmt.execute(createBooking);

            String createReviews = "CREATE TABLE IF NOT EXISTS reviews (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL, " +
                    "stars INTEGER, " +
                    "review TEXT, " +
                    "shop_id INTEGER REFERENCES Shop, " +
                    "booking_id INTEGER REFERENCES Booking, " +
                    "username TEXT REFERENCES User" +
                    ");";

            stmt.execute(createReviews);

            String createNotif = "CREATE TABLE IF NOT EXISTS Notification (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL, " +
                    "shop_id INTEGER NOT NULL, " +
                    "booking_id INTEGER, " +
                    "review_id INTEGER, " +
                    "type TEXT NOT NULL, " +
                    "created_time TEXT NOT NULL, " +
                    "read INTEGER NOT NULL DEFAULT 0, " +
                    "message TEXT NOT NULL DEFAULT '', " +
                    "FOREIGN KEY(booking_id) REFERENCES Booking(id), " +
                    "FOREIGN KEY(review_id) REFERENCES reviews(id)" +
                    ");";

            stmt.execute(createNotif);


            String insertShops = "INSERT INTO Shop (name, address, phone, email, description, image, openingHours, homeAssistance, latitude, longitude) VALUES " +
                    "('TechPoint Roma', 'Via Appia Nuova 100', '0611122233', 'info@techpoint.it', 'Specialisti Apple e Samsung.', NULL, '09:00-19:00', 0, 41.883, 12.513), " +
                    "('ElettroCasa 24', 'Viale Marconi 50', '0644455566', 'help@elettrocasa.it', 'Riparazione elettrodomestici a domicilio.', NULL, '08:00-20:00', 1, 41.865, 12.470), " +
                    "('PC Master Race', 'Via Tiburtina 200', '0677788899', 'gaming@pcmaster.it', 'Assemblaggio e fix PC Gaming.', NULL, '10:00-19:30', 0, 41.900, 12.540), " +
                    "('FastFix Console', 'Piazza Bologna 5', '0699900011', 'console@fastfix.it', 'Riparazione rapida PS5/Xbox.', NULL, '09:30-18:30', 0, 41.913, 12.520), " +
                    "('Vintage Audio Lab', 'Trastevere 10', '0612312312', 'audio@lab.it', 'Restauro Hi-Fi e Giradischi.', NULL, '14:00-20:00', 0, 41.888, 12.468), " +
                    "('RiparaTutto Express', 'Via Tuscolana 99', '0655511122', 'express@riparatutto.it', 'Riparazioni economiche in 1 ora.', NULL, '08:30-19:30', 0, 41.850, 12.550), " +
                    "('iFix Roma Nord', 'Corso Francia 22', '0633399988', 'nord@ifix.it', 'Centro autorizzato dispositivi mobili.', NULL, '09:00-18:00', 0, 41.940, 12.480), " +
                    "('Domotica Facile', 'Via Eur 44', '0600011100', 'smart@domotica.it', 'Installazione e fix Smart Home.', NULL, '09:00-17:00', 1, 41.830, 12.470), " +
                    "('Old School Radio', 'Via Cavour 12', '0644477700', 'radio@oldschool.it', 'Riparazione radio d''epoca e valvole.', NULL, '15:00-19:00', 0, 41.895, 12.495), " +
                    "('Doctor Drone', 'Parco degli Acquedotti', '0688822211', 'fly@drone.it', 'Assistenza droni DJI e custom.', NULL, '10:00-18:00', 0, 41.840, 12.560);";
            stmt.executeUpdate(insertShops);


            String insertUsers = "INSERT INTO User (name, username, email, password, address, latitude, longitude) VALUES " +
                    "('Utente Demo', 'demo', 'demo@justit.it', 'password', 'Via Roma 1', 41.902, 12.496), " +
                    "('Mario Rossi', 'mario.rossi', 'mario@email.com', '123456', 'Via Milano 2', 41.900, 12.490), " +
                    "('Luigi Verdi', 'luigi.v', 'luigi@email.com', 'test', 'Via Napoli 3', 41.910, 12.500), " +
                    "('Giulia Bianchi', 'giulia.b', 'giulia@email.com', 'pass1', 'Via Torino 4', 41.920, 12.510), " +
                    "('Francesco Neri', 'francy.n', 'francy@email.com', 'pass2', 'Via Venezia 5', 41.930, 12.520), " +
                    "('Sofia Gialli', 'sofia.g', 'sofia@email.com', 'pass3', 'Via Firenze 6', 41.940, 12.530), " +
                    "('Alessandro Blu', 'alex.b', 'alex@email.com', 'pass4', 'Via Genova 7', 41.950, 12.540), " +
                    "('Martina Viola', 'marty.v', 'martina@email.com', 'pass5', 'Via Bari 8', 41.960, 12.550);";
            stmt.executeUpdate(insertUsers);

            String insertTechs = "INSERT INTO Technician (name, username, password, email, shop) VALUES " +
                    "('Tecnico Demo', 'tec.demo', 'password', 'tec@techpoint.it', 1), " +
                    "('Roberto Elettro', 'rob.tec', 'password', 'rob@elettrocasa.it', 2), " +
                    "('Marco PC', 'marco.pc', 'password', 'marco@pcmaster.it', 3), " +
                    "('Sara Console', 'sara.c', 'password', 'sara@fastfix.it', 4), " +
                    "('Fabio Audio', 'fabio.a', 'password', 'fabio@vintage.it', 5), " +
                    "('Luca Express', 'luca.ex', 'password', 'luca@riparatutto.it', 6), " +
                    "('Elena Apple', 'elena.app', 'password', 'elena@ifix.it', 7), " +
                    "('Davide Smart', 'davide.s', 'password', 'davide@domotica.it', 8);";
            stmt.executeUpdate(insertTechs);

            String insertBookings = "INSERT INTO Booking (idShop, username, date, timeSlot, description, state, isHomeAssistance) VALUES " +
                    "(1, 'demo', '2026-03-10', 'MORNING', 'Sostituzione batteria iPhone 13', 'PENDING', 0), " +
                    "(2, 'demo', '2026-03-15', 'AFTERNOON', 'Lavatrice perde acqua', 'CONFIRMED', 1), " +
                    "(1, 'mario.rossi', '2026-02-01', 'MORNING', 'Cambio schermo rotto', 'COMPLETED', 0), " +
                    "(3, 'luigi.v', '2026-02-05', 'AFTERNOON', 'PC non si accende', 'REJECTED', 0), " +
                    "(7, 'giulia.b', '2026-04-01', 'MORNING', 'Vetro iPad crepato', 'PENDING', 0), " +
                    "(8, 'demo', '2026-04-10', 'MORNING', 'Installazione telecamere WiFi', 'CONFIRMED', 1), " +
                    "(6, 'francy.n', '2026-03-20', 'EVENING', 'Sostituzione connettore ricarica', 'PENDING', 0), " +
                    "(10, 'sofia.g', '2026-05-05', 'MORNING', 'Drone caduto in acqua', 'REJECTED', 0), " +
                    "(1, 'alex.b', '2026-02-28', 'AFTERNOON', 'Backup dati urgente', 'PENDING', 0);";

            stmt.executeUpdate(insertBookings);

            String insertReviews = "INSERT INTO reviews (title, stars, review, shop_id, booking_id, username) VALUES " +
                    "('Veloce e onesto', 5, 'Riparazione perfetta in 1 ora.', 1, 3, 'mario.rossi'), " +
                    "('Non male', 3, 'Il tecnico è arrivato in ritardo.', 2, NULL, 'luigi.v'), " +
                    "('Grandissimi!', 5, 'Il mio PC vola ora.', 3, NULL, 'demo'), " +
                    "('Pessima esperienza', 1, 'Hanno rotto il pezzo di ricambio.', 4, NULL, 'giulia.b'), " +
                    "('Nostalgia pura', 5, 'Hanno ridato vita alla radio del nonno.', 5, NULL, 'francy.n'), " +
                    "('Troppo cari', 2, 'Prezzi sopra la media.', 7, NULL, 'sofia.g'), " +
                    "('Professionali', 4, 'Lavoro pulito, ma lenti.', 8, 6, 'demo'), " +
                    "('Droni top', 5, 'Sanno il fatto loro.', 10, NULL, 'marty.v');";

            stmt.executeUpdate(insertReviews);

            String insertNotifs = "INSERT INTO Notification (username, shop_id, booking_id, review_id, type, created_time, read, message) VALUES " +
                    "('demo', 2, 2, NULL, 'BOOKING_STATUS', '2026-02-10T10:00:00.453918123', 0, 'La tua prenotazione per la lavatrice è confermata.'), " +
                    "('demo', 1, NULL, NULL, 'BOOKING_STATUS', '2026-02-01T09:00:05.123987456', 1, 'Benvenuto in JustIt Demo Mode!'), " +
                    "('giulia.b', 7, 5, NULL, 'BOOKING_STATUS', '2026-04-01T09:00:00.789123456', 0, 'Siamo in attesa dei ricambi per il tuo iPad.'), " +
                    "('demo', 8, 6, NULL, 'BOOKING_STATUS', '2026-04-09T18:00:22.333444555', 0, 'Ricordati l''appuntamento domani per la Domotica.'), " +
                    "('sofia.g', 10, 8, NULL, 'BOOKING_STATUS', '2026-05-01T12:00:10.999888777', 0, 'Non ripariamo danni da acqua salata.'), " +
                    "('alex.b', 1, 9, NULL, 'BOOKING_STATUS', '2026-02-28T10:00:59.111222333', 0, 'Porta anche l''alimentatore per il backup.'), " +
                    "('francy.n', 5, NULL, 5, 'REVIEW_CREATED', '2026-03-05T15:00:01.000111222', 1, 'Grazie Francesco! È stato un piacere.');";

            stmt.executeUpdate(insertNotifs);

        }catch(SQLException e){
            JustItLogger.getInstance().error(e.getMessage(),e);
            throw new DatabaseInitializationException("Error initializing DB",e);
        }

    }




    private void testingConnectToDb(Boolean isDemoMode) {
        String test;
        if(Boolean.TRUE.equals(isDemoMode)) {
            test = "[DB DEMO MODE TEST]";
        }
        else{
            test = "[DB TEST]";
        }
        try(Connection conn =  db.connectDB()) {
            JustItLogger.getInstance().info(String.format("%s connected", test));
        }catch(SQLException e){
            JustItLogger.getInstance().error(String.format("%s not connected", test),e);
        }
    }
}
