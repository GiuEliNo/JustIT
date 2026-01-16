package it.dosti.justit.DAO;

public interface TechnicianDAO {

     boolean loginTechnician(String username, String password);


     boolean registerTechnician(String username, String password, String email,String name, String shop);


     Integer getShopIDbyName(String shopName);
}
