package it.dosti.justit.controller.app;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.db.ConnectionDB;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.utils.JustItLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;

class UniqueUsernameOnRegisterTest {

    @BeforeEach
    void setUp() {

        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
    }

    @Test
    void registerUserWithDuplicateUsername() {
        boolean flag = true;
        RegisterController registerController = new RegisterController();
       RegisterBean user1 = createUser();
       RegisterBean user2 = createUser();
       try {
           registerController.registerNewUser(user1);
       } catch (RegisterOnDbException e) {
           JustItLogger.getInstance().error("Error registering user", e);
       }
       flag = registerController.isUsernameAvailable(user2);

       assertFalse(flag);
    }


    private RegisterBean createUser() {
        RegisterBean registerBean =  new RegisterBean();
        registerBean.setUsername("pippo");
        registerBean.setPassword("pippo");
        registerBean.setName("pippo");
        registerBean.setEmail("pippo@pippo");
        registerBean.setAddress("Via del politecnico 1,Roma,Italia");
        return registerBean;

    }


    @AfterEach
    void tearDown() {
        ConnectionDB.getInstance().setDbPath(Path.of("src/main/resources/DB/justit.db"));
        String sql =  "DELETE FROM User WHERE username='pippo'";
        try {
            Connection conn = ConnectionDB.getInstance().connectDB();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
