package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.ShopModel;
import it.dosti.justit.model.SessionModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SidebarListPageController {

    private final List<Shop> shops;

    public SidebarListPageController() {
        ShopModel shopModel = new ShopModel();
        this.shops = shopModel.getAllShops();
    }

    public List<Shop> getAllShops() {
        return new ArrayList<>(shops);
    }

    public void pageSelected(Shop selectedItem) {
        if (selectedItem != null) {
            SessionModel.getInstance().setSelectShop(selectedItem);
        }
    }

    public List<Shop> search(SearchBean bean) {
        String query = bean.getSearchText();
        List<Shop> filteredShops;
        if (query == null || query.isEmpty()) {
            filteredShops = new ArrayList<>(shops);
        } else {
            filteredShops = shops.stream()
                    .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return filteredShops;
    }
}
