package com.t360.socket;

import com.t360.constant.SocketConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ClientSocketPlayer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ClientSocketPlayer.class.getName());
    private final int port;
    private final String host;
    private Socket clientSocket = null;
    private BufferedReader input = null;
    private PrintStream output = null;
    private int sentMessageCounter = 0, receivedMessageCounter = 0;


    public ClientSocketPlayer(final int port, final String host) {
        this.port = port;
        this.host = host;
    }

    /**
     * @see Runnable#run
     */
    public void run() {
        assert (port > 0);

        LOGGER.log(Level.INFO, "client socket is starting");
        try {
            // try to connect server socket
            connectToServer();

            LOGGER.log(Level.INFO, "client socket connected with {0} port", port);

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream());

            while (sentMessageCounter < 10 && receivedMessageCounter < 10) {
                // read message
                readMessageFromServer();
                // write message
                writeMessageToServer();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "exception occured, detail" + e);
        } finally {
            // close opened stream,socket
            SocketConstant.safelyClose(output, LOGGER);
            SocketConstant.safelyClose(input, LOGGER);
            SocketConstant.safelyClose(clientSocket, LOGGER);
        }

    }

    /**
     * Write message to server and increase counter
     */
    private void writeMessageToServer() {
        String messageSent = "Hi Server!";
        output.println(messageSent);
        output.flush();
        sentMessageCounter++;

        LOGGER.log(Level.INFO, "Client sent message: {0} - sent message counter is: {1}",
                new Object[]{messageSent, sentMessageCounter});
    }

    /**
     * Read message from server and increase counter.
     *
     * @throws IOException {@link ClientSocketPlayer#input} may throw an {@link IOException} exception.
     */
    private void readMessageFromServer() throws IOException {
        String messageReceived = input.readLine();
        receivedMessageCounter++;

        LOGGER.log(Level.INFO, "Client received message: {0} - received message counter is: {1}",
                new Object[]{messageReceived, receivedMessageCounter});
    }

    /**
     * Try to connect server socket. It will try {@link SocketConstant#MAX_TRIAL_NUMBER} times to connect
     */
    private void connectToServer() {
        int userTrialNumber = 0;
        while (userTrialNumber <= SocketConstant.MAX_TRIAL_NUMBER) {
            userTrialNumber++;
            try {
                clientSocket = new Socket(host, port);
            } catch (UnknownHostException e) {
                LOGGER.log(Level.SEVERE,
                        "hostname error, client could not connect. Number of trials {0}. Exception {1}",
                        new Object[]{userTrialNumber, e});
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,
                        "client could not connect to server socket. Number of trials {0}. Exception {1}",
                        new Object[]{userTrialNumber, e});
            }
            if (clientSocket != null)
                break;
        }

    }

}
