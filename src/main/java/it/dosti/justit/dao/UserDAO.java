package it.dosti.justit.dao;

import it.dosti.justit.exceptions.LoginFromBackEndException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.User;

public interface UserDAO {

    boolean login(Credentials cred) throws LoginFromBackEndException;


    User findByUsername(String username) throws UserNotFoundException;

    boolean updateName(String username, String password) throws UpdateOnBackEndException;

    boolean updateEmail(String username, String password) throws UpdateOnBackEndException;

    boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnBackEndException;

    boolean isUsernameAvailable(String username);


}
