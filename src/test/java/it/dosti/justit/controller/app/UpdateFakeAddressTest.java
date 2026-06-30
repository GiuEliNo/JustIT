package it.dosti.justit.controller.app;


import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.dao.clientuser.ClientUserDAOJDBC;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.dao.shop.ShopDAOJDBC;
import it.dosti.justit.dao.tech.TechnicianDAO;
import it.dosti.justit.dao.tech.TechnicianDAOJDBC;
import it.dosti.justit.dao.user.UserDAO;
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

//Valerio Mazza
class UpdateFakeAddressTest {

    private static String sessionId;

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
        sessionId = SessionManager.getInstance().createSession();

    }

    @Test
    void testAddressClient() throws UserNotFoundException {
        UpdateController appController = new UpdateController();

        UserDAO dao = new ClientUserDAOJDBC();


        SessionManager.getInstance().getActiveSession(sessionId).setLoggedUser(dao.findByUsername(USERNAME));

        String newAddress = String.format("%s,%s,%s", ADDRESS_STREET, CITY, COUNTRY);

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        assertThrows(InvalidAddressException.class, () -> appController.updateAddress(session, newAddress));
    }

    @Test
    void testAddressShop() throws UserNotFoundException, ShopNotFoundException {


        UpdateController appController = new UpdateController();

        TechnicianDAO techDao = new TechnicianDAOJDBC();
        ShopDAO shopDao = new ShopDAOJDBC();

        User tech = techDao.findByUsername(TECH_USERNAME);
        SessionManager.getInstance().getActiveSession(sessionId).setLoggedUser(tech);
        SessionManager.getInstance().getActiveSession(sessionId).setCurrentShop(shopDao.retrieveShopById(TECH_SHOP));


        String newAddress = String.format("%s,%s,%s", ADDRESS_STREET, CITY, COUNTRY);

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);

        assertThrows(InvalidAddressException.class, () -> appController.updateAddress(session, newAddress));
    }

    @AfterEach
    void logout() {
        SessionManager.getInstance().logout(sessionId);
    }
}
