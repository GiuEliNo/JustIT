package it.dosti.justit.bean;

import it.dosti.justit.model.user.RoleType;

public class LoginBean {
    private String username;
    private String password;
    private RoleType roleType;

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
        this.password = password;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleTypeStringed(String roleTypeField) {
        this.setRoleType(roleTypeField.toUpperCase().startsWith("T") ? RoleType.TECHNICIAN : RoleType.CLIENT);
    }
}
