package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CMainUserView extends BaseCliView{
    @Override
    public void render() {
        System.out.println("===== WELCOME TO JUSTIT =====");
        System.out.println("1. User Profile");
        System.out.println("2. Browsing Shop");
        System.out.println("3. List Booking");
        System.out.println("4. Add Review (only shop with completed bookings)");
        System.out.println("5. Notification Center");
    }

    public String askChoice() {
        return scanner.nextLine();
    }
}
