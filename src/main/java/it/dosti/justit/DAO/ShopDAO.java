package it.dosti.justit.DAO;

import it.dosti.justit.DB.ConnectionDB;
import it.dosti.justit.model.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {
    public ShopDAO() {};

    public List<Shop>  getShops()
    {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Shop> shops = new ArrayList<>();
        try{

            conn = ConnectionDB.getInstance().connectDB();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from Shop");
            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String description = rs.getString("description");
                String image = rs.getString("image");
                String openingHours = rs.getString("openingHours");
                boolean homeAssistance = rs.getBoolean("homeAssistance");
                Shop shop = new Shop(name, address, phone, email, description, image, openingHours, homeAssistance);
                shops.add(shop);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return shops;
    }
}
