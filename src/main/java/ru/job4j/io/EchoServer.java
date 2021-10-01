package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    for (String str = in.readLine(); str != null && !str.isEmpty(); str = in.readLine()) {
                        System.out.println(str);
                        if (str.contains("=")) {
                            String[] s = str.split("[\\s=]+");
                            if ("Exit".equals(s[2])) {
                               server.close();
                            }
                            if ("Hello".equals(s[2])) {
                                out.write("Hello\r\n".getBytes());
                            } else {
                                out.write((s[2] + "\r\n").getBytes());
                            }
                        }
                    }
                    out.flush();
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in log example", e);
        }
    }
}