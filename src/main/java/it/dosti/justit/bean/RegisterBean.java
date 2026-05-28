package it.dosti.justit.bean;
import org.apache.commons.codec.digest.DigestUtils;

public class RegisterBean {
    private String username;
    private String password;
    private String email;
    private String name;
    private String role;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = DigestUtils.sha256Hex(password);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }



    public boolean emailValidation(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
