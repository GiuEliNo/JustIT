package it.dosti.justit.controller.app;

import it.dosti.justit.dao.CoordinatesDAO;
import it.dosti.justit.dao.CoordinatesDAOAPI;
import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.model.*;
import it.dosti.justit.utils.JustItLogger;

import java.sql.SQLException;

public class RegisterController {



    public boolean registerNewUser(RegisterBean registerBean) throws SQLException {

        ClientUserModel clientUserModel = new ClientUserModel();

        if(clientUserModel.registerClient(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail())){
            JustItLogger.getInstance().info("Register successful");
            return true;
        }
        else {
            JustItLogger.getInstance().error("Register failed");
            return false;
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) {
        TechnicianModel technicianModel = new TechnicianModel();
        Integer shopId = technicianModel.getShopIDbyName(registerBean.getShopName());
        if ( shopId == 0){
            JustItLogger.getInstance().warn("Shop name not found");
            JustItLogger.getInstance().error("Register Failed");
            return false;
        }
        else {

            JustItLogger.getInstance().info("Register successful");
            return technicianModel.registerTechnician(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail(), registerBean.getShopName());
        }

    }

    public boolean registerNewShop(ShopBean registerBean) {

        ShopModel shopModel = new ShopModel();



        CoordinatesDAO coordDap = new CoordinatesDAOAPI();

        Coordinates coord = coordDap.getCoordinates(registerBean.getAddress()).join();

        if(coord != null) {
            JustItLogger.getInstance().info("Coordinates found");
            registerBean.setCoordinates(coord);
        }
        else {
            JustItLogger.getInstance().warn("Coordinates not found, proceeding with empty bean");
        }

        Shop shop = new Shop.Builder(registerBean.getName())
                .address(registerBean.getAddress())
                .phone(registerBean.getPhone())
                .email(registerBean.getEmail())
                .description(registerBean.getDescription())
                .image(registerBean.getImage())
                .openingHours(registerBean.getOpeningHours())
                .homeAssistance(registerBean.isHomeAssistance())
                .coordinates(registerBean.getCoordinates())
                .build();

        return shopModel.registerShop(shop);
    }

}
