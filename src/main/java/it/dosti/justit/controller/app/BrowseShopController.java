package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.dao.ShopDAO;
import it.dosti.justit.dao.ShopDAOJDBC;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.model.Shop;
import it.dosti.justit.utils.SessionManager;
import it.dosti.justit.utils.CalculateCoordinateRangeDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//soppresso il warning Using pseudorandom number generators (PRNGs) is security-sensitive. Non essendo un caso d'uso di sicurezza ma un smeplice easter egg non c'è bisogno.
@SuppressWarnings("java:S2245")
public class BrowseShopController {

    private static final Random RANDOM = new Random();
    private final ShopDAO dao = new ShopDAOJDBC();

    public List<Shop> getAllShops() {
        return dao.retrieveAllShops();
    }

    public void pageSelected(Shop selectedItem) {
        if (selectedItem != null) {
            SessionManager.getInstance().setCurrentShop(selectedItem);
        }
    }

    public List<Shop> search(SearchBean bean) {
        String query = bean.getSearchText();
        List<Shop> shops = dao.retrieveAllShops();

        if (query == null || query.isEmpty()) {
            return shops;
        }

        return shops.stream()
                .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Shop> filterByRadius(Float radius) {
        List<Shop> shops = dao.retrieveAllShops();
        ClientUser clientUser = (ClientUser) SessionManager.getInstance().getLoggedUser();

        List<Shop> filtered = new ArrayList<>();
        for (Shop shop : shops) {
            if (CalculateCoordinateRangeDistance.distFrom(
                    (float) shop.getCoordinates().getLatitude(),
                    (float) shop.getCoordinates().getLongitude(),
                    (float) clientUser.getCoordinates().getLatitude(),
                    (float) clientUser.getCoordinates().getLongitude()
            ) < radius) {
                filtered.add(shop);
            }
        }
        return filtered;
    }

    public void randomShop() {
        List<Shop> shops = dao.retrieveAllShops();
        if (!shops.isEmpty()) {
            pageSelected(shops.get(RANDOM.nextInt(shops.size())));
        }
    }
}