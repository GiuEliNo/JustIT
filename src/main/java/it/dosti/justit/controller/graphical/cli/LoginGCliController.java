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

        if(Objects.equals(role, "") || Objects.equals(role, "C")) {
            role = "CLIENT";
        }
        else if(Objects.equals(role, "T")){
            role = "TECHNICIAN";
        }

        LoginBean loginBean = new LoginBean();

        loginBean.setUsername(username);
        loginBean.setPassword(password);
        loginBean.setRoleType(role);

        try {
                String sessionId = appController.checkLogin(loginBean);
                if(Objects.nonNull(sessionId)) {
                    if (Objects.equals(loginBean.getRoleType(), "CLIENT")) {
                        navigation.navigate(Screen.MAIN_USER, sessionId);
                    } else if (Objects.equals(loginBean.getRoleType(), "TECHNICIAN")) {
                        navigation.navigate(Screen.MAIN_TECH, sessionId);
                    }
                }
                else{
                    navigation.navigate(Screen.LOGIN, null);
                }
        } catch (LoginFromBackEndException | ShopNotFoundException | NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
