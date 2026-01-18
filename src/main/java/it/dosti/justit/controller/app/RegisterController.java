package it.dosti.justit.controller.app;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.ShopModel;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.UserModel;

public class RegisterController {



    public void registerNewUser(RegisterBean registerBean) {

        UserModel userModel = new UserModel();

        if(userModel.registerClient(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail())){
            System.out.println("Register successful");
        }
        else {
            System.out.println("Register failed");
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) {
        TechnicianModel technicianModel = new TechnicianModel();
        Integer shopId =technicianModel.getShopIDbyName(registerBean.getShopName());
        if ( shopId == 0){
            System.out.println("Shop name not found");
            System.out.println("Register failed");
            return false;
        }
        else {
            technicianModel.registerTechnician(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail(), registerBean.getShopName());
            System.out.println("Register successful");
            return true;
        }

    }

    public boolean registerNewShop(ShopBean registerBean) {

        ShopModel shopModel = new ShopModel();

        Shop shop = new Shop(registerBean.getName(), registerBean.getAddress(), registerBean.getPhone(), registerBean.getEmail(), registerBean.getDescription(), registerBean.getImage(), registerBean.getOpeningHours(), registerBean.isHomeAssistance());

        return shopModel.registerShop(shop);
    }

}
