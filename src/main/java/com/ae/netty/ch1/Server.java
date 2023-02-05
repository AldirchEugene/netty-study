package com.ae.netty.ch1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aldrich Eugene
 */
public class Server {

    private ServerSocket serverSocket;

    /**
     * 构造器中创建ServerSocket
     * @param port
     */
    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，端口:" + port);
        } catch (IOException e) {
            System.out.println("服务端启动失败");
        }
    }

    /**
     * 启动一个线程来监听客户端连接
     */
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        while (true){
            try {
                Socket socket = serverSocket.accept();
                new ClientMsgHandler(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
