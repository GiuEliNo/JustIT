package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.RegisterQuery;
import it.dosti.justit.DB.query.ShopQuery;
import it.dosti.justit.model.ClientUser;
import it.dosti.justit.model.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDAOJDBC implements ShopDAO{

    public List<Shop> retrieveAllShops()
    {
        Statement stmt = null;
        Connection conn = null;
        List<Shop> shops = new ArrayList<>();
        try{

            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();

            ResultSet rs = ShopQuery.getAllShops(stmt);

            while(rs.next()){
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String description = rs.getString("description");
                String image = rs.getString("image");
                String openingHours = rs.getString("openingHours");
                boolean homeAssistance = rs.getBoolean("homeAssistance");
                Shop shop = new Shop(id, name, address, phone, email, description, image, openingHours, homeAssistance);
                shops.add(shop);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return shops;
    }



    public boolean registerShop(Shop shop) {

        Connection conn = null;

        try{
            conn = ConnectionDB.getInstance().connectDB();
            if(RegisterQuery.RegisterShop(conn, shop)) {
                return true;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Shop retrieveShopById(Integer shopId) {
        Statement stmt = null;
        Connection conn = null;
        List<Shop> shops = new ArrayList<>();
        try{

            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();

            ResultSet rs = ShopQuery.retrieveShopById(stmt, shopId);

            if (rs.next()) {
                return new Shop(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getString("openingHours"),
                        rs.getBoolean("homeAssistance")
                );

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
