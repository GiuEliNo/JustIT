package it.dosti.justit.bean;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordBean {
    private String oldPassword;
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = DigestUtils.sha256Hex(newPassword);
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = DigestUtils.sha256Hex(oldPassword);
    }
}
