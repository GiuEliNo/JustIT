package it.dosti.justit.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.UpdateOnDBException;
import it.dosti.justit.exceptions.UserNotFoundException;
import it.dosti.justit.model.Credentials;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.user.TechnicianUser;
import it.dosti.justit.model.user.User;
import it.dosti.justit.utils.JsonHandler;

import java.util.List;

public class ClientUserDAOFile implements ClientUserDAO{

    private static final String FILENAME_USER = "users";
    private static final String FILENAME_CREDENTIALS = "credentials";
    private static final String FILENAME_TECHNICIAN = "technicians";


    @Override
    public boolean login(Credentials cred) throws LoginFromDBException{
        try{
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            boolean found = false;
            if(!users.isEmpty()) {
                for (ClientUser user : users) {
                    if (cred.getUser().getUsername().equals(user.getUsername())) {
                        found = true;
                        break;
                    }
                }
            }
            if(!credentials.isEmpty() && found ){
                for (Credentials credential : credentials) {
                        if (credential.getUser().getUsername().compareTo(cred.getUser().getUsername()) == 0 && credential.getPassword().compareTo(cred.getPassword()) == 0) {
                            return true;
                        }
                    }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(Credentials cred) throws RegisterOnDbException{
        try{
            List<ClientUser> users = JsonHandler.readCollectionOnJsonFile(FILENAME_USER, new TypeReference<>() {});
            List<Credentials> credentials = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            users.add((ClientUser) cred.getUser());
            credentials.add(cred);
            JsonHandler.writeJsonFile(users, FILENAME_USER);
            JsonHandler.writeJsonFile(credentials, FILENAME_CREDENTIALS);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

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
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateName(String username, String newName) throws UpdateOnDBException{
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

            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmail(String username, String newEmail) throws UpdateOnDBException{
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

            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String newPassword, String oldPassword) throws UpdateOnDBException{
        try {
            List<Credentials> creds = JsonHandler.readCollectionOnJsonFile(FILENAME_CREDENTIALS, new TypeReference<>() {});
            if(!creds.isEmpty()){
                for(Credentials cred : creds){
                    if(cred.getUser().getUsername().equals(username) &&  cred.getPassword().equals(oldPassword)){
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
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAddress(ClientUser user) throws UpdateOnDBException{
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
            e.printStackTrace();
        }
        return false;
    }

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
            List<TechnicianUser> techs = JsonHandler.readCollectionOnJsonFile(FILENAME_TECHNICIAN, new TypeReference<>() {});
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
