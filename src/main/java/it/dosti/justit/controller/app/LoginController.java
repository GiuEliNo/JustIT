package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.ClientUserModel;

public class LoginController {
    public boolean checkLogin(LoginBean loginBean) throws IllegalArgumentException, LoginFromDBException, ShopNotFoundException {
        return switch (loginBean.getRoleType()) {
            case CLIENT -> {
                ClientUserModel clientUserModel = new ClientUserModel();
                yield clientUserModel.loginClient(loginBean.getUsername(), loginBean.getPassword());
            }
            case TECHNICIAN -> {
                TechnicianModel technicianModel = new TechnicianModel();
                yield technicianModel.loginTechnician(loginBean.getUsername(), loginBean.getPassword());
            }
        };
    }
}
