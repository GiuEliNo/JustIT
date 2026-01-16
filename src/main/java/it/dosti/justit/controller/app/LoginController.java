package it.dosti.justit.controller.app;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.UserModel;

public class LoginController {
    public boolean checkLogin(LoginBean loginBean) throws Exception {
        SessionModel.getInstance().setUserRole(loginBean.getRoleType());
        switch (loginBean.getRoleType()) {
            case CLIENT:
                UserModel userModel = new UserModel();
                return true;
                //return userModel.loginClient(user, password);

            case TECHNICIAN:
                TechnicianModel technicianModel = new TechnicianModel();
                return true;
                //return technicianModel.loginTechnician(user, password);
            default:
                throw new Exception("Invalid type : " + loginBean.getRoleType());
        }
    }
}
