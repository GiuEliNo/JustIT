package it.dosti.justit.dao;

import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.db.query.RegisterQuery;
import it.dosti.justit.db.query.ShopQuery;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.JustItLogger;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopDAOJDBC implements ShopDAO{

    public List<Shop> retrieveAllShops()
    {
        List<Shop> shops = new ArrayList<>();
        String sql = ShopQuery.SELECT_ALL_SHOPS;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){


            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String description = rs.getString("description");
                String openingHours = rs.getString("openingHours");
                boolean homeAssistance = rs.getBoolean("homeAssistance");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                Shop shop = new Shop.Builder(name)
                        .id(id).address(address)
                        .phone(phone).email(email)
                        .description(description)
                        .openingHours(openingHours)
                        .homeAssistance(homeAssistance)
                        .coordinates(new Coordinates(latitude, longitude))
                        .build();

                shops.add(shop);
            }
        }catch(SQLException e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return shops;
    }



    public boolean registerShop(Shop shop) throws RegisterOnDbException{

        String sql = RegisterQuery.REGISTER_SHOP;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setString(1, shop.getName());
            pstmt.setString(2, shop.getAddress());
            pstmt.setString(3, shop.getPhone());
            pstmt.setString(4, shop.getEmail());
            pstmt.setString(5, shop.getDescription());
            pstmt.setBytes(6, shop.getImage());
            pstmt.setString(7, shop.getOpeningHours());
            pstmt.setBoolean(8, shop.isHomeAssistance());
            if(shop.getCoordinates()!=null) {
                pstmt.setDouble(9, shop.getCoordinates().getLatitude());
                pstmt.setDouble(10, shop.getCoordinates().getLongitude());
            }

             if (pstmt.executeUpdate()==1){
                 return true;
             }
        }
        catch(SQLException e){
            throw new RegisterOnDbException("Error on Shop registering", e);
        }
        return false;
    }

    public Shop retrieveShopById(Integer shopId) throws ShopNotFoundException {
        String sql = ShopQuery.SELECT_SHOP_BY_ID;

        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){

            pstmt.setInt(1, shopId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
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

        }catch(SQLException e){
            throw new ShopNotFoundException("Shop not found");
        }
        return null;
    }


    public Image retrieveShopImageById(Integer shopId) throws ShopNotFoundException {
        String sql = ShopQuery.SELECT_SHOP_IMAGE_BY_ID;
        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ShopDefault.png")));
        try(
                Connection conn = ConnectionDB.getInstance().connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                )
        {
            pstmt.setInt(1,shopId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                JustItLogger.getInstance().info(String.format("Shop image by id %d", shopId));
                InputStream is = rs.getBinaryStream("image");
                Image checkImage = new Image(is);
                if(!checkImage.isError()){
                    return checkImage;
                }
            }
        } catch (SQLException e) {
            throw new ShopNotFoundException("Shop not found");
        }
        return defaultImage;
    }
}
