package it.dosti.justit.controller.app;

import it.dosti.justit.bean.ClientRegisterBean;
import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.dao.*;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dao.coordinates.CoordinatesDAO;
import it.dosti.justit.dao.coordinates.CoordinatesDAOAPI;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dao.tech.TechnicianDAO;
import it.dosti.justit.dao.user.UserDaoFactory;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.*;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.utils.JustItLogger;

import java.util.Objects;

public class RegisterController {

    public boolean registerNewUser(ClientRegisterBean registerBean) throws RegisterOnBackEndException {

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

        if(dao.registerUser(new ClientUser(registerBean.getName(), registerBean.getUsername(), registerBean.getEmail(), registerBean.getAddress(), registerBean.getCoordinates()), cred ))
        {
            JustItLogger.getInstance().info("Register successful");
            return true;
        }
        else {
            JustItLogger.getInstance().error("Register failed");
            return false;
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) throws RegisterOnBackEndException, ShopNotFoundException {
        TechnicianDAO dao = DaoFactory.getTechnicianDAO();
        registerBean.setShopId(dao.getShopIDbyName(registerBean.getShopName()));
        if ( registerBean.getShopId() == 0){
            JustItLogger.getInstance().warn("Shop name not found");
            return false;
        }
        else {

            JustItLogger.getInstance().info("Register successful");
            Credentials cred = new Credentials(registerBean.getUsername(), registerBean.getPassword());

            return dao.registerTech(new TechnicianUser(registerBean.getName(), registerBean.getUsername(), registerBean.getEmail(), registerBean.getShopId()), cred);
        }

    }

    public boolean registerNewShop(ShopBean registerBean) throws RegisterOnBackEndException {

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
