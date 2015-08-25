package pl.mszarlinski.algo.eddsp.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mszarlinski on 2015-08-23.
 */
class TreeLogger {

    private static final Logger log = Logger.getLogger(TreeLogger.class.getName());

    static void log(final String message) {
        log.log(Level.INFO, message);
    }
}
