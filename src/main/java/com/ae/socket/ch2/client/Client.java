package com.ae.socket.ch2.client;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        // 超时时间
        socket.setSoTimeout(3000);

        // 连接本地，端口2000；超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);

        System.out.println("已发起服务器连接，并进入后续流程~");
        System.out.println("客户端信息：" + socket.getLocalAddress() + " p:" + socket.getLocalPort());
        System.out.println("服务端信息：" + socket.getInetAddress() + " p:" + socket.getPort());

        try {
            // 发送接收数据
            todo(socket);
        }catch (Exception e){
            System.out.println("异常关闭");
        }

        // 释放资源
        socket.close();
        System.out.println("客户端已退出~");
    }

    private static void todo(Socket socket) throws Exception{
        // 获取键盘输入流并转BufferedReader
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // 获取客户端输出流并转打印流
        PrintStream printStream = new PrintStream(socket.getOutputStream());

        // 获取客户端输入流并转BufferedReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        boolean flag = true;
        do {
            // 键盘读取一行
            String str = input.readLine();
            // 发送到服务器
            printStream.println(str);

            // 从服务器读取一行
            String echo = bufferedReader.readLine();
            if("bye".equalsIgnoreCase(echo)){
                flag = false;
            }else {
                System.out.println(echo);
            }
        }while (flag);

        // 释放资源
        input.close();
        printStream.close();
        bufferedReader.close();
    }
}
