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

public class ClientSocketPlayer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ClientSocketPlayer.class.getName());

    private Socket clientSocket = null;
    private BufferedReader input = null;

    private PrintStream output = null;

    private int sentMessageCounter = 0, receivedMessageCounter = 0;
    private int port;

    // FIXME Really need a  private constructor
    private ClientSocketPlayer() {
    }

    // FIXME need host parameter? It is never used
    public ClientSocketPlayer(int port, String host) {
        super();
        this.port = port;
    }

    /**
     * @see Runnable
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
            LOGGER.log(Level.SEVERE, "exception occurred, detail" + e);
        } finally {
            // close opened stream,socket
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
                if (clientSocket != null)
                    clientSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "exception occurred while socket-stream closed, detail", e);
            }

        }

    }

    /**
     * Write message to server and increase counter.
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
     * read message from server and increase counter
     *
     * @throws IOException {@link ClientSocketPlayer#input} should throw an {@link IOException} exception.
     */
    private void readMessageFromServer() throws IOException {
        String messageReceived = input.readLine();
        receivedMessageCounter++;

        LOGGER.log(Level.INFO, "Client received message: {0} - received message counter is: {1}",
                new Object[]{messageReceived, receivedMessageCounter});
    }

    /**
     * Try to connect server socket.
     * I will try  {@link SocketConstant#MAX_TRIAL_NUMBER} times to connect.
     */
    private void connectToServer() {
        int userTrialNumber = 0;
        while (userTrialNumber <= SocketConstant.MAX_TRIAL_NUMBER) {
            try {
                clientSocket = new Socket("localhost", port);
            } catch (UnknownHostException e) {
                LOGGER.log(Level.SEVERE, "hostname error, client could not connect,exception" + e);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "client could not connect to server socket, exception" + e);
            }
            if (clientSocket != null)
                break;
            userTrialNumber++;
        }

    }

}
