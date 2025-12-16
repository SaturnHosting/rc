package dev.saturn.saturnrc.util;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SocketClient(String host, int port, String token) throws IOException {
        socket = new Socket(host, port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        sendRaw("AUTH " + token);
    }

    public void sendMessage(String username, String message) throws IOException {
        sendRaw("MESSAGE " +  username + " " + message);
    }

    private void sendRaw(String msg) throws IOException {
        out.write(msg);
        out.newLine();
        out.flush();
    }

    public void start() {
        Thread listener = new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Received: " + line);

                    if (line.startsWith("MESSAGE ")) {
                        String messageContent = line.substring("MESSAGE ".length());
                        Utils.chatMessage(messageContent, Utils.MessageType.MESSAGE);
                    } else if (line.startsWith("ERROR ")) {
                        String errorContent = line.substring("ERROR ".length());
                        Utils.chatMessage(errorContent, Utils.MessageType.ERROR);
                    }

                }
            } catch (IOException e) {
                System.out.println("Disconnected from server");
            } finally {
                close();
            }
        });

        listener.setDaemon(true);
        listener.start();
    }

    public void close() {
        try { socket.close(); } catch (IOException ignored) {}
    }
}
