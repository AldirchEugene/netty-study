package com.ae.netty.ch1;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Aldrich Eugene
 */
public class Client {

    private static final String HOST = "192.168.0.101";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORT);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("客户端启动成功!");
                    while (true) {
                        try {
                            String message = "hello world";
                            System.out.println("客户端发送数据: " + message);
                            socket.getOutputStream().write(message.getBytes());
                        } catch (Exception e) {
                            System.out.println("写数据出错!");
                        }
                        sleep();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
