package it.dosti.justit.controller.app;

import it.dosti.justit.api.NominatimService;
import it.dosti.justit.dao.*;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.TechnicianUser;

public class UpdateController {

    public boolean updateName(String newName) throws UpdateOnDBException, UserNotFoundException, ShopNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        if(dao.updateName(username, newName)){
            if(dao instanceof ClientUserDAO) {
                updateSessionUser(username);
            }
            else{
                updateSessionTechnician(username);
            }
            return true;
        }
        return false;
    }

    public boolean updatePassword(String newPassword, String oldPassword) throws UpdateOnDBException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        return dao.updatePassword(username, newPassword, oldPassword);
    }

    public boolean updateEmail(String email) throws UpdateOnDBException, UserNotFoundException, ShopNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        if(dao.updateEmail(username, email)){
            if(dao instanceof ClientUserDAO) {
                updateSessionUser(username);
            }
            else{
                updateSessionTechnician(username);
            }
            return true;
        }
        else return false;
    }


    private void updateSessionUser(String username) throws UserNotFoundException {
        ClientUserDAO dao = new ClientUserDAOJDBC();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
    }


    private boolean updateSessionTechnician(String username) throws UserNotFoundException, ShopNotFoundException {
        TechnicianDAO dao = new TechnicianDAOJDBC();
        ShopDAO shopDao = new ShopDAOJDBC();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
        if(SessionManager.getInstance().getLoggedUser() != null) {
            TechnicianUser technicianUser = (TechnicianUser) SessionManager.getInstance().getLoggedUser();
            SessionManager.getInstance().setCurrentShop(shopDao.retrieveShopById(technicianUser.getShopId()));
            return true;
        }
        return false;
    }

    public boolean updateNameShop(String name) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateNameShop(new Shop
                .Builder(name)
                .id(shop.getId())
                .build());
    }

    public boolean updateDescriptionShop(String description) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateDescriptionShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .description(description)
                .build());
    }

    public boolean updateAddressShop(String address) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        CoordinatesDAO coordDao= new CoordinatesDAOAPI();
        Coordinates coord = coordDao.getCoordinates(address).join();
        Shop updateShop;
        if(coord != null) {
            updateShop = new Shop.Builder(shop.getName())
                    .id(shop.getId())
                    .address(address)
                    .coordinates(coord)
                    .build();
        }
        else{
            JustItLogger.getInstance().warn("New coordinates not found");
            updateShop = new Shop.Builder(shop.getName())
                    .id(shop.getId())
                    .address(address)
                    .build();
        }
        return dao.updateAddressCoordinates(updateShop);
    }

    public boolean updatePhoneNumberShop(String phoneNumber) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updatePhoneShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .phone(phoneNumber)
                .build());
    }


    public boolean updateEmailShop(String email) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateEmailShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .email(email)
                .build());

    }

    public boolean updateOpeningHourShop(String openingHour) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateOpeningHoursShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .openingHours(openingHour)
                .build());
    }

    public boolean updateHomeAssistanceShop(boolean isHomeAssistance) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateHomeAssistanceShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .homeAssistance(isHomeAssistance)
                .build());
    }

    public boolean updateImageShop(byte[] newImage) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        return dao.updateImageShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .image(newImage)
                .build());
    }

}
