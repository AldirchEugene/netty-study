package com.ae.socket.ch1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket编程
 */
public class TCPServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 1、创建服务端socket
            serverSocket = new ServerSocket(8888);

            // 2、接收客户端连接【阻塞】
            Socket socket = serverSocket.accept();
            System.out.println("接收到一个客户端连接："+socket.getRemoteSocketAddress());

            // 3、读取客户端数据【需要指定字符编码为GBK】【in.read(bytes)阻塞方法】
            InputStream in = socket.getInputStream();
            while (true){
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) == -1){
                    break;
                }
                String message = new String(bytes, 0, len,"GBK");
                System.out.println("接收到客户端发送的数据："+message);

                // 4、响应客户端
                OutputStream out = socket.getOutputStream();
                out.write(message.getBytes());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
