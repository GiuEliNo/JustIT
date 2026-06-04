package it.dosti.justit;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.DatabaseInitializationException;
import it.dosti.justit.model.booking.observer.BookingStatusPublisher;
import it.dosti.justit.model.notification.NotificationDbObserver;
import it.dosti.justit.model.review.observer.ReviewCreatedPublisher;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.utils.ZipExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;


public abstract class BaseAppMode implements AppMode {
    protected ConnectionDB db;
    protected Path dataDir;
    protected Path dbPath;

    protected BaseAppMode() {
        this.db= ConnectionDB.getInstance();
        if (!SessionManager.getInstance().getPersistencyType().equals(PersistencyType.DEMOMODE)) {
            if(SessionManager.getInstance().getPersistencyType().equals(PersistencyType.DATABASE)) {
                this.initDBRealMode();
                this.testingConnectToDb();
            }
            else{
                this.initJsonFilesystem();
                JustItLogger.getInstance().info("FS mode is running");
            }


        }
        else{
            JustItLogger.getInstance().info("Demo mode is running");
        }

        NotificationDbObserver notificationObserver = new NotificationDbObserver();
        BookingStatusPublisher.getInstance().registerObserver(notificationObserver);
        ReviewCreatedPublisher.getInstance().registerObserver(notificationObserver);

    }

    private void initDBRealMode() {
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




    private void testingConnectToDb() {
        String test;
        test = "[DB TEST]";
        try(Connection conn =  db.connectDB()) {
            JustItLogger.getInstance().info(String.format("%s connected", test));
        }catch(SQLException e){
            JustItLogger.getInstance().error(String.format("%s not connected", test),e);
        }
    }


    private void initJsonFilesystem() {
        Path baseDir = Paths.get(System.getProperty("user.dir"));
        dataDir = baseDir.resolve("data/json");
        dbPath = dataDir.resolve("jsons.zip");
        if(!Files.exists(dataDir)) {
            ZipExtractor zipExtractor = new ZipExtractor();
            zipExtractor.extractZip(dataDir, "/jsons.zip");
        }
    }




}
