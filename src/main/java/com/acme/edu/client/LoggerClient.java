package com.acme.edu.client;

import java.io.IOException;

public class LoggerClient {
    public static void main(String[] args) {
        try (LoggerClientStub logger = new LoggerClientStub()) {
            logger.log(1);
            logger.flush();
        } catch (IOException e) {

        }
    }
}
