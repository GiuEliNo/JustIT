package it.dosti.justit.controller.app;


import it.dosti.justit.dao.ClientUserDAOJDBC;
import it.dosti.justit.dao.UserDAO;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.InvalidAddressException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateFakeAddressTest {

    private static final String USERNAME = "demo";
    private static final String CITY = "Melly Town";
    private static final String ADDRESS_STREET = "Via Lorem Ipsum 42";
    private static final String COUNTRY = "India";


    @BeforeEach
    void setUp() throws UserNotFoundException {
        //setup account demo esistente già popolato nel DB
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().logout();

        UserDAO dao = new ClientUserDAOJDBC();

        SessionManager.getInstance().setLoggedUser(dao.findByUsername(USERNAME));
    }


    @Test
    void testAddressClient() {
        UpdateController appController = new UpdateController();

        String newAddress = String.format("%s,%s,%s", ADDRESS_STREET, CITY, COUNTRY);

        assertThrows(InvalidAddressException.class, () -> appController.updateAddress(newAddress));
    }

}
