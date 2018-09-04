package com.acme.edu;

import com.acme.edu.controller.Controller;
import com.acme.edu.message.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Controller controller = new Controller(System.out::println);
    public static void main(String[] args) {
        try(ServerSocket portListener = new ServerSocket(6666)) {
            try (Socket clientConnection = portListener.accept();
                 PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(
                                    new BufferedOutputStream(
                                            clientConnection.getOutputStream())));
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(
                                    new BufferedInputStream(
                                            clientConnection.getInputStream())));
            ) {
                String type = in.readLine();
                String data = in.readLine();
                System.out.println("сервер тип: " + type);
                System.out.println("сервер данные: " + data);
                int errorCode = 0;
                switch(type) {
                    case "BYTE":
                        errorCode = controller.log(new ByteMessage(Byte.parseByte(data)));
                        break;
                    case "BOOLEAN":
                        errorCode = controller.log(new BooleanMessage(Boolean.parseBoolean(data)));
                        break;
                    case "CHAR":
                        errorCode = controller.log(new CharMessage(data.charAt(0)));
                        break;
                    case "INT":
                        errorCode = controller.log(new IntMessage(Integer.parseInt(data)));
                        break;
                    case "STRING":
                        errorCode = controller.log(new StringMessage(data));
                        break;
                    default:
                        errorCode = 1;
                }
                out.println(errorCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
