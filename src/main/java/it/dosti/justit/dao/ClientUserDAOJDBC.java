package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.*;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.user.User;

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
    public boolean login(Credentials cred) throws LoginFromDBException {
        String sql = LoginQuery.LOGIN_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {

            pstmt.setString(1, cred.getUser().getUsername());
            pstmt.setString(2, cred.getPassword());


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
    public boolean register(Credentials cred) throws RegisterOnDbException {

        String sql = RegisterQuery.REGISTER_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            ClientUser user = (ClientUser) cred.getUser();
            pstmt.setString(2, user.getUsername());
            pstmt.setString(4, cred.getPassword());
            pstmt.setString(1, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(5, user.getAddress());
            pstmt.setDouble(6, user.getCoordinates().getLatitude());
            pstmt.setDouble(7, user.getCoordinates().getLongitude());
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
    public boolean updateName(String username, String newName) throws UpdateOnDBException {
        String sql = ClientQuery.UPDATE_NAME;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ) {
            pstmt.setString(2, username);
            pstmt.setString(1, newName);
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

    @Override
    public String getAddress(String username) {
        return "";
    }

}
