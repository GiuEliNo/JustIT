package it.dosti.justit.view.cli;

public class CLoginView extends BaseCliView {

    @Override
    public void render() {
        System.out.println("===== LOGIN =====");
    }

    public String askUsername() {
        System.out.print("Username: ");
        return scanner.nextLine();
    }

    public String askPassword() {
        System.out.print("Password: ");
        return scanner.nextLine();
    }

    public String askRole() {
        System.out.print("Role (CLIENT/TECHNICIAN): ");
        return scanner.nextLine();
    }
}

