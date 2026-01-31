package it.dosti.justit.utils;

import it.dosti.justit.model.SessionModel;

import java.util.logging.Logger;

public class JustItLogger {

    private static JustItLogger instance;

    private JustItLogger(){}

        public static JustItLogger getInstance() {
            if (instance == null) {
                instance = new JustItLogger();
            }
            return instance;
        }

        public void log(String message)
        {
            System.out.println(message);
        }
}
