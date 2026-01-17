package it.dosti.justit.view.cli;

public class CLauncherView implements CLIView{
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
    }