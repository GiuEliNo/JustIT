package it.dosti.justit.dao;

import it.dosti.justit.exceptions.LoginFromBackEndException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class TechnicianDAODemo implements TechnicianDAO {

    private final Map<String, TechnicianUser> usersByUsername = new HashMap<>();
    private final Map<String, String> passwordsByUsername = new HashMap<>();
    private final Map<String, Integer> shopIdByShopName = new HashMap<>();

    public TechnicianDAODemo() {
        TechnicianUser user1 = new TechnicianUser("Gulio Agricolo", "demo_tech", "demo.tech@mail.com", 1);

        usersByUsername.put(user1.getUsername(), user1);

        passwordsByUsername.put(user1.getUsername(), DigestUtils.sha256Hex("password"));

        shopIdByShopName.put("Arindale Riparazione", 1);
    }

    @Override
    public boolean login(Credentials cred) throws LoginFromBackEndException {

        String username = cred.getUser();
        String expectedPassword = passwordsByUsername.get(username);
        return expectedPassword != null && expectedPassword.equals(cred.getPassword());
    }

    public boolean registerTech(TechnicianUser user, Credentials cred) {

        TechnicianUser tec = new TechnicianUser(user.getName(), user.getUsername(), user.getEmail(), user.getShopId());

        String username = tec.getUsername();
        if (usersByUsername.containsKey(username)) {
            return false;
        }


        usersByUsername.put(username, tec);
        passwordsByUsername.put(username, cred.getPassword());
        return true;
    }

    @Override
    public Integer getShopIDbyName(String shopName) throws ShopNotFoundException {
        Integer shopId = shopIdByShopName.get(shopName);
        if (shopId == null) {
            throw new ShopNotFoundException("Shop not found: " + shopName);
        }
        return shopId;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        TechnicianUser user = usersByUsername.get(username);
        if (user == null) {
            throw new UserNotFoundException(String.format("Can't find the user %s in the db", username));
        }
        return user;
    }

    @Override
    public boolean updateName(String username, String newName) {
        TechnicianUser oldUser = usersByUsername.get(username);
        if (oldUser == null) {
            return false;
        }

        TechnicianUser updated = new TechnicianUser(
                newName,
                oldUser.getUsername(),
                oldUser.getEmail(),
                oldUser.getShopId()
        );
        usersByUsername.put(username, updated);
        return true;
    }

    @Override
    public boolean updateEmail(String username, String email) {
        TechnicianUser oldUser = usersByUsername.get(username);
        if (oldUser == null) {
            return false;
        }

        TechnicianUser updated = new TechnicianUser(
                oldUser.getName(),
                oldUser.getUsername(),
                email,
                oldUser.getShopId()
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
}