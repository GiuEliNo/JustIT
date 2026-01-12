package it.dosti.justit.DB.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginQuery {
    private LoginQuery() {}

    public static ResultSet login(Statement stmt, String username, String password) throws SQLException {
        String sql;
        sql = String.format("SELECT * FROM User WHERE username = '%s' AND password = '%s'", username, password);
        return stmt.executeQuery(sql);
    }
}
