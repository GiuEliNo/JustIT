package it.dosti.justit.controller.graphical.cli;

import it.dosti.justit.controller.app.BrowseShopController;
import it.dosti.justit.view.cli.CBrowseShopView;

public class BrowseShopGCliController extends BaseCliController{
    @Override
    public void initialize() throws Exception {
        BrowseShopController appController = new BrowseShopController();
        CBrowseShopView browseShopView = (CBrowseShopView) view;

        String choice = browseShopView.askChoice();

        switch (choice) {
            case "1":
                browseShopView.renderAllShops(appController.getAllShops());
                break;
        }

    }
}
