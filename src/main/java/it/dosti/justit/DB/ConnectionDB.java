package it.dosti.justit.DB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {

    private static ConnectionDB instance = null;
    private Connection con = null;


    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }


    public synchronized Connection connectDB() throws SQLException {
        if (con == null) {
            try {
                String path = this.getClass().getResource("/DB/justit.db").getPath();
                System.out.println(path);
                this.con = DriverManager.getConnection("jdbc:sqlite:"+this.getClass().getResource("/DB/justit.db").getPath());
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace(System.err);
            }
        }
        return this.con;
    }
}
