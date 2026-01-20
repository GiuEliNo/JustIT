package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.*;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.User;

import java.sql.*;

public class ClientUserDAOJDBC implements ClientUserDAO {

    @Override
    public User findByUsername(String username) throws SQLException {

        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();

            ResultSet rs = ClientQuery.findByUsername(conn, username);

            if (rs.next()) {
                return new ClientUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email")
                );

            }
            return null;
        } finally {

        }
    }

    @Override
    public boolean login(String username, String password)
    {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {

            conn = ConnectionDB.getInstance().connectDB();


            ResultSet rs = LoginQuery.login(conn, username, password);

            if(rs.next()) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerClient(String username, String password, String name, String email) {

        Connection conn = null;
        try{
            conn = ConnectionDB.getInstance().connectDB();
            return RegisterQuery.RegisterUser(conn, username, password, email, name);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateName(String username, String name) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            return ClientQuery.updateName(conn, username, name) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateEmail(String username, String email) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            return ClientQuery.updateEmail(conn, username, email) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            int response = ClientQuery.updatePassword(conn, username, newPassword, oldPassword);
            return (response == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
