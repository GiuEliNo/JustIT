package it.dosti.justit.controller.app;

import it.dosti.justit.dao.*;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.ClientUser;
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
        boolean updated = dao.updatePassword(username, newPassword, oldPassword);
        if (updated) {
            refreshSession();
        }
        return updated;
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
                if(updateSessionTechnician(username)){
                    return true;
                }
            }
            return true;
        }
        else return false;
    }

    public boolean updateAddress(String address) throws UpdateOnDBException, InvalidAddressException {
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
            ClientUserDAO dao = new ClientUserDAOJDBC();
            String username = SessionManager.getInstance().getLoggedUser().getUsername();
            ClientUser user = new ClientUser(username, address, coord);
            boolean updated = dao.updateAddress(user);
            if (updated) {
                refreshSession();
            }
            return updated;
        } else {

            ShopDAO dao = new ShopDAOJDBC();
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
        boolean updated = dao.updateNameShop(new Shop
                .Builder(name)
                .id(shop.getId())
                .build());
        if (updated) {
            refreshSession();
        }
        return updated;
    }

    public boolean updateDescriptionShop(String description) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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

    public boolean updatePhoneNumberShop(String phoneNumber) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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


    public boolean updateEmailShop(String email) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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

    public boolean updateOpeningHourShop(String openingHour) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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

    public boolean updateHomeAssistanceShop(boolean isHomeAssistance) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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

    public boolean updateImageShop(byte[] newImage) throws UpdateOnDBException {
        ShopDAO dao = new ShopDAOJDBC();
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
