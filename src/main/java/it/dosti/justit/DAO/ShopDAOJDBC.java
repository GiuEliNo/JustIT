package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.DB.query.RegisterQuery;
import it.dosti.justit.DB.query.ShopQuery;
import it.dosti.justit.model.Coordinates;
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
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                Shop shop = new Shop.Builder(name)
                        .id(id).address(address)
                        .phone(phone).email(email)
                        .description(description)
                        .image(image)
                        .openingHours(openingHours)
                        .homeAssistance(homeAssistance)
                        .coordinates(new Coordinates(latitude, longitude))
                        .build();

                shops.add(shop);
            }
            stmt.close();
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
        Connection conn = null;
        Statement stmt = null;
        try{

            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();

            ResultSet rs = ShopQuery.retrieveShopById(stmt, shopId);

            if (rs.next()) {
                return new Shop.Builder(
                        rs.getString("name"))
                        .id(rs.getInt("id"))
                        .address(rs.getString("address"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .description(rs.getString("description"))
                        .image(rs.getString("image"))
                        .openingHours(rs.getString("openingHours"))
                        .homeAssistance(rs.getBoolean("homeAssistance"))
                        .coordinates( new Coordinates(rs.getDouble("latitude"), rs.getDouble("longitude")))
                        .build();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
