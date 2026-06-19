package it.dosti.justit.bean;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginBean {
    private String username;
    private String password;
    private String roleType;

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = DigestUtils.sha256Hex(password);
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}
