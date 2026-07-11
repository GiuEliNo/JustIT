package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import it.dosti.justit.bean.SessionBean;
import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.bean.mapper.ShopMapper;
import it.dosti.justit.dao.DaoFactory;
import it.dosti.justit.dao.shop.ShopDAO;
import it.dosti.justit.exceptions.ShopNotFoundException;
import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.ClientUser;
import it.dosti.justit.utils.CalculateCoordinateRangeDistance;
import it.dosti.justit.utils.JustItLogger;
import it.dosti.justit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// soppresso warning: PRNG usato solo per easter egg, non per sicurezza
@SuppressWarnings("java:S2245")
public class BrowseShopController {

    private static final Random RANDOM = new Random();

    private final ShopDAO dao = DaoFactory.getShopDAO();


    public List<ShopBean> getAllShops() {
        return ShopMapper.toBeans(dao.retrieveAllShops());
    }


    public void pageSelected(SessionBean session, ShopBean bean) {

        if (bean != null) {

            Shop selectedItem = retrieveSelectedShop(bean);

            SessionManager.getInstance().getActiveSession(session.getSessionId()).setCurrentShop(selectedItem);
        }
    }

    private Shop retrieveSelectedShop(ShopBean bean) {
        if (bean.getId() == null) {
            return ShopMapper.toDomain(bean);
        }

        try {
            Shop shop = dao.retrieveShopById(bean.getId());
            if (shop != null) {
                return shop;
            }
        } catch (ShopNotFoundException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }

        return ShopMapper.toDomain(bean);
    }


    public List<ShopBean> search(SearchBean bean) {

        String query = bean.getSearchText();

        List<Shop> shops = dao.retrieveAllShops();


        if (query == null || query.isEmpty()) {
            return ShopMapper.toBeans(shops);
        }


        return shops.stream()
                .filter(s -> s.getName()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .map(ShopMapper::toBean)
                .toList();
    }


    public List<ShopBean> filterByRadius(SessionBean session, Float radius) {

        List<Shop> shops = dao.retrieveAllShops();

        ClientUser clientUser = (ClientUser) SessionManager.getInstance().getActiveSession(session.getSessionId()).getLoggedUser();


        List<ShopBean> filtered = new ArrayList<>();


        for (Shop shop : shops) {

            if (CalculateCoordinateRangeDistance.distFrom(
                    (float) shop.getCoordinates().getLatitude(),
                    (float) shop.getCoordinates().getLongitude(),
                    (float) clientUser.getCoordinates().getLatitude(),
                    (float) clientUser.getCoordinates().getLongitude()
            ) < radius) {

                filtered.add(
                        ShopMapper.toBean(shop)
                );
            }
        }

        return filtered;
    }


    public void randomShop(SessionBean session) {

        List<ShopBean> shops = getAllShops();

        if (!shops.isEmpty()) {

            pageSelected(
                    session,
                    shops.get(RANDOM.nextInt(shops.size()))
            );
        }
    }
}
