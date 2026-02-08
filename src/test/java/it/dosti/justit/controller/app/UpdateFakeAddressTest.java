package it.dosti.justit.controller.app;


import it.dosti.justit.dao.ClientUserDAOJDBC;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.dao.TechnicianDAO;
import it.dosti.justit.dao.TechnicianDAOJDBC;
import it.dosti.justit.dao.UserDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateFakeAddressTest {

    private static final String USERNAME = "demo";
    private static final String CITY = "Melly Town";
    private static final String ADDRESS_STREET = "Via Lorem Ipsum 42";
    private static final String COUNTRY = "India";
    private static final String TECH_USERNAME = "tec.demo";
    private static final Integer TECH_SHOP = 1;

    //setup account demo esistente già popolato nel DB
    @BeforeEach
    void setUp() {

        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().logout();

    }

    @Test
    void testAddressClient() throws UserNotFoundException {
        UpdateController appController = new UpdateController();

        UserDAO dao = new ClientUserDAOJDBC();

        SessionManager.getInstance().setLoggedUser(dao.findByUsername(USERNAME));

        String newAddress = String.format("%s,%s,%s", ADDRESS_STREET, CITY, COUNTRY);

        assertThrows(InvalidAddressException.class, () -> appController.updateAddress(newAddress));
    }

    @Test
    void testAddressShop() throws UserNotFoundException, ShopNotFoundException {
        UpdateController appController = new UpdateController();

        TechnicianDAO techDao = new TechnicianDAOJDBC();
        ShopDAO shopDao = new ShopDAOJDBC();

        User tech = techDao.findByUsername(TECH_USERNAME);
        SessionManager.getInstance().setLoggedUser(tech);
        SessionManager.getInstance().setCurrentShop(shopDao.retrieveShopById(TECH_SHOP));


        String newAddress = String.format("%s,%s,%s", ADDRESS_STREET, CITY, COUNTRY);

        assertThrows(InvalidAddressException.class, () -> appController.updateAddress(newAddress));
    }

    @AfterEach
    void logout() {
        SessionManager.getInstance().logout();
    }
}
