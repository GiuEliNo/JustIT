package it.dosti.justit.view.cli;

import it.dosti.justit.utils.SessionManager;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CMainUserView extends BaseCliView{
    @Override
    public void render() {
        String username = SessionManager.getInstance().getLoggedUser().getUsername();
        System.out.println("===== " + username + " =====");
        System.out.println("1. User Profile");
        System.out.println("2. Browsing Shop");
        System.out.println("3. List Booking");
        System.out.println("4. Add Review (only shop with completed bookings)");
        System.out.println("5. Notification Center");
        System.out.println("6. Logout");
    }

    public String askChoice() {
        return scanner.nextLine();
    }
}
