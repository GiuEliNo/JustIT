package it.dosti.justit.DAO;

import it.dosti.justit.model.User;

public interface TechnicianDAO {

     boolean loginTechnician(String username, String password);


     boolean registerTechnician(String username, String password, String email,String name, String shop);


     Integer getShopIDbyName(String shopName);

    User findByUsername(String username);
}
