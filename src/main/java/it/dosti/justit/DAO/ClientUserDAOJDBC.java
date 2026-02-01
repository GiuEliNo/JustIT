package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.*;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.User;
import it.dosti.justit.utils.JustItLogger;

import java.sql.*;

public class ClientUserDAOJDBC implements ClientUserDAO {

    @Override
    public User findByUsername(String username) throws SQLException {

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
                        rs.getString("email")
                );

            }
            return null;
        }catch(SQLException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean login(String username, String password)
    {
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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean registerClient(String username, String password, String name, String email) {

        String sql = RegisterQuery.REGISTER_USER;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            if(pstmt.executeUpdate() == 1) {
                return true;
            }
        }
        catch(SQLException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateName(String newName, String username) {
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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateEmail(String username, String email) {
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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) {
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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

}
