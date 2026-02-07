package it.dosti.justit.db;



import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {

    private static ConnectionDB instance = null;
    private Path dbPath;

    private boolean demoMode = false;

    private Connection keepAliveConnection = null;
    private static final String DEMO_CONNECTION_STRING = "jdbc:sqlite:memory:demoDb?mode=memory&cache=shared";



    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public void setConfiguration(Path path, boolean isDemo) {
        this.dbPath = path;
        this.demoMode = isDemo;
    }


    public Connection connectDB() throws SQLException {

        if(demoMode) {
            if(keepAliveConnection == null || !keepAliveConnection.isClosed()) {
                keepAliveConnection = DriverManager.getConnection(DEMO_CONNECTION_STRING);
            }
                return DriverManager.getConnection(DEMO_CONNECTION_STRING);
        }

        return DriverManager.getConnection("jdbc:sqlite:" + dbPath.toString());
    }

}
