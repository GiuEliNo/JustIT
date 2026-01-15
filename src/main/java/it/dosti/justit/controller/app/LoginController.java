package it.dosti.justit.controller.app;

import it.dosti.justit.controller.gui.MainTechGController;
import it.dosti.justit.controller.gui.MainUserGController;
import it.dosti.justit.model.RoleType;
import it.dosti.justit.model.SessionModel;
import it.dosti.justit.model.TechnicianModel;
import it.dosti.justit.model.UserModel;

public class LoginController {
    public boolean checkLogin(String user, String password, RoleType userRole) throws Exception {
        SessionModel.getInstance().setUserRole(userRole);
        switch (userRole) {
            case CLIENT:
                UserModel userModel = new UserModel();
                return true;
                //return userModel.loginClient(user, password);

            case TECHNICIAN:
                TechnicianModel technicianModel = new TechnicianModel();
                return true;
                //return technicianModel.loginTechnician(user, password);
            default:
                throw new Exception("Invalid type : " + userRole);
        }
    }
}
