package it.dosti.justit.dao;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.*;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.User;

import java.sql.*;

public class ClientUserDAOJDBC implements ClientUserDAO {

    @Override
    public User findByUsername(String username) throws UserNotFoundException{

        String sql = ClientQuery.SELECT_USERNAME;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ClientUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("address"),
                        new Coordinates(rs.getDouble("latitude"), rs.getDouble("longitude"))
                );

            }
            return null;
        }catch(SQLException e){
            throw new UserNotFoundException(String.format("Can't find the user %s in the db", username), e);
        }
    }

    @Override
    public boolean login(String username, String password) throws LoginFromDBException {
        String sql = LoginQuery.LOGIN_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {

            pstmt.setString(1, username);
            pstmt.setString(2, password);


            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return true;
            }
        }catch(SQLException e){
            throw new LoginFromDBException("Error checking login", e);
        }
        return false;
    }

    @Override
    public boolean register(RegisterBean registerBean) throws RegisterOnDbException {

        String sql = RegisterQuery.REGISTER_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {

            pstmt.setString(2, registerBean.getUsername());
            pstmt.setString(4, registerBean.getPassword());
            pstmt.setString(1, registerBean.getName());
            pstmt.setString(3, registerBean.getEmail());
            pstmt.setString(5, registerBean.getAddress());
            pstmt.setDouble(6, registerBean.getCoordinates().getLatitude());
            pstmt.setDouble(7, registerBean.getCoordinates().getLongitude());
            if(pstmt.executeUpdate() == 1) {
                return true;
            }
        }
        catch(SQLException e){
            throw new RegisterOnDbException("Error registering the new Client", e);
        }
        return false;
    }

    @Override
    public boolean updateName(String newName, String username) throws UpdateOnDBException {
        String sql = ClientQuery.UPDATE_USERNAME;
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
        String sql = ClientQuery.UPDATE_EMAIL;

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
        String sql = ClientQuery.UPDATE_PASSWORD;

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
