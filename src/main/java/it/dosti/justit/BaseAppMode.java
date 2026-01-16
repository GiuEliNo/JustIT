package it.dosti.justit;

import it.dosti.justit.DB.ConnectionDB;

import java.sql.SQLException;

public abstract class BaseAppMode implements AppMode {
    protected ConnectionDB db;

    public BaseAppMode() {
        this.db = new ConnectionDB();
    }

    protected void connectToDB() throws SQLException {
        db.connectDB();
        System.out.println("[DB] Connected.");
    }
}