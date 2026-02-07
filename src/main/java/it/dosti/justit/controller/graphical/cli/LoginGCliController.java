package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.user.RoleType;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CLoginView;

public class LoginGCliController extends BaseCliController {

    @Override
    public void initialize() {
        LoginController appController = new LoginController();
        CLoginView loginView = (CLoginView) view;

        String username = loginView.askUsername();
        String password = loginView.askPassword();
        String role = loginView.askRole();

        LoginBean loginBean = new LoginBean(username, password, role);

        try {
            if (appController.checkLogin(loginBean)) {
                if (loginBean.getRoleType() == RoleType.CLIENT) {
                    navigation.navigate(Screen.MAIN_USER);
                } else if(loginBean.getRoleType() == RoleType.TECHNICIAN){
                    navigation.navigate(Screen.MAIN_TECH);
                }
            } else {
                navigation.navigate(Screen.LOGIN);
            }
        } catch (LoginFromDBException | ShopNotFoundException | NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
