package it.dosti.justit.view.cli;

import java.util.Scanner;

public class CLauncherView implements CLIView{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void render() {
        System.out.println("===== WELCOME TO JUSTIT =====");
        System.out.println("1. Login");
        System.out.println("2. SignIn");
    }

    @Override
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String askChoice(){
        return scanner.nextLine();
    }
}