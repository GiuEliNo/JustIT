package it.dosti.justit.bean;

import it.dosti.justit.model.RoleType;

public class LoginBean {
    private String username;
    private String password;
    private RoleType roleType;

    public LoginBean(String usernameField, String passwordField, RoleType roleTypeField) {
        this.setUsername(usernameField);
        this.setPassword(passwordField);
        this.setRoleType(roleTypeField);
    }

    public LoginBean(String usernameField, String passwordField, String roleTypeField) {
        this.setUsername(usernameField);
        this.setPassword(passwordField);
        this.setRoleTypeStringed(roleTypeField);
    }

    private void setRoleTypeStringed(String roleTypeField) {
        this.setRoleType(roleTypeField.toUpperCase().startsWith("T") ? RoleType.TECHNICIAN : RoleType.CLIENT);
    }

    public String getUsername(){
        return this.username;
    }

    private void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    private void setPassword(String password){
        this.password = password;
    }

    private void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
