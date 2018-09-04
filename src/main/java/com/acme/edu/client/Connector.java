package com.acme.edu.client;

import java.io.*;
import java.net.Socket;

public class Connector implements Closeable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    Connector(String address, int port) throws IOException {
        socket = new Socket(address, port);
        out = new PrintWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                socket.getOutputStream())));
        in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                socket.getInputStream())));
    }

    public int send(String request) throws IOException {
        out.println(request);
        int response = Integer.parseInt(in.readLine());
        return response;
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
