package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.view.cli.CSignInClient;

public class RegisterUserGCliController extends BaseCliController {

    @Override
    public void initialize() {
        RegisterController appController = new RegisterController();
        CSignInClient signInClientView = (CSignInClient) view;





    }
}
