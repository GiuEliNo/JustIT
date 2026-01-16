package it.dosti.justit;

import java.sql.SQLException;

public class CLIMode extends BaseAppMode {
    @Override
    public void start(String[] args) {
        try {
            connectToDB();
            System.out.println("CLI Mode");
        } catch (SQLException ignored) {
        }
    }
}
