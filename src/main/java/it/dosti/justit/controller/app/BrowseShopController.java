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
    private final ShopDAO dao = new ShopDAOJDBC();

    public List<ShopBean> getAllShops() {
        return toBeans(dao.retrieveAllShops());
    }

    public void pageSelected(ShopBean bean) {
        if (bean != null) {
            Shop selectedItem = new Shop.Builder(bean.getName())
                    .id(bean.getId())
                    .address(bean.getAddress())
                    .phone(bean.getPhone())
                    .email(bean.getEmail())
                    .description(bean.getDescription())
                    .image(bean.getImage())
                    .openingHours(bean.getOpeningHours())
                    .homeAssistance(bean.isHomeAssistance())
                    .coordinates(bean.getCoordinates())
                    .build();

            SessionManager.getInstance().setCurrentShop(selectedItem);
        }
    }

    public List<ShopBean> search(SearchBean bean) {
        String query = bean.getSearchText();
        List<Shop> shops = dao.retrieveAllShops();

        if (query == null || query.isEmpty()) {
            return toBeans(shops);
        }

        return shops.stream()
                .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()))
                .map(this::toBean)
                .collect(Collectors.toList());
    }

    public List<ShopBean> filterByRadius(Float radius) {
        List<Shop> shops = dao.retrieveAllShops();
        ClientUser clientUser = (ClientUser) SessionManager.getInstance().getLoggedUser();

        List<ShopBean> filtered = new ArrayList<>();
        for (Shop shop : shops) {
            if (CalculateCoordinateRangeDistance.distFrom(
                    (float) shop.getCoordinates().getLatitude(),
                    (float) shop.getCoordinates().getLongitude(),
                    (float) clientUser.getCoordinates().getLatitude(),
                    (float) clientUser.getCoordinates().getLongitude()
            ) < radius) {
                filtered.add(toBean(shop));
            }
        }
        return filtered;
    }

    public void randomShop() {
        List<ShopBean> shops = getAllShops();
        if (!shops.isEmpty()) {
            pageSelected(shops.get(RANDOM.nextInt(shops.size())));
        }
    }

    private List<ShopBean> toBeans(List<Shop> shops) {
        List<ShopBean> beans = new ArrayList<>();
        for (Shop shop : shops) {
            beans.add(toBean(shop));
        }
        return beans;
    }

    private ShopBean toBean(Shop shop) {
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
}
