package it.dosti.justit.DAO;

public interface ClientUserDAO {

    public boolean login(String username, String password);

    public boolean registerClient(String username, String password, String name, String email);
}
