package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.bean.ShopBean;
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

    public List<ShopBean> getAllShops() {
        return toShopBeans(shops);
    }

    public void pageSelected(ShopBean selectedItem) {
        if (selectedItem == null) {
            return;
        }

        Shop selectedShop = findShopById(selectedItem.getId());
        if (selectedShop != null) {
            SessionManager.getInstance().setCurrentShop(selectedShop);
        }
    }

    public List<ShopBean> search(SearchBean bean) {
        String query = bean.getSearchText();
        List<Shop> filteredShops;
        if (query == null || query.isEmpty()) {
            filteredShops = new ArrayList<>(shops);
        } else {
            filteredShops = shops.stream()
                    .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return toShopBeans(filteredShops);
    }

    public List<ShopBean> filterByRadius(Float radius) {
        List<Shop> filteredShops = new ArrayList<>();
        ClientUser clientUser = (ClientUser) SessionManager.getInstance().getLoggedUser();
        for (Shop shop : shops) {
            if (CalculateCoordinateRangeDistance.distFrom((float) shop.getCoordinates().getLatitude(), (float) shop.getCoordinates().getLongitude(), (float) clientUser.getCoordinates().getLatitude(), (float) clientUser.getCoordinates().getLongitude()) < radius ){
                filteredShops.add(shop);
            }
        }
        return toShopBeans(filteredShops);
    }


    public void randomShop() {
        List<Shop> allShops = new ArrayList<>(shops);
        if (allShops.isEmpty()) {
            return;
        }
        Shop selectedShop = allShops.get(RANDOM.nextInt(allShops.size()));
        SessionManager.getInstance().setCurrentShop(selectedShop);
    }

    private List<ShopBean> toShopBeans(List<Shop> shopList) {
        return shopList.stream()
                .map(this::toShopBean)
                .collect(Collectors.toList());
    }

    private ShopBean toShopBean(Shop shop) {
        ShopBean bean = new ShopBean();
        bean.setId(shop.getId());
        bean.setName(shop.getName());
        bean.setAddress(shop.getAddress());
        bean.setPhone(shop.getPhone());
        bean.setEmail(shop.getEmail());
        bean.setDescription(shop.getDescription());
        bean.setImage(shop.getImage());
        bean.setOpeningHours(shop.getOpeningHours());
        bean.setHomeAssistance(shop.isHomeAssistance());
        bean.setCoordinates(shop.getCoordinates());
        return bean;
    }

    private Shop findShopById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Shop shop : shops) {
            if (id.equals(shop.getId())) {
                return shop;
            }
        }
        return null;
    }
}
