package it.dosti.justit.DAO;

import it.dosti.justit.model.ClientUser;

public interface ClientUserDAO {

    boolean login(String username, String password);

    boolean registerClient(String username, String password, String name, String email);

    ClientUser findByUsername(String username);

    boolean updateName(String username, String password);

    boolean updateEmail(String username, String password);

    boolean updatePassword(String username, String password);
}
