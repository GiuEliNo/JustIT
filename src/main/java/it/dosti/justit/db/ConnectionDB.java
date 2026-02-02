package it.dosti.justit.db;


import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {

    private static ConnectionDB instance = null;
    private Path dbPath;



    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public void setDBPath(Path path) {
        this.dbPath = path;
    }


    public Connection connectDB() throws SQLException {

        return DriverManager.getConnection("jdbc:sqlite:" + dbPath.toString());
    }

}
