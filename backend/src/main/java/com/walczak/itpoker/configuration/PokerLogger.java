package com.walczak.itpoker.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokerLogger {
    private final Logger logger;

    public PokerLogger() {
        this.logger = LoggerFactory.getLogger("ITPoker");
    }

    public void info(String logHeader, String message) {
        logger.info(logHeader + " :: " + message);
    }

    public void info(String logHeader, Object object) {
        logger.info(logHeader + " :: " + object);
    }

    public void info(String message) {
        logger.info(message);
    }
}
