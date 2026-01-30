package it.dosti.justit.DB.query;

import java.sql.*;

public class ShopQuery {
    private ShopQuery() {}

    public static ResultSet getAllShops(Statement stmt) throws SQLException {
        String sql;
        sql = "SELECT * FROM Shop";
        return stmt.executeQuery(sql);
    }

    public static ResultSet getShop(Connection conn, String shop) throws SQLException {
        String sql;
        sql = "SELECT * FROM Shop WHERE name = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shop);
            return pstmt.executeQuery();
        }

    }

    public static Integer getShopID(Connection conn, String shop) throws SQLException {
        String sql;
        sql= "SELECT ID FROM Shop WHERE name = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shop);
            return pstmt.executeQuery().getInt(1);
        }
    }

    public static ResultSet retrieveShopById(Statement stmt, Integer shopId) throws SQLException {
        String sql;
        sql = String.format("SELECT * FROM Shop WHERE id = %d", shopId);
        return stmt.executeQuery(sql);
    }
}
