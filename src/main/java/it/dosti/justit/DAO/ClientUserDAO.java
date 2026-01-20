package it.dosti.justit.DAO;

import it.dosti.justit.model.User;

import java.sql.SQLException;

public interface ClientUserDAO {

    boolean login(String username, String password);

    boolean registerClient(String username, String password, String name, String email);

    User findByUsername(String username) throws SQLException;

    boolean updateName(String username, String password);

    boolean updateEmail(String username, String password);

    boolean updatePassword(String username, String newPassword, String oldPassword);
}
