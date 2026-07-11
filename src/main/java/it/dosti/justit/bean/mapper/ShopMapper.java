package it.dosti.justit.bean.mapper;

import it.dosti.justit.bean.ShopBean;
import it.dosti.justit.model.Shop;

import java.util.List;
public class ShopMapper {

    private ShopMapper() {
    }


    public static ShopBean toBean(Shop shop) {

        if (shop == null) {
            return null;
        }

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


    public static List<ShopBean> toBeans(List<Shop> shops) {

        return shops.stream()
                .map(ShopMapper::toBean)
                .toList();
    }

    public static Shop toDomain(ShopBean bean) {

        if (bean == null) {
            return null;
        }

        return new Shop.Builder(bean.getName())
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
    }
}
