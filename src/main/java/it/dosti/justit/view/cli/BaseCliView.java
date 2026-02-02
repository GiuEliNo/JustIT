package it.dosti.justit.view.cli;

import java.util.Scanner;

@SuppressWarnings("java:S106") // uso di System.out accettabile nella CLI
public abstract class BaseCliView implements CLIView{
    protected final Scanner scanner;

    protected BaseCliView(){
        this.scanner = new Scanner(System.in);
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
interface CLIView {
    void render();
}

