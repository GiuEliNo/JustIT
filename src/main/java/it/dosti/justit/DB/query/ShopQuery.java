package it.dosti.justit.DB.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopQuery {
    private ShopQuery() {}

    public static ResultSet getAllShops(Statement stmt) throws SQLException {
        String sql;
        sql = "SELECT * FROM Shop";
        return stmt.executeQuery(sql);
    }

    public static ResultSet getShop(Statement stmt, String shop) throws SQLException {
        String sql;
        sql = String.format("SELECT * FROM Shop WHERE name = '%s'", shop);
        return stmt.executeQuery(sql);
    }
}
