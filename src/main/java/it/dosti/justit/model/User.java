package it.dosti.justit.model;

public interface User {
     Integer id = null;
     String name = "";
     String username = "";
     String email = "";
     String password = "";

    Integer getId();
    String getName();
    String getUsername();
    String getEmail();
    String getPassword();
}
