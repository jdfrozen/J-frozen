package com.frozen.bio.client;


import com.frozen.bio.constants.HostInfo;
import com.frozen.bio.util.InputUtil;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author: Frozen
 * @create: 2019-04-08 15:31
 * @description: BioClient
 **/
public class BioClient {
    private static Logger log = Logger.getLogger("BioClient");
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(HostInfo.HOST_NAME, HostInfo.PORT);
        Scanner scanner = new Scanner(socket.getInputStream());
        scanner.useDelimiter("\n");
        PrintStream out = new PrintStream(socket.getOutputStream());
        Boolean flag =true;
        while (flag){
            String inputData = InputUtil.getString("请输入要发送的内容：").trim() ;
            out.println(inputData);
            if(scanner.hasNext()){
                String str = scanner.next().trim();
                log.info(str);
            }
            if ("byebye".equalsIgnoreCase(inputData)) {
                flag = false ;
            }
        }
        socket.close();
    }
}
