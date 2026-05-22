package it.dosti.justit.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.exceptions.*;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JsonHandler;

import java.util.List;

public class TechnicianDAOFile implements TechnicianDAO{

    private static final String FILENAME_TECHNICIANS = "technicians";
    private static final String FILENAME_SHOPS = "shops";
    private static final String FILENAME_CREDENTIALS = "credentials";
    private static final String FILENAME_USER = "users";

    @Override
    public Integer getShopIDbyName(String shopName) throws ShopNotFoundException{
        try{
            List<Shop> shops = JsonHandler.readCollectionOnJsonFile(FILENAME_SHOPS, new TypeReference<>() {});
            if (!shops.isEmpty()){
                for(Shop shop : shops){
                    if(shop.getName().equals(shopName)){
                        return shop.getId();
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean login(Credentials cred) throws LoginFromDBException{
        try {
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            List<TechnicianUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});

            String targetUsername = cred.getUser();
            String targetPassword = cred.getPassword();

            boolean isTechnician = users.stream()
                    .anyMatch(tec -> targetUsername.equals(tec.getUsername()));

            if (!isTechnician) {
                return false;
            }

            return credentials.stream()
                    .anyMatch(c -> targetUsername.equals(c.getUser()) &&
                            targetPassword.equals(c.getPassword()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean register(TechnicianUser user, Credentials cred) throws RegisterOnDbException{
        try{
            List<TechnicianUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            users.add(user);
            credentials.add(cred);
            JsonHandler.writeJsonFile(users, FILENAME_TECHNICIANS);
            JsonHandler.writeJsonFile(credentials, FILENAME_CREDENTIALS);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException{
        try{
            List<TechnicianUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});
            for(User user : users){
                if(user.getUsername().equals(username)){
                    return user;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateName(String username, String newName) throws UpdateOnDBException{
        try{
            List<TechnicianUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(User user : users){
                    if(user.getUsername().equals(username)){
                        user.setName(newName);
                        JsonHandler.writeJsonFile(users, FILENAME_TECHNICIANS);
                        return true;
                    }
                }
            }
        }catch(Exception e){

            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmail(String username, String newEmail) throws UpdateOnDBException{

        try{
            List<TechnicianUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(User user : users){
                    if(user.getUsername().equals(username)){
                        user.setEmail(newEmail);
                        JsonHandler.writeJsonFile(users, FILENAME_TECHNICIANS);
                        return true;
                    }
                }
            }
        }catch(Exception e){

            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException{
        try {
            List<Credentials> creds = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            if(!creds.isEmpty()){
                for(Credentials cred : creds){
                    if(cred.getUser().equals(username) &&  cred.getPassword().equals(oldPassword)){
                        cred.setPassword(newPassword);
                        JsonHandler.writeJsonFile(creds, FILENAME_CREDENTIALS);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUsernameAvailable(String username){
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(ClientUser user : users){
                    if(user.getUsername().equals(username)){
                        return true;
                    }
                }
            }
            List<TechnicianUser> techs = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIANS, new TypeReference<>() {});
            if(!techs.isEmpty()){
                for(TechnicianUser user : techs){
                    if(user.getUsername().equals(username)){
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
