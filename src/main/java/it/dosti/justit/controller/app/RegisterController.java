package it.dosti.justit.controller.app;

import it.dosti.justit.bean.RegisterBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.ShopModel;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.ClientUserModel;
import java.sql.SQLException;

public class RegisterController {



    public boolean registerNewUser(RegisterBean registerBean) throws SQLException {

        ClientUserModel clientUserModel = new ClientUserModel();

        if(clientUserModel.registerClient(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail())){
            System.out.println("Register successful");
            return true;
        }
        else {
            System.out.println("Register failed");
            return false;
        }
    }

    public boolean registerNewTechnician(TechnicRegisterBean registerBean) {
        TechnicianModel technicianModel = new TechnicianModel();
        Integer shopId = technicianModel.getShopIDbyName(registerBean.getShopName());
        if ( shopId == 0){
            System.out.println("Shop name not found");
            System.out.println("Register failed");
            return false;
        }
        else {

            System.out.println("Register successful");
            return technicianModel.registerTechnician(registerBean.getUsername(), registerBean.getPassword(), registerBean.getName(), registerBean.getEmail(), registerBean.getShopName());
        }

    }

    public boolean registerNewShop(ShopBean registerBean) {

        ShopModel shopModel = new ShopModel();

        Shop shop = new Shop(registerBean.getName(), registerBean.getAddress(), registerBean.getPhone(), registerBean.getEmail(), registerBean.getDescription(), registerBean.getImage(), registerBean.getOpeningHours(), registerBean.isHomeAssistance());

        return shopModel.registerShop(shop);
    }

}
