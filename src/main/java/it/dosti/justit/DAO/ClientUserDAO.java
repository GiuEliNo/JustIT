package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.LoginQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientUserDAO {
    public ClientUserDAO() {}

    public boolean login(String username, String password)
    {
        Statement stmt = null;
        Connection conn = null;
        try {

            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();

            ResultSet rs = LoginQuery.login(stmt, username, password);

            if(rs.next()) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
