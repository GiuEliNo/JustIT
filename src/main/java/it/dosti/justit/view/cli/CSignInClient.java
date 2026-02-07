package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CSignInClient  extends BaseCliView{
    @Override
    public void render() {
        System.out.println("===== SigIn Client =====");
    }

    public Integer askUsername() {
        System.out.println("Enter username (You will not be able to change it in the future.): ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String askPassword() {
        System.out.println("Password: ");
        return scanner.nextLine();
    }

    public String askFullName() {
        System.out.println("Full name (Mario Rossi): ");
        return scanner.nextLine();
    }

    public String askEmail() {
        System.out.println("Email (name@example.com): ");
        return scanner.nextLine();
    }

    public String askCountry() {
        System.out.println("Country (Italy): ");
        return scanner.nextLine();
    }

    public String askCity() {
        System.out.println("City (Rome): ");
        return scanner.nextLine();
    }

    public String askStreetAddress() {
        System.out.println("Street Address (Via Giolitti 1): ");
        return scanner.nextLine();
    }
}
