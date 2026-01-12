package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.model.Shop;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SidebarListPageController {

    //solo per testing in assenza di DAO
    private final List<Shop> shops = List.of(
            new Shop("Mammelloni Samueloni"),
            new Shop("Il meglio indiano di Torpigna"),
            new Shop("El Gugno Maduro"),
            new Shop("Bombai PC riparazione"),
            new Shop("CurryRiparo")
    );

    public void pageSelected(Shop selectedItem) {
        if (selectedItem != null) {
            MainController.getInstance().setSelectShop(selectedItem);
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

        return filteredShops.stream()
                .map(s -> new Shop(s.getName()))
                .collect(Collectors.toList());
    }
}
