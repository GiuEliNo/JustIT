package it.dosti.justit.utils;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JustItLogger {

    private static final JustItLogger instance = new JustItLogger();

    private final Logger logger;

    private JustItLogger(){
        logger = Logger.getLogger(JustItLogger.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }

    public static JustItLogger getInstance() {
            return instance;
    }


    public void info(String message) {
            logger.info(message);
    }

    public void warn(String message) {
        logger.warning(message);
    }

    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    public void error(String message, Throwable error) {
        logger.log(Level.SEVERE, message, error);
    }

}
