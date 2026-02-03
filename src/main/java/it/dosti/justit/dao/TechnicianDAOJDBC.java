package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.LoginQuery;
import it.dosti.justit.db.query.RegisterQuery;
import it.dosti.justit.db.query.ShopQuery;
import it.dosti.justit.db.query.TechnicianQuery;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.TechnicianUser;
import it.dosti.justit.model.User;

import java.sql.*;

public class TechnicianDAOJDBC implements TechnicianDAO {


    public boolean loginTechnician(String username, String password) throws LoginFromDBException {

        String sql = LoginQuery.LOGIN_TECHNICIAN;

        try(
                Connection conn= ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(SQLException e){
            throw new LoginFromDBException("Error checking login", e);
        }
        return false;
    }

    public boolean registerTechnician(String username, String password, String email,String name, String shopName) throws RegisterOnDbException {

        String sql1 = RegisterQuery.REGISTER_TECHNICIAN;
        String sql2 = ShopQuery.SELECT_SHOP_BY_NAME;
        boolean shopExist = false;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB()) {

            try(PreparedStatement pstmt2 = conn.prepareStatement(sql2)){

                pstmt2.setString(1, shopName);
                try(ResultSet rs =pstmt2.executeQuery()){
                    if(rs.next()) {
                    shopExist = true;
                }
                }
            }

            if(shopExist){
                try(PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
                    pstmt1.setString(1, username);
                    pstmt1.setString(2, password);
                    pstmt1.setString(3, email);
                    pstmt1.setString(4, name);
                    pstmt1.setString(5, shopName);


                    if (pstmt1.executeUpdate() == 1) {
                        return true;

                    }
                }
            }
        }catch(SQLException e){
            throw new RegisterOnDbException("Error registering the new technician", e);
        }
        return false;
    }

    public Integer getShopIDbyName(String shopName) throws ShopNotFoundException {
        String sql1 = ShopQuery.SELECT_ID_SHOP;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt1 = conn.prepareStatement(sql1)
                )
        {
            pstmt1.setString(1, shopName);
            ResultSet rs = pstmt1.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        }
        catch(SQLException e){
            throw new ShopNotFoundException("Shop not found");
        }
        return null;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {


        String sql = TechnicianQuery.SELECT_BY_USERNAME;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt =conn.prepareStatement(sql)
        )
        {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

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

            throw new UserNotFoundException("User not found");
        }

        return null;
    }

}
