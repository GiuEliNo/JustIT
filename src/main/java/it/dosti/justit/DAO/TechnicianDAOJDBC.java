package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.RegisterQuery;
import it.dosti.justit.DB.query.ShopQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TechnicianDAOJDBC implements TechnicianDAO {


    public boolean loginTechnician(String email, String password){
        return true;
    }

    public boolean registerTechnician(String username, String password, String email,String name, String Shop) {

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();
            if (ShopQuery.getShop(stmt, Shop) != null) {
                return RegisterQuery.RegisterTechnician(conn, username, password, email, name, Shop);
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
