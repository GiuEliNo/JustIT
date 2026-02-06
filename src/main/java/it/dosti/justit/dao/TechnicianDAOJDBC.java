package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.*;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;

import java.sql.*;

public class TechnicianDAOJDBC implements TechnicianDAO {


    public boolean login(Credentials cred) throws LoginFromDBException {

        String sql = LoginQuery.LOGIN_TECHNICIAN;

        try(
                Connection conn= ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setString(1, cred.getUser().getUsername());
            pstmt.setString(2, cred.getPassword());
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

    @Override
    public boolean register(Credentials cred) throws RegisterOnDbException {

        String sql1 = RegisterQuery.REGISTER_TECHNICIAN;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB()
        ) {
                try(PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
                    TechnicianUser user = (TechnicianUser)cred.getUser();
                    pstmt1.setString(1, user.getUsername());
                    pstmt1.setString(2, cred.getPassword());
                    pstmt1.setString(3, user.getEmail());
                    pstmt1.setString(4, user.getName());
                    pstmt1.setInt(5, user.getShopId());

                    if (pstmt1.executeUpdate() == 1) {
                        return true;
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
            throw new ShopNotFoundException("Shop not found", e);
        }
        return 0;
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

            throw new UserNotFoundException("User not found", e);
        }

        return null;
    }
    @Override
    public boolean updateName(String username, String newName) throws UpdateOnDBException {
        String sql = TechnicianQuery.UPDATE_NAME;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, newName);
            pstmt.setString(2, username);
            if(pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new UpdateOnDBException("Error updating the username", e);
        }
        return false;
    }

    @Override
    public boolean updateEmail(String username, String email) throws UpdateOnDBException {
        String sql = TechnicianQuery.UPDATE_EMAIL;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        )
        {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            if(pstmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new UpdateOnDBException("Error updating the email", e);
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException {
        String sql = TechnicianQuery.UPDATE_PASSWORD;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        )
        {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.setString(3, oldPassword);


            if (pstmt.executeUpdate() == 1){
                return true;
            }
        } catch (SQLException e) {
            throw new UpdateOnDBException("Error updating the password", e);
        }
        return false;
    }

}
