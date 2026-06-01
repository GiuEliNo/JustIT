package it.dosti.justit.controller.app;

import it.dosti.justit.dao.*;
import it.dosti.justit.dao.clientuser.ClientUserDAO;
import it.dosti.justit.dao.coordinates.CoordinatesDAO;
import it.dosti.justit.dao.coordinates.CoordinatesDAOAPI;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dao.tech.TechnicianDAO;
import it.dosti.justit.dao.user.UserDAO;
import it.dosti.justit.dao.user.UserDaoFactory;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.model.user.TechnicianUser;

public class UpdateController {

    public boolean updateName(String newName) throws UpdateOnBackEndException, UserNotFoundException, ShopNotFoundException {
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

    public boolean updatePassword(String newPassword, String oldPassword) throws UpdateOnBackEndException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        boolean updated = dao.updatePassword(username, newPassword, oldPassword);
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updateEmail(String email) throws UpdateOnBackEndException, UserNotFoundException, ShopNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDAO dao = factory.createUserDAO(SessionManager.getInstance().isClient());
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        if(dao.updateEmail(username, email)){
            if(dao instanceof ClientUserDAO) {
                updateSessionUser(username);
            }
            else{
                if(updateSessionTechnician(username)){
                    return true;
                }
            }
            return true;
        }
        else return false;
    }

    public boolean updateAddress(String address) throws UpdateOnBackEndException, InvalidAddressException {
        Coordinates coord;
        try {
            CoordinatesDAO coordDao = new CoordinatesDAOAPI();
            coord = coordDao.getCoordinates(address).join();
        } catch (Exception e) {
            throw new InvalidAddressException("Invalid address", e);
        }
        if (coord == null) {
            throw new InvalidAddressException("Invalid address");
        }

        if (SessionManager.getInstance().isClient()) {
            ClientUserDAO dao = DaoFactory.getClientUserDAO();
            String username = SessionManager.getInstance().getLoggedUser().getUsername();
            ClientUser user = new ClientUser(username, address, coord);
            boolean updated = dao.updateAddress(user);
            if (updated) {
                refreshSession();
            }
            return updated;
        } else {

            ShopDAO dao = DaoFactory.getShopDAO();
            Shop shop = SessionManager.getInstance().getCurrentShop();
            Shop updateShop = new Shop.Builder(shop.getName())
                    .id(shop.getId())
                    .address(address)
                    .coordinates(coord)
                    .build();
            boolean updated = dao.updateAddressCoordinates(updateShop);
            if (updated) {
                refreshSession();
            }
            return updated;
        }
    }


    private void updateSessionUser(String username) throws UserNotFoundException {
        ClientUserDAO dao = DaoFactory.getClientUserDAO();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
    }


    private boolean updateSessionTechnician(String username) throws UserNotFoundException, ShopNotFoundException {
        TechnicianDAO dao = DaoFactory.getTechnicianDAO();
        ShopDAO shopDao = DaoFactory.getShopDAO();
        SessionManager.getInstance().setLoggedUser(dao.findByUsername(username));
        if(SessionManager.getInstance().getLoggedUser() != null) {
            TechnicianUser technicianUser = (TechnicianUser) SessionManager.getInstance().getLoggedUser();
            SessionManager.getInstance().setCurrentShop(shopDao.retrieveShopById(technicianUser.getShopId()));
            return true;
        }
        return false;
    }

    public boolean updateNameShop(String name) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateNameShop(new Shop
                .Builder(name)
                .id(shop.getId())
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updateDescriptionShop(String description) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateDescriptionShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .description(description)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updatePhoneNumberShop(String phoneNumber) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updatePhoneShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .phone(phoneNumber)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }


    public boolean updateEmailShop(String email) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateEmailShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .email(email)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;

    }

    public boolean updateOpeningHourShop(String openingHour) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateOpeningHoursShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .openingHours(openingHour)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updateHomeAssistanceShop(boolean isHomeAssistance) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateHomeAssistanceShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .homeAssistance(isHomeAssistance)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updateImageShop(byte[] newImage) throws UpdateOnBackEndException {
        ShopDAO dao = DaoFactory.getShopDAO();
        Shop shop = SessionManager.getInstance().getCurrentShop();
        boolean updated = dao.updateImageShop(new Shop
                .Builder(shop.getName())
                .id(shop.getId())
                .image(newImage)
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    private void refreshSession() {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        try {
            if (SessionManager.getInstance().isClient()) {
                updateSessionUser(username);
            } else {
                updateSessionTechnician(username);
            }
        } catch (UserNotFoundException | ShopNotFoundException e) {
            JustItLogger.getInstance().error("Failed to refresh session", e);
        }
    }

}
