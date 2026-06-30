package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.ClientRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnBackEndException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CSignInClient;

public class RegisterUserGCliController extends BaseCliController {

    @Override
    public void initialize() throws NavigationException {
        RegisterController appController = new RegisterController();
        CSignInClient signInClientView = (CSignInClient) view;

        ClientRegisterBean registerBean = new ClientRegisterBean();
        registerBean.setUsername(signInClientView.askUsername());
        registerBean.setPassword(signInClientView.askPassword());
        registerBean.setName(signInClientView.askFullName());
        registerBean.setEmail(signInClientView.askEmail());

        String address = signInClientView.askStreetAddress() + "," + signInClientView.askCity() + "," + signInClientView.askCountry();
        registerBean.setAddress(address);

        try {
            if (appController.registerNewUser(registerBean)) {
                navigation.navigate(Screen.LAUNCHER, null);
            } else {
                signInClientView.errorSignInUser();
                navigation.navigate(Screen.REGISTER_USER, null);
            }
        } catch (RegisterOnBackEndException | NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage());
        }
    }
}
