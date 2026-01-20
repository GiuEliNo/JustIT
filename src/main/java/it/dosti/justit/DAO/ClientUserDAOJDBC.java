package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.LoginQuery;
import it.dosti.justit.DB.query.RegisterQuery;
import it.dosti.justit.DB.query.UserQuery;
import it.dosti.justit.model.ClientUser;

import java.sql.*;

public class ClientUserDAOJDBC implements ClientUserDAO {

    @Override
    public ClientUser findByUsername(String username) {

        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            ResultSet rs = UserQuery.findByUsername(conn, username);

            if (rs.next()) {
                return new ClientUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            return UserQuery.updateName(conn, username, name) == 1;
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
            return UserQuery.updateEmail(conn, username, email) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String password) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getInstance().connectDB();
            return UserQuery.updatePassword(conn, username, password) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
