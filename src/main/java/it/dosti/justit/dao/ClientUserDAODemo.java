package it.dosti.justit.dao;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.User;

import java.util.HashMap;
import java.util.Map;

public class ClientUserDAODemo implements ClientUserDAO {

    private final Map<String, ClientUser> usersByUsername = new HashMap<>();
    private final Map<String, String> passwordsByUsername = new HashMap<>();

    public ClientUserDAODemo() {
        ClientUser user = new ClientUser("Franco Scagnallara", "demo_client", "demo.client@mail.com", "Via Casilina 416", new Coordinates(41.87, 12.54));

        usersByUsername.put(user.getUsername(), user);

        passwordsByUsername.put(user.getUsername(), "password");
    }

    @Override
    public boolean login(Credentials cred) {

        String username = cred.getUser();
        String expectedPassword = passwordsByUsername.get(username);
        return expectedPassword != null && expectedPassword.equals(cred.getPassword());
    }

    @Override
    public boolean register(RegisterBean registerBean, Credentials cred) {

        ClientUser user = new ClientUser(registerBean.getName(), registerBean.getUsername(), registerBean.getEmail(), registerBean.getAddress(), registerBean.getCoordinates());

        String username = user.getUsername();
        if (usersByUsername.containsKey(username)) {
            return false;
        }
        usersByUsername.put(username, user);
        passwordsByUsername.put(username, cred.getPassword());
        return true;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        ClientUser user = usersByUsername.get(username);
        if (user == null) {
            throw new UserNotFoundException(String.format("Can't find the user %s in the db", username));

        }
        return user;
    }

    @Override
    public boolean updateName(String username, String newName) {
        ClientUser oldUser = usersByUsername.get(username);
        if (oldUser == null) {
            return false;
        }

        ClientUser updated = new ClientUser(
                newName,
                oldUser.getUsername(),
                oldUser.getEmail(),
                oldUser.getAddress(),
                oldUser.getCoordinates()
        );
        usersByUsername.put(username, updated);
        return true;
    }

    @Override
    public boolean updateEmail(String username, String email) {
        ClientUser oldUser = usersByUsername.get(username);
        if (oldUser == null) {
            return false;
        }

        ClientUser updated = new ClientUser(
                oldUser.getName(),
                oldUser.getUsername(),
                email,
                oldUser.getAddress(),
                oldUser.getCoordinates()
        );
        usersByUsername.put(username, updated);
        return true;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) {
        String currentPassword = passwordsByUsername.get(username);
        if (currentPassword == null || !currentPassword.equals(oldPassword)) {
            return false;
        }
        passwordsByUsername.put(username, newPassword);
        return true;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !usersByUsername.containsKey(username);
    }

    @Override
    public String getAddress(String username) {
        ClientUser user = usersByUsername.get(username);
        return user == null ? null : user.getAddress();
    }

    @Override
    public boolean updateAddress(ClientUser user) {
        if (user == null || user.getUsername() == null || !usersByUsername.containsKey(user.getUsername())) {
            return false;
        }

        ClientUser oldUser = usersByUsername.get(user.getUsername());
        ClientUser updated = new ClientUser(
                oldUser.getName(),
                oldUser.getUsername(),
                oldUser.getEmail(),
                user.getAddress(),
                user.getCoordinates()
        );
        usersByUsername.put(user.getUsername(), updated);
        return true;
    }
}