package com.acme.edu.client;

import java.io.Closeable;
import java.io.IOException;

public class LoggerClientStub implements Closeable {
    private Connector connector;

    public LoggerClientStub() throws IOException {
        connector = new Connector("127.0.0.1", 6666);
    }

    public int flush() {
        return 0;
    }

    public int log(int message) throws IOException {
        String request = "INT" + System.lineSeparator() + message;
        return connector.send(request);
    }

    public int log(int[] message) {
        return 0;
    }

    public int log(int[][] message) {
        return 0;
    }

    public int log(byte message) throws IOException {
        String request = "BYTE" + System.lineSeparator() + message;
        return connector.send(request);
    }

    public int log(char message) throws IOException {
        String request = "CHAR" + System.lineSeparator() + message;
        return connector.send(request);
    }

    public int log(String message) throws IOException {
        String request = "STRING" + System.lineSeparator() + message;
        return connector.send(request);
    }

    public int log(boolean message) throws IOException {
        String request = "BOOLEAN" + System.lineSeparator() + message;
        return connector.send(request);
    }

    public int log(Object message) {
        return 0;
    }

    @Override
    public void close() throws IOException {
        connector.close();
    }
}
