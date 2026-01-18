package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.LoginBean;
import it.dosti.justit.controller.app.LoginController;
import it.dosti.justit.model.RoleType;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CLoginView;

public class LoginGCliController extends BaseCliController {

    @Override
    public void initialize() throws Exception {
        LoginController appController = new LoginController();
        CLoginView loginView = (CLoginView) view;

        String username = loginView.askUsername();
        String password = loginView.askPassword();
        RoleType role = RoleType.valueOf(loginView.askRole());

        LoginBean loginBean = new LoginBean(username, password, role);

        if (appController.checkLogin(loginBean)) {
            //navigation.navigate(Screen.MAIN_USER);
            System.out.println("Corretto");
        } else {
            System.out.println("Errore");
            navigation.navigate(Screen.LOGIN);
        }
    }
}
