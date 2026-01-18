package it.dosti.justit.view.cli;

import it.dosti.justit.model.Shop;

import java.util.List;

public class CBrowseShopView extends BaseCliView{

    @Override
    public void render() {
        System.out.println("===== BROWSE SHOP =====");
        System.out.println("1. List all shops");
        System.out.println("2. Search shops");
        System.out.println("3. I'm feeling lucky"); // TODO uno shop random
    }

    public String askChoice() {
        return scanner.nextLine();
    }

    //TODO forse si deve passare un bean, da vedere
    public void renderAllShops(List<Shop> allShops) {
        if (allShops == null || allShops.isEmpty()) {
            System.out.println("Shop not found");
            return;
        }
        String separatorLine = "+";
        for (int i = 0; i < 133; i++) {
            separatorLine += "-";
        }
        separatorLine += "+";

        System.out.println(separatorLine);
        System.out.printf("| %-4s | %-40s | %-30s | %-50s |\n", "N.", "Name", "Address", "Description");
        System.out.println(separatorLine);

        for (int i = 0; i < allShops.size(); i++) {
            Shop shop = allShops.get(i);
            System.out.printf(
                    "| %-4d | %-40s | %-30s | %-50s |\n",
                    (i + 1),
                    shop.getName(),
                    shop.getAddress(),
                    shop.getDescription()
            );
        }

        System.out.println(separatorLine);
    }
}
