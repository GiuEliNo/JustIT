package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
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
        System.out.print("Role [C]LIENT/[T]ECHNICIAN (default CLIENT): ");
        return scanner.nextLine();
    }
}

