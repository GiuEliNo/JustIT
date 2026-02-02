package it.dosti.justit;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.utils.JustItLogger;

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
        this.db = ConnectionDB.getInstance();
    }

    protected void connectToDB() throws SQLException {
        try(
                Connection conn = db.connectDB()
                )
        {
            JustItLogger.getInstance().info("[DB] Connected.");

        }catch(SQLException e){
            JustItLogger.getInstance().error("[DB] not connected",e);
        }

    }

    protected void initDataDirectory() {
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
                        throw new RuntimeException("DB resource not found");
                    }

                    Files.copy(input, dbPath);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize data directory", e);
        }
    }
}