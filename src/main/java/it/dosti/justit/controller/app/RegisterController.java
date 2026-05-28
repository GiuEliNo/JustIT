package it.dosti.justit.controller.app;

import it.dosti.justit.dao.*;
import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.*;
import it.dosti.justit.utils.JustItLogger;

import java.util.Objects;

public class RegisterController {

    public boolean registerNewUser(RegisterBean registerBean) throws RegisterOnDbException {

        ClientUserDAO dao = DaoFactory.getClientUserDAO();
        CoordinatesDAO coordDAO= new CoordinatesDAOAPI();
        Coordinates coord = coordDAO.getCoordinates(registerBean.getAddress()).join();
        if(coord!=null) {
            JustItLogger.getInstance().warn("Coordinates found");
            registerBean.setCoordinates(coord);
        }
        else{
            JustItLogger.getInstance().warn("Coordinates not found, proceding with empy beans");
        }

        Credentials cred = new Credentials(registerBean.getUsername(), registerBean.getPassword());

        if(dao.register(registerBean, cred ))
        {
            JustItLogger.getInstance().info("Register successful");
            return true;
        }
        else {
            JustItLogger.getInstance().error("Register failed");
            return false;
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) throws RegisterOnDbException, ShopNotFoundException {
        TechnicianDAO dao = DaoFactory.getTechnicianDAO();
        registerBean.setShopId(dao.getShopIDbyName(registerBean.getShopName()));
        if ( registerBean.getShopId() == 0){
            JustItLogger.getInstance().warn("Shop name not found");
            return false;
        }
        else {

            JustItLogger.getInstance().info("Register successful");
            Credentials cred = new Credentials(registerBean.getUsername(), registerBean.getPassword());

            return dao.register(registerBean, cred);
        }

    }

    public boolean registerNewShop(ShopBean registerBean) throws RegisterOnDbException {

        ShopDAO dao = DaoFactory.getShopDAO();

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

    public boolean isUsernameAvailable(RegisterBean registerBean) {
        UserDaoFactory factory = new UserDaoFactory();


        if(Objects.equals(registerBean.getRole(), "CLIENT")){
            ClientUserDAO dao = (ClientUserDAO) factory.createUserDAO(true);
            return dao.isUsernameAvailable(registerBean.getUsername());
        }
        else{
            TechnicianDAO dao=(TechnicianDAO) factory.createUserDAO(false);
            return dao.isUsernameAvailable(registerBean.getUsername());
        }

    }


}
