package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.RegisterQuery;
import it.dosti.justit.DB.query.ShopQuery;
import it.dosti.justit.DB.query.TechnicianQuery;
import it.dosti.justit.model.TechnicianUser;
import it.dosti.justit.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TechnicianDAOJDBC implements TechnicianDAO {


    public boolean loginTechnician(String email, String password){
        return true;
    }

    public boolean registerTechnician(String username, String password, String email,String name, String shopName) {

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();
            if (ShopQuery.getShop(stmt, shopName) != null) {
                return RegisterQuery.RegisterTechnician(conn, username, password, email, name, shopName);
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Integer getShopIDbyName(String shopName){
        Connection conn = null;
        Statement stmt = null;
        try{
            conn=ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();
            return ShopQuery.getShopID(stmt, shopName);
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = TechnicianQuery.findByUsername(conn, username);

            if (rs.next()) {
                return new TechnicianUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("shop")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
