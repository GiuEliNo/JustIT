package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.controller.app.ShopController;
import it.dosti.justit.exceptions.NavigationException;
import it.dosti.justit.ui.navigation.Screen;
import it.dosti.justit.view.cli.CPageShopCliView;


public class PageShopUserGCliController extends BaseCliController{
    @Override
    public void initialize() throws NavigationException {
        ShopController appController = new ShopController();
        CPageShopCliView pageShopCliView = (CPageShopCliView) view;

        SessionBean session = new SessionBean();
        session.setSessionId(sessionId);
        ShopBean shopBean = appController.getShopBean(session);
        pageShopCliView.renderShop(shopBean);

        String choice = pageShopCliView.askChoice();

        switch (choice){
            case "1":
                navigation.navigate(Screen.BOOKING_PAGE_USER, sessionId);
                break;
            case "2":
                navigation.navigate(Screen.REVIEWS_BOX, sessionId);
                break;
            case "0":
                navigation.navigate(Screen.SEARCH_LIST_SHOP, sessionId);
                break;
            default:
                navigation.navigate(Screen.PAGE_SHOP_USER, sessionId);
                break;
        }
    }
}
