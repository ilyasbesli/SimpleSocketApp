package com.t360;

import com.t360.constant.SocketConstant;
import com.t360.socket.ClientSocketPlayer;
import com.t360.socket.ServerSocketPlayer;

public class MessengerMain {

	/**
	 * Parameter in arguments:
	 * First param : number of client. Accepted values are 1 or 2
	 * 
	 * second param (optional) : if you create 1 client, select initializer(server) or not.
	 * Accepted values are server or client
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		MessengerMain main = new MessengerMain();
		main.startMessenger(args);
	}

	// start configure and initialize socket thread
	private void startMessenger(String[] args) {
		int numberOfClient, port;
		String clientType;

		// get number of clients in this process and port info
		numberOfClient = getNumberOfClients(args);
		port = getPortInfo(args);

		// create server socket and-or client socket
		// if this process will contain 2 client, create server and client socket
		if (numberOfClient == 2) {
			createServerSocket(port);
			createClientSocket(port);
		} else {
			// if this process will contain 1 client, look socket type
			clientType = getClientType(args);
			// initialize server socket
			if (clientType.equalsIgnoreCase(SocketConstant.SERVER))
				createServerSocket(port);
			else
				// initialize server socket
				createClientSocket(port);
		}
	}

	/**
	 * Get port input from user if it givens, Otherwise use default port number
	 * 
	 * @param args
	 * @return
	 */
	private int getPortInfo(String[] args) {
		String portParam = System.getProperty(SocketConstant.SOCKET_PORT_NUMBER);
		if (portParam != null && !portParam.equals("")) {
			try {
				return Integer.parseInt(portParam);
			} catch (NumberFormatException e) {
				return SocketConstant.DEFAULT_PORT;
			}
		} else {
			return SocketConstant.DEFAULT_PORT;
		}
	}

	/**
	 * If 1 client will be initialized, determine socket type
	 * 
	 * @param args
	 * @return
	 */
	private String getClientType(String[] args) {
		if (args != null && args.length >= 2
				&& (args[1].equalsIgnoreCase(SocketConstant.SERVER) || args[1].equals(SocketConstant.CLIENT))) {
			return args[1];
		} else {
			throw new IllegalArgumentException("Enter the client type as a parameter");
		}
	}

	/**
	 * Get number of clients will be initialized in this process it it givens
	 * Otherwise use default value
	 * 
	 * @param args
	 * @return
	 */
	private int getNumberOfClients(String[] args) {
		if (args == null || args.length == 0)
			return SocketConstant.DEFAULT_NUMBER_OF_CLIENT;
		if (args != null && args.length > 0 && !args[0].equals("1") && !args[0].equals("2"))
			throw new IllegalArgumentException(args[0]);
		else
			return Integer.parseInt(args[0]);

	}

	/**
	 * create and start client socket thread with port
	 * 
	 * @param port
	 */
	private void createClientSocket(int port) {
		new Thread(new ClientSocketPlayer(port)).start();
	}

	/**
	 * create and start server socket thread with port
	 * 
	 * @param port
	 */
	private void createServerSocket(int port) {
		new Thread(new ServerSocketPlayer(port)).start();
	}

}
