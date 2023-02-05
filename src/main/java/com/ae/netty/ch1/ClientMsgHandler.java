package com.ae.netty.ch1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Aldrich Eugene
 */
public class ClientMsgHandler {

    public static final int MAX_DATA_LEN = 1024;
    private Socket socket;

    /**
     * 将监听的连接传入到处理器
     * @param socket
     */
    public ClientMsgHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * 处理客户端的消息
     */
    public void start(){
        System.out.println("新客户端接入");
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true){
                byte[] data = new byte[MAX_DATA_LEN];
                int len = 0;
                while ((len = inputStream.read(data)) != -1){
                    String message = new String(data, 0, len);
                    System.out.println("客户端传来消息: " + message);
                    socket.getOutputStream().write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
