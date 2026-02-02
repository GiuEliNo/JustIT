package it.dosti.justit.view.cli;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public class CLauncherView extends BaseCliView{

    @Override
    public void render() {
        System.out.println("===== WELCOME TO JUSTIT =====");
        System.out.println("1. Login");
        System.out.println("2. SignIn");
    }

    public String askChoice(){
        return scanner.nextLine();
    }
}