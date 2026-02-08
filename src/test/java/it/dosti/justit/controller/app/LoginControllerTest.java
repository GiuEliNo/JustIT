package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.model.user.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LoginControllerTest {


    @BeforeEach
    void setUp() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
    }


    @Test
    void loginTestUser() {
        LoginController loginController = new LoginController();
        int flag = 0;
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("demo");
        loginBean.setPassword("password");
        loginBean.setRoleType(RoleType.CLIENT);
        try {
            loginController.checkLogin(loginBean);

        } catch (Exception e) {
            flag = 1;

        }
        assertEquals(0, flag);

    }

    @Test
    void loginTestTechUser() {
        LoginController loginController = new LoginController();

        boolean flag = true;
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("tec.demo");
        loginBean.setPassword("password");
        loginBean.setRoleType(RoleType.CLIENT);
        try {
            flag  = loginController.checkLogin(loginBean);
        } catch (Exception e) {
            flag = true;
        }

        assertFalse(flag);
    }
}