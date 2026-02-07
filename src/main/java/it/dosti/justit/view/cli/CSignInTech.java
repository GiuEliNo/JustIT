package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CSignInTech extends BaseCliView {
    @Override
    public void render() {
        System.out.println("===== SIGN IN TECHNICIAN =====");
    }

    public String askUsername() {
        System.out.print("Username (you will not be able to change it in the future): ");
        return scanner.nextLine();
    }

    public String askPassword() {
        System.out.print("Password: ");
        return scanner.nextLine();
    }

    public String askFullName() {
        System.out.print("Full name (Mario Rossi): ");
        return scanner.nextLine();
    }

    public String askEmail() {
        System.out.print("Email (name@example.com): ");
        return scanner.nextLine();
    }

    public String askShopName() {
        System.out.print("Shop name: ");
        return scanner.nextLine();
    }

    public void errorSignInTech() {
        System.out.println("Error sign in tech try again");
    }
}
