package com.t360;

import com.t360.constant.SocketConstant;
import com.t360.socket.ClientSocketPlayer;
import com.t360.socket.ServerSocketPlayer;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SocketStarter {

    private static final Logger LOGGER = Logger.getLogger(ServerSocketPlayer.class.getName());

    private String[] userParam = new String[2];
    private int numberOfClient, port;
    private String clientType, host;

    public SocketStarter() {
        super();
        // it will print only some information to log
        System.setProperty("java.util.logging.SimpleFormatter.format", SocketConstant.LOG_FORMAT);

    }

    /**
     * Initialize with user arguments
     */
    public SocketStarter(final String[] args) {
        this();
        userParam = args;
        if (userParam == null) {
            userParam = new String[0];
        }
        // get user arguments
        numberOfClient = getNumberOfClients();
        port = getPortInfo();
        host = getHostInfo();
        clientType = getClientType().orElse("");
    }

    /**
     * create server socket and-or client socket
     */
    public void configAndInitializeSocket() {

        // if this process will contain 2 client, create server and client socket
        if (numberOfClient == 2) {
            createServerSocket();
            createClientSocket();
        } else {

            // initialize server socket
            if (clientType.equalsIgnoreCase(SocketConstant.SERVER))
                createServerSocket();
            else
                // initialize server socket
                createClientSocket();
        }
    }

    /**
     * Get port input from user if it givens, Otherwise use default port number
     */
    private int getPortInfo() {
        int port = SocketConstant.DEFAULT_PORT;
        String portParam = System.getProperty(SocketConstant.SOCKET_PORT);
        if (portParam != null && !portParam.equals("")) {
            try {
                port = Integer.parseInt(portParam);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "User port parameter is not parseble. Default port selected");
            }
        }

        return port;
    }

    /**
     * Get host input from user if it givens, Otherwise use default port number
     */
    private String getHostInfo() {
        String host = SocketConstant.DEFAULT_HOST;
        String hostParam = System.getProperty(SocketConstant.SOCKET_HOST);
        if (hostParam != null && !hostParam.equals("")) {
            host = hostParam;
        }
        return host;
    }

    //

    /**
     * If 1 client will be initialized, determine socket type
     */
    private Optional<String> getClientType() {
        if (userParam.length >= 2 && userParam[0].equals("1")) {
            if (userParam[1].equalsIgnoreCase(SocketConstant.SERVER) || userParam[1].equals(SocketConstant.CLIENT))
                return Optional.of(userParam[1]);
            else {
                throw new IllegalArgumentException("Enter the client type as a parameter");
            }
        }
        return Optional.empty();
    }

    //

    /**
     * Get number of clients will be initialized in this process it it givens Otherwise use default value
     */
    private int getNumberOfClients() {
        if (userParam.length == 0)
            return SocketConstant.DEFAULT_NUMBER_OF_CLIENT;
        else if (!(userParam[0].equals("1") || userParam[0].equals("2")))
            throw new IllegalArgumentException(userParam[0]);
        else
            return Integer.parseInt(userParam[0]);

    }

    /**
     * create and start client socket thread with {@link SocketStarter#port}.
     */
    private void createClientSocket() {
        new Thread(new ClientSocketPlayer(port, host)).start();
    }

    /**
     * create and start server socket thread with {@link SocketStarter#port}.
     */
    private void createServerSocket() {
        new Thread(new ServerSocketPlayer(port, host)).start();
    }

}
