package com.t360;

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

		SocketStarter socketStarter = new SocketStarter(args);
		socketStarter.configAndInitializeSocket();
	}

}
