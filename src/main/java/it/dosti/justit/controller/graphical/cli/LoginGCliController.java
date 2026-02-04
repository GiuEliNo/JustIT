package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.exceptions.LoginFromDBException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.RoleType;
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
        RoleType role = RoleType.valueOf(loginView.askRole());

        LoginBean loginBean = new LoginBean(username, password, role);

        try {
            if (appController.checkLogin(loginBean)) {
                navigation.navigate(Screen.MAIN_USER);
            } else {
                System.out.println("Errore");
                navigation.navigate(Screen.LOGIN);
            }
        } catch (LoginFromDBException | ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
