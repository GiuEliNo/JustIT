package it.dosti.justit.view.cli;

import it.dosti.justit.bean.ShopBean;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CPageShopCliView extends BaseCliView{
    @Override
    public void render() {
        System.out.println("===== SHOP DETAILS =====");
    }

    public void renderShop(ShopBean shopBean) {
        System.out.println("Name: " + shopBean.getName());
        System.out.println("Address: " + shopBean.getAddress());
        System.out.println("Email: " + shopBean.getEmail());
        System.out.println("Phone: " + shopBean.getPhone());
        System.out.println("Description: " + shopBean.getDescription());
        System.out.println("Opening Hours: " + shopBean.getOpeningHours());
        System.out.println("Home Assistance: " + shopBean.getHomeAssistanceMessage());
        System.out.println("------------------------------------------------");
    }

    public String askChoice() {
        System.out.println("1. Booking");
        System.out.println("2. Read Reviews");
        System.out.println("0. Back");
        return scanner.nextLine();
    }
}
