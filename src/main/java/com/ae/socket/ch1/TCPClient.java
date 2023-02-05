package com.ae.socket.ch1;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 1、连接服务端并创建一个socket
            socket = new Socket("192.168.0.101", 8888);

            // 2、发送数据
            OutputStream out = socket.getOutputStream();
            String req = "Hello Server!";
            out.write(req.getBytes());
            out.flush();

            // 3、接收服务器的数据
            InputStream in = socket.getInputStream();
            while (true){
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) == -1){
                    break;
                }
                String message = new String(bytes, 0, len,"GBK");
                System.out.println("接收到服务端响应的数据："+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
