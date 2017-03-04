package com.t360.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketPlayer implements Runnable {

	private static final Logger	LOGGER				= Logger.getLogger(ServerSocketPlayer.class.getName());

	private ServerSocket		serverSocket		= null;
	private Socket				socket				= null;
	private BufferedReader		input				= null;;
	private PrintStream			output				= null;

	private String				messageReceived, messageSent;
	private int					sentMessageCounter	= 0, receivedMessageCounter = 0;
	private int					port;
	private String				host;

	public ServerSocketPlayer() {
		super();
	}

	public ServerSocketPlayer(int port, String host) {
		super();
		this.port = port;
		this.host = host;
	}

	public void run() {

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
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				if (socket != null)
					socket.close();
				if (serverSocket != null)
					serverSocket.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "exception occured while socket-stream closed, detail", e);
			}

		}
	}

	/**
	 * Read message from client and increase counter
	 */
	private void readMessageFromClient() throws IOException {
		messageReceived = input.readLine();
		receivedMessageCounter++;

		LOGGER.log(Level.INFO, "Server received message: {0} - received message counter is: {1}",
				new Object[] { messageReceived, receivedMessageCounter });
	}

	/**
	 * Write message to client and increase counter
	 */
	private void writeMessageToClient() {
		messageSent = "Hi Client!";
		output.println(messageSent);
		output.flush();
		sentMessageCounter++;

		LOGGER.log(Level.INFO, "Server sent message: {0} - sent message counter is: {1}",
				new Object[] { messageSent, sentMessageCounter });
	}

	/**
	 * initialize server socket
	 */
	private void initializeServerSocket() {
		try {
			InetAddress addr = InetAddress.getByName(host);
			serverSocket = new ServerSocket(port, 50, addr);
		} catch (IOException e) {
			System.out.println("Server socket could not initialized, exception detail:" + e);
		}
	}

}
