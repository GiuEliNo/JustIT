package it.dosti.justit.view.cli;

import java.util.Scanner;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public abstract class BaseCliView implements CLIView{
    protected final Scanner scanner;
    protected static final String OPTION_BACK = "0. Back";

    protected BaseCliView(){
        this.scanner = new Scanner(System.in);
    }

    protected Integer readIntCheck() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input not valid, only a number");
            return -1;
        }
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
interface CLIView {
    void render();
}
