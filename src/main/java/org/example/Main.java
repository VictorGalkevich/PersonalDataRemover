package org.example;

import org.example.connector.TcpConnector;
import org.example.worker.Worker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        TcpConnector tcpConnector = new TcpConnector(8555);
        while (!tcpConnector.isConnected()) {
            System.out.println("Waiting for connection...");
        }
        Socket socket = tcpConnector.getConnectionSocket();
        try (BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream())) {
            String received = Arrays.toString(inputStream.readAllBytes());
            Worker.doWork(received);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}