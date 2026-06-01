package it.dosti.justit.dao.clientuser;

import it.dosti.justit.dao.user.UserDAO;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;

public interface ClientUserDAO extends UserDAO {

    String getAddress(String username);

    boolean updateAddress(ClientUser user) throws UpdateOnBackEndException;

    boolean registerUser(ClientUser user, Credentials cred) throws RegisterOnBackEndException;

}
