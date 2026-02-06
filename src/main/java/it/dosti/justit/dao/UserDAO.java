package it.dosti.justit.dao;

import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.User;

public interface UserDAO {

    boolean login(Credentials cred) throws LoginFromDBException;

    boolean register(Credentials cred) throws RegisterOnDbException;

    User findByUsername(String username) throws UserNotFoundException;

    boolean updateName(String username, String password) throws UpdateOnDBException;

    boolean updateEmail(String username, String password) throws UpdateOnDBException;

    boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException;

}
