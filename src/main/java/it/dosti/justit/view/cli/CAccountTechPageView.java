package it.dosti.justit.view.cli;

import it.dosti.justit.bean.UserBean;

@SuppressWarnings("java:S106")
public class CAccountTechPageView extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== ACCOUNT TECH PAGE =====");
    }

    public void renderAccount(UserBean userBean) {
        System.out.println("Username " + userBean.getUsername());
        System.out.println("Name: " + userBean.getName());
        System.out.println("Email: " + userBean.getEmail());
        System.out.println("------------------------------------------------");
    }

    public String askChoice() {
        System.out.println(OPTION_BACK);
        return scanner.nextLine();
    }
}
