package it.dosti.justit.dao;

import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.User;

public interface ClientUserDAO {

    boolean login(String username, String password) throws LoginFromDBException;

    boolean registerClient(String username, String password, String name, String email) throws RegisterOnDbException;

    User findByUsername(String username) throws UserNotFoundException;

    boolean updateName(String username, String password) throws UpdateOnDBException;

    boolean updateEmail(String username, String password) throws UpdateOnDBException;

    boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException;
}
