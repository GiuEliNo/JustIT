package it.dosti.justit.view.cli;


@SuppressWarnings("java:S106")
public class CMainTechView extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== WELCOME TO JUSTIT =====");
        System.out.println("1. Tech Profile");
        System.out.println("2. Shop Profile");
        System.out.println("3. List Booking");
        System.out.println("4. Check Reviews");
        System.out.println("5. Notification Center");
    }

    public String askChoice() {
        return scanner.nextLine();
    }
}
