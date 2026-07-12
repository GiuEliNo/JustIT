package it.dosti.justit.dao.tech;

import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.*;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JustItLogger;

import java.sql.*;

public class TechnicianDAOJDBC implements TechnicianDAO {


    public boolean login(Credentials cred) throws LoginFromBackEndException {

        String sql = LoginQuery.LOGIN_TECHNICIAN;

        try(
                Connection conn= ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setString(1, cred.getUser());
            pstmt.setString(2, cred.getPassword());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(SQLException e){
            throw new LoginFromBackEndException("Error checking login", e);
        }
        return false;
    }

    @Override
    public boolean registerTech(TechnicianUser user, Credentials cred) throws RegisterOnBackEndException {

        String sql1 = RegisterQuery.REGISTER_TECHNICIAN;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB()
        ) {
                try(PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
                    pstmt1.setString(1, user.getUsername());
                    pstmt1.setString(2, cred.getPassword());
                    pstmt1.setString(3, user.getEmail());
                    pstmt1.setString(4, user.getName());
                    pstmt1.setInt(5, user.getShop().getId());

                    if (pstmt1.executeUpdate() == 1) {
                        return true;
                    }
                }
        }catch(SQLException e){
            throw new RegisterOnBackEndException("Error registering the new technician", e);
        }
        return false;
    }

    public Shop getShopbyName(String shopName) throws ShopNotFoundException {
        String sql1 = ShopQuery.SELECT_SHOP_BY_NAME;
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt1 = conn.prepareStatement(sql1)
                )
        {
            pstmt1.setString(1, shopName);
            ResultSet rs = pstmt1.executeQuery();
            if(rs.next()) {
                return new Shop.Builder(
                        rs.getString("name"))
                        .id(rs.getInt("id"))
                        .address(rs.getString("address"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .description(rs.getString("description"))
                        .image(rs.getBytes("image"))
                        .openingHours(rs.getString("openingHours"))
                        .homeAssistance(rs.getBoolean("homeAssistance"))
                        .coordinates( new Coordinates(rs.getDouble("latitude"), rs.getDouble("longitude")))
                        .build();
            }
        }
        catch(SQLException e){
            throw new ShopNotFoundException("Shop not found", e);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {


        String sql = TechnicianQuery.SELECT_BY_USERNAME;
        ShopDAO shopDAO = DaoFactory.getInstance().getShopDAO();

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt =conn.prepareStatement(sql)
        )
        {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Shop shop = shopDAO.retrieveShopById(rs.getInt("shop"));
                return new TechnicianUser(
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("email"),
                        shop
                );
            }

        } catch (SQLException e) {

            throw new UserNotFoundException("User not found", e);
        }
        catch (ShopNotFoundException n){
            JustItLogger.getInstance().error("Shop not found");
        }

        return null;
    }
    @Override
    public boolean updateName(String username, String newName) throws UpdateOnBackEndException {
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
            throw new UpdateOnBackEndException("Error updating the username", e);
        }
        return false;
    }

    @Override
    public boolean updateEmail(String username, String email) throws UpdateOnBackEndException {
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
            throw new UpdateOnBackEndException("Error updating the email", e);
        }

        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnBackEndException {
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
            throw new UpdateOnBackEndException("Error updating the password", e);
        }
        return false;
    }

        @Override
        public boolean isUsernameAvailable(String username) {
            String sql = RegisterQuery.USERNAME_AVAILABLE;
            try(
                    Connection conn = ConnectionDB.getInstance().connectDB();
                    PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {

                pstmt.setString(1, username);
                pstmt.setString(2, username);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return false;
                }
            }catch(SQLException e){
                JustItLogger.getInstance().error("Error checking if the username is available", e);
            }
            return true;
    }
}
