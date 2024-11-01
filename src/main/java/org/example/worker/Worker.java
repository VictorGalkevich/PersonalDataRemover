package org.example.worker;

import org.example.connector.DbConnector;
import org.example.enumeration.FileType;
import org.example.source.Csv;
import org.example.source.Db;
import org.example.sql.RequestBuilder;
import org.example.transformer.DbToCsvTransformer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker {
    private static final StringBuilder res = new StringBuilder();
    private static final Lock lock = new ReentrantLock();
    private static int start;
    private static int end;
    private static int threadNumber;
    private static List<String> fields;

    public static void doWork(String string, int threadNumber) {
        Worker.threadNumber = threadNumber;
        String[] params = string.split(" ");
        FileType source = FileType.valueOf(params[0]);
        FileType target = FileType.valueOf(params[1]);
        start = Integer.parseInt(params[2]);
        end = Integer.parseInt(params[3]);
        int size = Integer.parseInt(params[4]);
        fields = new ArrayList<>(size);
        fields.addAll(Arrays.asList(params).subList(5, size + 5));

        switch (source) {
            case DB -> handleDb(target, params, 5 + size + 1);
            case CSV -> handleCsv();
        }
    }

    private static void handleDb(FileType target, String[] params, int pos) {
        String address = params[pos];
        String table = params[pos + 1];
        String username = params[pos + 2];
        String password = params[pos + 3];
        if (target == FileType.DB) {

        } else {
            int size = start - end;
            int block = size / threadNumber;
            
            try {
                Thread[] threads = new Thread[threadNumber];
                for (int i = 0; i < threadNumber; i++) {
                    int start = block * i;
                    int end = block * (i + 1);

                    threads[i] = new Thread(() -> {
                        DbConnector connector = new DbConnector();
                        try (Connection connection = connector.createConnection(address, username, password)) {
                            String generate = RequestBuilder.generate(fields, table);
                            PreparedStatement stmt = connection.prepareStatement(generate);
                            ResultSet resultSet = stmt.executeQuery();

                            Db retrievedData = new Db(resultSet, fields);

                            DbToCsvTransformer transformer = new DbToCsvTransformer();
                            Csv transform = transformer.transform(retrievedData, start - end);

                            while (!lock.tryLock()) {}
                            res.append(transform.getBody());
                            lock.unlock();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    threads[i].start();
                }
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void handleCsv() {

    }
}
