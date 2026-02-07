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
    private final List<Shop> shops;

    public BrowseShopController() {
        ShopDAO dao = new ShopDAOJDBC();
        this.shops = dao.retrieveAllShops();
    }

    public List<Shop> getAllShops() {
        return new ArrayList<>(shops);
    }

    public void pageSelected(Shop selectedItem) {
        if (selectedItem != null) {
            SessionManager.getInstance().setCurrentShop(selectedItem);
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

    public List<Shop> filterByRadius(Float radius) {
        List<Shop> filteredShops = new ArrayList<>();
        ClientUser clientUser = (ClientUser) SessionManager.getInstance().getLoggedUser();
        for (Shop shop : shops) {
            if (CalculateCoordinateRangeDistance.distFrom((float) shop.getCoordinates().getLatitude(), (float) shop.getCoordinates().getLongitude(), (float) clientUser.getCoordinates().getLatitude(), (float) clientUser.getCoordinates().getLongitude()) < radius ){
                filteredShops.add(shop);
            }
        }
        return filteredShops;
    }


    public void randomShop() {
        List<Shop> allShops = getAllShops();
        if (allShops.isEmpty()) {
            return;
        }
        this.pageSelected(allShops.get(RANDOM.nextInt(allShops.size())));
    }
}
