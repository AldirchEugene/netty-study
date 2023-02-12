package com.ae.socket.ch2.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);

        System.out.println("服务器准备就绪~");
        System.out.println("服务器信息：" + serverSocket.getInetAddress() + " p:" + serverSocket.getLocalPort());

        // 等待客户端连接
        for (;;){
            // 得到客户端
            Socket client = serverSocket.accept();
            // 客户端构造异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            // 启动线程
            clientHandler.start();
        }
    }

    private static class ClientHandler extends Thread{
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("新客户端连接：" + socket.getInetAddress() +
                    "p:"+socket.getPort());
            try {
                // 接收客户端数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // 获取打印流用来输出
                PrintStream printStream = new PrintStream(socket.getOutputStream());

                do{
                    String str = bufferedReader.readLine();
                    if("bye".equalsIgnoreCase(str)){
                        flag = false;
                        // 回送数据
                        printStream.println("bye");
                    }else {
                        // 打印并回送数据长度
                        System.out.println(str);
                        printStream.println("回送：" + str.length());
                    }
                }while (flag);

                // 释放资源
                printStream.close();
                bufferedReader.close();

            }catch (Exception e){
                System.out.println("连接异常断开");
            }finally  {
                // 连接关闭
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("客户端已退出：" + socket.getInetAddress() +
                    "p:"+socket.getPort());
        }
    }
}
