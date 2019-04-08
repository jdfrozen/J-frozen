package com.frozen.bio.server;

import com.frozen.bio.constants.HostInfo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author: Frozen
 * @create: 2019-04-08 15:29
 * @description: BioServer
 **/
public class BioServer {
    private static Logger log = Logger.getLogger("BioServer");

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(HostInfo.PORT);
        log.info("服务器端已经启动，监听的端口为：" + HostInfo.PORT);
        boolean flag = true;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (flag) {
            Socket socket = serverSocket.accept();
            executorService.submit(new ServerHandler(socket));
        }
    }

    private static class ServerHandler implements Runnable {
        private Socket socket;
        private Scanner scanner;
        private PrintStream out;
        private boolean flag = true;

        public ServerHandler(Socket socket) {
            this.socket = socket;
            try {
                this.scanner = new Scanner(socket.getInputStream());
                this.scanner.useDelimiter("\n");
                this.out = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (flag) {
                if (scanner.hasNext()) {
                    String str = scanner.next().trim();
                    log.info(str);
                    if ("byebye".equalsIgnoreCase(str)) {
                        this.out.println("Byte...");
                        this.flag = false;
                    } else {
                        out.println("echo " + str);
                    }
                }
            }
            this.scanner.close();
            this.out.close();
            try {
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }
}
