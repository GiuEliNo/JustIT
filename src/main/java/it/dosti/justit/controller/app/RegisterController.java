package it.dosti.justit.controller.app;

import it.dosti.justit.dao.*;
import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.*;
import it.dosti.justit.utils.JustItLogger;

public class RegisterController {



    public boolean registerNewUser(RegisterBean registerBean) throws RegisterOnDbException, UserNotFoundException {

        ClientUserDAO dao = new ClientUserDAOJDBC();

        CoordinatesDAO coordDAO= new CoordinatesDAOAPI();
        Coordinates coord = coordDAO.getCoordinates(registerBean.getAddress()).join();
        if(coord!=null) {
            JustItLogger.getInstance().warn("Coordinates found");
            registerBean.setCoordinates(coord);
        }
        else{
            JustItLogger.getInstance().warn("Coordinates not found, proceding with empy beans");
        }

        if(dao.register(registerBean)){
            JustItLogger.getInstance().info("Register successful");
            return true;
        }
        else {
            JustItLogger.getInstance().error("Register failed");
            return false;
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) throws RegisterOnDbException, UserNotFoundException, ShopNotFoundException {
        TechnicianDAO dao = new TechnicianDAOJDBC();
        Integer shopId = dao.getShopIDbyName(registerBean.getShopName());
        if ( shopId == 0){  //TODO VALUTARE SE È EFFETTIVAMENTE UTILE FARE QUESTO CONTROLLO DOPPIO
            JustItLogger.getInstance().warn("Shop name not found");
            return false;
        }
        else {

            JustItLogger.getInstance().info("Register successful");
            return dao.register(registerBean);
        }

    }

    public boolean registerNewShop(ShopBean registerBean) throws RegisterOnDbException {

        ShopDAO dao = new ShopDAOJDBC();



        CoordinatesDAO coordDao = new CoordinatesDAOAPI();

        Coordinates coord = coordDao.getCoordinates(registerBean.getAddress()).join();

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

        return dao.registerShop(shop);
    }

}
