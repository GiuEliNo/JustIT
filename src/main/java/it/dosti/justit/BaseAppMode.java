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
                    "booking_id INTEGER REFERENCES Booking, " +
                    "username TEXT REFERENCES User" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Notification (" +
                    "id           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username     TEXT    NOT NULL, " +
                    "shop_id      INTEGER NOT NULL, " +
                    "booking_id   INTEGER, " +
                    "review_id    INTEGER, " +
                    "type         TEXT    NOT NULL, " +
                    "message      TEXT    NOT NULL, " +
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


            String insertShops = "INSERT INTO Shop (name, address, phone, email, description, image, openingHours, homeAssistance, latitude, longitude) VALUES " +
                    "('TechFix Roma', 'Via Appia Nuova 123', '0612345678', 'info@techfix.it', 'Riparazioni rapide smartphone e PC.', NULL, '09:00-19:00', 1, 41.883, 12.513), " +
                    "('PC Wizard', 'Viale Marconi 45', '0687654321', 'support@pcwizard.com', 'Specialisti in assemblaggio e riparazione PC.', NULL, '10:00-18:30', 0, 41.865, 12.470), " +
                    "('ElettroCasa', 'Via Tuscolana 900', '0611223344', 'contact@elettrocasa.it', 'Riparazione grandi elettrodomestici a domicilio.', NULL, '08:00-20:00', 1, 41.850, 12.550), " +
                    "('MobileMedic', 'Piazza Bologna 12', '0655667788', 'help@mobilemedic.it', 'Sostituzione schermi in 30 minuti.', NULL, '09:00-19:30', 0, 41.913, 12.520), " +
                    "('GameConsole Pros', 'Via del Corso 10', '0699887766', 'fix@consoles.it', 'Esperti in PS5, Xbox e Switch.', NULL, '10:00-19:00', 0, 41.905, 12.480), " +
                    "('DataRescue Lab', 'Via Ostiense 30', '0622334455', 'recovery@datarescue.it', 'Recupero dati da Hard Disk danneggiati.', NULL, '09:00-18:00', 0, 41.870, 12.480), " +
                    "('SmartHome Solutions', 'Viale Europa 55', '0633445566', 'info@smarthome.it', 'Domotica e impianti di allarme.', NULL, '09:00-18:00', 1, 41.830, 12.470), " +
                    "('Vintage Audio Repair', 'Trastevere 5', '0644556677', 'audio@vintage.it', 'Restauro giradischi e amplificatori.', NULL, '14:00-20:00', 0, 41.888, 12.468), " +
                    "('QuickFix 24/7', 'Stazione Termini', '0655443322', 'urgent@quickfix.it', 'Riparazioni d''emergenza.', NULL, '00:00-23:59', 0, 41.901, 12.501), " +
                    "('Green Energy Fix', 'Via Nomentana 200', '0666778899', 'solar@greenfix.it', 'Manutenzione pannelli solari e inverter.', NULL, '08:00-17:00', 1, 41.920, 12.530);";

            stmt.executeUpdate(insertShops);


            String insertUsers = "INSERT INTO User (name, username, email, password, address, latitude, longitude) VALUES " +
                    "('Mario Rossi', 'mario.rossi', 'mario@email.com', 'password', 'Via Roma 1', 41.902, 12.496), " +
                    "('Luigi Verdi', 'luigi.v', 'luigi@email.com', '123456', 'Via Milano 2', 41.900, 12.490), " +
                    "('Giulia Bianchi', 'giulia.b', 'giulia@email.com', 'secret', 'Via Napoli 3', 41.910, 12.500), " +
                    "('Francesca Neri', 'francy', 'francy@email.com', 'pass', 'Via Torino 4', 41.920, 12.510), " +
                    "('Paolo Gialli', 'paolo.g', 'paolo@email.com', 'qwerty', 'Via Venezia 5', 41.930, 12.520), " +
                    "('Sofia Blu', 'sofia.blu', 'sofia@email.com', '123123', 'Via Firenze 6', 41.940, 12.530), " +
                    "('Marco Viola', 'marco.v', 'marco@email.com', 'admin', 'Via Genova 7', 41.950, 12.540), " +
                    "('Elena Arancio', 'elena.a', 'elena@email.com', 'guest', 'Via Bari 8', 41.960, 12.550), " +
                    "('Luca Marrone', 'luca.m', 'luca@email.com', 'demo', 'Via Palermo 9', 41.970, 12.560), " +
                    "('Anna Rosa', 'anna.r', 'anna@email.com', 'test', 'Via Verona 10', 41.980, 12.570);";

            stmt.executeUpdate(insertUsers);

            String insertTechnicians = "INSERT INTO Technician (name, username, password, email, shop) VALUES " +
                    "('Giovanni Tec', 'gio.tec', 'pass', 'gio@techfix.it', 1), " +
                    "('Roberto PC', 'rob.pc', 'pass', 'rob@pcwizard.com', 2), " +
                    "('Carlo Elettro', 'carlo.el', 'pass', 'carlo@elettrocasa.it', 3), " +
                    "('Sara Mobile', 'sara.mob', 'pass', 'sara@mobilemedic.it', 4), " +
                    "('Matteo Game', 'matteo.game', 'pass', 'matteo@consoles.it', 5), " +
                    "('Davide Data', 'davide.data', 'pass', 'davide@datarescue.it', 6), " +
                    "('Simone Smart', 'simo.smart', 'pass', 'simo@smarthome.it', 7), " +
                    "('Fabio Audio', 'fabio.audio', 'pass', 'fabio@vintage.it', 8), " +
                    "('Andrea Fast', 'andrea.fast', 'pass', 'andrea@quickfix.it', 9), " +
                    "('Claudio Solar', 'claudio.sol', 'pass', 'claudio@greenfix.it', 10);";

            String insertBookings = "INSERT INTO Booking (idShop, username, date, timeSlot, description, state, isHomeAssistance) VALUES " +
                    "(1, 'mario.rossi', '2026-02-10', '09:00', 'Sostituzione batteria iPhone', 'PENDING', 0), " +
                    "(1, 'luigi.v', '2026-02-12', '14:30', 'Schermo rotto Samsung', 'CONFIRMED', 0), " +
                    "(3, 'mario.rossi', '2026-02-15', '10:00', 'Lavatrice non scarica acqua', 'CONFIRMED', 1), " +
                    "(2, 'giulia.b', '2026-01-20', '16:00', 'PC lento e virus', 'COMPLETED', 0), " +
                    "(5, 'francy', '2026-03-01', '11:00', 'PS5 surriscaldamento', 'PENDING', 0), " +
                    "(4, 'paolo.g', '2026-02-05', '09:30', 'Cambio vetro iPad', 'REJECTED', 0), " +
                    "(7, 'sofia.blu', '2026-02-28', '15:00', 'Installazione allarme', 'CONFIRMED', 1), " +
                    "(1, 'marco.v', '2025-12-10', '10:00', 'Pulizia connettore', 'COMPLETED', 0), " +
                    "(8, 'elena.a', '2026-02-18', '17:00', 'Giradischi Technics non gira', 'PENDING', 0), " +
                    "(10, 'mario.rossi', '2026-04-05', '09:00', 'Controllo annuale pannelli', 'CONFIRMED', 1);";

            stmt.executeUpdate(insertBookings);

            String insertReviews = "INSERT INTO reviews (title, stars, review, shop_id, username) VALUES " +
                    "('Ottimo servizio!', 5, 'Veloci e professionali, consigliatissimo.', 1, 'marco.v'), " +
                    "('Discreto', 3, 'Riparazione ok, ma tempi un po'' lunghi.', 2, 'giulia.b'), " +
                    "('Tecnico preparato', 5, 'Ha risolto il problema della lavatrice in 20 min.', 3, 'mario.rossi'), " +
                    "('Non male', 4, 'Prezzo onesto.', 1, 'luigi.v'), " +
                    "('Pessima esperienza', 1, 'Non hanno risolto nulla.', 4, 'paolo.g'), " +
                    "('Salvati i miei dati!', 5, 'Pensavo di aver perso tutto, grazie!', 6, 'francy'), " +
                    "('Impianto perfetto', 5, 'Lavoro pulito e preciso.', 7, 'sofia.blu'), " +
                    "('Vintage top', 4, 'Bravi ma costosi.', 8, 'elena.a'), " +
                    "('Aperti di notte!', 5, 'Mi hanno salvato la vita alle 2 di notte.', 9, 'luca.m'), " +
                    "('Tutto ok', 4, 'Buon servizio.', 10, 'mario.rossi');";

            stmt.executeUpdate(insertReviews);

            stmt.executeUpdate(insertTechnicians);


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
