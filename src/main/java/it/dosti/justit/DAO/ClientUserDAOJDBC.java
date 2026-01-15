package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.LoginQuery;
import it.dosti.justit.DB.query.RegisterQuery;

import java.sql.*;

public class ClientUserDAOJDBC implements ClientUserDAO {
    public ClientUserDAOJDBC() {}

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
}
