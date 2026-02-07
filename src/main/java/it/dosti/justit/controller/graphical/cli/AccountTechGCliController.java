package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.controller.app.AccountController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CAccountTechPageView;

public class AccountTechGCliController extends BaseCliController {
    @Override
    public void initialize() throws NavigationException {
        AccountController appController = new AccountController();
        CAccountTechPageView accountPageView = (CAccountTechPageView) view;

        accountPageView.renderAccount(appController.getUserBean());

        String choice = accountPageView.askChoice();

        if(choice.equals("0")){
            navigation.navigate(Screen.MAIN_TECH);
        } else {
            navigation.navigate(Screen.ACCOUNT_PAGE_TECH);
        }
    }
}
