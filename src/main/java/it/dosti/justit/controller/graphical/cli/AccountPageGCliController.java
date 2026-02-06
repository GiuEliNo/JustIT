package it.dosti.justit.controller.graphical.cli;


import it.dosti.justit.controller.app.AccountController;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CAccountPageView;

public class AccountPageGCliController extends BaseCliController {

    @Override
    public void initialize(){
        AccountController appController = new AccountController();
        CAccountPageView accountPageView = (CAccountPageView) view;

        accountPageView.renderAccount(appController.getUserBean());

        String choice = accountPageView.askChoice();

        if(choice.equals("0")){
            navigation.navigate(Screen.MAIN_USER);
        } else {
            navigation.navigate(Screen.ACCOUNT_PAGE);
        }
    }
}
