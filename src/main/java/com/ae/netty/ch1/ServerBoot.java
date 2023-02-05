package com.ae.netty.ch1;

/**
 * @author Aldrich Eugene
 */
public class ServerBoot {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
