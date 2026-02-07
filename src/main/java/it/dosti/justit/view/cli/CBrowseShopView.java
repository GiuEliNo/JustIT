package it.dosti.justit.view.cli;

import it.dosti.justit.model.Shop;

import java.util.List;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CBrowseShopView extends BaseCliView{

    private static final String TABLE_HEADER_FORMAT = "| %-4s | %-40s | %-30s | %-50s |%n";

    @Override
    public void render() {
        System.out.println("===== BROWSE SHOP =====");
        System.out.println("1. List all shops");
        System.out.println("2. Search shops");
        System.out.println("3. I'm feeling lucky");
    }

    public String askChoice() {
        return scanner.nextLine();
    }

    public void renderShops(List<Shop> allShops) {
        if (allShops == null || allShops.isEmpty()) {
            System.out.println("Shop not found");
            return;
        }
        String separatorLine = "+" + "-".repeat(133) + "+";

        System.out.println(separatorLine);
        System.out.printf(
                TABLE_HEADER_FORMAT,
                "N.", "Name", "Address", "Description"
        );
        System.out.println(separatorLine);

        for (int i = 0; i < allShops.size(); i++) {
            Shop shop = allShops.get(i);
            System.out.printf(
                    TABLE_HEADER_FORMAT,
                    (i + 1),
                    shop.getName(),
                    shop.getAddress(),
                    shop.getDescription()
            );
        }

        System.out.println(separatorLine);
    }

    public Integer askShopSelection() {
        System.out.print("Select shop number: ");
        return readIntCheck();
    }

    public String askQueryShop() {
        System.out.print("Search shops: ");
        return scanner.nextLine();
    }

    public void showInvalidSelection(){
        System.out.println("Invalid selection");
    }

    public  void noShopList(){
        System.out.println("No shop in list");
    }
}
