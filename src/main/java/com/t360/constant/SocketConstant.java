package com.t360.constant;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Constants for program.
 *
 * @see com.t360.socket.ClientSocketPlayer
 * @see com.t360.socket.ServerSocketPlayer
 */
public interface SocketConstant {

    /**
     * Server parameter.
     */
    String SERVER = "server";

    /**
     * Client name. // FIXME correct definition.
     */
    String CLIENT = "client";

    /**
     * Port args name
     */
    String SOCKET_PORT = "port";

    /**
     * Host argument name.
     */
    String SOCKET_HOST = "host";

    /**
     * Default host name.
     */
    String DEFAULT_HOST = "localhost";

    /**
     * Default port for opening server.
     */
    int DEFAULT_PORT = 9999;

    /**
     * Log format.
     */
    String LOG_FORMAT = "%1$tH:%1$tM:%1$tS:%1$tL %4$-6s %5$s%6$s%n";


    /**
     * Max try for read message from server.
     */
    int MAX_TRIAL_NUMBER = 3;

    /**
     * Default number fo client.
     */
    int DEFAULT_NUMBER_OF_CLIENT = 2;


    /**
     * Safely close
     *
     * @param obj    object which will be close {@link Closeable}
     * @param logger Logger for log {@link java.util.logging.Logger}
     */
    static void safelyClose(final Closeable obj, final java.util.logging.Logger logger) {
        if (obj != null) try {
            obj.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "exception occured while socket-stream closed, detail", e);
        }
    }
}
