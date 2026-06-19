package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.exceptions.LoginFromBackEndException;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CLoginView;

import java.util.Objects;

public class LoginGCliController extends BaseCliController {

    @Override
    public void initialize() {
        LoginController appController = new LoginController();
        CLoginView loginView = (CLoginView) view;

        String username = loginView.askUsername();
        String password = loginView.askPassword();
        String role = loginView.askRole();

        LoginBean loginBean = new LoginBean();

        loginBean.setUsername(username);
        loginBean.setPassword(password);
        loginBean.setRoleType(role);

        try {
            if (appController.checkLogin(loginBean)) {
                if (Objects.equals(loginBean.getRoleType(), "CLIENT")) {
                    navigation.navigate(Screen.MAIN_USER);
                } else if(Objects.equals(loginBean.getRoleType(), "TECHNICIAN")){
                    navigation.navigate(Screen.MAIN_TECH);
                }
            } else {
                navigation.navigate(Screen.LOGIN);
            }
        } catch (LoginFromBackEndException | ShopNotFoundException | NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
