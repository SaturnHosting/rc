package dev.saturn.saturnrc.util;

import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.systems.modules.Modules;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;
    private volatile boolean running = true;

    public SocketClient(String host, int port, String token, String username) throws IOException {
        this.username = username;
        socket = new Socket(host, port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        sendRaw("AUTH " + token + " " + username);

        String authResponse = in.readLine();
        if (authResponse == null || !authResponse.startsWith("AUTH_SUCCESS")) {
            String errorMsg = authResponse != null && authResponse.startsWith("ERROR ")
                ? authResponse.substring(6)
                : "Unknown error";
            throw new IOException("Authentication failed: " + errorMsg);
        }

        System.out.println("Authentication successful for user: " + username);
    }

    public void sendMessage(String username, String message) throws IOException {
        sendRaw("MESSAGE " + username + " " + message);
    }

    public void sendOnline() throws IOException {
        sendRaw("ONLINE");
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
                while (running && (line = in.readLine()) != null) {
                    System.out.println("Received: " + line);

                    if (line.startsWith("ONLINE ")) {
                        String playersList = line.substring("ONLINE ".length());
                        String[] players = playersList.split(",");

                        StringBuilder playerMessage = new StringBuilder("Online players: ");
                        for (int i = 0; i < players.length; i++) {
                            if (i > 0) playerMessage.append(", ");
                            playerMessage.append(players[i]);
                        }
                        Utils.chatMessage(playerMessage.toString(), Utils.MessageType.INFO);
                    }
                    else if (line.startsWith("MESSAGE ")) {
                        String messageContent = line.substring("MESSAGE ".length());
                        Utils.chatMessage(messageContent, Utils.MessageType.MESSAGE);
                    }
                    else if (line.startsWith("ERROR ")) {
                        String errorContent = line.substring("ERROR ".length());
                        Utils.chatMessage(errorContent, Utils.MessageType.ERROR);
                    }
                    else if (line.startsWith("CONNECTION ") && Modules.get().get(RCModule.class).connectionMsgs.get()) {
                        String connectionInfo = line.substring("CONNECTION ".length());
                        if (connectionInfo.contains(" joined")) {
                            String joinedUser = connectionInfo.substring(0, connectionInfo.length() - 7);
                            Utils.chatMessage(joinedUser + " connected", Utils.MessageType.INFO);
                        } else if (connectionInfo.contains(" left")) {
                            String leftUser = connectionInfo.substring(0, connectionInfo.length() - 5);
                            Utils.chatMessage(leftUser + " disconnected", Utils.MessageType.INFO);
                        }
                    } else if (line.startsWith("KEEPALIVE ")) {
                        String challengeStr = line.substring("KEEPALIVE ".length());
                        try {
                            sendRaw("KEEPALIVE_RESPONSE " + challengeStr);
                            System.out.println("Responded to keepalive challenge: " + challengeStr);
                        } catch (IOException e) {
                            System.out.println("Failed to respond to keepalive: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                if (running) {
                    System.out.println("Disconnected from server: " + e.getMessage());
                    Utils.chatMessage("Disconnected from server: " + e.getMessage(), Utils.MessageType.ERROR);
                }
            } finally {
                close();
            }
        });

        listener.setDaemon(true);
        listener.start();
    }

    public void close() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {}
    }
}
