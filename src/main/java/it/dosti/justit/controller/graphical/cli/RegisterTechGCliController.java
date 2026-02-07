package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.TechnicRegisterBean;
import it.dosti.justit.controller.app.RegisterController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.exceptions.RegisterOnDbException;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.view.cli.CSignInTech;

public class RegisterTechGCliController extends BaseCliController {

    @Override
    public void initialize() {
        RegisterController appController = new RegisterController();
        CSignInTech signInTechView = (CSignInTech) view;

        TechnicRegisterBean registerBean = new TechnicRegisterBean();
        registerBean.setUsername(signInTechView.askUsername());
        registerBean.setPassword(signInTechView.askPassword());
        registerBean.setName(signInTechView.askFullName());
        registerBean.setEmail(signInTechView.askEmail());
        registerBean.setShopName(signInTechView.askShopName());

        try {
            if (appController.registerNewTechnician(registerBean)) {
                navigation.navigate(Screen.LAUNCHER);
            } else {
                signInTechView.errorSignInTech();
                navigation.navigate(Screen.REGISTER_TECH);
            }
        } catch (RegisterOnDbException | ShopNotFoundException | NavigationException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}
