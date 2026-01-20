package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.ClientUserModel;

public class LoginController {
    public boolean checkLogin(LoginBean loginBean) throws Exception {
        switch (loginBean.getRoleType()) {
            case CLIENT:
                ClientUserModel clientUserModel = new ClientUserModel();
                return clientUserModel.loginClient(loginBean.getUsername(), loginBean.getPassword());

            case TECHNICIAN:
                TechnicianModel technicianModel = new TechnicianModel();
                return technicianModel.loginTechnician(loginBean.getUsername(),  loginBean.getPassword());

            default:
                throw new Exception("Invalid type : " + loginBean.getRoleType());
        }
    }
}
