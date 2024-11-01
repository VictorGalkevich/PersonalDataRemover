package org.example.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpConnector {
    private static ServerSocket initialSocket;
    private static int port;
    private static Socket connectionSocket;
    private static boolean connected;

    public TcpConnector(int port) {
        TcpConnector.port = port;
        awaitConnection();
    }

    private void awaitConnection() {
        try {
            initialSocket = new ServerSocket(port);
            connectionSocket = initialSocket.accept();
            connected = true;
        } catch (IOException e) {
            connected = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }
}
