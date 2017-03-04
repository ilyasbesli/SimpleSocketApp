package com.t360;

/**
 * The entry point of application.
 *
 * @see com.t360.constant.SocketConstant
 * @see com.t360.socket.ClientSocketPlayer
 * @see com.t360.socket.ServerSocketPlayer
 */
public class MessengerMain {

    /**
     * Parameter in arguments:
     * First param : number of client. Accepted values are 1 or 2
     * <p>
     * second param (optional) : if you create 1 client, select initializer(server) or not.
     * Accepted values are server or client
     */
    public static void main(String[] args) {

        SocketStarter socketStarter = new SocketStarter(args);
        socketStarter.configAndInitializeSocket();
    }

}
