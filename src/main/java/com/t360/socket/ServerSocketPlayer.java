package com.t360.socket;

import com.t360.constant.SocketConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServerSocketPlayer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerSocketPlayer.class.getName());
    private final int port;
    private final String host;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintStream output = null;
    private int sentMessageCounter = 0, receivedMessageCounter = 0;

    public ServerSocketPlayer(final int port, final String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * @see Runnable#run
     */
    public void run() {

        assert (port > 0);

        LOGGER.log(Level.INFO, "server socket is starting");

        try {
            // initialize server socket
            initializeServerSocket();

            LOGGER.log(Level.INFO, "Server socket connected with {0} port", port);

            // acpet client request
            socket = serverSocket.accept();
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());

            // write and read until process will finished
            while (sentMessageCounter < 10 && receivedMessageCounter < 10) {
                // write message
                writeMessageToClient();
                // read message
                readMessageFromClient();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "exception occured, detail" + e);
        } finally {
            // close opened stream,socket
            SocketConstant.safelyClose(output, LOGGER);
            SocketConstant.safelyClose(input, LOGGER);
            SocketConstant.safelyClose(socket, LOGGER);
            SocketConstant.safelyClose(serverSocket, LOGGER);
        }
    }

    /**
     * Read message from client and increase counter
     *
     * @throws IOException {@link ServerSocketPlayer#input} may throw an {@link IOException} exception.
     */
    private void readMessageFromClient() throws IOException {
        String messageReceived = input.readLine();
        receivedMessageCounter++;

        LOGGER.log(Level.INFO, "Server received message: {0} - received message counter is: {1}",
                new Object[]{messageReceived, receivedMessageCounter});
    }

    /**
     * Write message to client and increase counter
     */
    private void writeMessageToClient() {
        String messageSent = "Hi Client!";
        output.println(messageSent);
        output.flush();
        sentMessageCounter++;

        LOGGER.log(Level.INFO, "Server sent message: {0} - sent message counter is: {1}",
                new Object[]{messageSent, sentMessageCounter});
    }

    /**
     * initialize server socket
     */
    private void initializeServerSocket() {
        try {
            InetAddress addr = InetAddress.getByName(host);
            serverSocket = new ServerSocket(port, 50, addr);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server socket could not initialized, exception detail:" + e);
        }
    }

}
