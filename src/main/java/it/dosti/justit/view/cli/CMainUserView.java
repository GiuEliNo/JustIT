package it.dosti.justit.view.cli;

public class CMainUserView extends BaseCliView{
    @Override
    public void render() {
        System.out.println("===== WELCOME TO JUSTIT ====="); // TODO prendere nome dell'utente dal SessionModel
        System.out.println("1. User Profile");
        System.out.println("2. Browsing Shop");
        System.out.println("3. List Booking");
    }

    public String askChoice() {
        return scanner.nextLine();
    }
}
