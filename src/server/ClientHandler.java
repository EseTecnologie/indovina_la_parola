package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class ClientHandler implements Runnable {

    final Socket socket;
    final Scanner scan;
    String name;
    boolean isLosggedIn;
    List<ClientHandler> clients;
    private DataInputStream input;
    private DataOutputStream output;
    private final String wordToFind;

    public ClientHandler(Socket socket, String name, List<ClientHandler> clients, String wordToFind) {
        this.socket = socket;
        scan = new Scanner(in);
        this.name = name;
        isLosggedIn = true;
        this.clients = clients;
        this.wordToFind = wordToFind;

        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            log("ClientHander : " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        String received;
        write(output, "Your name : " + name);

        System.out.println("Clients clients:");
        write(output, "Clients status:");
        for (ClientHandler client : clients
        ) {
            if (client.isLosggedIn) {
                log("Client " + client.name + " is active");
                if (!client.name.equals(name))
                    write(output, "Client " + client.name + " is active");

            } else {
                log("Client " + client.name + " is inactive");
                write(output, "Client " + client.name + " is inactive");

            }
        }

        write(output, "Parola da trovare --> " + FindWordManager.convertToAsterisks(wordToFind, ""));

        while (true) {
            received = read();
            if (received.equalsIgnoreCase(Constants.LOGOUT)) {
                this.isLosggedIn = false;
                log("Client " + name + " logged out");
                closeSocket();
                closeStreams();
                break;
            }

            try {
                forwardToClient(received);
            } catch (Exception e) {
                log("Client " + name + " logged out");
                isLosggedIn = false;
                return;
            }
        }
        closeStreams();
    }

    private void
    forwardToClient(String received) {
        if (received.charAt(0) != '#') {
            log(received);
            return;
        }
        // username # message
        StringTokenizer tokenizer = new StringTokenizer(received, "#"); //TODO Convert to string to avoid space issues
        String recipient = tokenizer.nextToken().trim();
        String message = tokenizer.nextToken().trim();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();

        for (ClientHandler c : Server.getClients()) {
            if (c.isLosggedIn && c.name.equals(recipient)) {
                write(c.output, dtf.format(now) + " " + recipient + " : " + message);
                log(name + " --> " + recipient + " : " + message);
                break;
            }
        }

    }

    private String read() {
        String line = "";
        try {
            line = input.readUTF();
        } catch (IOException ex) {
            log("read : " + ex.getMessage());
        }
        return line;
    }

    private void write(DataOutputStream output, String message) {
        try {
            output.writeUTF(message);
        } catch (IOException ex) {
            log("write : " + ex.getMessage());
        }
    }

    private void closeStreams() {
        this.isLosggedIn = false;
        try {
            this.input.close();
            this.output.close();
        } catch (IOException ex) {
            log("closeStreams : " + ex.getMessage());
        }
    }

    private void closeSocket() {
        this.isLosggedIn = false;
        try {
            socket.close();
        } catch (IOException ex) {
            log("closeSocket : " + ex.getMessage());
        }
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}

class FindWordManager {
    public static boolean checkWord(String wordToFind, String userWord) {
        return wordToFind.equals(userWord);
    }

    public static String convertToAsterisks(String wordToFind, String userWord) {
        wordToFind = wordToFind.trim();
        userWord = userWord.trim();
        if (!wordToFind.equals(userWord)) {
            return "*".repeat(wordToFind.length());
        }
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < wordToFind.length(); i++) {
            if (wordToFind.charAt(i) == userWord.charAt(i)) toReturn.append(wordToFind.charAt(i));
            else toReturn.append("*");
        }
        return toReturn.toString();
    }
}