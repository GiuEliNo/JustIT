package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.model.user.RoleType;
import it.dosti.justit.utils.PersistencyType;
import it.dosti.justit.utils.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

//Giulio Rustia
class LoginControllerTest {


    @BeforeEach
    void setUp() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        SessionManager.getInstance().setPersistencyType(PersistencyType.DATABASE);
    }


    @Test
    void loginTestUser() {
        LoginController loginController = new LoginController();
        String flag;
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("demo");
        loginBean.setPassword("password");
        loginBean.setRoleType(String.valueOf(RoleType.CLIENT));
        try {
            flag = loginController.checkLogin(loginBean);

        } catch (Exception e) {
            flag = null;

        }
        assertNotNull( flag);

    }

    @Test
    void loginTestTechUser() {
        LoginController loginController = new LoginController();

        String flag;
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("tec.demo");
        loginBean.setPassword("password");
        loginBean.setRoleType(String.valueOf(RoleType.CLIENT));
        try {
            flag  = loginController.checkLogin(loginBean);
        } catch (Exception e) {
            flag = null;
        }

        assertNull(flag);
    }
}