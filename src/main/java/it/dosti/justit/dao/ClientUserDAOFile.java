package it.dosti.justit.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.exceptions.LoginFromBackEndException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.exceptions.UpdateOnBackEndException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JsonHandler;
import it.dosti.justit.utils.JustItLogger;

import java.util.List;

public class ClientUserDAOFile implements ClientUserDAO{

    private static final String FILENAME_USER = "users";
    private static final String FILENAME_CREDENTIALS = "credentials";
    private static final String FILENAME_TECHNICIAN = "technicians";


    @Override
    public boolean login(Credentials cred) throws LoginFromBackEndException {
        try {
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});

            String targetUsername = cred.getUser();
            String targetPassword = cred.getPassword();

            boolean isClient = users.stream()
                    .anyMatch(user -> targetUsername.equals(user.getUsername()));

            if (!isClient) {
                return false;
            }
            return credentials.stream()
                    .anyMatch(c -> targetUsername.equals(c.getUser()) &&
                            targetPassword.equals(c.getPassword()));

        } catch (Exception e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean registerUser(ClientUser user, Credentials cred) throws RegisterOnBackEndException {
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            users.add( user );
            credentials.add(cred);
            JsonHandler.writeJsonFile(users, FILENAME_USER);
            JsonHandler.writeJsonFile(credentials, FILENAME_CREDENTIALS);
            return true;
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException{
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            for(User user : users){
                if(user.getUsername().equals(username)){
                    return user;
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean updateName(String username, String newName) throws UpdateOnBackEndException {
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(User user : users){
                    if(user.getUsername().equals(username)){
                        user.setName(newName);
                        JsonHandler.writeJsonFile(users, FILENAME_USER);
                        return true;
                    }
                }
            }
        }catch(Exception e){

            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateEmail(String username, String newEmail) throws UpdateOnBackEndException {
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(User user : users){
                    if(user.getUsername().equals(username)){
                        user.setEmail(newEmail);
                        JsonHandler.writeJsonFile(users, FILENAME_USER);
                        return true;
                    }
                }
            }
        }catch(Exception e){

            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnBackEndException {
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
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public String getAddress(String username){
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(ClientUser user : users){
                    if(user.getUsername().equals(username)){
                        return user.getAddress();
                    }
                }
            }
        }catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean updateAddress(ClientUser user) throws UpdateOnBackEndException {
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            if(!users.isEmpty()){
                for(ClientUser user1 : users){

                    if(user1.getUsername().equals(user.getUsername())){
                        user1.setAddress(user.getAddress());
                        JsonHandler.writeJsonFile(users, FILENAME_USER);
                        return true;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
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
                        return false;
                    }
                }
            }
            List<TechnicianUser> techs = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIAN, new TypeReference<>() {});
            if(!techs.isEmpty()){
                for(TechnicianUser user : techs){
                    if(user.getUsername().equals(username)){
                        return false;
                    }
                }
            }
        }
        catch(Exception e){
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
        return true;
    }
}
